<script setup>
defineProps({ newsList: Object });
</script>
<template>
  <div class="outer-container">
    <!-- 기사 섹션 -->
    <div class="content-section">
      <div v-for="(news, index) in newsList" :key="index">
        <RouterLink :to="`/notice/${news.noticeNano}`" class="news-item">
          <!-- <div class="news-info">
            <p class="news-time">{{ news.pretty_time }}</p>
          </div> -->
          <div class="news-content hanken-grotesk-content">
            <h2 class="news-title hanken-grotesk-content">{{ news.title }}</h2>
            <p class="news-description hanken-grotesk-content">
              {{ news.content }}
            </p>
            <p class="news-category hanken-grotesk-content">{{ news.title }}</p>
            <!-- Category 추가 -->
          </div>
          <img
            v-if="news.image"
            :src="news.image"
            alt="News Image"
            class="news-image"
          />
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 외부 컨테이너 */
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
  padding: 0;
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

.news-content {
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
  /* font-family: "bookk-light"; */
}

/* Category 스타일 */
.news-category {
  font-size: 12px;
  color: #000000; /* 밝은 회색 */
  margin-top: 10px;
  /* font-family: "bookk-light"; */
  text-align: left;
}

/* 이미지 스타일 */
.news-image {
  width: 300px;
  height: 170px;
  object-fit: cover;
  flex-shrink: 0;
}
</style>
