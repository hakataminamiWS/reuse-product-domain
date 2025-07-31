const API_BASE = '/api/products';

export const fetchProducts = async () => {
    const res = await fetch(API_BASE);
    return handleJsonResponse(res, 'Failed to fetch products');
};

export const createProduct = async (payload) => {
    const res = await fetch(API_BASE, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });
    return handleJsonResponse(res, 'Failed to create product');
};

export const updateProduct = async (id, payload) => {
    const res = await fetch(`${API_BASE}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });
    return handleJsonResponse(res, 'Failed to update product');
};

const handleJsonResponse = async (res, defaultMessage) => {
    if (!res.ok) {
        const errorJson = await res.json().catch(() => null);
        const message = errorJson?.message || defaultMessage;
        throw new Error(message);
    }
    return res.json();
};