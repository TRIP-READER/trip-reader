<script setup></script>

<template>
  <footer class="footer">
    <div class="footer-container">
      <!-- 로고 영역 -->
      <div class="footer-logo">
        <img src="/eyeRollingLogo.gif" alt="Main Logo" />
      </div>

      <!-- 점선 -->
      <div class="divider"></div>

      <!-- 링크 섹션 -->
      <div class="footer-links">
        <a href="/about" class="footer-link">About Us</a>
        <a href="/contact" class="footer-link">Contact</a>
        <a href="/privacy" class="footer-link">Privacy Policy</a>
        <a href="/terms" class="footer-link">Terms of Service</a>
      </div>
      <!-- 텍스트 섹션 -->
      <div class="footer-text">
        <p>&copy; 2024 Trip Reader. All Rights Reserved.</p>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  color: #fff; /* 텍스트 색상 */
  padding: 20px 0;
  text-align: center;
  font-family: "Roboto", sans-serif;
  background-color: #ffffff;

  /* 점선 추가 (좌우 여백 포함) */
  position: relative;
}

.footer::before {
  content: "";
  position: absolute;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 20px;
}

.footer-logo img {
  max-width: 200px;
  margin-bottom: 10px;
}

.divider {
  width: 90%; /* 점선의 너비 */
  border-top: 2px dotted #222222; /* 점선 스타일 */
  margin: 15px 0; /* 점선 위아래 간격 */
}

.footer-links {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 10px;
  margin-top: 30px;
}

.footer-link {
  color: #000000;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s ease;
}

.footer-link:hover {
  color: #fff; /* 링크에 마우스를 올렸을 때 색상 변경 */
}

.footer-text p {
  font-size: 12px;
  margin: 0;
  color: #000000;
}
</style>
