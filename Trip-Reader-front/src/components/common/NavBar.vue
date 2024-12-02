<script setup>
import { RouterLink, useRouter } from "vue-router";
import { ref, watch } from "vue";
import { message } from "ant-design-vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import LoginModal from "@/components/member/LoginModal.vue";
import OnLoginMenu from "@/components/member/OnLoginMenu.vue";
const memberStore = useMemberStore();
const { isLogin, memberName, needLogin, needLoginMsg } =
  storeToRefs(memberStore);

const router = useRouter();
// 스타일 수정

const navOffset = {
  height: "5vh",
  minHeight: "40px",
};

const open = ref(false);
const openModal = () => {
  open.value = true;
};

watch(isLogin, (newVal) => {
  if (newVal) {
    open.value = false;
    needLogin.value = false;
  }
});

watch(needLogin, (newVal) => {
  if (newVal) {
    needLogin.value = false;
    open.value = true;
    message.info(needLoginMsg.value);
  }
});

const modalTheme = {
  colorPrimary: "#000000",
  borderRadius: "8px",
  colorLink: "#b17457",
};
</script>

<template>
  <a-layout-header class="navStyle">
    <a-flex justify="space-between" align="center" style="height: 100%">
      <RouterLink :to="{ name: 'main' }" class="logo-container">
        <img src="/trip reader bear alone.png" />
        <span class="logo-text hanken-grotesk-content">TRIP READER</span>
      </RouterLink>
      <a-flex justify="flex-end" style="height: inherit">
        <RouterLink class="navLinkStyle" :to="{ name: 'article' }"
          >AI NEWS</RouterLink
        >
        <RouterLink class="navLinkStyle" :to="{ name: 'festival-info-list' }"
          >CALENDAR</RouterLink
        >
        <RouterLink class="navLinkStyle" :to="{ name: 'notice' }"
          >NOTICE</RouterLink
        >
        <template v-if="!isLogin">
          <a class="navLinkStyle" @click="openModal" href="#">LOGIN</a>
          <a-config-provider
            :theme="{
              token: {
                ...modalTheme,
              },
            }"
          >
            <a-modal
              v-model:open="open"
              style="padding-top: 20px"
              centered
              :footer="null"
            >
              <LoginModal @close-modal="open = false"></LoginModal>
            </a-modal>
          </a-config-provider>
        </template>
        <template v-else>
          <OnLoginMenu />
        </template>
      </a-flex>
    </a-flex>
  </a-layout-header>
  <div :style="navOffset"></div>
</template>

<style scoped>
.navStyle {
  height: 5vh;
  min-height: 40px;
  width: 100%;
  min-width: 800px;
  background-color: #d8d2c2;
  position: fixed;
  z-index: 1;
  padding-inline-end: 20px;
  opacity: 0.8;
}
.navLinkStyle {
  /* margin-right: 10px; */
  color: black;
  height: inherit;
  display: flex;
  align-items: center;
  padding: 0 10px;
  margin-right: clamp(10px, 1vw, 20px);
  font-size: clamp(25px, 1.8vh, 2.5vh);
  font-family: hankengrotesk;
  font-weight: bold;
}

.navLinkStyle:hover {
  color: #b17457;
  text-decoration: underline;
}

.logo-container {
  display: flex; /* 이미지와 텍스트를 한 줄로 정렬 */
  align-items: center; /* 이미지와 텍스트를 수직으로 중앙 정렬 */
  text-decoration: none; /* 링크 기본 밑줄 제거 */
  gap: 10px; /* 이미지와 텍스트 간 간격 */
  height: 100%; /* 부모 높이에 맞추기 */
}

.logo-container img {
  height: 80%; /* navStyle height보다 살짝 작게 */
  max-height: 80%; /* 최대 크기를 제한 */
}

.logo-text {
  font-size: clamp(25px, 1.8vh, 2.5vh); /* 텍스트 크기를 반응형으로 조정 */
  font-weight: bold;
  color: black;
  letter-spacing: -0.15rem;
  line-height: 1; /* 텍스트 중앙 정렬 */
}
</style>
