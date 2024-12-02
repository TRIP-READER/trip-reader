<script setup>
import { ref, onMounted, nextTick, onUnmounted, watch } from "vue";
import { getNewsList } from "@/api/newsApi";
import ArticleList from "@/components/common/ArticleList.vue";
import AreaCascader from "@/components/common/AreaCascader.vue";
import SortingSelect from "@/components/common/SortingSelect.vue";

const hoverTimeout = ref(null); // 타이머 저장
const isActive = ref(false);
const darkmodeON = ref(false);
const newsList = ref([]); // 동적으로 로드할 뉴스 리스트
const locationInfo = ref({
  location1: "모든 지역",
  location2: "",
});
const haveSide = true;

// 페이지네이션과 상태 관리 변수
const page = ref(1);
const sorting = ref("like");
const area = ref(0);
const sigungu = ref(null);
const loading = ref(false);
const noMoreData = ref(false);

const observer = ref(null); // IntersectionObserver 객체

// API 호출 함수
async function fetchNews(reset = false) {
  if (loading.value || (noMoreData.value && !reset)) return;

  if (reset) {
    newsList.value = []; // 리스트 초기화
    page.value = 1; // 페이지 초기화
    noMoreData.value = false; // 데이터 없음 상태 초기화
  }

  loading.value = true;

  const params = {
    sorting: sorting.value,
    pageno: page.value,
    area_code: area.value,
    sigungu_code: sigungu.value,
  };

  try {
    await getNewsList(
      params,
      (response) => {
        if (response.data.length > 0) {
          newsList.value = [...newsList.value, ...response.data];
          page.value += 1;
          console.log(newsList.value);
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

// 무한 스크롤 처리
function setupObserver() {
  if (observer.value) observer.value.disconnect();

  observer.value = new IntersectionObserver(
    async ([entry]) => {
      if (entry.isIntersecting && !loading.value && !noMoreData.value) {
        await fetchNews();
      }
    },
    { threshold: 0.5 }
  );

  const sentinelId = darkmodeON.value ? "scroll-sentinel2" : "scroll-sentinel";
  const sentinel = document.getElementById(sentinelId);

  if (sentinel) {
    observer.value.observe(sentinel);
  } else {
    console.error("Sentinel element not found:", sentinelId);
  }
}

watch(darkmodeON, () => {
  nextTick(() => {
    setupObserver();
    fetchNews(true); // 다크 모드로 변경 시 데이터 새로 로드
  });
});

// 초기 데이터 로드 및 스크롤 이벤트 추가
onMounted(async () => {
  await fetchNews(true); // 첫 페이지 로드
  nextTick(() => {
    setupObserver(); // 무한 스크롤 감지 초기화
  });
});

// 컴포넌트가 제거될 때 observer 해제
onUnmounted(() => {
  if (observer.value) observer.value.disconnect();
});

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

  if (
    region.sigunguCode != null &&
    region.sigunguCode !== "null" &&
    region.labels[1] != null
  ) {
    locationInfo.value.location2 = " / " + region.labels[1];
  } else {
    locationInfo.value.location2 = " ";
  }

  fetchNews(true); // 데이터를 새로 로드
}

const headerTheme = {
  token: {
    colorPrimary: `black`,
    colorPrimaryHover: `#b17457`,
    colorTextQuaternary: `black`,
    colorTextQuaternaryHover: `#b17457`,
    colorTextTertiary: `#b17457`,
  },
};

const startHover = () => {
  // 2초 후 이벤트 실행
  hoverTimeout.value = setTimeout(() => {
    console.log("마우스를 2초 이상 올려두셨습니다!");
    isActive.value = true;
    setTimeout(() => {
      darkmodeON.value = true;
    }, 3000); // 2000ms = 2초
  }, 2000); // 2000ms = 2초
  setupObserver();
};

const stopHover = () => {
  clearTimeout(hoverTimeout.value); // 타이머 제거
};
</script>

<template>
  <Transition>
    <a-layout class="outerStyle" v-if="!darkmodeON">
      <a-layout-content class="contentStyle">
        <h1
          class="title hanken-grotesk-title"
          :class="{ 'animate__animated animate__hinge': isActive }"
          @mouseenter="startHover"
          @mouseleave="stopHover"
        >
          AI NEWS
        </h1>

        <div class="header">
          <span class="headerText hanken-grotesk-title">
            {{ locationInfo.location1 }}{{ locationInfo.location2 }}</span
          >
          <br />
          <div class="headerUtils">
            <a-config-provider :theme="{ ...headerTheme }">
              <AreaCascader
                :add-all-areas="true"
                @update-region="handleRegionChange"
              />
              <SortingSelect @update-sorting="handleSortingChange" />
            </a-config-provider>
          </div>
        </div>
        <a-divider style="height: 2px; background-color: #eeeeee" />
        <ArticleList :news-list="newsList" :have-side="haveSide" />
        <div
          id="scroll-sentinel"
          style="width: 100%; height: 1px; background: transparent"
        ></div>
        <div v-if="loading" class="loading">로딩 중...</div>
        <div v-if="noMoreData" class="no-more-data">
          더 이상 데이터가 없습니다.
        </div>
      </a-layout-content>
    </a-layout>
  </Transition>

  <a-layout class="outerStyle dark" v-if="darkmodeON">
    <a-layout-content class="contentStyle dark">
      <Transition appear>
        <h1 class="title hanken-grotesk-title dark">
          AI NEWS
          <div style="font-size: 0.3em; margin-bottom: 0; margin-left: 20px">
            DARK MODE
            <img
              style="width: 1.1em; margin: 0px 10px 10px 10px"
              src="/darkmodeicon.png"
            />
          </div>
        </h1>
      </Transition>
      <div class="header dark">
        <span class="headerText hanken-grotesk-title dark">
          {{ locationInfo.location1 }}{{ locationInfo.location2 }}</span
        >
        <br />
        <div class="headerUtils dark">
          <a-config-provider
            :theme="{
              ...headerTheme,
            }"
          >
            <AreaCascader
              :add-all-areas="true"
              :dark-mode="darkmodeON"
              @update-region="handleRegionChange"
            />
            <SortingSelect @update-sorting="handleSortingChange" />
          </a-config-provider>
        </div>
      </div>
      <a-divider style="height: 2px; background-color: #7f7d7d" />
      <ArticleList
        :news-list="newsList"
        :have-side="haveSide"
        :dark-mode="darkmodeON"
      />
      <div
        id="scroll-sentinel2"
        style="width: 100%; height: 1px; background: transparent"
      ></div>
      <div v-if="loading" class="loading">로딩 중...</div>
      <div v-if="noMoreData" class="no-more-data">
        더 이상 데이터가 없습니다.
      </div>
    </a-layout-content>
  </a-layout>
</template>

<style scoped>
.title {
  font-size: 6vw;
  margin-top: 10px; /* 제목 위아래 간격 제거 */
  color: black;
}

.loading {
  text-align: center;
  margin: 20px 0;
  font-size: 16px;
  color: #999;
}

.no-more-data {
  text-align: center;
  margin: 20px 0;
  font-size: 16px;
  color: #999;
}

.header {
  margin-top: 5em;
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

.contentStyle {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 60vw;
  min-width: 800px;
}

.headerUtils {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 1em;
  width: 100%;
}

.outerStyle {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
}

.dark {
  background-color: #181a1b;
  color: #bab4ab;
}
</style>
