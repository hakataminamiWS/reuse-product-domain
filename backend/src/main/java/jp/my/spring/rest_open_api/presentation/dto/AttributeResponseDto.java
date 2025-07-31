package jp.my.spring.rest_open_api.presentation.dto;

import jp.my.spring.rest_open_api.domain.model.Attribute;
import jp.my.spring.rest_open_api.domain.model.AttributeType;

public record AttributeResponseDto(Long id, String name, AttributeType type) {
    public static AttributeResponseDto fromEntity(Attribute attribute) {
        return new AttributeResponseDto(
                attribute.getId(),
                attribute.getName(),
                attribute.getType());
    }
}
