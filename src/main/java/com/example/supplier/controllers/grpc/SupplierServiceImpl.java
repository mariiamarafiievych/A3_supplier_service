package com.example.supplier.controllers.grpc;

import com.example.seller.*;
import com.example.supplier.entities.Item;
import com.example.supplier.entities.Supplier;
import com.example.supplier.entities.dto.SupplyDTO;
import com.example.supplier.services.ItemService;
import com.example.supplier.services.SupplierService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class SupplierServiceImpl extends supplierServiceGrpc.supplierServiceImplBase {
    private final SupplierService supplierService;
    private final ItemService itemService;

    @Autowired
    public SupplierServiceImpl(SupplierService supplierService, ItemService itemService) {
        this.supplierService = supplierService;
        this.itemService = itemService;
    }


    @Override
    public void getSuppliers(GetRequestSupplier request, StreamObserver<GetResponseSupplier> responseStreamObserver) {
        List<Supplier> suppliers = supplierService.getSuppliers();

        List<ProtoSupplier> protoSuppliers = new ArrayList<>();
        for (Supplier supplier: suppliers) {
            ProtoSupplier protoSupplier = ProtoSupplier.newBuilder()
                    .setFirstName(supplier.getFirstName())
                    .setLastName(supplier.getLastName())
                    .build();
            protoSuppliers.add(protoSupplier);
        }
        GetResponseSupplier response = GetResponseSupplier.newBuilder().addAllSuppliers(protoSuppliers).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void getItems(GetRequestItem request, StreamObserver<GetResponseItem> responseStreamObserver) {
        List<Item> items = itemService.getItems();

        List<ProtoItem> protoItems = new ArrayList<>();
        for (Item item: items) {
            ProtoItem protoItem = ProtoItem.newBuilder()
                    .setName(item.getName())
                    .setPrice(item.getPrice())
                    .build();
            protoItems.add(protoItem);
        }
        GetResponseItem response = GetResponseItem.newBuilder().addAllItems(protoItems).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseStreamObserver){
        String serveJson = request.getServeJson();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        SupplyDTO supply = gson.fromJson(serveJson, SupplyDTO.class);
        List<Item> toStorage = supply.getItems();
        List<Integer> itemQuantities = supply.getItemQuantities();
        Supplier jsonSupplier = supply.getSupplier();
        supplierService.save(jsonSupplier);

        Supplier supplier = supplierService.findSupplierByName(jsonSupplier.getFirstName(), jsonSupplier.getLastName());
        for(Item th: toStorage){
            th.setAddedBy(supplier);
            itemService.addItem(th);
        }
        supplierService.addItemsToStorage(supplier, toStorage, itemQuantities);

        CreateResponse response = CreateResponse.newBuilder()
                .build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

}
