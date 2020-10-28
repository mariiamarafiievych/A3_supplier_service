package supplier.services;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supplier.entities.Item;
import supplier.entities.Storage;
import supplier.entities.Supplier;
import supplier.repo.StorageRepository;
import supplier.repo.SupplierRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, StorageRepository storageRepository){
        this.supplierRepository = supplierRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public Supplier findSupplierByName(String firstName, String lastName){
        return supplierRepository.findSupplierByName(firstName, lastName);
    }


    @Transactional
    public void addItemsToStorage(Supplier supplier, List<Item> toStorage, List<Integer> itemQuantities){
        supplierRepository.save(supplier);

        for(int i = 0; i < toStorage.size(); i++){
            Item itemTemp = toStorage.get(i);
            int itemQTemp = itemQuantities.get(i);
            Storage existsStorageItem = storageRepository.findByItem(itemTemp);

            if(existsStorageItem == null){
                Storage storageItem = new Storage();
                storageItem.setId(UUID.randomUUID());
                storageItem.setItem(itemTemp);
                storageItem.setQuantity(itemQTemp);
                storageRepository.save(storageItem);
            }else {
                int itemQuantity = existsStorageItem.getQuantity();
                int newItemQuantity = itemQuantity + itemQTemp;
                existsStorageItem.setQuantity(newItemQuantity);
                storageRepository.save(existsStorageItem);
            }

        }
    }

    @Transactional
    public List<Supplier> getSuppliers(){
        return supplierRepository.findAll();
    }

    @Transactional
    public Supplier getSupplierById(UUID id) throws NotFoundException {
        Optional<Supplier> tempSupplier = supplierRepository.findById(id);
        if (tempSupplier.isPresent())
            return tempSupplier.get();
        else
            throw new NotFoundException(String.format("Seller with id %s was not found", id));
    }
}
