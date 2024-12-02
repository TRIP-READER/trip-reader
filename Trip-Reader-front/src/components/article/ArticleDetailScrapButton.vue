<script setup>
import { ref, computed, defineEmits } from "vue";
import { HeartOutlined, HeartFilled } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
import { postScrap } from "@/api/newsApi";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { callGetRefresh } = memberStore;
const { isLogin } = storeToRefs(memberStore);

const props = defineProps(["articleNano", "scrapCount", "isScraped"]);
const emit = defineEmits("scrapCountUpdate", "scrapStateUpdate");

const onClickButton = () => {
  postScrap(
    { news_summary_nano: props.articleNano, i_scrap_this: !props.isScraped },
    (response) => {
      emit("scrapStateUpdate", response.data.i_scrap_this);
      emit("scrapCountUpdate", response.data.scrap_count);
      if (response.data.i_scrap_this) {
        message.success("책갈피 추가 완료");
      } else {
        message.success("책갈피 삭제 완료");
      }
    },
    (fail) => {
      message.error("책갈피 추가 실패");
    }
  );
};

const scrapMessage = computed(() => {
  if (props.isScraped) {
    return "책갈피 삭제";
  } else {
    return "책갈피 추가";
  }
});
</script>

<template>
  <template v-if="isLogin">
    <context-holder />
    <a-tooltip :title="scrapMessage" color="red">
      <a-button danger shape="round" size="large" @click="onClickButton">
        {{ scrapCount }}
        <template #icon>
          <HeartFilled v-if="isScraped" /><HeartOutlined v-else />
        </template>
      </a-button>
    </a-tooltip>
  </template>
  <template v-else>
    <a-tooltip title="책갈피 수" color="red">
      <HeartFilled style="color: red; font-size: 20px" />
      <span class="scrapCountNumber">{{ scrapCount }}</span>
    </a-tooltip>
  </template>
</template>

<style scoped>
.scrapCountNumber {
  font-size: 20px;
  color: red;
  margin-left: 10px;
}
</style>
