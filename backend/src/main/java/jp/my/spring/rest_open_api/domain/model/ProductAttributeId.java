package jp.my.spring.rest_open_api.domain.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductAttributeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "attribute_id")
    private Long attributeId;

    protected ProductAttributeId() {
    }

    public ProductAttributeId(Long productId, Long attributeId) {
        this.productId = productId;
        this.attributeId = attributeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ProductAttributeId that = (ProductAttributeId) obj;
        if (this.productId == null || this.attributeId == null) {
            return false;
        }
        return Objects.equals(productId, that.productId) &&
                Objects.equals(attributeId, that.attributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, attributeId);
    }

    public Long getProductId() {
        return productId;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    protected void setProductId(Long productId) {
        this.productId = productId;
    }

    protected void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

}
