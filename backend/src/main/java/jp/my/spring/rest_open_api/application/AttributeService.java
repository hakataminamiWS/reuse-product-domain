package jp.my.spring.rest_open_api.application;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jp.my.spring.rest_open_api.application.exception.UniqueConstraintViolationException;
import jp.my.spring.rest_open_api.domain.model.Attribute;
import jp.my.spring.rest_open_api.domain.repository.AttributeRepository;
import jp.my.spring.rest_open_api.presentation.dto.AttributeCreateRequestDto;
import jp.my.spring.rest_open_api.presentation.dto.AttributeResponseDto;
import jp.my.spring.rest_open_api.presentation.dto.AttributeUpdateRequestDto;

/**
 * Attribute の登録、変更を行う。直接DBに登録、変更を行うもの。
 * 
 * <p>
 * 今後の検討内容
 * </p>
 * <ul>
 * <li>更新・削除を行うか？商品に割り当たっているなら、更新・削除は不整合のもと
 * <li>そもそも削除というオペレーションがあるか？（オペミスの場合は、使用していないことの確認）
 * <li>Magenta というEC 用の OSS でも EAV を採用しているよう。
 * <li>属性は使用されているので削除できません。のメッセージを見かけた
 * </ul>
 * 
 * @throws UniqueConstraintViolationException Attribute name の Unique 制約違反
 * 
 */
@Service
@Transactional
public class AttributeService {

    private final AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public AttributeResponseDto create(AttributeCreateRequestDto dto) {
        boolean existingNameAlready = attributeRepository.existsByName(dto.name());
        if (existingNameAlready) {
            throw new UniqueConstraintViolationException(
                    "Attribute name already exists: " + dto.name());
        }

        Attribute newAttribute = new Attribute(dto.name(), dto.type());

        try {
            Attribute created = attributeRepository.save(newAttribute);
            return AttributeResponseDto.fromEntity(created);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new UniqueConstraintViolationException(
                        "Attribute name already exists: " + newAttribute.getName(),
                        e);
            }
            throw e;
        }
    }

    public AttributeResponseDto update(AttributeUpdateRequestDto dto) {
        Attribute existingAttribute = attributeRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found: " + dto.id()));

        boolean existingNameAlready = attributeRepository.existsByNameAndIdNot(dto.name(), dto.id());
        if (existingNameAlready) {
            throw new UniqueConstraintViolationException(
                    "Attribute name already exists: " + dto.name());
        }

        existingAttribute.update(dto.name(), dto.type());

        try {
            Attribute updated = attributeRepository.save(existingAttribute);
            return AttributeResponseDto.fromEntity(updated);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new UniqueConstraintViolationException(
                        "Attribute name already exists: " + existingAttribute.getName(),
                        e);
            }
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<AttributeResponseDto> findAll() {
        return attributeRepository.findAll().stream()
                .map(AttributeResponseDto::fromEntity)
                .toList();
    }
}