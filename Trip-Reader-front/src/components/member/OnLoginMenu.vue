<script setup>
import {
  UserOutlined,
  BookOutlined,
  PoweroffOutlined,
} from "@ant-design/icons-vue";

import { ref, computed } from "vue";
import { textLengthOverCut } from "@/util/string-util";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const { memberName } = storeToRefs(memberStore);
const { callGetLogout } = memberStore;

const prettyName = computed(() => {
  return textLengthOverCut(memberName.value, 8);
});
</script>

<template>
  <a-dropdown placement="bottom">
    <a-button type="text" class="navLinkStyle">
      <span style="color: #b17457">{{ prettyName }}</span
      >님
    </a-button>
    <template #overlay>
      <a-menu>
        <a-menu-item>
          <RouterLink class="tr-font-pc-r" :to="{ name: 'member-info' }">
            <UserOutlined class="icon-margin" />나의 정보
          </RouterLink>
        </a-menu-item>
        <a-menu-item>
          <RouterLink class="tr-font-pc-r" :to="{ name: 'member-scrap' }">
            <BookOutlined class="icon-margin" />나의 책갈피
          </RouterLink>
        </a-menu-item>
        <a-menu-divider />
        <a-menu-item
          style="color: red"
          class="tr-font-pc-r"
          @click="callGetLogout"
        >
          <PoweroffOutlined class="icon-margin" />로그아웃
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
</template>

<style scoped>
.icon-margin {
  margin-right: 5px;
}

.navLinkStyle {
  /* margin-right: 10px; */
  color: black;
  height: inherit;
  display: flex;
  align-items: center;
  padding: 0 10px;
  margin-right: clamp(10px, 1vw, 20px);
  font-size: clamp(15px, 2.5vh, 20px);
  font-family: hankengrotesk;
  font-weight: bold;
}

.navLinkStyle:hover {
  color: #b17457;
  text-decoration: underline;
}
</style>
