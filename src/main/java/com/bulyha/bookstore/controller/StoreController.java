package com.bulyha.bookstore.controller;

import com.bulyha.bookstore.dto.StoreDto;
import com.bulyha.bookstore.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/v1/store")
@RestController
public class StoreController {
    private final StoreService storeService;
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreDto> addStore(@RequestBody StoreDto storeDto){
        StoreDto savedStore = storeService.addStore(storeDto);
        return new ResponseEntity<>(savedStore, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStore(@PathVariable("id") Long storeId){
        StoreDto storeDto = storeService.getStore(storeId);
        return ResponseEntity.ok(storeDto);
    }

    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStores(){
        return ResponseEntity.ok(storeService.getAllStores());
    }


    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> updateStore(@RequestBody StoreDto orderDto,
                                                @PathVariable("id") Long orderId){
        return ResponseEntity.ok(storeService.updateStore(orderDto, orderId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable("id") Long orderId){
        storeService.deleteStore(orderId);
        return ResponseEntity.ok("Store deleted successfully!.");
    }

}
