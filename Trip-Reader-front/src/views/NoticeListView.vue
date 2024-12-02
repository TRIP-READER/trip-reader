<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import NoticeHeader from "@/components/notice/NoticeHeader.vue";
import NoticeList from "@/components/notice/NoticeList.vue";
import { getNoticeList } from "@/api/noticeApi";

const noticeList = ref([]);
const page = ref(1);
const noMoreData = ref(false);
const loading = ref(false);

async function fetchNotice(reset = false) {
  if (loading.value || (noMoreData.value && !reset)) return;

  if (reset) {
    noticeList.value = []; // 리스트 초기화
    page.value = 1; // 페이지 초기화
    noMoreData.value = false; // 데이터 없음 상태 초기화
  }

  const params = {
    pageno: page.value,
  };

  try {
    await getNoticeList(
      params,
      (response) => {
        if (response.data.length > 0) {
          noticeList.value = [...noticeList.value, ...response.data];
          page.value += 1;

          console.log(noticeList.value);
        } else {
          noMoreData.value = true;
        }
      },
      (error) => {
        console.error("뉴스 데이터를 가져오는 중 오류 발생:", error);
      }
    );
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  fetchNotice();
});
</script>

<template>
  <a-layout class="outerStyle">
    <a-layout-content class="contentStyle">
      <NoticeHeader />
      <div class="notice-list-container">
        <NoticeList :newsList="noticeList" />
      </div>
    </a-layout-content>
  </a-layout>
</template>

<style scoped>
.outerStyle {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  min-height: 100vh;
  width: 100%;
}

.contentStyle {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 60vw;
  min-width: 800px;
}
</style>
