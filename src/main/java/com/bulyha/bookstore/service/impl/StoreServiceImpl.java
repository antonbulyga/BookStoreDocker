package com.bulyha.bookstore.service.impl;

import com.bulyha.bookstore.dto.StoreDto;
import com.bulyha.bookstore.exception.ResourceNotFoundException;
import com.bulyha.bookstore.model.Store;
import com.bulyha.bookstore.repository.StoreRepository;
import com.bulyha.bookstore.service.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {
    private final ModelMapper modelMapper;
    private final StoreRepository storeRepository;

    public StoreServiceImpl(ModelMapper modelMapper, StoreRepository storeRepository) {
        this.modelMapper = modelMapper;
        this.storeRepository = storeRepository;
    }

    @Override
    public StoreDto addStore(StoreDto storeDto) {
        Store store = modelMapper.map(storeDto, Store.class);
        Store savedStore = storeRepository.save(store);
        return modelMapper.map(savedStore, StoreDto.class);
    }

    @Override
    public StoreDto getStore(Long storeId) {
        Optional<Store> store = Optional.ofNullable(storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store", "Id", storeId)));
        return modelMapper.map(store.get(), StoreDto.class);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(o -> modelMapper.map(o, StoreDto.class)).toList();
    }

    @Override
    public StoreDto updateStore(StoreDto storeDto, Long storeId) {
        Optional<Store> store = Optional.ofNullable(storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store", "Id", storeId)));
        store.ifPresent(s -> s.setId(s.getId()));
        store.ifPresent(o -> o.setName(storeDto.getName()));
        storeRepository.save(store.get());
        return modelMapper.map(store.get(), StoreDto.class);
    }

    @Override
    public void deleteStore(Long storeId) {
        if (storeRepository.findById(storeId).isPresent()) {
            storeRepository.deleteById(storeId);
        } else {
            throw new ResourceNotFoundException("We don't have such store in the database");
        }

    }
}
