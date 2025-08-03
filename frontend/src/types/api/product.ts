type ProductAttributeMap = Map<string, string>;

type Product = {
  id: number;
  name: string;
  widthMm?: number | null;
  productAttributes: ProductAttributeMap;
};

type CreateProductPayload = Omit<Product, 'id'>;
type UpdateProductPayload = Product;

export type { Product, CreateProductPayload, UpdateProductPayload };
