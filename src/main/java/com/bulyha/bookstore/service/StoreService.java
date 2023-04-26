package com.bulyha.bookstore.service;

import com.bulyha.bookstore.dto.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto addStore(StoreDto storeDto);

    StoreDto getStore(Long storeId);

    List<StoreDto> getAllStores();

    StoreDto updateStore(StoreDto orderDto, Long storeId);

    void deleteStore(Long storeId);
}
