package com.example.spring_boot_testing;

public class ItemNotFoundException extends RuntimeException{
    
    public ItemNotFoundException(String message) {
        super(message);
    }
}
