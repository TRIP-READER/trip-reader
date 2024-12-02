import { getAxios } from "@/util/http-commons";

const local = getAxios();

async function getFestivalCount(param, success, fail) {
  await local
    .get(`/festival/count`, { params: param })
    .then(success)
    .catch(fail);
}

async function getFestivalDetail(contentid, success, fail) {
  await local.get(`/festival/${contentid}`).then(success).catch(fail);
}

async function getFestivalList(param, success, fail) {
  await local
    .get(`/festival/list`, { params: param })
    .then(success)
    .catch(fail);
}

export { getFestivalCount, getFestivalDetail, getFestivalList };
