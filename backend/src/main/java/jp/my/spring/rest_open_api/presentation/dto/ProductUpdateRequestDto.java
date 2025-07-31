package jp.my.spring.rest_open_api.presentation.dto;

import java.util.Map;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 商品マスタ更新リクエストDTO。
 *
 * <p>
 * 属性名（キー）は、事前に登録された属性の name に対応する。<br>
 * 属性値（値）は文字列で渡される。属性の型に基づき、サーバー側で型変換を行う。
 * </p>
 *
 * <p>
 * 使用例：
 * </p>
 * 
 * <pre>{@code
 * {
 *   "id": 1,
 *   "name": "商品A-更新",
 *   "widthMm": 120,
 *   "productAttributes": {
 *     "color": "blue",
 *     "brand": "UpdatedBrand",
 *     "widthMm": "120"
 *   }
 * }
 * }</pre>
 * </p>
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
public record ProductUpdateRequestDto(
        @NotNull Long id,
        @NotBlank @Size(max = 255) String name,
        Integer widthMm,
        Map<String, String> productAttributes) {
}