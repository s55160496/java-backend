package com.example.backend.api;

import com.example.backend.businesslogic.ProductBusiness;
import com.example.backend.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductApi {
    private final ProductBusiness business;

    public ProductApi(ProductBusiness business) {
        this.business = business;
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String id) throws BaseException {
        String response = business.GetProductById(id);
        return ResponseEntity.ok(id);
    }
}
