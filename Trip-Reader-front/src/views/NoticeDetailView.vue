<script setup>
import SideBox from "@/components/common/SideBox.vue";
import { RouterLink, useRoute, useRouter } from "vue-router";
import { getNoticeDetail } from "@/api/noticeApi";
import { ref, onMounted } from "vue";

const route = useRoute();
const router = useRouter();
const noticeNano = route.params.noticeNano;

const article = ref({});

onMounted(() => {
  if (!noticeNano) {
    console.error("noticeNano is undefined. Redirecting to list page.");
    router.push({ name: "article" }); // Redirect to list page
    return;
  }

  getNoticeDetail(
    noticeNano,
    (response) => {
      article.value = response.data;
    },
    (error) => {
      console.error("Error fetching notice detail:", error);
    }
  );
});
</script>

<template>
  <a-layout>
    <SideBox>
      <RouterLink
        style="font-size: 25px"
        class="tr-font-bkk-b"
        :to="{ name: 'notice' }"
      >
        목록으로
      </RouterLink>
      <a-anchor
        :affix="false"
        :items="getArticleList"
        offsetTop="42"
      ></a-anchor>
    </SideBox>
    <a-layout-content class="outerStyle">
      <div class="mainContentStyle">
        <div class="headerStyle">
          <h1>{{ article.title }}</h1>
        </div>
        <a-divider style="margin-bottom: 40px" />
        <div
          class="imageContainer"
          style="display: flex; justify-content: center; padding: 20px"
        >
          <img
            :src="article.image"
            alt="article image"
            style="height: auto; width: 50%; object-fit: contain"
          />
        </div>

        <p>{{ article.content }}</p>
        <div style="height: 30vh"></div>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<style scoped>
a:hover {
  color: #bd927b;
}
a:active {
  color: #8a523d;
}
h1 {
  font-size: 64px;
}
.date {
  font-size: 20px;
  margin: 30px 0px;
}

h2 {
  font-family: "pyeongchang-regular";
  font-size: 36px;
  color: #4a4947;
}
p {
  margin-bottom: 3vh;
  font-size: 20px;
  line-height: 35px;
}

h1,
p,
.date {
  font-family: "bookk-light";
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
</style>
