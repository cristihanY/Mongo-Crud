package com.mongo.crud.CRUD.service;

import com.mongo.crud.CRUD.dto.ProductDTO;
import com.mongo.crud.CRUD.entity.Product;
import com.mongo.crud.CRUD.repository.ProductRepository;
import com.mongo.crud.global.exceptions.AttibuteException;
import com.mongo.crud.global.exceptions.ResourceNotFountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service

public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById( int id) throws ResourceNotFountException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("NO se encontro"));
    }

    public Product save( ProductDTO dto) throws AttibuteException {

        if(productRepository.existsByName(dto.getName()))
            throw new AttibuteException("name already in use");


        int id = autoIncrement();
        Product product= new Product(id, dto.getName(), dto.getPrice());
        return productRepository.save(product);
    }

    public Product update( int id, ProductDTO dto) throws ResourceNotFountException, AttibuteException {

        Product product= productRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFountException("El producto no fue encontrado"));

        if(productRepository.existsByName(dto.getName()) &&
                productRepository.findByName(dto.getName()).get().getId() != id )
            throw new AttibuteException("name already in use");

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }

    public Product delete( int id) throws ResourceNotFountException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("No se encontro el producto"));
        productRepository.delete(product);
        return product;
    }

    //Private metodos
    private int autoIncrement() {
        List<Product> products = productRepository.findAll();
        return productRepository.findAll().isEmpty()? 1 :
                products.stream().max(Comparator.comparing(Product::getId)).get().getId()+1;
    }

}
