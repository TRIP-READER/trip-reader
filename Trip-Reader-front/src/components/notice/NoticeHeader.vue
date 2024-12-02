<script setup></script>

<template>
  <!-- 상단 Header -->
  <div class="header">
    <!-- 곰 로고 -->
    <div class="bear-logo">
      <!-- <img
        src="/trip reader bear alone.png"
        alt="Bear Logo"
        class="bear-image"
      /> -->
      <h1 class="notice-title hanken-grotesk-title">NOTICE</h1>
    </div>
    <!-- 공지사항 제목 -->
  </div>
</template>

<style scoped>
/* 상단 헤더 */
.header {
  display: flex;
  flex-direction: column; /* 곰 로고와 제목을 세로로 정렬 */
  align-items: center;
  text-align: center;
  padding: 1vh 0; /* 상단 여백 추가 */
}

/* 곰 로고 */
.bear-logo {
  margin-bottom: 10px; /* 공지사항 제목과의 간격 */
}

.bear-image {
  width: 200px; /* 곰 로고 크기 */
  height: auto;
}

/* 공지사항 제목 */
.notice-title {
  font-size: 6vw;
  margin-top: 10px; /* 제목 위아래 간격 제거 */

  color: black;
}
</style>
