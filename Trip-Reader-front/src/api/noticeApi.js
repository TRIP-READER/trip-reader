import { getAxios } from "@/util/http-commons";

const local = getAxios();

async function getNoticeList(param, success, fail) {
  await local.get(`/notice/list`, { params: param }).then(success).catch(fail);
}

async function getNoticeDetail(param, success, fail) {
  await local.get(`/notice/${param}`).then(success).catch(fail);
}

export { getNoticeList, getNoticeDetail };
