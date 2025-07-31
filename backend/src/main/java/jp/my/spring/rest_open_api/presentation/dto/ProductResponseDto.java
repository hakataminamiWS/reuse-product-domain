package jp.my.spring.rest_open_api.presentation.dto;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jp.my.spring.rest_open_api.domain.model.Product;
import jp.my.spring.rest_open_api.domain.model.ProductAttributeInteger;
import jp.my.spring.rest_open_api.domain.model.ProductAttributeText;

public record ProductResponseDto(
        @NotNull Long id,
        @NotBlank String name,
        Integer widthMm,
        Map<String, String> productAttributes) {

    public static ProductResponseDto fromEntity(Product product) {
        Map<String, String> attributesMap = new HashMap<>();

        for (ProductAttributeText pat : product.getProductAttributesText()) {
            attributesMap.put(pat.getAttribute().getName(), pat.getValue());
        }

        for (ProductAttributeInteger pai : product.getProductAttributesInteger()) {
            attributesMap.put(pai.getAttribute().getName(), String.valueOf(pai.getValue()));
        }

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getWidthMm(),
                attributesMap);
    }
}
