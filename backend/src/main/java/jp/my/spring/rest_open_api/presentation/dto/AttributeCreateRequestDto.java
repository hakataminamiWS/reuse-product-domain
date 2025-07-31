package jp.my.spring.rest_open_api.presentation.dto;

import jp.my.spring.rest_open_api.domain.model.AttributeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 属性マスタ登録リクエストDTO。
 *
 * <p>
 * {@code name} は属性名を表し、
 * 商品登録時の {@code productAttributes} のキーとして使用される。
 * </p>
 * <p>
 * {@code type} は属性値のデータ型を表す列挙型で、
 * {@link AttributeType} とリンク。
 * </p>
 *
 * <p>
 * 使用例：
 * </p>
 *
 * <pre>{@code
 * {
 *   "name": "color",
 *   "type": "text"
 * }
 * }</pre>
 *
 * <p>
 * 注意事項:
 * </p>
 * <ul>
 * <li>
 * {@code name} は重複不可。ユニークである必要がある。
 * </li>
 * <li>
 * {@code type} は{@link AttributeType} を指定する。
 * </li>
 * </ul>
 */
public record AttributeCreateRequestDto(
        @NotBlank @Size(max = 255) String name,
        @NotNull AttributeType type) {
}