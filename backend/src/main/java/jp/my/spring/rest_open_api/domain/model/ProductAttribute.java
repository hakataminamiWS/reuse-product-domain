package jp.my.spring.rest_open_api.domain.model;

import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.MapsId;

@MappedSuperclass
// public class ProductAttribute {
public abstract class ProductAttribute<T> {

    @EmbeddedId
    private ProductAttributeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    public abstract T getValue();

    public abstract void setValue(T value);

    protected ProductAttribute() {
    }

    public ProductAttribute(Product product, Attribute attribute) {
        this.product = product;
        this.attribute = attribute;
        this.id = new ProductAttributeId(product.getId(), attribute.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ProductAttribute<?> other = (ProductAttribute<?>) obj;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    protected void setProduct(Product product) {
        this.product = product;

        if (this.id == null)
            this.id = new ProductAttributeId();
        this.id.setProductId(product != null ? product.getId() : null);
    }

    protected void setAttribute(Attribute attribute) {
        this.attribute = attribute;
        if (this.id == null)
            this.id = new ProductAttributeId();
        this.id.setAttributeId(attribute.getId());
    }

    public ProductAttributeId getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Attribute getAttribute() {
        return attribute;
    }
}
