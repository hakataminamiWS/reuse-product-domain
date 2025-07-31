package jp.my.spring.rest_open_api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "products_attributes_text")
public class ProductAttributeText extends ProductAttribute<String> {

    @Column(name = "value", nullable = false)
    private String value;

    protected ProductAttributeText() {
    }

    public ProductAttributeText(Product product, Attribute attribute, String value) {
        super(product, attribute);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
