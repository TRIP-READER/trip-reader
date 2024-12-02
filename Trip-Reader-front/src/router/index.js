import { createRouter, createWebHistory } from "vue-router";
import mainRoutes from "./mainRoutes";
import memberRoutes from "./memberRoutes";

const routes = [...mainRoutes, ...memberRoutes];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 항상 맨 위로 이동
    return { top: 0 };
  },
});

export default router;
