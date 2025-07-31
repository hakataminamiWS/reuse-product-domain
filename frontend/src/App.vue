<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
    <router-view />
    <nav>
        <router-link to="/products">Products</router-link> |
        <router-link to="/attributes">Attributes</router-link>
    </nav>
</template>

<style scoped></style>
