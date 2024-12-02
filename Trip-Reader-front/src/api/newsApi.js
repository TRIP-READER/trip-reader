import { getAxios } from "@/util/http-commons";

const local = getAxios();

async function getNewsList(param, success, fail) {
  await local.get(`/news/list`, { params: param }).then(success).catch(fail);
}

async function getNewsSummary(newsNano, success, fail) {
  await local.get(`/news/${newsNano}`).then(success).catch(fail);
}

async function postScrap(params, success, fail) {
  local.defaults.headers["access_token"] =
    sessionStorage.getItem("access_token");
  await local
    .post(`/member/scrap`, { ...params })
    .then(success)
    .catch(fail);
}

async function getNewsMain(success, fail) {
  await local.get(`/main`).then(success).catch(fail);
}

export { getNewsSummary, getNewsList, getNewsMain, postScrap };
