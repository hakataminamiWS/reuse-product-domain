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
import jp.my.spring.rest_open_api.application.AttributeService;
import jp.my.spring.rest_open_api.presentation.dto.AttributeCreateRequestDto;
import jp.my.spring.rest_open_api.presentation.dto.AttributeResponseDto;
import jp.my.spring.rest_open_api.presentation.dto.AttributeUpdateRequestDto;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping
    public ResponseEntity<AttributeResponseDto> create(
            @Valid @RequestBody AttributeCreateRequestDto dto) {

        AttributeResponseDto responseDto = attributeService.create(dto);
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
    public ResponseEntity<AttributeResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody AttributeUpdateRequestDto dto) {

        if (!id.equals(dto.id())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        AttributeResponseDto responseDto = attributeService.update(dto);
        return ResponseEntity
                .ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<AttributeResponseDto>> list() {

        List<AttributeResponseDto> attributes = attributeService.findAll();
        return ResponseEntity
                .ok(attributes);
    }

}
