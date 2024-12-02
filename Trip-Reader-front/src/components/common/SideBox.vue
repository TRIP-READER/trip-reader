<script setup>
import { ref, onMounted, onBeforeMount, watch } from "vue";

const sideTheme = {
  colorLink: "#B17457",
  colorPrimary: "#D8D2C2",
  colorPrimaryTextActive: "#bd927b",
};
const width = ref(0);
const height = ref(0);
const handleResize = () => {
  width.value = window.innerWidth;
  height.value = window.innerHeight;
};
const sideBoxThreshold = ref(true);
onMounted(() => {
  window.addEventListener("resize", handleResize);
  handleResize();
});

onBeforeMount(() => {
  window.removeEventListener("resize", handleResize);
});

watch(width, (newWidth) => {
  if (newWidth < 1265) {
    sideBoxThreshold.value = false;
  } else {
    sideBoxThreshold.value = true;
  }
});
</script>

<template>
  <div v-if="sideBoxThreshold">
    <a-config-provider :theme="{ token: { ...sideTheme } }">
      <a-layout-sider class="siderStyle">
        <a-affix :offset-top="120" @change="change">
          <a-flex justify="center">
            <div class="boxStyle">
              <a-flex align="center" :vertical="true">
                <!-- <img src="/trip reader bear alone.png" :style="logoStyle" /> -->
                <slot />
              </a-flex>
            </div>
          </a-flex>
        </a-affix>
      </a-layout-sider>
    </a-config-provider>
  </div>
</template>

<style scoped>
.siderStyle {
  min-height: 120px;
  background-color: white;
  position: absolute;
  left: 0;
}

.boxStyle {
  background-color: #faf7f0;
  border-radius: 30px;
  width: 20vw;
  min-width: 200px;
  margin-left: 3vw;
  padding: 20px 0px;
}

/* const siderStyle = {
  minHeight: 120,
  backgroundColor: "white",
};

const boxStyle = {
  backgroundColor: "#FAF7F0",
  borderRadius: "30px",
  width: "90%",
  minWidth: "200px",
  margin: "0 10px 0 10px",
  padding: "20px 0px",
};

const logoStyle = {
  width: "40%",
  margin: "10px 0",
};

const sideTheme = {
  colorLink: "#B17457",
  colorPrimary: "#D8D2C2",
  colorPrimaryTextActive: "#bd927b",
}; */
</style>
