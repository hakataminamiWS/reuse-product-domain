<script setup lang="ts">
import { reactive } from 'vue';

import { createProduct } from '@/api/product';
import type { CreateProductPayload } from '@/types/api/product';

const emit = defineEmits<{
    (e: 'created'): void;
}>();

type ProductFormState = {
    name: string;
    widthMm: number | null;
    productAttributesArray: {
        key: string;
        value: string;
    }[];
};

const form = reactive<ProductFormState>({
    name: '',
    widthMm: 0,
    productAttributesArray: [],
});

function addAttribute() {
    form.productAttributesArray.push({ key: '', value: '' });
}
function removeAttribute(index: number) {
    form.productAttributesArray.splice(index, 1);
}

async function onSubmit() {
    try {
        const productAttributes = new Map<string, string>();

        for (const { key, value } of form.productAttributesArray) {
            const k = key.trim();
            if (!k) continue;

            if (productAttributes.has(k)) {
                throw new Error(`属性のキー "${k}" が重複しています。`);
            }
            productAttributes.set(k, value);
        }

        const payload: CreateProductPayload = {
            name: form.name,
            widthMm: form.widthMm,
            productAttributes,
        };

        await createProduct(payload);
        emit('created');
        form.name = '';
        form.widthMm = 0;
        form.productAttributesArray = [];
    } catch (error) {
        const message = error instanceof Error ? error.message : String(error);
        alert('登録に失敗しました: ' + message);
    }
}
</script>

<template>
    <form @submit.prevent="onSubmit">
        <div>
            <label for="name">Name:</label>
            <input id="name" v-model="form.name" required />
        </div>
        <div>
            <label for="widthMm">幅 (mm):</label>
            <input id="widthMm" type="number" v-model.number="form.widthMm" required />
        </div>
        <div>
            <label>属性 (複数入力可):</label>
            <table border="1" style="margin-top: 0.5em">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Value</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(attr, index) in form.productAttributesArray" :key="index">
                        <td>
                            <input v-model="attr.key" placeholder="例: color" />
                        </td>
                        <td>
                            <input v-model="attr.value" placeholder="例: red" />
                        </td>
                        <td>
                            <button
                                    type="button"
                                    @click="removeAttribute(index)"
                                    :disabled="form.productAttributesArray.length === 1">
                                削除
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <button type="button" @click="addAttribute" style="margin-top: 0.5em">属性行を追加</button>
        </div>
        <button type="submit">登録</button>
    </form>
</template>
