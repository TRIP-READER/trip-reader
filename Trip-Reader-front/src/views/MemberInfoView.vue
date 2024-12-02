<script setup>
import AreaCascader from "@/components/common/AreaCascader.vue";
import { message } from "ant-design-vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { ref, reactive, onMounted, computed } from "vue";
import dayjs from "dayjs";
const dateFormat = "YYYY-MM-DD";
const memberStore = useMemberStore();
const { callGetInfo, callPutInfo, callGetIdValidation } = memberStore;
const { userInfo, needLoginMsg } = storeToRefs(memberStore);

const locationInfo = ref({
  location1: "",
  location2: "",
});

const formState = ref({
  id: "",
  password: "",
  name: "",
  email: "",
  areaCode: 0,
  sigunguCode: 0,
  birth: "",
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

const onClickSubmit = async () => {
  if (formState.value.password === "") {
    return;
  }
  formState.value.birth = formState.value.birth.format(dateFormat).toString();
  formState.value.mailing = formState.value.mailing ? 1 : 0;
  if (await callPutInfo(formState.value)) {
    alert("회원정보 수정이 완료되었습니다.");
    window.location.href = "/";
  } else {
    message.error("정보 수정 실패! 다시 시도해주세요.");
  }
};

const handleOnFinish = (values) => {
  message.info("정보 수정중..");
};

const handleFinishFailed = (errorInfo) => {
  message.error("입력을 제대로 확인해주세요!");
};

onMounted(async () => {
  needLoginMsg.value = "로그인이 필요한 서비스입니다.";
  await callGetInfo();
  formState.value = Object.assign({}, userInfo.value);
  formState.value.password = "";
  formState.value.passwordConfrim = "";
  formState.value.mailing = userInfo.value.mailing == 1;
  formState.value.birth = dayjs(userInfo.value.birth, dateFormat);
  // locationInfo.value.location1 = userInfo.value.area;
});

function handleRegionChange(region) {
  formState.value.area_code = region.areaCode;
  formState.value.sigungu_code = region.sigunguCode;

  locationInfo.value.location1 = region.labels[0];
  if (
    region.sigunguCode != null &&
    region.sigunguCode != "null" &&
    region.labels[1] != null
  ) {
    locationInfo.value.location2 = " / " + region.labels[1];
  } else {
    locationInfo.value.location2 = " ";
  }
}

const validatePassword = (rule, value) => {
  if (value !== formState.value.password) {
    return Promise.reject("비밀번호가 일치하지 않습니다.");
  } else {
    return Promise.resolve();
  }
};

const validateId = async (rule, value) => {
  if (await callGetIdValidation(value)) {
    return Promise.resolve();
  } else {
    return Promise.reject("아이디가 중복됩니다.");
  }
};

const rules = {
  id: [
    { required: true, message: "아이디를 입력해주세요" },
    { validator: validateId, trigger: "change" },
  ],
  password: [
    { required: true, message: "비밀번호를 입력해주세요" },
    { min: 8, message: "비밀번호는 최소 8글자여야 합니다." },
  ],
  passwordConfrim: [
    { required: true, message: "비밀번호 확인해주세요" },
    { min: 8, message: "비밀번호는 최소 8글자여야 합니다." },
    {
      validator: validatePassword,
      trigger: "change",
    },
  ],
  name: [{ required: true, message: "이름을 입력해주세요" }],
  email: [{ type: "email", message: "이메일 형식이 아닙니다." }],
};
</script>
<template>
  <a-layout class="outerStyle">
    <a-layout-content class="contentStyle">
      <a-config-provider :theme="{ ...headerTheme }">
        <div class="header">
          <span class="headerText"> 나의 페이지</span>
        </div>
        <a-divider style="height: 2px; background-color: #eeeeee" />
        <a-form
          class="formStyle"
          :model="formState"
          :label-col="{ span: 5, offset: 3 }"
          :wrapper-col="{ span: 10 }"
          @finish="handleOnFinish"
          @submit="onClickSubmit"
          @finishFailed="handleFinishFailed"
          :rules="rules"
        >
          <a-form-item label="아이디" name="id">
            <a-input v-model:value="formState.id" :disabled="true"> </a-input>
          </a-form-item>

          <a-form-item label="비밀번호" name="password">
            <a-input-password
              v-model:value="formState.password"
              :visibilityToggle="false"
            >
            </a-input-password>
          </a-form-item>
          <a-form-item label="비밀번호 확인" name="passwordConfrim">
            <a-input-password
              v-model:value="formState.passwordConfrim"
              :visibilityToggle="false"
            >
            </a-input-password>
          </a-form-item>

          <a-form-item label="이름" name="name">
            <a-input v-model:value="formState.name" placeholder="이름">
            </a-input>
          </a-form-item>

          <a-form-item label="이메일" name="email">
            <a-input v-model:value="formState.email" placeholder="email">
            </a-input>
          </a-form-item>

          <a-form-item label="생년월일" name="birth">
            <a-date-picker
              v-model:value="formState.birth"
              style="width: 100%"
            />
          </a-form-item>

          <a-form-item label="지역 선택" name="area">
            {{ locationInfo.location1 }}{{ locationInfo.location2 }}
            <AreaCascader
              :default-area="`${formState.area_code}`"
              :default-sigungu="`${formState.sigungu_code}`"
              @update-region="handleRegionChange"
            />
          </a-form-item>

          <a-from-item name="mailing">
            <a-checkbox
              v-model:checked="formState.mailing"
              style="margin-bottom: 3vh"
              >메일 수신 동의</a-checkbox
            >
          </a-from-item>
          <a-form-item :wrapper-col="{ span: 24 }">
            <a-button type="primary" htmlType="submit" class="form-button">
              정보 수정
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
