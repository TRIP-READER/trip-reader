<script setup>
defineProps({ newsList: Object, darkMode: Boolean });
import { textLengthOverCut } from "@/util/string-util";
</script>
<template>
  <div class="outer-container" v-if="!darkMode">
    <!-- 기사 섹션 -->
    <div class="content-section">
      <div v-for="(news, index) in newsList" :key="index" class="news-item">
        <RouterLink
          :to="{
            name: 'aricle-detail-view',
            params: { articlenano: news.news_summary_nano },
          }"
          class="news-content-wrapper"
        >
          <!-- 텍스트 콘텐츠 (왼쪽) -->
          <div class="text-content">
            <h2 class="news-title hanken-grotesk-content">{{ news.header }}</h2>
            <p class="news-category hanken-grotesk-content">{{ news.title }}</p>
            <p class="news-description hanken-grotesk-content">
              {{ textLengthOverCut(news.content, 200) }}
            </p>
            <span class="news-time hanken-grotesk-content">{{
              news.pretty_time
            }}</span>
          </div>

          <!-- 이미지 콘텐츠 (오른쪽) -->
          <div class="image-container">
            <img
              v-if="news.image"
              :src="news.image"
              alt="News Image"
              class="news-image"
            />
            <div class="scrap-overlay">❤️ {{ news.scrap_count }}</div>
          </div>
        </RouterLink>
      </div>
    </div>
  </div>

  <!-- Dark mode -->
  <div class="outer-container" v-if="darkMode">
    <!-- 기사 섹션 -->
    <div class="content-section dark">
      <div
        v-for="(news, index) in newsList"
        :key="index"
        class="news-item-dark dark"
      >
        <RouterLink
          :to="{
            name: 'aricle-detail-view',
            params: { articlenano: news.news_summary_nano },
          }"
          class="news-content-wrapper"
        >
          <!-- 텍스트 콘텐츠 (왼쪽) -->
          <div class="text-content dark">
            <h2
              class="news-title hanken-grotesk-content dark"
              style="color: #ff5370"
            >
              {{ news.dark_title }}
            </h2>
            <p class="news-category hanken-grotesk-content dark">
              {{ news.title }}
            </p>
            <p class="news-description hanken-grotesk-content dark">
              {{ textLengthOverCut(news.content, 200) }}
            </p>
            <span class="news-time hanken-grotesk-content dark">{{
              news.pretty_time
            }}</span>
          </div>

          <!-- 이미지 콘텐츠 (오른쪽) -->
          <div class="image-container dark">
            <img
              v-if="news.image"
              :src="news.image"
              alt="News Image"
              class="news-image"
            />
            <div class="scrap-overlay dark">❤️ {{ news.scrap_count }}</div>
          </div>
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 외부 컨테이너 */
.news-content-wrapper {
  display: flex;
  flex-direction: row;
  gap: 16px;
  width: 100%;
  text-decoration: none;
  color: black;
}

.outer-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  min-height: 100vh;
  width: 100%;
}

/* 콘텐츠 섹션 */
.content-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 개별 뉴스 아이템 */
.news-item {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 16px;
  border-bottom: 1px solid #ddd;
  padding: 10px;
  width: 100%;
  min-width: 800px;
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 애니메이션 */
}

.news-item:hover {
  transform: translateY(-2px); /* 위로 5px 이동 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2); /* 부드러운 그림자 추가 */
}

.news-item-dark {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 16px;
  padding: 10px;
  width: 100%;
  min-width: 800px;
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 애니메이션 */
  border-bottom: 1px solid #444; /* 다크모드 테두리 색 변경 */
}

.news-item-dark:hover {
  transform: translateY(-2px); /* 위로 5px 이동 */
  box-shadow: 0 2px 8px rgba(255, 255, 255, 0.2); /* 다크모드 그림자 */
}

/* 텍스트 콘텐츠 (왼쪽) */
.text-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  text-align: left;
}

.news-title {
  font-size: 24px;
  /* font-family: "nanumgothic-bold"; */
  margin-bottom: 8px;
  color: black;
}

.news-description {
  font-size: 16px;
  color: #333;
  margin: 0;
  /* font-family: "nanumgothic"; */
}

.news-category {
  font-size: 13px;
  color: #000000;
  margin-top: 10px;
  font-weight: bold;
  text-align: left;
}

.news-time {
  font-size: 12px;
  color: #888;
  margin-top: 10px;
  margin-bottom: 10px;
}

/* 이미지 콘텐츠 (오른쪽) */
.image-container {
  position: relative;
  width: 300px;
  height: 170px;
  flex-shrink: 0;
}

.news-image {
  width: 300px;
  height: 170px;
  object-fit: cover;
  flex-shrink: 0;
}

/* 하트 오버레이 */
.scrap-overlay {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: rgba(70, 70, 70, 0.7);
  color: rgb(255, 255, 255);
  padding: 5px 10px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: bold;
}
.dark {
  background-color: #181a1b;
  color: #bab4ab;
}
</style>
