<script setup>
import { reactive } from 'vue'
import { createAttribute } from '@/api/attribute.js'

const emit = defineEmits(['created'])

const form = reactive({
    name: '',
    type: '',
})

async function onSubmit() {
    try {
        const payload = {
            name: form.name,
            type: form.type,
        }

        await createAttribute(payload)
        emit('created')
        form.name = ''
        form.type = ''
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
            <label for="type">Type:</label>
            <input id="type" v-model="form.type" required />
        </div>
        <button type="submit">登録</button>
    </form>
</template>
