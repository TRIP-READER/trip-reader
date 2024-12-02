<script setup>
import MainPage from "@/components/mainpage/MainPage.vue";
import { ref, onMounted, watch } from "vue";
import { getNewsMain } from "@/api/newsApi";
import MainPageHeader from "@/components/mainpage/MainPageHeader.vue";

const responseNews = ref(null);

const fetchNews = async () => {
  await getNewsMain(
    (response) => {
      responseNews.value = response.data;
      console.log(responseNews.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

onMounted(() => {
  fetchNews();
});

watch(responseNews, (newValue) => {
  newValue ? (mainNews.value = newValue) : null;
});

const mainNews = ref([]);
</script>

<template>
  <MainPageHeader />

  <MainPage class="test" :mainNews="mainNews" />
</template>

<style scoped></style>
