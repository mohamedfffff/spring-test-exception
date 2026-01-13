package com.example.spring_boot_testing;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1")
public class ItemController {
    
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Item> saveNewItem(@RequestParam String name, @RequestParam double price) {
        Item item = itemService.saveNewItem(name, price);
        return ResponseEntity.ok(item);
        
    }

    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestParam String name, @RequestParam double price) {
        Item item = itemService.updateItem(name, price);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteItem(@RequestParam String name) {
        itemService.deleteItem(name);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{name}")
    public ResponseEntity<Item> getItem(@PathVariable String name) {
        Item item = itemService.getItem(name);
        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> list = itemService.getAllItems();
        return ResponseEntity.ok(list);
    }
}
