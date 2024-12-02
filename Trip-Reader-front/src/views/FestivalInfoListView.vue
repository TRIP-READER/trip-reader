<script setup>
import { ref, onMounted, watch, nextTick } from "vue";
import FestivalCalendar from "@/components/info/FestivalCalendar.vue";
import AreaCascader from "@/components/common/AreaCascader.vue";
import FestivalInfoList from "@/components/info/FestivalInfoList.vue";
import { getFestivalList } from "@/api/festivalApi";

const InfoList = ref([]); // 동적으로 로드할 뉴스 리스트
const locationInfo = ref({
  location1: "모든 지역",
  location2: "",
});

const selectDate = ref(null);
const page = ref(1);
const area = ref(0);
const sigungu = ref(null);
const loading = ref(false);
const noMoreData = ref(false);

const observer = ref(null); // IntersectionObserver 객체

const selectDateFunction = (selectedDate) => {
  selectDate.value = selectedDate;
};

// 지역 변경 처리
function handleRegionChange(region) {
  area.value = region.areaCode;
  sigungu.value = region.sigunguCode;

  locationInfo.value.location1 = region.labels[0];

  console.log(region.sigunguCode);

  if (
    region.sigunguCode != null &&
    region.sigunguCode != "null" &&
    region.labels[1] != null
  ) {
    locationInfo.value.location2 = " / " + region.labels[1];
  } else {
    locationInfo.value.location2 = " ";
  }

  fetchInfo(true); // 데이터를 새로 로드
}

async function fetchInfo(reset = false) {
  if (loading.value || (noMoreData.value && !reset)) return;
  if (reset) {
    InfoList.value = []; // 리스트 초기화
    page.value = 1; // 페이지 초기화
    noMoreData.value = false; // 데이터 없음 상태 초기화
  }

  loading.value = true;

  const params = {
    page: page.value,
    area: area.value,
    sigungu: sigungu.value,
    date: selectDate.value,
  };

  try {
    await getFestivalList(
      params,
      (response) => {
        if (response.data.length > 0) {
          InfoList.value = [...InfoList.value, ...response.data];
          page.value += 1;
          console.log(InfoList.value);
        } else {
          noMoreData.value = true;
        }
      },
      (error) => {
        console.error("뉴스 데이터를 가져오는 중 오류 발생:", error);
      }
    );
  } finally {
    loading.value = false;
  }
}

function setupObserver() {
  if (observer.value) observer.value.disconnect(); // 기존 observer 해제

  observer.value = new IntersectionObserver(
    async ([entry]) => {
      if (entry.isIntersecting && !loading.value && !noMoreData.value) {
        await fetchInfo();
      }
    },
    { threshold: 0.5 }
  );

  const sentinel = document.getElementById("scroll-sentinel");
  if (sentinel) observer.value.observe(sentinel); // 감지 대상 설정
}

watch(
  () => selectDate.value,
  () => {
    handleRegionChange({
      areaCode: 0,
      sigunguCode: null,
      labels: ["모든 지역", ""],
    });
  }
);

onMounted(async () => {
  await fetchInfo(true); // 첫 페이지 로드
  nextTick(() => {
    setupObserver(); // 무한 스크롤 감지 초기화
  });
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
</script>

<template>
  <a-layout>
    <div class="outer-container2">
      <h1 class="title hanken-grotesk-title">CALENDAR</h1>
      <div class="calendar-container">
        <FestivalCalendar @selectDate="selectDateFunction" />
      </div>

      <div class="list-container">
        <div class="header">
          <span class="headerText hanken-grotesk-title">
            {{ locationInfo.location1 }}{{ locationInfo.location2 }}</span
          >
          <div class="headerUtils">
            <a-config-provider
              :theme="{
                ...headerTheme,
              }"
            >
              <AreaCascader
                :add-all-areas="true"
                @update-region="handleRegionChange"
              />
            </a-config-provider>
          </div>
        </div>
        <a-divider style="height: 2px; background-color: #eeeeee" />
        <FestivalInfoList :festivalInfoList="InfoList" />
        <!-- 무한 스크롤 감지용 엘리먼트 -->
        <div
          id="scroll-sentinel"
          style="width: 100%; height: 1px; background: transparent"
        ></div>
        <div v-if="loading" class="loading">Loading...</div>
      </div>
    </div>
  </a-layout>
</template>

<style scoped>
/* 스타일은 동일 */
.headerUtils {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 1em;
  width: 100%;
}
.header {
  margin-top: 1em;
  color: #4a4947;
  text-align: left;
  width: 100%;
  line-height: 2.5em;
}
.headerText {
  font-size: 50px;
  font-weight: bold;
  font-family: "nanumgothic-ebold";
}
.title {
  font-size: 6vw;
  margin-top: 10px; /* 제목 위아래 간격 제거 */

  color: black;
}
.list-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 60vw;
  min-width: 800px;
}

.outer-container2 {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}
.calendar-container {
  display: flex;
  justify-content: center;
  padding: 20px;
  margin-bottom: 50px;
  min-width: 800px;
}
.loading {
  text-align: center;
  margin-top: 10px;
  color: gray;
}
</style>
