<template>
  <div class="heavn-one-container">
    <header class="header-section">
      <p class="date hanken-grotesk-content">
        Site of the Day - {{ currentDate }}
      </p>
      <h1 class="header-title hanken-grotesk-title">TRIP READER</h1>
      <div class="credits">
        <div class="circle-images hanken-grotesk-content">
          <a class="circle-item" href="https://ssafy.com" target="_blank">
            <img src="/ssafy.png" alt="SSAFY Logo" />
            <p>SSAFY</p>
          </a>
          <a
            class="circle-item"
            href="https://github.com/heo-hyunjun"
            target="_blank"
          >
            <img src="/heo-hyunjun.jpeg" alt="Profile Heo Hyunjun" />
            <p>@heo-hyunjun</p>
          </a>
          <a
            class="circle-item"
            href="https://github.com/wavewwave20"
            target="_blank"
          >
            <img src="/wavewwave20.jpg" alt="Profile wavewwave20" />
            <p>@wavewwave20</p>
          </a>
        </div>
      </div>
    </header>

    <main class="main-content">
      <div class="image-container">
        <div class="image-section">
          <img class="fade-image" src="/black_bear_sitting_at_a_desk.png" />
          <div class="overlay-text">
            <!-- <h2>TRIP READER</h2> -->
            <p class="hanken-grotesk-content">"Read, dream, and wander</p>
            <p class="hanken-grotesk-content">with TRIP READER."</p>
          </div>
        </div>
      </div>
    </main>

    <div class="header-section2">
      <h1 class="header-title hanken-grotesk-content">여행을 읽다.</h1>
      <p class="hanken-grotest-content desc">
        The easiest way to get inspiration
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "HeavnOneComponent",
  data() {
    return {
      currentDate: new Date().toLocaleDateString("en-US", {
        year: "numeric",
        month: "short",
        day: "numeric",
      }),
    };
  },
};
</script>

<style scoped>
.fade-image {
  width: 100%; /* 이미지 크기 */
  max-width: 100vh;
  border-radius: 15px; /* 부드러운 모서리 */
  mask-image: radial-gradient(
    circle,
    rgba(0, 0, 0, 1) 50%,
    rgba(0, 0, 0, 0) 70%
  );
  -webkit-mask-image: radial-gradient(
    circle,
    rgba(0, 0, 0, 1) 50%,
    rgba(0, 0, 0, 0) 70%
  );
  mask-repeat: no-repeat;
  mask-position: center;
  -webkit-mask-repeat: no-repeat;
  -webkit-mask-position: center;
}

.desc {
  font-size: 1.2vw;
  color: #000000;
  margin-bottom: 8vh;
  text-align: center;
  padding: 20px;
}
.heavn-one-container {
  font-family: Arial, sans-serif;
  background-color: #ffffff;
  color: #333;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  margin: 0;
}

h1 {
  margin-bottom: 0;
}

.header-section {
  background-color: #d8d2c2;
  text-align: center;
  padding: 20px;
  width: 100%;
  /* box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); */
  padding-top: 3.5vw;
}

.header-section2 {
  background-color: #ffffff;
  padding-top: 6vw;
}

.date {
  font-size: 1.2vw;
  color: #000000;
  margin-bottom: 10px;
  font-weight: bold;
}

.header-title {
  font-size: 8vw;
  font-weight: bold;
  color: #000000;
}

.credits {
  margin-top: 20px;
}

.circle-images {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 2vw; /* 아이템 간 간격 */
}

.circle-item {
  display: flex; /* 가로 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
  text-align: left; /* 텍스트 정렬 */
  gap: 1vw; /* 사진과 텍스트 간격 */
}
.circle-item:hover {
  color: #000; /* 호버 시 텍스트 색상 */
}
.circle-item p {
  font-size: 1.2vw;
  margin: 0;
  transition: color 0.3s ease;
}
.circle-item:hover p {
  color: #000; /* 호버 시 텍스트 색상 변경 */
}
.circle-item:hover img {
  filter: grayscale(0%); /* 호버 시 이미지 원래 색상 복원 */
}

.circle-item img {
  width: 2.2vw;
  height: 2.2vw;
  border-radius: 50%;
  object-fit: cover;
  filter: grayscale(100%);
}

.circle-item p {
  font-size: 1.2vw;
  color: #333;
  margin: 0; /* 텍스트 여백 제거 */
}

.main-content {
  flex: 1;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
  padding-bottom: 70px;
  background: rgb(216, 210, 194);
  background: linear-gradient(
    180deg,
    rgba(216, 210, 194, 1) 0%,
    rgba(255, 255, 255, 1) 99%
  );
}

.image-section {
  position: relative;
  width: 90vw;
  border-radius: 10px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #221c1c; /* 배경색 */
  padding: 15vh;
}

.image-section img {
  width: 100%;
  height: auto;
  display: block;
}

.overlay-text {
  position: absolute;
  top: 30%;
  right: 10%;
  transform: translateY(-10%);
  color: white;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.7);
  opacity: 0.7;
  text-align: left;
}

.overlay-text h2 {
  font-size: 3vw;
  margin-bottom: 10px;
}

.overlay-text p {
  font-size: 1.5vw;
  margin: 5px 0;
}

.footer {
  text-align: center;
  padding: 20px;
  background-color: #fff;
  width: 100%;
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
}

.footer h3 {
  font-size: 1.8vw;
  color: #333;
}

@media (max-width: 768px) {
  .header-title {
    font-size: 5vw;
  }

  .circle-item img {
    width: 100px;
    height: 100px;
  }

  .circle-item p {
    font-size: 3vw;
  }
}
</style>
