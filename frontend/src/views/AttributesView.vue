<script setup lang="ts">
import type { Ref } from 'vue';
import { ref } from 'vue';

import AttributeCreateForm from '@/components/AttributeCreateForm.vue';
import type { Attribute } from '@/types/api/attribute';
import { fetchAttributes } from '@/api/attribute';

const attributes: Ref<Attribute[]> = ref([]);

async function refreshList() {
    try {
        attributes.value = await fetchAttributes();
    } catch (e) {
        const message = e instanceof Error ? e.message : String(e);
        alert('属性の取得に失敗しました: ' + message);
    }
}

function onCreated() {
    refreshList();
}
</script>

<template>
    <div>
        <h2>Attribute Create</h2>
        <AttributeCreateForm @created="onCreated" />

    </div>

    <div>
        <h2>Attribute List</h2>
        <button @click="refreshList">属性を取得</button>
        <table v-if="attributes.length" border="1" style="margin-top: 1em">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="attr in attributes" :key="attr.id">
                    <td>{{ attr.id }}</td>
                    <td>{{ attr.name }}</td>
                    <td>{{ attr.type }}</td>
                </tr>
            </tbody>
        </table>

        <p v-else style="margin-top: 1em">データはまだ読み込まれていません。</p>
    </div>
</template>
