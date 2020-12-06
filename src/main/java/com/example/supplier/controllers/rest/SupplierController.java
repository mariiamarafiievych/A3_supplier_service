package com.example.supplier.controllers.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.supplier.entities.Item;
import com.example.supplier.entities.Supplier;
import com.example.supplier.entities.dto.SupplyDTO;
import com.example.supplier.repo.SupplierRepository;
import com.example.supplier.services.ItemService;
import com.example.supplier.services.SupplierService;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("suppliers")
public class SupplierController {
    private final SupplierService supplierService;
    private final ItemService itemService;
    private final SupplierRepository supplierRepo;

    @Autowired
    public SupplierController(SupplierService supplierService, ItemService itemService, SupplierRepository supplierRepo) {
        this.supplierService = supplierService;
        this.itemService = itemService;
        this.supplierRepo = supplierRepo;
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> show() {
        return ResponseEntity.ok(supplierService.getSuppliers());
    }

    @GetMapping("{id}")
    public ResponseEntity<Supplier> showById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }


    @PostMapping
    public ResponseEntity<Void> ItemsFromSupplier(@RequestBody String serveJson){
        System.out.println("serveJson !!!!!!!!!!!!" +serveJson);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        SupplyDTO serve = gson.fromJson(serveJson, SupplyDTO.class);
        System.out.println("serve!!!!!!!!!!!"+serve);
        List<Item> toStorage = serve.getItems();
        System.out.println("toStorage !!!!!!!!!!"+toStorage);
        List<Integer> itemQuantities = serve.getItemQuantities();
        System.out.println("itemQuantities !!!!!!!!!!"+itemQuantities);

        Supplier jsonSupplier = serve.getSupplier();
        System.out.println("jsonSupplier !!!!!!!!!!"+jsonSupplier);

        supplierRepo.save(jsonSupplier);

        Supplier supplier = supplierService.findSupplierByName(jsonSupplier.getFirstName(), jsonSupplier.getLastName());
        System.out.println("supplier !!!!!!!!!!"+supplier);
        for(Item th: toStorage){
            th.setAddedBy(supplier);
            itemService.addItem(th);
        }
        System.out.println("th !!!!!!!!!!");
        supplierService.addItemsToStorage(supplier, toStorage, itemQuantities);

        return ResponseEntity.ok().build();
    }
}
