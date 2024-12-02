<script setup>
import { festivalColumnInfo } from "@/assets/festivalInfo";
import { ref, watch } from "vue";
import MapComponent from "@/components/common/MapComponent.vue";
import { areaData, regionData } from "@/assets/areaCodes";
const props = defineProps({
  festivalDetail: Object,
});

const getColumnName = (columnName) => {
  return festivalColumnInfo[columnName];
};
const setDetailInfo = (festivalDetail) => {
  festivalDetailInfo.value = [];
  festivalMarker.value = {};
  Object.keys(festivalDetail).forEach((key) => {
    if (festivalDetail[key] === null || festivalDetail[key] === "") return;
    if (festivalColumnInfo[key] === null) {
      if (key.includes("map")) festivalMarker.value[key] = festivalDetail[key];
      if (key === "firstimage") festivalImage.value = festivalDetail[key];

      return;
    }
    if (key === "title") {
      festivalTitle.value = festivalDetail[key];
      festivalMarker.value[key] = festivalDetail[key];
      return;
    }
    if (key === "overview") {
      festivalOverview.value = festivalDetail[key];
      return;
    }
    if (key == "areaCode") {
      const areaLabel = areaData.filter(
        (area) => area.value == festivalDetail["areaCode"]
      )[0].label;
      festivalDetailInfo.value.push({
        columnName: getColumnName(key),
        content: areaLabel,
      });
      return;
    }
    if (key == "sigunguCode") {
      const areaCode = areaData.filter(
        (area) => area.value == festivalDetail["areaCode"]
      )[0].value;
      festivalDetail["areaCode"].value;
      var content = null;
      regionData[festivalDetail["areaCode"]].forEach((element) => {
        if (element.value == festivalDetail[key]) content = element;
      });
      festivalDetailInfo.value.push({
        columnName: getColumnName(key),
        //area data에 있는 지역 코드를 이용해 지역 이름을 가져옴
        content: content.label,
      });
      return;
    }
    festivalDetailInfo.value.push({
      columnName: getColumnName(key),
      content: festivalDetail[key],
    });
  });
};

const festivalOverview = ref("");
const festivalTitle = ref("");
const festivalDetailInfo = ref([]);
const festivalMarker = ref(null);
const festivalImage = ref("");
const mapRender = ref(false);

watch(props, (newProp) => {
  setDetailInfo(newProp.festivalDetail);
  mapRender.value = true;
});
</script>

<template>
  <a-layout-content class="outerStyle">
    <div class="mainContentStyle">
      <div class="headerStyle">
        <h1>{{ festivalTitle }}</h1>
      </div>
      <a-divider style="margin-bottom: 40px" />
      <div v-if="festivalImage" class="imageWrapper">
        <img :src="festivalImage" class="festivalImage" />
      </div>
      <h2>개요</h2>
      <p v-html="festivalOverview"></p>
      <h2>정보</h2>
      <MapComponent :main-marker="festivalMarker" :map-render="mapRender" />
      <div class="ulWrapper">
        <ul class="liWrapper">
          <li
            v-for="item in festivalDetailInfo"
            :key="item.columnName"
            class="responsiveItem"
          >
            <strong>{{ item.columnName }}</strong>
            <span v-html="item.content"></span>
          </li>
        </ul>
      </div>
    </div>
  </a-layout-content>
</template>

<style scoped>
h1 {
  font-size: 64px;
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
p {
  font-family: "bookk-light";
  color: #4a4947;
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
  max-width: 100%; /* 부모 너비 제한 */
}
.festivalImage {
  margin-bottom: 3vh;
  max-height: 60vh;
  max-width: 100%; /* 컨테이너 너비를 넘지 않도록 */
  width: auto; /* 이미지 비율 유지 */
  height: auto; /* 이미지 비율 유지 */
}

.ulWrapper {
  border-style: solid 1px #7e7e7e; /* 테두리 스타일 */
  font-family: "flightsans-regular";
}

.liWrapper {
  display: grid; /* Grid 레이아웃 사용 */
  grid-template-columns: repeat(
    auto-fit,
    minmax(300px, 1fr)
  ); /* 열 너비와 반응형 설정 */
  gap: 20px; /* 항목 간 간격 */
  margin-top: 1vh;
  list-style-type: disc; /* 디스크 스타일 유지 */
  list-style-position: inside; /* 점을 li 안으로 이동 */
  padding-inline-start: 20px; /* 기본 패딩 */
}

.responsiveItem {
  display: flex; /* Flexbox 사용 */
  flex-direction: row; /* 가로 정렬 */
  align-items: center; /* 수직 정렬 */
}

.responsiveItem strong {
  margin-right: 8px; /* 콜론 앞 간격 */
  white-space: nowrap; /* 텍스트 줄바꿈 방지 */
}

.responsiveItem span {
  white-space: nowrap; /* 텍스트 줄바꿈 방지 */
}
</style>
