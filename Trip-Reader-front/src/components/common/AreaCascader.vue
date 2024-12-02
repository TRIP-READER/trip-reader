<script setup>
import { ref, onMounted, watch } from "vue";
import { areaData, regionData } from "@/assets/areaCodes";

// 이벤트 정의
const props = defineProps({
  addAllAreas: Boolean,
  defaultArea: {
    type: String,
    default: "0",
  },
  defaultSigungu: {
    type: String,
    default: null,
  },
  darkMode: Boolean,
});
const emit = defineEmits(["update-region"]);
const options = ref(areaData);
// 선택된 값
const value = ref([]);
onMounted(() => {
  if (props.addAllAreas) {
    options.value[0] = { value: "0", label: "모든 지역", isLeaf: false };
  }
});

watch(props, (newProps) => {
  if (props.defaultArea) {
    value.value = [newProps.defaultArea];
    if (props.defaultSigungu) {
      value.value = [newProps.defaultArea, newProps.defaultSigungu];
    }
  }
  handleRegionChange();
});

// 동적으로 데이터 로드하는 함수
const loadData = (selectedOptions) => {
  const targetOption = selectedOptions[selectedOptions.length - 1];
  targetOption.loading = true;

  // 비동기 데이터 로드
  setTimeout(() => {
    targetOption.loading = false;
    targetOption.children = regionData[targetOption.value] || [];
    options.value = [...options.value]; // 상태 갱신
  }, 300);
};

// 지역 선택 시 이벤트 emit
const handleRegionChange = () => {
  if (value.value.length === 0) return;

  const areaCode = value.value[0] || null;
  const sigunguCode = value.value[1] || null;
  // 선택된 옵션의 label을 찾기
  const selectedLabels = value.value.map((val, index) => {
    let ret = null;

    if (index == 0)
      return options.value.find((opt) => opt.value === val)?.label || null;
    else {
      if (!regionData[value.value[0]].find((opt) => opt.value === val)) {
        return null;
      } else {
        return (
          regionData[value.value[0]].find((opt) => opt.value === val)?.label ||
          null
        );
      }
    }
  });

  emit("update-region", {
    areaCode,
    sigunguCode,
    labels: selectedLabels, // label 배열 추가
  });
};
</script>

<template>
  <a-config-provider
    :theme="{
      token: {
        colorPrimary: '#d8d2c2',
        borderRadius: `0px`,
        fontFamily: `pyeongchang-regular`,
      },
    }"
  >
    <a-cascader
      v-model:value="value"
      :options="options"
      :load-data="loadData"
      change-on-select
      @change="handleRegionChange"
    >
      <a-button
        type="primary"
        class="text tr-font-pc-b"
        shape="round"
        size="small"
        color="black"
        >도시 변경</a-button
      >
    </a-cascader>
  </a-config-provider>
</template>
<style scoped>
a {
  color: #bd927b;
}
a:hover {
  color: #d8d2c2;
}
a:active {
  color: #8a523d;
}

.text {
  font-size: 1em;
}
</style>
