<template>
  <a-switch
    class="tr-font-pc-r switchButton"
    v-model:checked="isSortingByLike"
    checked-children="인기순 정렬"
    un-checked-children="최신순 정렬"
    @change="emitSortingChange"
    style="margin-bottom: 2px"
  ></a-switch>
</template>

<script setup>
import { ref } from "vue";

// 이벤트 정의
const emit = defineEmits(["update-sorting"]);

const isSortingByLike = ref(true); // 기본값: 인기순 정렬

// 정렬 기준 변경 시 이벤트 발생
function emitSortingChange() {
  const newSorting = isSortingByLike.value ? "like" : "new";
  emit("update-sorting", newSorting); // 부모 컴포넌트에 정렬 변경 알림
}
</script>

<style scoped></style>
