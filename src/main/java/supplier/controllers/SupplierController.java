package supplier.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supplier.entities.Item;
import supplier.entities.Supplier;
import supplier.entities.dto.SupplyDTO;
import supplier.services.ItemService;
import supplier.services.SupplierService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("suppliers")
public class SupplierController {
    private final SupplierService supplierService;
    private final ItemService itemService;

    @Autowired
    public SupplierController(SupplierService supplierService, ItemService itemService) {
        this.supplierService = supplierService;
        this.itemService = itemService;
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

        Supplier jsonSupplier = serve.getSupplier();
        Supplier supplier = supplierService.findSupplierByName(jsonSupplier.getFirstName(), jsonSupplier.getLastName());
        for(Item th: toStorage){
            th.setAddedBy(supplier);
            itemService.addItem(th);
        }
        supplierService.addItemsToStorage(supplier, toStorage, itemQuantities);

        return ResponseEntity.ok().build();
    }
}
