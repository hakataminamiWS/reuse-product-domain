package jp.my.spring.rest_open_api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "products_attributes_integer")
public class ProductAttributeInteger extends ProductAttribute<Integer> {

    @Column(name = "value", nullable = false)
    private Integer value;

    protected ProductAttributeInteger() {
    }

    public ProductAttributeInteger(Product product, Attribute attribute, Integer value) {
        super(product, attribute);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
