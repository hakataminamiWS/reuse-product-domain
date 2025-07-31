<script setup>
import { ref } from 'vue';
import ProductCreateForm from '@/components/ProductCreateForm.vue';
import { fetchProducts } from '@/api/product.js';

const products = ref([]);
const showForm = ref(false);

async function refreshList() {
    try {
        products.value = await fetchProducts();
    } catch (e) {
        alert('商品の取得に失敗しました: ' + e.message);
    }
}

function onCreated() {
    refreshList();
}
</script>

<template>
    <div>
        <h2>Product Create</h2>
        <ProductCreateForm @created="onCreated" />
    </div>

    <div>
        <h2>Product List</h2>
        <button @click="refreshList">商品を取得</button>
        <table v-if="products.length" border="1" style="margin-top: 1em">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>widthMm</th>
                    <th>属性</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="product in products" :key="product.id">
                    <td>{{ product.id }}</td>
                    <td>{{ product.name }}</td>
                    <td>{{ product.widthMm }}</td>
                    <td>
                        <pre>{{ product.productAttributes }}</pre>
                    </td>
                </tr>
            </tbody>
        </table>

        <p v-else style="margin-top: 1em">データはまだ読み込まれていません。</p>
    </div>
</template>
