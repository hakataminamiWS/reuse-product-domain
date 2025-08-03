<script setup lang="ts">
import { reactive } from 'vue';

import { attributeTypes } from '@/types/api/attribute';
import { createAttribute } from '@/api/attribute';
import type { CreateAttributePayload } from '@/types/api/attribute';

const emit = defineEmits<{
    (e: 'created'): void
}>();

const form = reactive<CreateAttributePayload>({
    name: '',
    type: 'text', // default
});

async function onSubmit() {
    try {
        await createAttribute({ ...form });
        emit('created');
        form.name = '';
        form.type = 'text';
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
            <label for="type">Type:</label>
            <select id="type" v-model="form.type" required>
                <option v-for="t in attributeTypes"
                        :key="t"
                        :value="t">
                    {{ t }}
                </option>
            </select>
        </div>
        <button type="submit">登録</button>
    </form>
</template>
