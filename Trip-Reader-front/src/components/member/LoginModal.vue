<template>
  <div align="center">
    <img src="/trip reader bear alone.png" class="login-logo" />
    <div class="logo-text">TRIP READER</div>
    <div class="logo-text" style="margin-bottom: 25px">여행을 읽다</div>
  </div>
  <a-form :model="formState" name="normal_login" class="login-form">
    <a-form-item
      name="id"
      :rules="[{ required: true, message: '아이디를 입력해 주세요!' }]"
    >
      <a-input v-model:value="formState.id" placeholder="아이디"> </a-input>
    </a-form-item>

    <a-form-item
      name="password"
      :rules="[{ required: true, message: '비밀번호를 입력해 주세요!' }]"
    >
      <a-input-password
        v-model:value="formState.password"
        placeholder="비밀번호"
      >
      </a-input-password>
    </a-form-item>

    <a-form-item>
      <RouterLink
        :to="{ name: 'member-find-password' }"
        class="login-form-forgot"
        @click="$emit('closeModal')"
        >비밀번호 찾기</RouterLink
      >
    </a-form-item>

    <a-form-item>
      <div class="login-form-register">
        아직 회원이 아니신가요?
        <RouterLink :to="{ name: 'member-join' }" @click="$emit('closeModal')"
          >회원가입</RouterLink
        >
      </div>
    </a-form-item>
    <div v-show="!successLogin" align="center" style="color: red">
      아이디, 비밀번호를 확인해주세요
    </div>
    <a-form-item>
      <a-button
        :disabled="disabled"
        type="primary"
        html_type="submit"
        class="login-form-button"
        :loading="isLoading"
        @click="login"
      >
        로그인
      </a-button>
    </a-form-item>
  </a-form>
</template>

<script setup>
import { RouterLink } from "vue-router";
import { ref, reactive, computed } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { callPostLogin } = memberStore;

const successLogin = ref(true);
const isLoading = ref(false);

const memberDto = {
  id: "string",
  password: "string",
};

const login = async () => {
  isLoading.value = true;

  memberDto.id = formState.id;
  memberDto.password = formState.password;

  await callPostLogin(memberDto);

  if (isLogin.value) {
    successLogin.value = true;
    console.log("로그인 성공");
  } else {
    successLogin.value = false;
    console.log("로그인 실패");
  }

  isLoading.value = false;
};

const formState = reactive({
  id: "",
  password: "",
  remember: true,
});

const disabled = computed(() => {
  return !(formState.id && formState.password);
});
</script>
<style scoped>
.login-logo {
  width: 80px;
  margin-bottom: 3px;
}
.login-form-forgot {
  float: right;
}
.login-form-button {
  width: 100%;
}
.login-form-register {
  width: 100%;
  text-align: center;
}

.logo-text {
  font-family: "pyeongchang-bold";
  font-size: 20px;
  line-height: 21px;
}
</style>
