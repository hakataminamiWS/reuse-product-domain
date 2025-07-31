package jp.my.spring.rest_open_api.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255, unique = true)
    private String name;

    // 商品幅(mm単位)
    @Column(name = "width_mm", nullable = true)
    private Integer widthMm;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductAttributeText> productAttributesText = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductAttributeInteger> productAttributesInteger = new HashSet<>();

    public Product() {
    }

    // ------------------------------
    public void update(String name, Integer widthMm) {
        this.name = name;
        this.widthMm = widthMm;
    }

    public void addProductAttributeText(ProductAttributeText pa) {
        productAttributesText.add(pa);
        pa.setProduct(this);
    }

    public void addProductAttributeInteger(ProductAttributeInteger pa) {
        productAttributesInteger.add(pa);
        pa.setProduct(this);
    }

    public void clearProductAttributes() {
        for (ProductAttributeText pat : productAttributesText) {
            pat.setProduct(null);
        }
        productAttributesText.clear();

        for (ProductAttributeInteger pai : productAttributesInteger) {
            pai.setProduct(null);
        }
        productAttributesInteger.clear();
    }
    // ------------------------------

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getWidthMm() {
        return widthMm;
    }

    public Set<ProductAttributeText> getProductAttributesText() {
        return productAttributesText;
    }

    public Set<ProductAttributeInteger> getProductAttributesInteger() {
        return productAttributesInteger;
    }
}