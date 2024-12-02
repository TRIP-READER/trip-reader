<script setup>
import { ref, onBeforeUpdate } from "vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";

const props = defineProps({
  mainMarker: {
    type: Object,
    default: () => {
      return {
        title: "mainMarker",
        mapy: 33.450701,
        mapx: 126.570667,
      };
    },
  },
  markerList: {
    type: Array,
    default: () => {
      return [];
    },
  },
  mapRender: {
    type: Boolean,
    default: false,
  },
});

// marker
// key, mapy, mapx, title

//마커 클릭 시 인포윈도우의 visible 값을 반전시킵니다
const onClickMapMarker = (markerItem) => {
  if (
    markerItem.infoWindow?.visible !== null &&
    markerItem.infoWindow?.visible !== undefined
  ) {
    markerItem.infoWindow.visible = !markerItem.infoWindow.visible;
  } else {
    markerItem.infoWindow.visible = true;
  }
};
</script>

<template>
  <div class="outerContainer">
    <div class="mapBox">
      <KakaoMap
        v-if="mapRender"
        :lat="mainMarker.mapy"
        :lng="mainMarker.mapx"
        class="kakaomap"
        width="100%"
        height="100%"
        @onLoadKakaoMap="onLoadKakaoMap"
      >
        <KakaoMapMarker
          :lat="mainMarker.mapy"
          :lng="mainMarker.mapx"
          :name="mainMarker.title"
        >
          <template v-slot:infoWindow>
            {{ mainMarker.title }}
          </template>
        </KakaoMapMarker>

        <KakaoMapMarker
          v-for="(marker, index) in markerList"
          :key="index"
          :lat="marker.mapy"
          :lng="marker.mapx"
          :name="marker.title"
          @onClick="onClickMapMarker(marker)"
        >
        </KakaoMapMarker>
      </KakaoMap>
    </div>
  </div>
</template>

<style scoped>
.outerContainer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}
.mapBox {
  width: 50vw;
  height: 28.125vw;
  min-width: 600px;
  max-width: calc(28.125vw * 2);
  margin-bottom: 3vh;
}

.kakaomap {
  z-index: 0;
  margin-bottom: 3vh;
}
</style>
