<script setup>
import AreaCascader from "@/components/common/AreaCascader.vue";
import { message } from "ant-design-vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { ref, reactive, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import dayjs from "dayjs";
const dateFormat = "YYYY-MM-DD";
const customFormat = (value) => {
  return value.format(dateFormat);
};
const memberStore = useMemberStore();
const { callGetIdValidation, callPostJoin } = memberStore;
const { userInfo, needLogin, needLoginMsg } = storeToRefs(memberStore);

const locationInfo = ref({
  location1: "거주하는 지역을 입력해주세요",
  location2: "",
});
const router = useRouter();
const formState = ref({
  id: "",
  password: "",
  name: "",
  email: "",
  areaCode: 0,
  sigunguCode: 0,
  birth: dayjs("2000-01-01", dateFormat),
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
  formState.value.birth = formState.value.birth.format(dateFormat).toString();
  formState.value.mailing = formState.value.mailing ? 1 : 0;
  console.log(formState.value);
  if (await callPostJoin(formState.value)) {
    setTimeout(() => {}, 1000);
    needLoginMsg.value = "회원가입이 완료되었습니다.";
    isLoading.value = false;

    alert("회원가입이 완료되었습니다.");
    window.location.href = "/";
    // router.go(-1);
  } else {
    message.error("회원가입 실패! 다시 시도해주세요.");
    isLoading.value = false;
  }
};

const handleFinishFailed = (errorInfo) => {
  message.error("입력을 제대로 확인해주세요!");
};

onMounted(async () => {
  formState.value.passwordConfrim = "";
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
  if (value.length < 4) {
    return;
  }

  if (await callGetIdValidation(value)) {
    return Promise.resolve();
  } else {
    return Promise.reject("아이디가 중복됩니다.");
  }
};

const rules = {
  id: [
    { required: true, message: "아이디를 입력해주세요" },
    { min: 4, message: "아이디는 최소 4글자여야 합니다." },
    { validator: validateId, trigger: "change" },
  ],
  password: [
    { required: true, message: "비밀번호를 입력해주세요" },
    { min: 8, message: "비밀번호는 최소 8글자여야 합니다." },
  ],
  passwordConfrim: [
    { required: true, message: "비밀번호 확인해주세요" },
    {
      validator: validatePassword,
      trigger: "change",
    },
  ],
  birth: [{ required: true, message: "생년월일을 입력해주세요" }],
  name: [{ required: true, message: "이름을 입력해주세요" }],
  email: [{ type: "email", message: "이메일 형식이 아닙니다." }],
};
</script>
<template>
  <a-layout class="outerStyle">
    <a-layout-content class="contentStyle">
      <a-config-provider :theme="{ ...headerTheme }">
        <div class="header">
          <span class="headerText"> 회원가입</span>
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

          <a-form-item label="비밀번호" name="password">
            <a-input-password
              v-model:value="formState.password"
              placeholder="비밀번호를 입력해주세요"
              :visibilityToggle="false"
            >
            </a-input-password>
          </a-form-item>
          <a-form-item label="비밀번호 확인" name="passwordConfrim">
            <a-input-password
              v-model:value="formState.passwordConfrim"
              placeholder="비밀번호를 입력해주세요"
              :visibilityToggle="false"
            >
            </a-input-password>
          </a-form-item>

          <a-form-item label="이름" name="name">
            <a-input v-model:value="formState.name" placeholder="이름">
            </a-input>
          </a-form-item>

          <a-form-item label="이메일" name="email">
            <a-input
              v-model:value="formState.email"
              placeholder="email을 입력해주세요"
            >
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
              회원 가입
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
