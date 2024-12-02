<script setup>
import AreaCascader from "../common/AreaCascader.vue";
import SortingSelect from "../common/SortingSelect.vue";

defineProps({
  locationInfo: Object,
});

const headerTheme = {
  token: {
    colorPrimary: `black`,
    colorPrimaryHover: `#b17457`,
    colorTextQuaternary: `black`,
    colorTextQuaternaryHover: `#b17457`,
    colorTextTertiary: `#b17457`,
  },
};

// 정렬 기준 변경 처리
function handleSortingChange(newSorting) {
  sorting.value = newSorting; // 새로운 정렬 기준 설정
  fetchNews(true); // 데이터를 새로 로드
}

// 지역 변경 처리
function handleRegionChange(region) {
  area.value = region.areaCode;
  sigungu.value = region.sigunguCode;

  locationInfo.value.location1 = region.labels[0];

  console.log(region.sigunguCode);

  if (region.sigunguCode != null) {
    locationInfo.value.location2 = " / " + region.labels[1];
  } else {
    locationInfo.value.location2 = " ";
  }

  fetchNews(true); // 데이터를 새로 로드
}
</script>

<template>
  <div class="header">
    <span class="headerText">
      {{ locationInfo.location1 }}{{ locationInfo.location2 }}</span
    >
    <br />
    <div class="headerUtils">
      <a-config-provider
        :theme="{
          ...headerTheme,
        }"
      >
        <AreaCascader @update-region="handleRegionChange" />
        <SortingSelect @update-sorting="handleSortingChange" />
      </a-config-provider>
    </div>
  </div>
</template>

<style scoped>
.header {
  margin-top: 5em;

  color: #4a4947;
  text-align: left;
  width: 100%;
  line-height: 2.5em;
}

.headerText {
  font-size: 50px;
  font-family: "nanumgothic-bold";
}
.headerUtils {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 1em;
  width: 100%;
}
</style>
