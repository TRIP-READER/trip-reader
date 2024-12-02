<script setup>
defineProps({ festivalInfoList: Object });
import { textLengthOverCut } from "@/util/string-util";
</script>
<template>
  <div class="outer-container">
    <!-- 기사 섹션 -->
    <div class="content-section">
      <div v-for="(finfo, index) in festivalInfoList" :key="index">
        <RouterLink
          :to="{
            name: 'festival-info-detail',
            params: { festivalContentId: finfo.contentid },
          }"
          class="festival-item"
        >
          <div class="festival-content">
            <h2 class="festival-title hanken-grotesk-content">
              {{ finfo.title }}
            </h2>
            <p class="festival-date hanken-grotesk-content">
              {{ finfo.eventstartdate }} ~ {{ finfo.eventenddate }}
            </p>
            <p class="festival-description hanken-grotesk-content">
              {{ textLengthOverCut(finfo.overview, 200) }}
            </p>
            <p class="festival-addr hanken-grotesk-content">
              {{ finfo.addr1 }} {{ finfo.addr2 }}
            </p>
          </div>
          <img v-if="finfo.image" :src="finfo.image" class="festival-image" />
          <img
            v-else
            src="/black_bear_sitting_at_a_desk.png"
            class="festival-image"
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
  padding: 0px;
}

/* 콘텐츠 섹션 */
.content-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 0;
}

/* 개별 뉴스 아이템 */
.festival-item {
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

.festival-item:hover {
  transform: translateY(-2px); /* 위로 5px 이동 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2); /* 부드러운 그림자 추가 */
}

/* 텍스트 정보 */
.festival-date {
  color: black;
  font-weight: bold;
}

.festival-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  text-align: left;
  /* padding: 0 30px; */
}

.festival-title {
  font-size: 24px;
  margin-bottom: 8px;
  color: black;
}

.festival-description {
  font-size: 16px;
  color: #333;
  margin: 0;
}

/* Category 스타일 */
.festival-category {
  font-size: 12px;
  color: #888; /* 밝은 회색 */
  margin-top: 10px;
  text-align: left;
}

/* 이미지 스타일 */
.festival-image {
  width: 300px;
  height: 170px;
  object-fit: cover;
  flex-shrink: 0;
}

.festival-addr {
  font-size: 12px;
  color: #888;
  margin-top: 10px;
}
</style>
