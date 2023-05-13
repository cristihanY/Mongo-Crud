package com.mongo.crud.CRUD.controller;

import com.mongo.crud.CRUD.dto.ProductDTO;
import com.mongo.crud.CRUD.entity.Product;
import com.mongo.crud.CRUD.service.ProductService;
import com.mongo.crud.global.dto.MessageDto;
import com.mongo.crud.global.exceptions.AttibuteException;
import com.mongo.crud.global.exceptions.ResourceNotFountException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


   private final ProductService productService;

   @Autowired
   public ProductController(ProductService productService){
       this.productService= productService;
   }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") int id) throws ResourceNotFountException {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MessageDto> save(@Valid @RequestBody ProductDTO dto) throws AttibuteException {

       Product product = productService.save(dto);
       String message =  "Product " + product.getName() +" have ben saved";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> update(@Valid @PathVariable("id") int id,
                                          @RequestBody ProductDTO dto)
            throws ResourceNotFountException, AttibuteException {
        Product product = productService.update(id, dto);
        String message =  "Product " + product.getName() +" have ben updated";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id)
            throws ResourceNotFountException {
         Product product = productService.delete(id);
        String message =  "Product " + product.getName() +" have ben deleted";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }
}
