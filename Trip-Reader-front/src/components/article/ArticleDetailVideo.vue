<script setup>
import { LeftCircleOutlined, RightCircleOutlined } from "@ant-design/icons-vue";
defineProps(["videoList"]);
</script>

<template>
  <div id="video"><h2>📺관련 영상</h2></div>

  <a-carousel arrows style="margin-bottom: 30px">
    <template #prevArrow>
      <div class="custom-slick-arrow" style="left: 10px; z-index: 1">
        <left-circle-outlined />
      </div>
    </template>
    <template #nextArrow>
      <div class="custom-slick-arrow" style="right: 10px">
        <right-circle-outlined />
      </div>
    </template>
    <div class="videoBoxStyle" v-for="video in videoList">
      <iframe
        class="videoStyle"
        :key="video.id"
        :src="`https://www.youtube.com/embed/${video.url}`"
      >
      </iframe>
    </div>
  </a-carousel>
</template>

<style scoped>
h2 {
  font-family: "pyeongchang-regular";
  font-size: 36px;
  color: #4a4947;
}
.videoBoxStyle {
  /* width와 height의 비율이 16:9가 되도록 설정 */
  /* width크기는 50vw 높이는 비율에 맞춰서
    최소 width크기는 600px, 최대 width크기는 높이에 맞춰서 설정 */
  width: 50vw;
  height: 28.125vw;
  min-width: 600px;
  max-width: calc(28.125vw * 2);

  /* min-height: 315px; */
}

.videoStyle {
  width: 90%;
  height: 90%;
}

:deep(.slick-dots li.slick-active button) {
  background-color: #8a523d;
}
:deep(.slick-dots li button) {
  background-color: #bd927b;
}
:deep(.slick-slide) {
  text-align: center;
  overflow: hidden;
}

:deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #bd927b;
  transition: ease all 0.3s;
  opacity: 0.3;
  z-index: 1;
}
:deep(.slick-arrow.custom-slick-arrow:before) {
  display: none;
}
:deep(.slick-arrow.custom-slick-arrow:hover) {
  color: #8a523d;
  opacity: 0.5;
}
</style>
