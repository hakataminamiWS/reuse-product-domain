const attributeTypes = ['text', 'integer'] as const;
type AttributeType = (typeof attributeTypes)[number];

type Attribute = {
  id: number;
  name: string;
  type: AttributeType;
};

type CreateAttributePayload = Omit<Attribute, 'id'>;
type UpdateAttributePayload = Attribute;

export { attributeTypes };
export type { Attribute, AttributeType, CreateAttributePayload, UpdateAttributePayload };
