const API_BASE = '/api/attributes';

export const fetchAttributes = async () => {
    const res = await fetch(API_BASE);
    return handleJsonResponse(res, 'Failed to fetch attributes');
};

export const createAttribute = async (payload) => {
    const res = await fetch(API_BASE, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });
    return handleJsonResponse(res, 'Failed to create attributes');
};

export const updateAttribute = async (id, payload) => {
    const res = await fetch(`${API_BASE}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    });
    return handleJsonResponse(res, 'Failed to update attributes');
};

const handleJsonResponse = async (res, defaultMessage) => {
    if (!res.ok) {
        const errorJson = await res.json().catch(() => null);
        const message = errorJson?.message || defaultMessage;
        throw new Error(message);
    }
    return res.json();
};