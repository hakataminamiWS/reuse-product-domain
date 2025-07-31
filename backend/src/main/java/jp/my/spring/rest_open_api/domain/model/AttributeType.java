package jp.my.spring.rest_open_api.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AttributeType {
    TEXT,
    INTEGER;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static AttributeType from(String value) {
        return AttributeType.valueOf(value.toUpperCase());
    }
}