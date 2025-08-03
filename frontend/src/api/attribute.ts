import type {
  Attribute,
  CreateAttributePayload,
  UpdateAttributePayload,
} from '@/types/api/attribute';

import { handleJsonResponse } from './utils';

const API_BASE = '/api/attributes';

const fetchAttributes = async (): Promise<Attribute[]> => {
  const res = await fetch(API_BASE);
  return handleJsonResponse<Attribute[]>(res, 'Failed to fetch attributes');
};

const createAttribute = async (payload: CreateAttributePayload): Promise<Attribute> => {
  const res = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  return handleJsonResponse<Attribute>(res, 'Failed to create attributes');
};

const updateAttribute = async (payload: UpdateAttributePayload): Promise<Attribute> => {
  const res = await fetch(`${API_BASE}/${payload.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  return handleJsonResponse<Attribute>(res, 'Failed to update attributes');
};

export { fetchAttributes, createAttribute, updateAttribute };
