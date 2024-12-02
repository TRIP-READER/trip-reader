import ArticleDetailView from "@/views/ArticleDetailView.vue";
import MainPageView from "@/views/MainPageView.vue";
import NoticeListView from "@/views/NoticeListView.vue";
import ArticleListView from "@/views/ArticleListView.vue";
import NoticeDetailView from "@/views/NoticeDetailView.vue";
import FestivalInfoListView from "@/views/FestivalInfoListView.vue";
import FestivalInfoDetailView from "@/views/FestivalInfoDetailView.vue";

export default [
  {
    path: "/",
    name: "main",
    component: MainPageView,
    // component: () => import("@/views/TestView.vue"),
  },
  {
    path: "/notice",
    name: "notice",
    component: NoticeListView,
  },
  {
    path: "/notice/:noticeNano",
    name: "notice-detail-view",
    component: NoticeDetailView,
  },

  {
    path: "/article/:articlenano",
    name: "aricle-detail-view",
    component: ArticleDetailView,
  },
  {
    path: "/article",
    name: "article",
    component: ArticleListView,
  },

  {
    path: "/festivalinfo",
    name: "festival-info-list",
    component: FestivalInfoListView,
  },

  {
    path: "/festival/:festivalContentId",
    name: "festival-info-detail",
    component: FestivalInfoDetailView,
  },
];
