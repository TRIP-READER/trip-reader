<template>
  <div class="calendar" :class="calendarAnimation">
    <!-- 달력 상단 헤더 -->
    <div class="calendar-header">
      <button @click="prevMonth">◀</button>
      <h2>{{ year }}년 {{ month + 1 }}월</h2>
      <button @click="nextMonth">▶</button>
    </div>
    <!-- 달력 본문 -->
    <div class="calendar-body">
      <!-- 요일 헤더 -->
      <div class="calendar-row days-header">
        <div v-for="day in days" :key="day" class="day-header">
          <span class="sunday" v-if="day === `일`">{{ day }}</span>
          <span class="saturday" v-else-if="day === `토`">{{ day }}</span>
          <span v-else>{{ day }}</span>
        </div>
      </div>
      <!-- 날짜 정보 -->
      <div class="calendar-row" v-for="week in weeks" :key="week">
        <div
          v-for="date in week"
          :key="date.date"
          :class="[
            'day-cell',
            {
              today: date.isToday && !selectedDate,
              selected: date.date === selectedDate,
            },
          ]"
          @click="selectDate(date.date)"
        >
          <span class="day-number">{{ date.day }}</span>
          <span class="festival-info">{{ date.festivals || 0 }}개</span>
        </div>
      </div>
    </div>
    <!-- 선택된 날짜 정보 -->
    <div class="date-info" v-if="selectedDateInfo">
      <h3>선택된 날짜: {{ selectedDateInfo.date }}</h3>
      <h1>{{ selectedDateInfo.festivals || 0 }}개의 축제,공연/행사</h1>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, defineEmits } from "vue";
import { getFestivalCount } from "@/api/festivalApi";

const today = new Date();
const currentYear = today.getFullYear();
const currentMonth = today.getMonth();
const currentDate = today.toISOString().split("T")[0]; // YYYY-MM-DD 형식으로 변환

const year = ref(currentYear);
const month = ref(currentMonth);
const selectedDate = ref(today.date); // 선택된 날짜 초기화
const days = ["일", "월", "화", "수", "목", "금", "토"];

const festivals = ref({}); // 축제 데이터를 저장할 상태

// 달력의 첫째 날과 마지막 날 계산
const getCalendarRange = () => {
  const firstDay = new Date(year.value, month.value, 1);
  const lastDay = new Date(year.value, month.value + 1, 0);

  const startDate = new Date(firstDay);
  startDate.setDate(firstDay.getDate() - firstDay.getDay()); // 첫째 줄 시작

  const endDate = new Date(lastDay);
  endDate.setDate(lastDay.getDate() + (6 - lastDay.getDay())); // 마지막 줄 종료

  return {
    startdate: `${startDate.getFullYear()}-${String(
      startDate.getMonth() + 1
    ).padStart(2, "0")}-${String(startDate.getDate()).padStart(2, "0")}`,
    enddate: `${endDate.getFullYear()}-${String(
      endDate.getMonth() + 1
    ).padStart(2, "0")}-${String(endDate.getDate()).padStart(2, "0")}`,
  };
};

// API 호출 함수
const fetchFestivals = async () => {
  const { startdate, enddate } = getCalendarRange(); // 달력의 첫째 날과 마지막 날 가져오기
  try {
    await getFestivalCount(
      { startdate, enddate },
      (response) => {
        festivals.value = response.data; // API 결과를 festivals 상태에 저장
      },
      (error) => {
        console.error("축제 데이터를 가져오는 중 오류 발생:", error);
        festivals.value = {}; // 에러 발생 시 초기화
      }
    );
  } catch (error) {
    console.error("축제 데이터를 가져오는 중 오류 발생:", error);
    festivals.value = {}; // 에러 발생 시 초기화
  }
};

// 월이 변경될 때 호출
const updateFestivals = () => {
  fetchFestivals();
};

// 달력 날짜 계산
const weeks = computed(() => {
  const daysArray = [];
  const { startdate, enddate } = getCalendarRange();
  const startDate = new Date(startdate);
  const endDate = new Date(enddate);

  for (
    let date = new Date(startDate);
    date <= endDate;
    date.setDate(date.getDate() + 1)
  ) {
    daysArray.push(new Date(date));
  }

  return Array.from({ length: daysArray.length / 7 }, (_, i) =>
    daysArray.slice(i * 7, i * 7 + 7).map((date) => {
      const dateStr = `${date.getFullYear()}-${String(
        date.getMonth() + 1
      ).padStart(2, "0")}-${String(date.getDate()).padStart(2, "0")}`;
      return {
        date: dateStr, // ISO 형식 날짜
        day: date.getDate(), // 날짜 숫자
        isToday: dateStr === currentDate, // 오늘인지 확인
        isCurrentMonth: date.getMonth() === month.value, // 현재 월의 날짜인지 확인
        festivals: festivals.value[dateStr] || null, // 축제 데이터
      };
    })
  );
});

const emit = defineEmits(["selectDate"]);

const selectedDateInfo = computed(() => {
  if (!selectedDate.value) return null;
  const allDates = weeks.value.flat();
  const returndate = allDates.find((date) => date.date === selectedDate.value);

  if (returndate) {
    emit("selectDate", returndate.date);

    return {
      date: returndate.date,
      festivals: returndate.festivals,
    };
  }

  return null;
});

const selectDate = (date) => {
  selectedDate.value = date;
};

const calendarAnimation = ref(""); // 애니메이션 클래스 상태

const prevMonth = () => {
  calendarAnimation.value = "animate__animated animate__fadeOutRight"; // 애니메이션 시작
  setTimeout(() => {
    if (month.value === 0) {
      month.value = 11;
      year.value--;
    } else {
      month.value--;
    }
    updateFestivals();
    calendarAnimation.value = "animate__animated animate__fadeInLeft"; // 다음 애니메이션
    setTimeout(() => {
      calendarAnimation.value = ""; // 애니메이션 초기화
    }, 500);
  }, 500); // 애니메이션 지속 시간
};

const nextMonth = () => {
  calendarAnimation.value = "animate__animated animate__fadeOutLeft"; // 애니메이션 시작
  setTimeout(() => {
    if (month.value === 11) {
      month.value = 0;
      year.value++;
    } else {
      month.value++;
    }
    updateFestivals();
    calendarAnimation.value = "animate__animated animate__fadeInRight"; // 다음 애니메이션
    setTimeout(() => {
      calendarAnimation.value = ""; // 애니메이션 초기화
    }, 500);
  }, 500); // 애니메이션 지속 시간
};

// 컴포넌트가 로드될 때 축제 데이터 가져오기
onMounted(() => {
  fetchFestivals();
  emit("selectDate", currentDate);
  selectDate(currentDate);
});
</script>

<style scoped>
.animate__animated {
  --animate-duration: 0.5s;
}

/* 달력 스타일 */
.sunday {
  color: #cb132d;
}

.saturday {
  color: #3d5b95;
}
.calendar {
  width: 40vw;
  min-height: 40vh;
  min-width: 540px;
  margin: auto;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  font-family: "Arial", sans-serif;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 10px;
  background-color: #f9f9f9;
  border-bottom: 2px solid #ddd;
}

.calendar-header h2 {
  font-size: 24px;
  font-weight: bold;
  color: black;
}

.calendar-header button {
  background: none;
  border: none;
  font-size: 20px;
  color: #333;
  cursor: pointer;
}

.calendar-header button:hover {
  color: #b17457;
}

.days-header {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  text-align: center;
  background-color: #f5f5f5;
  font-weight: bold;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}

.day-header {
  color: #555;
  font-size: 16px;
}

/* 날짜 셀 */
.calendar-row {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
}

.day-cell {
  text-align: center;
  padding: 15px;
  border: 1px solid #f0f0f0;
  position: relative;
  font-size: 14px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 5vw;
  min-height: 50px;
  cursor: pointer;
}

.day-cell:hover {
  background-color: #dcdadac5;
}

.day-number {
  font-size: 1.5vw;
  font-weight: bold;
}

.festival-info {
  font-size: 12px;
  color: #888;
  margin-top: 5px;
}

/* 오늘 날짜 강조 */
.day-cell.today {
  background-color: #e0dbce;
  /* border: 2px solid #b17457; */
  font-weight: bold;
}

/* 선택된 날짜 강조 */
.day-cell.selected {
  background-color: #e0dbce;
  /* border: 2px solid #b17457; */
  font-weight: bold;
}

/* 비활성화된 날짜 */
.day-cell.inactive {
  color: #aaa;
}

/* 선택된 날짜 정보 */
.date-info {
  text-align: center;
  margin-top: 20px;
}

.date-info h3 {
  font-size: 20px;
  color: #b17457;
}

.date-info p {
  font-size: 16px;
  color: #333;
}
</style>
