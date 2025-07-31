<script setup>
import { reactive } from 'vue'
import { createProduct } from '@/api/product.js'

const emit = defineEmits(['created'])

const form = reactive({
    name: '',
    widthMm: 0,
    productAttributes: [
        { key: '', value: '' }
    ],
})

function addAttribute() {
    form.productAttributes.push({ key: '', value: '' });
}
function removeAttribute(index) {
    form.productAttributes.splice(index, 1);
}

async function onSubmit() {
    try {
        const productAttributesJson = {};
        form.productAttributes.forEach(({ key, value }) => {
            if (key.trim() !== '') {
                productAttributesJson[key.trim()] = value;
            }
        });

        const payload = {
            name: form.name,
            widthMm: form.widthMm,
            productAttributes: productAttributesJson,
        }

        await createProduct(payload)
        emit('created')
        form.name = ''
        form.widthMm = 0
        form.productAttributesJson = '{}'
    } catch (error) {
        alert('登録に失敗しました: ' + error.message)
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
            <table border="1" style="margin-top: 0.5em;">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Value</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(attr, index) in form.productAttributes" :key="index">
                        <td><input v-model="attr.key" placeholder="例: color" /></td>
                        <td><input v-model="attr.value" placeholder="例: red" /></td>
                        <td>
                            <button type="button" @click="removeAttribute(index)"
                                    :disabled="form.productAttributes.length === 1">
                                削除
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <button type="button" @click="addAttribute" style="margin-top: 0.5em;">属性行を追加</button>
        </div>
        <button type="submit">登録</button>
    </form>
</template>
