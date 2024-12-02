import MemberInfoView from "@/views/MemberInfoView.vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import MemberScrapView from "@/views/MemberScrapView.vue";
import MemberJoinView from "@/views/MemberJoinView.vue";
import MemberFindPasswordView from "@/views/MemberFindPasswordView.vue";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { isValidToken, isLogin, needLogin } = storeToRefs(memberStore);

  if (!isValidToken.value || isLogin.value != true) {
    needLogin.value = true;
    next({ name: "main" });
  } else {
    next();
  }
};

export default [
  {
    path: "/member",
    children: [
      {
        path: "/member/info",
        name: "member-info",
        component: MemberInfoView,
        beforeEnter: onlyAuthUser,
      },
      {
        path: "/member/scrap",
        name: "member-scrap",
        component: MemberScrapView,
        beforeEnter: onlyAuthUser,
      },
      {
        path: "/member/join",
        name: "member-join",
        component: MemberJoinView,
      },
      {
        path: "/member/find-password",
        name: "member-find-password",
        component: MemberFindPasswordView,
      },
    ],
  },
];
