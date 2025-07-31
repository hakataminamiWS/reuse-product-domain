package jp.my.spring.rest_open_api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jp.my.spring.rest_open_api.domain.model.AttributeType;

/**
 * 属性マスタ更新リクエストDTO。
 * 
 * 
 * <p>
 * <strong>
 * 検討事項:
 * </strong>
 * </p>
 *
 * <ul>
 * <li>
 * 更新のオペレーションはある？
 * </li>
 * <li>
 * すでに商品に割り当てられた場合、更新（type 変更）は不整合のもと
 * </li>
 * <li>
 * 更新、削除を有効にするなら、使用されていないことの確認処理が必要
 * </li>
 * </ul>
 * 
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
 *   "id": 2,
 *   "name": "heightMm",
 *   "type": "integer"
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
public record AttributeUpdateRequestDto(
        @NotNull Long id,
        @NotBlank @Size(max = 255) String name,
        @NotNull AttributeType type) {
}
