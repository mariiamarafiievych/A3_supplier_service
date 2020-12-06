package com.example.supplier.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.supplier.entities.Item;
import com.example.supplier.entities.Storage;
import com.example.supplier.repo.StorageRepository;

@Service
public class StorageService {
    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Transactional
    public Storage findByItem(Item item){
        return storageRepository.findByItem(item);
    }

    @Transactional
    public void saveStorageItem(Storage storage){
        storageRepository.save(storage);
    }
}
