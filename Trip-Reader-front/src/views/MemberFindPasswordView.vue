<script setup>
import { message } from "ant-design-vue";
import { ref } from "vue";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { callPostFindPassword } = memberStore;
const formState = ref({
  id: "",
  email: "",
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
const isLoading = ref(false);
const onClickSubmit = async () => {
  isLoading.value = true;
  if (await callPostFindPassword(formState.value)) {
    isLoading.value = false;
    alert("임시 비밀번호가 메일로 발송되었습니다. 메일함을 확인해주세요");
    window.location.href = "/";
    // router.go(-1);
  } else {
    message.error("회원 조회 실패! 입력을 확인해주세요");
    isLoading.value = false;
  }
};

const handleFinishFailed = (errorInfo) => {
  message.error("입력을 제대로 확인해주세요!");
};

const rules = {
  id: [{ required: true, message: "아이디를 입력해주세요" }],

  email: [
    { type: "email", message: "이메일 형식이 아닙니다." },
    { required: true, message: "이메일을 입력해주세요" },
  ],
};
</script>
<template>
  <a-layout class="outerStyle">
    <a-layout-content class="contentStyle">
      <a-config-provider :theme="{ ...headerTheme }">
        <div class="header">
          <span class="headerText"> 비밀번호 찾기</span>
        </div>
        <a-divider style="height: 2px; background-color: #eeeeee" />
        <a-form
          class="formStyle"
          :model="formState"
          :label-col="{ span: 5, offset: 3 }"
          :wrapper-col="{ span: 10 }"
          @submit="onClickSubmit"
          @finishFailed="handleFinishFailed"
          :rules="rules"
        >
          <a-form-item label="아이디" name="id">
            <a-input
              v-model:value="formState.id"
              placeholder="아이디를 입력해주세요"
            >
            </a-input>
          </a-form-item>

          <a-form-item label="이메일" name="email">
            <a-input
              v-model:value="formState.email"
              placeholder="email을 입력해주세요"
            >
            </a-input>
          </a-form-item>

          <a-form-item :wrapper-col="{ span: 24 }">
            <a-button
              type="primary"
              htmlType="submit"
              class="form-button"
              :loading="isLoading"
            >
              비밀번호 찾기
            </a-button>
          </a-form-item>
        </a-form>
      </a-config-provider>
    </a-layout-content>
  </a-layout>
</template>

<style scoped>
.formStyle {
  width: 100%;
}

.form-button {
  width: 100%;
  height: 5vh;
  min-height: 43px;
  margin-top: 1vh;
}
.header {
  margin-top: 5em;
  font-family: "pyeongchang-regular";
  font-weight: lighter;
  color: #4a4947;
  text-align: left;
  width: 100%;
  line-height: 2.5em;
}

.headerText {
  font-size: 50px;
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
</style>
