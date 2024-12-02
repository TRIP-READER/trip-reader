<script setup>
import { ref, watch, onMounted, onUnmounted } from "vue";
import ArticleDetailNewsLinkes from "@/components/article/ArticleDetailNewsLinkes.vue";
import ArticleDetailVideo from "@/components/article/ArticleDetailVideo.vue";
import ArticleDetailScrapButton from "./ArticleDetailScrapButton.vue";
import MapComponent from "@/components/common/MapComponent.vue";

const props = defineProps({
  article: {
    news_summary_nano: String,
    title: String,
    image: String,
    content_list: [],
    news_list: [],
    video_list: [],
    pretty_time: String,
    main_marker: {
      title: String,
      mapy: Number,
      mapx: Number,
    },
    marker_list: [],
    isScraped: false,
    content_id: Number,
  },
});

const mapRender = ref(false);
watch(props, async (newProp) => {
  mapRender.value = true;
});
</script>
<template>
  <a-layout-content class="outerStyle">
    <div class="mainContentStyle">
      <div class="headerStyle">
        <h1>{{ article.title }}</h1>
        <div class="headerInfoStyle">
          <div class="date">작성일 : {{ article.pretty_time }}</div>
          <ArticleDetailScrapButton
            :article-nano="article.news_summary_nano"
            :is-scraped="article.isScraped"
            :scrap-count="article.scrap_count"
            @scrap-state-update="article.isScraped = $event"
            @scrap-count-update="article.scrap_count = $event"
            style="align-self: self-end"
          />
        </div>
      </div>
      <a-divider style="margin-bottom: 40px" />
      <br />
      <template v-if="article.image">
        <div class="imageWrapper">
          <img :src="article.image" alt="News Image" class="newsImage" />
        </div>
      </template>

      <template v-for="(item, index) in article.content_list" :key="item.id">
        <div :id="item.id" />
        <h2>{{ item.header }}</h2>
        <p>{{ item.content }}</p>
      </template>
      <div v-if="article.video_list != null">
        <ArticleDetailVideo :video-list="article.video_list" />
      </div>
      <div v-if="article.main_marker != null">
        <div id="map"><h2>🗺️지도 보기</h2></div>

        <MapComponent
          :main-marker="article.main_marker"
          :marker-list="article.marker_list"
          :map-render="mapRender"
        />
      </div>
      <div v-if="article.news_list != null">
        <ArticleDetailNewsLinkes :news-list="article.news_list" />
      </div>
      <div id="info">
        <RouterLink
          :to="{
            name: 'festival-info-detail',
            params: { festivalContentId: article.content_id },
          }"
        >
          <h2>🔗<span class="goinfo">정보 바로가기</span></h2>
        </RouterLink>
      </div>

      <div style="height: 30vh" />
    </div>
  </a-layout-content>
</template>

<style scoped>
.goinfo:hover {
  color: #b17457;
  text-decoration: underline;
}

h1 {
  font-size: 64px;
}
.date {
  font-size: 20px;
  margin: 30px 0px;
}

h2 {
  /* font-family: "pyeongchang-regular"; */
  font-size: 36px;
  color: #4a4947;
  font-family: "nanumgothic";
}
p {
  margin-bottom: 3vh;
  font-size: 20px;
  line-height: 35px;
  font-family: "nanumgothic";
}

h1,
p,
.date {
  /* font-family: "bookk-light"; */
  color: #4a4947;
}

.headerInfoStyle {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.headerStyle {
  margin-top: 5em;
}

.mainContentStyle {
  display: flex;
  flex-direction: column;
  width: 60vw;
  min-width: 800px;
}

.outerStyle {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  min-height: 100vh;
  width: 100%;
}
.imageWrapper {
  display: flex; /* 플렉스 컨테이너 */
  justify-content: center; /* 가운데 정렬 */
  align-items: center; /* 가운데 정렬 */
  width: 100%; /* 부모 너비 제한 */
}
.newsImage {
  margin-bottom: 3vh;
  max-height: 60vh;
  max-width: 100%; /* 컨테이너 너비를 넘지 않도록 */
  width: auto; /* 이미지 비율 유지 */
  height: auto; /* 이미지 비율 유지 */
}
</style>
