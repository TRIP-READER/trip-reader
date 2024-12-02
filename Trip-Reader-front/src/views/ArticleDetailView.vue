<script setup>
import ArticleDetail from "@/components/article/ArticleDetail.vue";
import SideBox from "@/components/common/SideBox.vue";
import { RouterLink, useRoute } from "vue-router";
import { getNewsSummary } from "@/api/newsApi";
import { ref, computed, onMounted } from "vue";
import { textLengthOverCut } from "@/util/string-util";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { callGetIsScrap } = memberStore;
const { isLogin } = storeToRefs(memberStore);

const route = useRoute();
const articleNano = route.params.articlenano;
const article = ref({
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
});

onMounted(async () => {
  await getNewsSummary(
    articleNano,
    (response) => {
      article.value = response.data;
      console.log(article.value);
    },
    (error) => {
      console.log(error);
    }
  );
  if (!isLogin.value) return;
  article.value.isScraped = await callGetIsScrap(articleNano);

  console.log(article.value);
});

const getArticleList = computed(() => {
  let ret = [];
  article.value.content_list.forEach((content) => {
    ret.push({
      key: content.id,
      href: "#" + content.id,
      title: content.header ? textLengthOverCut(content.header, 10) : null,
    });
  });
  if (article.value.video_list.length > 0) {
    ret.push({
      key: "video",
      href: "#video",
      title: "📺관련 영상",
    });
  }
  if (article.value.main_marker.mapy && article.value.main_marker.mapx) {
    ret.push({
      key: "map",
      href: "#map",
      title: "🗺️지도 보기",
    });
  }
  if (article.value.news_list.length > 0) {
    ret.push({
      key: "news",
      href: "#news",
      title: "📰관련 기사",
    });
  }
  if (article.value.news_list.length > 0) {
    ret.push({
      key: "info",
      href: "#info",
      title: "🔗정보 바로가기",
    });
  }
  return ret;
});
</script>

<template>
  <a-layout>
    <SideBox>
      <RouterLink
        style="font-size: 25px"
        class="tr-font-bkk-b"
        :to="{ name: 'article' }"
        >목록으로</RouterLink
      >
      <a-anchor :affix="false" :items="getArticleList" offsetTop="42">
      </a-anchor>
    </SideBox>
    <ArticleDetail :article="article"> </ArticleDetail>
  </a-layout>
</template>

<style scoped>
a:hover {
  color: #bd927b;
}
a:active {
  color: #8a523d;
}
</style>
