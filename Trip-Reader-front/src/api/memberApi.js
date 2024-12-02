import { getAxios } from "@/util/http-commons";

const local = getAxios();

async function postLogin(param, success, fail) {
  await local.post(`/member/login`, param).then(success).catch(fail);
}
async function postJoin(param, success, fail) {
  await local.post(`/member/join`, param).then(success).catch(fail);
}
async function getInfo(success, fail) {
  local.defaults.headers["access_token"] =
    sessionStorage.getItem("access_token");
  await local.get(`/member/info`).then(success).catch(fail);
}

async function getScrap(success, fail) {
  local.defaults.headers["access_token"] =
    sessionStorage.getItem("access_token");
  await local.get(`/member/scrap`).then(success).catch(fail);
}

async function getIsScrap(news_summary_nano, success, fail) {
  local.defaults.headers["access_token"] =
    sessionStorage.getItem("access_token");
  await local
    .get(`/member/isScraped/${news_summary_nano}`)
    .then(success)
    .catch(fail);
}

async function putInfo(param, success, fail) {
  local.defaults.headers["access_token"] =
    sessionStorage.getItem("access_token");
  await local.put(`/member/info`, param).then(success).catch(fail);
}

async function getIdValidation(id, success, fail) {
  await local.get(`/member/id_validation/${id}`).then(success).catch(fail);
}

async function getRefresh(success, fail) {
  local.defaults.headers["refresh_token"] =
    sessionStorage.getItem("refresh_token"); //axios header에 refresh_token 셋팅
  await local.get(`/member/refresh`).then(success).catch(fail);
}

async function getLogout(success, fail) {
  local.defaults.headers["access_token"] =
    sessionStorage.getItem("access_token");
  await local.get(`/member/logout`).then(success).catch(fail);
}

async function postFindPassword(param, success, fail) {
  await local.post(`/member/find_password`, param).then(success).catch(fail);
}

export {
  postLogin,
  postJoin,
  postFindPassword,
  getInfo,
  getScrap,
  putInfo,
  getIdValidation,
  getRefresh,
  getIsScrap,
  getLogout,
};
