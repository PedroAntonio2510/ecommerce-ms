package com.ms.ecommerce.controller;

import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.model.dtos.ProductRequestDTO;
import com.ms.ecommerce.model.dtos.ProductResponseDTO;
import com.ms.ecommerce.model.mapper.ProductMapper;
import com.ms.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController implements GenericController{

    @Autowired
    private ProductService service;

    @Autowired
    private ProductMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> save(@RequestBody @Valid ProductRequestDTO dto){
        Product product = mapper.toEntity(dto);
        URI uri = headerLocation(product.getId());
        service.saveProduct(product);
        return ResponseEntity.created(uri).build();
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'CLIENT')")
    public ResponseEntity<List<ProductResponseDTO>> search(@RequestParam(value = "name", required = false) String name,
                                                           @RequestParam(value = "price", required = false)BigDecimal price){
        List<Product> listProducts = service.searchProducts(name, price);
        List<ProductResponseDTO> productResponse = listProducts.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'CLIENT')")
    public ResponseEntity<ProductResponseDTO> getProductDetails(@PathVariable String id) {
        var productId = UUID.fromString(id);

        return service.getProductById(productId)
                .map(product -> {
                    ProductResponseDTO dto = mapper.toDTO(product);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid ProductRequestDTO dto) {
        return service.getProductById(UUID.fromString(id))
                .map(product -> {
                    Product newProduct = mapper.toEntity(dto);
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setQuantity(newProduct.getQuantity());

                    service.saveProduct(product);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        var idProduct = UUID.fromString(id);
        Optional<Product> productOptional = service.getProductById(idProduct);

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteProduct(productOptional.get());
        return ResponseEntity.noContent().build();
    }
}
