import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import {
  postLogin,
  getInfo,
  putInfo,
  postJoin,
  getIdValidation,
  getRefresh,
  getLogout,
  getScrap,
  getIsScrap,
  postFindPassword,
} from "@/api/memberApi";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter();

  const isLogin = ref(sessionStorage.getItem("access_token") ? true : false);
  const needLogin = ref(false);
  const needLoginMsg = ref("");

  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(
    sessionStorage.getItem("access_token") ? true : false
  );
  const memberName = ref(
    sessionStorage.getItem("member_name")
      ? sessionStorage.getItem("member_name")
      : null
  );
  const removeInfos = () => {
    isLogin.value = false;
    userInfo.value = null;
    isValidToken.value = false;
    memberName.value = null;
    sessionStorage.removeItem("access_token");
    sessionStorage.removeItem("refresh_token");
    sessionStorage.removeItem("member_name");
  };

  const setInfos = (data) => {
    isLogin.value = true;
    isValidToken.value = true;
    let accessToken = data["access_token"];
    let refreshToken = data["refresh_token"];
    sessionStorage.setItem("access_token", accessToken);
    sessionStorage.setItem("refresh_token", refreshToken);
    if (data["name"]) {
      memberName.value = data["name"];
      sessionStorage.setItem("member_name", data["name"]);
    }
  };

  const callPostLogin = async (loginUser) => {
    await postLogin(
      loginUser,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          console.log("로그인 성공!!!!");
          setInfos(response.data);
        }
      },
      (error) => {
        console.log("로그인 실패!!!!");
        isLoginError.value = true;
        console.error(error);
        removeInfos();
      }
    );
  };

  const callGetInfo = async () => {
    await getInfo(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data;
        } else {
          console.log("유저 정보 없음!!!!");
        }
      },
      async (error) => {
        console.error("g[토큰 만료되어 사용 불가능.] : ", error);
        callGetLogout();
      }
    );
  };

  const callGetScrap = async () => {
    var res = null;
    await getScrap(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          res = response.data;
        }
      },
      async (error) => {
        console.error("스크랩 정보 없음!!!!");
      }
    );
    return res;
  };

  const callGetIsScrap = async (news_summary_nano) => {
    if (isLogin.value === false) {
      return false;
    }
    var res = false;
    await getIsScrap(
      news_summary_nano,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          res = response.data;
        }
      },
      (error) => {
        console.log(error);
      }
    );
    return res;
  };
  const callPutInfo = async (param) => {
    var res = false;

    await putInfo(param, (response) => {
      if (response.status === httpStatusCode.OK) {
        res = true;
      } else {
        res = false;
      }
    });
    await callGetInfo();
    memberName.value = userInfo.value.name;
    sessionStorage.setItem("member_name", memberName.value);

    return res;
  };

  const callGetIdValidation = async (id) => {
    var res = false;
    await getIdValidation(id, (response) => {
      if (response.status === httpStatusCode.OK) {
        res = response.data;
        if (userInfo.value != null && userInfo.value.id === id) {
          res = true;
        }
      }
    });
    return res;
  };

  const callGetRefresh = async () => {
    await getRefresh(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          setInfos(response.data);
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await callGetLogout();
        }
      }
    );
  };

  const callPostJoin = async (params) => {
    var res = false;
    await postJoin(params, (response) => {
      if (response.status === httpStatusCode.OK) {
        res = true;
      }
    });
    return res;
  };
  const callPostFindPassword = async (params) => {
    var res = false;
    await postFindPassword(
      params,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          res = true;
        }
      },
      (error) => {
        return;
      }
    );
    return res;
  };
  const callGetLogout = async () => {
    await getLogout(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          setInfos(response.data);
        } else {
          console.error("유저 정보 없음!!!!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
    removeInfos();
    router.refresh();
  };

  return {
    isLogin,
    isLoginError,
    userInfo,
    memberName,
    needLogin,
    needLoginMsg,
    isValidToken,
    callPostLogin,
    callPostJoin,
    callPostFindPassword,
    callGetInfo,
    callGetScrap,
    callGetIsScrap,
    callPutInfo,
    callGetIdValidation,
    callGetRefresh,
    callGetLogout,
  };
});
