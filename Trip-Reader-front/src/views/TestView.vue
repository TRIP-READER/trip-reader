<script setup>
import { defineProps } from "vue";

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
  articleId: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link
    href="https://fonts.googleapis.com/css2?family=Roboto+Condensed&family=Roboto:wght@300&display=swap"
    rel="stylesheet"
  />
  <link
    rel="stylesheet"
    href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0"
  />

  <main>
    <div class="card">
      <img
        src="https://images.unsplash.com/photo-1656618020911-1c7a937175fd?crop=entropy&cs=tinysrgb&fm=jpg&ixid=MnwzMjM4NDZ8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTc1MzQyNTE&ixlib=rb-1.2.1&q=80"
        alt="축제 이미지"
      />
      <div class="card-content">
        <h2>{{ title }}</h2>
        <p>{{ description }}</p>
        <a :href="'/articles/' + articleId" class="button">
          기사 요약 보기
          <span class="material-symbols-outlined">arrow_right_alt</span>
        </a>
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 50px;
  font-family: "Roboto", sans-serif;
}

.card {
  width: 24rem;
  height: 36rem;
  border-radius: 10px;
  overflow: hidden;
  cursor: pointer;
  position: relative;
  color: rgb(240, 240, 240);
  box-shadow: 0 10px 30px 5px rgba(0, 0, 0, 0.2);

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

  h2 {
    position: absolute;
    inset: auto auto 30px 30px;
    margin: 0;
    transition: inset 0.3s 0.3s ease-out;
    font-family: "Roboto Condensed", sans-serif;
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
