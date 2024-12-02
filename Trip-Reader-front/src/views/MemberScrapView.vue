<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import ArticleList from "@/components/common/ArticleList.vue";
import ArticleHeader from "@/components/articlelist/ArticleHeader.vue";
import SideBox from "@/components/common/SideBox.vue";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { memberName } = storeToRefs(memberStore);
const { callGetScrap } = memberStore;

const newsList = ref([]); // 동적으로 로드할 뉴스 리스트

// 초기 데이터 로드 및 스크롤 이벤트 추가
onMounted(async () => {
  newsList.value = await callGetScrap();
});

const headerTheme = {
  token: {
    colorPrimary: `black`,
    colorPrimaryHover: `#b17457`,
    colorTextQuaternary: `black`,
    colorTextQuaternaryHover: `#b17457`,
    colorTextTertiary: `#b17457`,
  },
};
</script>

<template>
  <a-layout class="outerStyle">
    <a-layout-content class="contentStyle">
      <div class="header">
        <span class="headerText hanken-grotesk-content">
          <span style="color: #b17457">{{ memberName }}</span
          >님의 책갈피</span
        >
        <br />
        <div class="headerUtils">
          <span class="hanken-grotesk-content" style="font-weight: bold"
            >책갈피 갯수 : {{ newsList.length }}</span
          >
        </div>
      </div>
      <a-divider style="height: 2px; background-color: #eeeeee" />
      <ArticleList :news-list="newsList" :have-side="haveSide" />
    </a-layout-content>
  </a-layout>
</template>

<style scoped>
.loading {
  text-align: center;
  margin: 20px 0;
  font-size: 16px;
  color: #999;
}

.no-more-data {
  text-align: center;
  margin: 20px 0;
  font-size: 16px;
  color: #999;
}

.header {
  margin-top: 5em;
  color: #4a4947;
  text-align: left;
  width: 100%;
  line-height: 2.5em;
}

.headerText {
  font-size: 50px;
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

.headerUtils {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 1em;
  width: 100%;
}

.outerStyle {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  min-height: 100vh;
  width: 100%;
}
</style>
