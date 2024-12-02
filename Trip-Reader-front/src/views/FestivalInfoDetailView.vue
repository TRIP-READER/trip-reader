<script setup>
import { getFestivalDetail } from "@/api/festivalApi";
import FestivalInfoDetail from "@/components/info/FestivalInfoDetail.vue";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

const festivalDetail = ref(null);
const route = useRoute();
const festivalContentId = route.params.festivalContentId;

onMounted(async () => {
  await getFestivalDetail(
    festivalContentId,
    (response) => {
      festivalDetail.value = response.data;
      console.log(festivalDetail.value);
    },
    (error) => {
      console.log(error);
    }
  );
});
</script>

<template>
  <a-layout class="outerStyle">
    <a-layout-content class="contentStyle">
      <FestivalInfoDetail :festival-detail="festivalDetail" />
    </a-layout-content>
  </a-layout>
</template>

<style scoped></style>
