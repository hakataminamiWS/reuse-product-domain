import type { CreateProductPayload, Product, UpdateProductPayload } from '@/types/api/product';

import { handleJsonResponse } from './utils';

const API_BASE = '/api/products';

const fetchProducts = async (): Promise<Product[]> => {
  const res = await fetch(API_BASE);
  return handleJsonResponse(res, 'Failed to fetch products');
};

const createProduct = async (payload: CreateProductPayload): Promise<Product> => {
  const sendPayload = {
    ...payload,
    productAttributes: Object.fromEntries(payload.productAttributes),
  };

  const res = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(sendPayload),
  });
  return handleJsonResponse<Product>(res, 'Failed to create product');
};

const updateProduct = async (payload: UpdateProductPayload) => {
  const res = await fetch(`${API_BASE}/${payload.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  return handleJsonResponse<Product>(res, 'Failed to update product');
};

export { fetchProducts, createProduct, updateProduct };
