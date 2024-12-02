import "./assets/main.css";
import "animate.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import { useKakao } from "vue3-kakao-maps/@utils";

import Antd from "ant-design-vue";
import "ant-design-vue/dist/reset.css";

const { VITE_APP_KAKAO_API_KEY } = import.meta.env;
useKakao(VITE_APP_KAKAO_API_KEY);
const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(Antd);

app.mount("#app");
