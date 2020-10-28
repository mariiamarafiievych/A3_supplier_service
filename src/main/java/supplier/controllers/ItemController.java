package supplier.controllers;


import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supplier.entities.Item;
import supplier.entities.Storage;
import supplier.entities.dto.ItemDTO;
import supplier.services.ItemService;
import supplier.services.StorageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;
    private final StorageService storageService;

    @Autowired
    public ItemController(ItemService itemService, StorageService storageService) {
        this.itemService = itemService;
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> show() {
        return ResponseEntity.ok(itemService.getItems());
    }

    @GetMapping("{id}")
    public ResponseEntity<Item> showById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PutMapping(value = "/newQuantity")
    public void setNewQuantity(@RequestBody ItemDTO itemDTO){
        Item item = itemService.findItemByName(itemDTO.getName());
        Storage storageItem = storageService.findByItem(item);
        storageItem.setQuantity(itemDTO.getQuantity());
        storageService.saveStorageItem(storageItem);
    }

    @GetMapping(value = "/name={name}")
    public @ResponseBody
    ItemDTO getItemByName(@PathVariable String name){
        Item item = itemService.findItemByName(name);
        Storage storageItem = storageService.findByItem(item);

        return new ItemDTO(item.getId(), item.getName(), item.getPrice(), storageItem.getQuantity());
    }


}
