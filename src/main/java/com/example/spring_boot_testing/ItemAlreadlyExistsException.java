package com.example.spring_boot_testing;

public class ItemAlreadlyExistsException extends RuntimeException{

    public ItemAlreadlyExistsException(String message) {
        super(message);
    }
}
