-- ===========================
-- データスキーマの設計
-- ===========================
-- 前提
--   商品マスタとは、商品を区別し、属性（ブランド、色、サイズ、年代等）を管理するマスタとする。
--   リユース系に限らず、多種を扱う業務の商品マスタは、
--   シングルテーブルにて属性を列管理する場合、
--   多くの属性値がNULLとなり検索の際に注意が必要になる。
--   または、入力しない属性が管理画面に表示され、メンテナンス業務の負荷が高まる。
--   加えて、属性追加時にシステム変更が伴い、運用負荷も高まる。
--   だからといって、EAV(Entity-Attribute-Value)は、
--   SQLアンチパターンと呼ばれ、JOIN が複雑になったり、
--   クエリパフォーマンスが低下するといった意見がある。
-- 
-- 目的
--   商品マスタの属性のうち、数値型属性（例: width_mm）について
--     1. シングルテーブル
--     2. データ型ごと属性テーブル（product_attribute_integer）
--   のように定義した場合の、実装方法や変更時の修正箇所を確かめるもの。
--   つまり、商品マスタの設計において、ひとつの知見を得る目的です。

-- 商品マスタ
CREATE TABLE products (
    id BIGSERIAL,
    name VARCHAR(255) NOT NULL UNIQUE,
    -- 商品幅(単位mm)
    width_mm INTEGER,
    -- 全文検索用。性能検証のとき有効に、あるいは別テーブル？
    -- searchable_attributes_text TEXT,

    PRIMARY KEY (id)
);

-- 全文検索用。性能検証のときに有効に
-- CREATE INDEX idx_products_searchable_bigram
--   ON products USING gin (searchable_attributes_text gin_bigm_ops);

-- 属性マスタ
CREATE TABLE attributes (
    id BIGSERIAL,
    name VARCHAR(255) NOT NULL UNIQUE,
    -- 属性値がもつDB上の型を指定する。範囲検索（商品幅 <= 500mm）などができるように。
    type VARCHAR(64) NOT NULL,

    PRIMARY KEY (id)
);

-- 商品ごとの属性値。テキスト用
CREATE TABLE products_attributes_text (
    product_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    value TEXT NOT NULL,

    PRIMARY KEY (product_id, attribute_id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (attribute_id) REFERENCES attributes(id) ON DELETE CASCADE
);

-- 商品ごとの属性値。INTEGER用
CREATE TABLE products_attributes_integer (
    product_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    value INTEGER NOT NULL,

    PRIMARY KEY (product_id, attribute_id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (attribute_id) REFERENCES attributes(id) ON DELETE CASCADE
);