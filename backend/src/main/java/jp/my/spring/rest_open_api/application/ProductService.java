package jp.my.spring.rest_open_api.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jp.my.spring.rest_open_api.application.exception.InvalidAttributeException;
import jp.my.spring.rest_open_api.application.exception.InvalidAttributeValueException;
import jp.my.spring.rest_open_api.application.exception.UniqueConstraintViolationException;
import jp.my.spring.rest_open_api.domain.model.Attribute;
import jp.my.spring.rest_open_api.domain.model.Product;
import jp.my.spring.rest_open_api.domain.model.ProductAttributeInteger;
import jp.my.spring.rest_open_api.domain.model.ProductAttributeText;
import jp.my.spring.rest_open_api.domain.repository.AttributeRepository;
import jp.my.spring.rest_open_api.domain.repository.ProductRepository;
import jp.my.spring.rest_open_api.presentation.dto.ProductCreateRequestDto;
import jp.my.spring.rest_open_api.presentation.dto.ProductResponseDto;
import jp.my.spring.rest_open_api.presentation.dto.ProductUpdateRequestDto;

/**
 * Product の登録、変更を行う。直接DBに登録、変更を行うもの。
 * 
 * <p>
 * 制約事項
 * </p>
 * Product がもつ Attribute は、事前に登録されている必要がある。
 * つまり、{@code save()} では、Attribute 自体は登録されない。
 * 
 * <p>
 * 今後の検討内容
 * </p>
 * <ul>
 * <li>削除を行うか？それはどのような削除？
 * <li>そもそも削除というオペレーションがあるか？（オペミス？参照データ登録後はどうする？）
 * <li>更新、つまり名前の変更や属性割り当ての更新はできそう（販売伝票等の名称が変わるのはどうするか？）
 * <li>在庫や販売の伝票に商品マスタを紐づけるか？商品マスタの snapshot を保存？
 * </ul>
 * 
 * @throws UniqueConstraintViolationException Product name の Unique 制約違反
 * 
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    public ProductService(
            ProductRepository productRepository,
            AttributeRepository attributeRepository) {
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
    }

    public ProductResponseDto create(ProductCreateRequestDto dto) {

        boolean existingNameAlready = productRepository.existsByName(dto.name());
        if (existingNameAlready) {
            throw new UniqueConstraintViolationException(
                    "Product name already exists: " + dto.name());
        }

        Map<String, Attribute> existingAttributeMap = attributeRepository.findAll().stream()
                .collect(Collectors.toMap(Attribute::getName, attr -> attr));

        Product newProduct = new Product();
        ProductService.toEntity(
                newProduct,
                existingAttributeMap,
                dto.name(),
                dto.widthMm(),
                dto.productAttributes());

        try {
            Product created = productRepository.save(newProduct);
            return ProductResponseDto.fromEntity(created);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new UniqueConstraintViolationException(
                        "Product name already exists: " + newProduct.getName(),
                        e);
            }
            throw e;
        }
    }

    public ProductResponseDto update(ProductUpdateRequestDto dto) {

        boolean existingNameAlready = productRepository
                .existsByNameAndIdNot(dto.name(), dto.id());
        if (existingNameAlready) {
            throw new UniqueConstraintViolationException(
                    "Product name already exists: " + dto.name());
        }

        // ここから managed
        Product existingProduct = productRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + dto.id()));

        Map<String, Attribute> existingAttributeMap = attributeRepository.findAll().stream()
                .collect(Collectors.toMap(Attribute::getName, attr -> attr));

        ProductService.toEntity(
                existingProduct,
                existingAttributeMap,
                dto.name(),
                dto.widthMm(),
                dto.productAttributes());

        try {
            Product updated = productRepository.save(existingProduct);
            return ProductResponseDto.fromEntity(updated);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new UniqueConstraintViolationException(
                        "Product name already exists: " + existingProduct.getName(),
                        e);
            }
            throw e;
        }
    }

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::fromEntity)
                .toList();
    }

    /**
     * DTO の情報をもとに、指定された Product エンティティを更新し、返す。
     * 
     * @param product              新規または更新対象のProduct エンティティ
     * @param existingAttributeMap 属性名をキーとする Attribute (name, type)を引けるマップ
     * @param dtoName              商品名
     * @param dtoWidthMm           商品幅（mm）
     * @param dtoProductAttributes 属性名と値
     * @return 更新済みの Product エンティティ
     * @throws InvalidAttributeException      存在しない属性名が指定された場合
     * @throws InvalidAttributeValueException 属性値が不正な場合（nullや型変換エラー）
     */
    protected static Product toEntity(
            Product product,
            Map<String, Attribute> existingAttributeMap,
            String dtoName,
            Integer dtoWidthMm,
            Map<String, String> dtoProductAttributes) {

        product.update(dtoName, dtoWidthMm);
        product.clearProductAttributes();

        if (dtoProductAttributes == null || dtoProductAttributes.isEmpty()) {
            return product;
        }

        for (Map.Entry<String, String> entry : dtoProductAttributes.entrySet()) {
            String attrName = entry.getKey();
            String attrValue = entry.getValue();

            if (attrValue == null) {
                throw new InvalidAttributeValueException(
                        "Invalid attribute value: null for attribute: "
                                + attrName);
            }

            Attribute existingAttribute = existingAttributeMap.get(attrName);
            if (existingAttribute == null) {
                throw new InvalidAttributeException(
                        "Invalid attribute: " + attrName);
            }

            switch (existingAttribute.getType()) {
                case TEXT -> {
                    ProductAttributeText attributeText = new ProductAttributeText(
                            product,
                            existingAttribute,
                            attrValue);
                    product.addProductAttributeText(attributeText);
                }
                case INTEGER -> {
                    try {
                        Integer intValue = Integer.valueOf(attrValue);
                        ProductAttributeInteger attributeInteger = new ProductAttributeInteger(
                                product,
                                existingAttribute,
                                intValue);
                        product.addProductAttributeInteger(attributeInteger);
                    } catch (NumberFormatException e) {
                        throw new InvalidAttributeValueException(
                                "Invalid attribute value: " + attrValue
                                        + " for attribute: " + attrName,
                                e);
                    }
                }
            }
        }
        return product;
    }
}