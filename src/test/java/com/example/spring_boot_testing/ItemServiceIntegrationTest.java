package com.example.spring_boot_testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemServiceIntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService.saveNewItem("car", 100);
        itemService.saveNewItem("banana", 25);
        itemService.saveNewItem("beans", 3);
    }

    @Test
    void getAllItems_ShouldReturnAllItems() {
        List<Item> items = itemService.getAllItems();
        assertNotNull(items);
        assertEquals(items.size(), 3);
    }

    @Test
    void saveNewItem_ShouldPersistItemInDatabase() {
        itemService.saveNewItem("mango", 54);
        var itemFound = itemRepository.findByName("mango");

        assertTrue(itemFound.isPresent());
        assertEquals(itemFound.get().getPrice(), 54);
    }

    @Test
    void getItem_ShouldReturnItemFromDatabse_whenFound() {
        String itemName = "car";
        Optional<Item> itemFound = itemService.getItem(itemName);

        assertTrue(itemFound.isPresent());
    }

    @Test
    void getItem_ShouldThrowItemNotFoundException_whenNotFound() {
        String itemName = "mango";
        assertThrows(ItemNotFoundException.class, () -> itemService.getItem(itemName));
    }

    @Test
    void updateItem_ShouldUpdateDataInDatabase_WhenItemExists() {
        double itemPrice = 48;
        Item itemUpdated= itemService.updateItem("beans", itemPrice);
        assertEquals(itemUpdated.getPrice(), itemPrice);
    }

    @Test
    void updateItem_ShouldThrowItemNotFoundException_WhenItemDoesNotExists() {
        String itemName = "tree";
        assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(itemName, 48));
    }

    @Test
    void deleteItem_ShouldDeleteItemFromDatabase_WhenFound() {
        String itemName = "beans";
        itemService.deleteItem(itemName);
    }

    @Test
    void deleteItem_ShouldThrowItemNotFoundException_WhenNotFound() {
        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem("mongo"));
    }
    
}
