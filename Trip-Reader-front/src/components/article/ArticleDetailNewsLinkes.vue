<script setup>
import { LeftCircleOutlined, RightCircleOutlined } from "@ant-design/icons-vue";
defineProps(["newsList"]);

const cardHeadStyle = {
  fontFamily: "pyeongchang-regular",
  color: "#4a4947",
};

const onClickCard = (url) => {
  window.open(url, "_blank");
};
</script>

<template>
  <div id="news">
    <h2>📰관련 기사</h2>
  </div>
  <div class="newsBoxStyle">
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
      <div v-for="news in newsList" :key="news.id" class="cardBox">
        <a-card
          hoverable
          :title="news.title"
          :headStyle="cardHeadStyle"
          class="cardStyle"
          @click="onClickCard(news.url)"
        >
          <p v-html="news.content"></p>
        </a-card>
      </div>
    </a-carousel>
  </div>
</template>

<style scoped>
h2 {
  font-family: "pyeongchang-regular";
  font-size: 36px;
  color: #4a4947;
}
p {
  font-family: "bookk-light";
  color: #4a4947;
}

.cardBox {
  padding: 20px;
}

:deep(.slick-slide .cardStyle) {
  width: 80%;
  background-color: lightgrey;
  justify-self: center;
  border-radius: 0px;
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
