<script setup>
import { textLengthOverCut } from "@/util/string-util";
import ArticlePreviewCard from "./ArticlePreviewCard.vue";
const leftTextLength = 100;
const rightTextLength = 110;
const headerTextLength = 15;

defineProps({
  mainNews: Object,
});
</script>

<template>
  <main class="grid-container">
    <ArticlePreviewCard
      v-for="(article, index) in mainNews"
      :key="article.articleId"
      title=" "
      :description="article.content"
      :articlenano="article.news_summary_nano"
      :image="article.image"
    />
  </main>
</template>

<style scoped>
ArticlePreviewCard {
  z-index: 0.9;
}
.grid-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 15px; /* 카드 간격 */
  padding: 3.31vw;
  font-family: "Roboto", sans-serif;
}

/* 외부 컨테이너 */
.outer-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.bear-logo {
  position: absolute; /* 상단에 고정 */
  /* top: 60px; 상단에서 50px 떨어진 위치 */
  left: 50%; /* 가로 중앙 정렬 */
  transform: translateX(-50%) translateY(-20%); /* 가로 중앙 정렬 유지 */
  z-index: 0.8; /* 다른 요소 위로 위치 */
}

.bear-image {
  width: 50vw; /* 곰 이미지 크기 */
  height: auto;
  opacity: 1; /* 완전히 불투명 */
}
</style>
