package com.ssg.mmm.item.controller;

import com.ssg.mmm.item.entity.Item;
import com.ssg.mmm.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/api/items")
    public ResponseEntity<?> readAll() {

        List<Item> items = itemRepository.findAll();

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
