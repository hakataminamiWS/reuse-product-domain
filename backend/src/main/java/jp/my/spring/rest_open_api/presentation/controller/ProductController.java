package jp.my.spring.rest_open_api.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import jp.my.spring.rest_open_api.application.ProductService;
import jp.my.spring.rest_open_api.presentation.dto.ProductResponseDto;
import jp.my.spring.rest_open_api.presentation.dto.ProductUpdateRequestDto;
import jp.my.spring.rest_open_api.presentation.dto.ProductCreateRequestDto;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(
            @RequestBody ProductCreateRequestDto dto) {

        ProductResponseDto responseDto = productService.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.id())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(responseDto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequestDto dto) {

        if (!id.equals(dto.id())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        ProductResponseDto responseDto = productService.update(dto);
        return ResponseEntity
                .ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> list() {
        List<ProductResponseDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

}
