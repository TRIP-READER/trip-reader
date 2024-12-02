<script setup>
import { RouterView } from "vue-router";
import NavBar from "./components/common/NavBar.vue";
import footerbar from "./components/common/FooterBar.vue";
// import TestView from "./views/TestView.vue";
</script>

<template>
  <div>
    <a-config-provider :theme="{ token: { colorBgLayout: `#00000000` } }">
      <a-layout class="test">
        <NavBar />
        <a-layout>
          <RouterView />
        </a-layout>
      </a-layout>
      <footerbar />
    </a-config-provider>
  </div>
</template>

<style scoped>
.test {
  /* background: rgb(216, 210, 194);
  background: linear-gradient(
    180deg,
    rgba(216, 210, 194, 1) 0%,
    rgba(255, 255, 255, 1) 68%
  ); */
}
</style>

<!-- https://antdv.com/components/layout -->
<!--
<a-layout>
  <a-layout-header :style="headerStyle">Header</a-layout-header>
  <a-layout>
    <a-layout-sider :style="siderStyle">Sider</a-layout-sider>
    <a-layout-content :style="contentStyle">Content</a-layout-content>
  </a-layout>
  <a-layout-footer :style="footerStyle">Footer</a-layout-footer>
</a-layout>
-->

<!--

<script setup>
const contentStyle = {
  textAlign: 'center',
  minHeight: 120,
  lineHeight: '120px',
  color: '#fff',
  backgroundColor: '#108ee9',
};
const siderStyle = {
  textAlign: 'center',
  lineHeight: '120px',
  color: '#fff',
  backgroundColor: '#3ba0e9',
};
</script>

<template>
  <a-layout-sider :style="siderStyle">Sider</a-layout-sider>

  <a-layout-content :style="contentStyle">
    <div>
      <h1>this is main</h1>
    </div>
  </a-layout-content>

</template>

<style scoped>

</style>

-->
