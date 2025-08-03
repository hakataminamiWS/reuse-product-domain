import { createRouter, createWebHistory } from 'vue-router';
import type { RouteRecordRaw } from 'vue-router';

import ProductsView from '@/views/ProductsView.vue';
import AttributesView from '@/views/AttributesView.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/products',
    name: 'Products',
    component: ProductsView,
  },
  {
    path: '/attributes', // 追加
    name: 'Attributes',
    component: AttributesView,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
