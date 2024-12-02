<script setup>
import { defineProps } from "vue";
import { textLengthOverCut } from "@/util/string-util";

// Prop 정의
defineProps({
  title: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  articlenano: {
    type: String,
    required: true,
  },
  image: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <main>
    <RouterLink
      :to="{
        name: 'aricle-detail-view',
        params: { articlenano: articlenano },
      }"
    >
      <div class="card">
        <img :src="image" alt="축제 이미지" />
        <div class="card-overlay"></div>
        <div class="card-content">
          <h2>{{ title }}</h2>
          <p>{{ textLengthOverCut(description, 120) }}</p>
          <a class="button">
            기사 요약 보기
            <span> ⮕</span>
          </a>
        </div>
      </div>
    </RouterLink>
  </main>
</template>

<style scoped>
main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 7px;
  font-family: "hankengrotesk";
}

.card {
  width: 20vw;
  height: 15vw;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  position: relative;
  color: rgb(240, 240, 240);
  box-shadow: 0 10px 30px 5px rgba(0, 0, 0, 0.2);
  font-size: 1vw;

  img {
    position: absolute;
    object-fit: cover;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    opacity: 0.9;
    transition: opacity 0.2s ease-out;
  }

  .card-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0);
    transition: background-color 0.3s ease-in-out;
  }

  &:hover .card-overlay {
    background-color: rgba(0, 0, 0, 0.6);
  }

  h2 {
    position: absolute;
    inset: auto auto 30px 30px;
    margin: 0;
    transition: inset 0.3s 0.3s ease-out;
    font-family: "hankengrotesk";
    font-weight: normal;
    text-transform: uppercase;
  }

  p,
  a {
    position: absolute;
    opacity: 0;
    max-width: 80%;
    transition: opacity 0.3s ease-out;
  }

  p {
    inset: auto auto 80px 30px;
  }

  a {
    inset: auto auto 40px 30px;
    color: inherit;
    text-decoration: none;
  }

  &:hover h2 {
    inset: auto auto 220px 30px;
    transition: inset 0.3s ease-out;
  }

  &:hover p,
  &:hover a {
    opacity: 1;
    transition: opacity 0.5s 0.1s ease-in;
  }

  &:hover img {
    transition: opacity 0.3s ease-in;
    opacity: 1;
  }
}

.material-symbols-outlined {
  vertical-align: middle;
}
</style>
