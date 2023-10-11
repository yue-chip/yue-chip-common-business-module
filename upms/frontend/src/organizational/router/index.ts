import type { App } from 'vue'
import { createRouter, createWebHashHistory } from 'vue-router'

export const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('../views/index.vue'),
      meta: {keepAlive: true }
    },
    {
      path: '/addOrUpdate',
      name: 'addOrUpdate',
      component: () => import('../views/addOrUpdate.vue'),
      meta: {keepAlive: false }
    },
  ],
  strict: true,
  scrollBehavior: () => ({ left: 0, top: 0 }),
})
export function setupRouter(app: App<Element>) {
  app.use(router)
}
