package jp.my.spring.rest_open_api.presentation.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 商品マスタ登録リクエストDTO。
 *
 * <p>
 * 属性 {@code productAttributes} のキーは、
 * あらかじめ登録された {@code Attribute} の {@code name} に対応している必要がある。
 * </p>
 * <p>
 * 属性 {@code productAttributes} の値は、
 * すべて文字列で渡される。
 * サーバー側では、
 * あらかじめ登録された {@code Attribute} の {@code type} に基づいて型変換を行う。
 * </p>
 *
 * <p>
 * 使用例：
 * </p>
 * 
 * <pre>{@code
 * {
 *   "name": "商品A",
 *   "widthMm": 100,
 *   "productAttributes": {
 *     "color": "red",
 *     "brand": "SomeBrand"
 *     "widthMm": "100"
 *   }
 * }
 * }</pre>
 *
 * <p>
 * 注意事項:
 * </p>
 * <ul>
 * <li>
 * {@code widthMm} は商品本体が保持する属性であり、
 * {@code productAttributes}
 * とは独立（optional）。
 * </li>
 * <li>
 * {@code widthMm} は、
 * 検証・比較の目的でフラットテーブル上のフィールドとしても、
 * EAV モデル上の属性としても保持。
 * </li>
 * <li>
 * {@code productAttributes} は optional。
 * </li>
 * </ul>
 */
public record ProductCreateRequestDto(
        @NotBlank @Size(max = 255) String name,
        Integer widthMm,
        Map<String, String> productAttributes) {
}