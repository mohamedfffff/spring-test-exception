package com.example.spring_boot_testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ItemServiceUnitTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;


    ////////////////////////////////////////////////////////////////////////
    
    @Test
    void getAllItems_ShouldReturnAllItems() {

        List<Item> mockItems = Arrays.asList(
            new Item("chips", 50),
            new Item("beans", 5),
            new Item("date", 3)
        );

        when(itemRepository.findAll()).thenReturn(mockItems);

        List<Item> foundItems = itemService.getAllItems();

        assertFalse(foundItems.isEmpty());
        assertEquals(foundItems.size(), mockItems.size());
        assertEquals(foundItems.get(0).getName(), "chips");
        assertEquals(foundItems.get(1).getPrice(), 5);

        verify(itemRepository).findAll();
    }

    /////////////////////////////////////////////////////////////////////

    @Test
    void getItem_ShouldReturnSingleItem_WhenFound() {

        String existingItemName = "chips";

        when(itemRepository.findByName(existingItemName))
            .thenReturn(Optional.of(new Item(existingItemName, 5)));

        Optional<Item> foundItem = itemService.getItem(existingItemName);

        assertTrue(foundItem.isPresent());
        assertEquals(foundItem.get().getName(), existingItemName);
        assertEquals(foundItem.get().getPrice(), 5);

        verify(itemRepository, times(1)).findByName(existingItemName);
    }

    @Test
    void getItem_ShouldThrowItemNotFoundException_WhenNotFound() {

        String NonExistingItemName = "ananas";

        when(itemRepository.findByName(NonExistingItemName))
            .thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, 
            () -> itemService.getItem(NonExistingItemName)
        );


        verify(itemRepository).findByName(NonExistingItemName);
    }

    ////////////////////////////////////////////////////////////////////////

    @Test
    void saveNewItem_ShouldReturnSavedItem() {

        Item itemToBeSaved = new Item("apple", 45);
        Item itemSaved = new Item(100L, "apple", 45);

        when(itemRepository.save(itemToBeSaved))
            .thenReturn(itemSaved);

        Item foundItem = itemService.saveNewItem(itemToBeSaved.getName(), itemToBeSaved.getPrice());

        assertNotNull(foundItem);
        assertEquals(foundItem.getId(), 100L);

        verify(itemRepository).save(itemToBeSaved);
    }

    /////////////////////////////////////////////////////////////
    
    @Test
    void updateItem_ShouldReturnUpdatedItem_WhenItemExists() {

        Long itemId = 100L;
        Item itemToBeUpdated = new Item(itemId,"apple", 45);
        Item itemDetails = new Item(null, "apple", 40);
        Item itemUpdated = new Item(itemId, "apple", 40);

        when(itemRepository.findByName(itemDetails.getName()))
            .thenReturn(Optional.of(itemToBeUpdated));
            
        when(itemRepository.save(any(Item.class)))
            .thenReturn(itemUpdated);

        Item foundItem = itemService.updateItem(itemDetails.getName(), itemDetails.getPrice());

        assertNotNull(foundItem);
        assertEquals(foundItem.getId(), itemId);
        assertEquals(foundItem.getPrice(), itemUpdated.getPrice());

        verify(itemRepository, times(1)).findByName(itemDetails.getName());
        verify(itemRepository, times(1)).save(any(Item.class));
    }
    
    /////////////////////////////////////////////////////////////
    
    @Test
    void updateItem_ShouldThrowItemNotFoundException_WhenItemDoesNotExists() {

        Item itemDetails = new Item(null, "apple", 40);

        when(itemRepository.findByName(itemDetails.getName()))
            .thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
             () -> itemService.updateItem(itemDetails.getName(), itemDetails.getPrice())
        );

        verify(itemRepository, never()).save(any(Item.class));
    }

    //////////////////////////////////////////////////////////////////////////////////
    
    @Test
    void deleteItem_ShouldDeleteItem_WhenFound() {

        Item itemToBeDeleted = new Item("apple", 45);

        when(itemRepository.findByName(itemToBeDeleted.getName()))
            .thenReturn(Optional.of(itemToBeDeleted));

        itemService.deleteItem(itemToBeDeleted.getName());

        verify(itemRepository, times(1)).findByName(itemToBeDeleted.getName());
        verify(itemRepository, times(1)).deleteByName(itemToBeDeleted.getName());
    }

    @Test
    void deleteItem_ShouldThrowItemNotFoundException_WhenNotFound() {

        String itemName = "ananas";

        when(itemRepository.findByName(itemName))
            .thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, 
            () -> itemService.deleteItem(itemName)
        );


        verify(itemRepository, times(1)).findByName(itemName);
        verify(itemRepository, never()).deleteByName(itemName);
    }
}
