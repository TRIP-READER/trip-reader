<template>
  <div class="news-list">
    <h1>뉴스 리스트</h1>
    <div v-for="news in newsList" :key="news.newsSummaryNano" class="news-item">
      <img :src="news.image" :alt="news.title" class="news-image" />
      <div class="news-content">
        <h2>{{ news.title }}</h2>
        <p>{{ news.header }}</p>
        <small>{{ news.prettyTime }}</small>
      </div>
    </div>
    <div v-if="loading" class="loading">로딩 중...</div>
    <div v-if="noMoreData" class="no-more-data">더 이상 데이터가 없습니다.</div>
  </div>
</template>

<script>
import { getNewsList } from "@/api/newsApi";

export default {
  data() {
    return {
      newsList: [], // 뉴스 데이터 리스트
      page: 1, // 기본 페이지 번호
      sorting: "new", // 기본 정렬 기준
      area: 0, // 기본 지역 코드
      sigungu: null, // 기본 시군구 값
      loading: false, // 로딩 상태
      noMoreData: false, // 더 이상 데이터가 없는 경우
    };
  },
  created() {
    this.fetchNews(); // 컴포넌트 생성 시 첫 데이터 로드
    window.addEventListener("scroll", this.handleScroll); // 스크롤 이벤트 추가
  },
  beforeUnmount() {
    window.removeEventListener("scroll", this.handleScroll); // 컴포넌트 제거 시 스크롤 이벤트 제거
  },
  methods: {
    async fetchNews() {
      console.log("fetchNews 호출");
      if (this.loading || this.noMoreData) return;

      this.loading = true;

      const params = {
        sorting: this.sorting,
        pageno: this.page,
        area_code: this.area,
        sigungu_code: this.sigungu,
      };

      try {
        await getNewsList(
          params,
          (response) => {
            if (response.data.length > 0) {
              this.newsList = [...this.newsList, ...response.data];
              this.page += 1;
            } else {
              this.noMoreData = true;
            }
          },
          (error) => {
            console.error("뉴스 데이터를 가져오는 중 오류 발생:", error);
          }
        );
      } finally {
        this.loading = false;
      }
    },
    handleScroll() {
      const scrollPosition = window.innerHeight + window.scrollY;
      const bottomPosition = document.documentElement.offsetHeight - 100;

      if (scrollPosition >= bottomPosition) {
        this.fetchNews();
      }
    },
  },
};
</script>

<style scoped>
.news-list {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.news-item {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 1px solid #ddd;
  padding-bottom: 10px;
}

.news-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 10px;
}

.news-content {
  flex: 1;
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
</style>
