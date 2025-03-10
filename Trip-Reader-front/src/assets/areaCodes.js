// 지역데이터
const areaData = [
  { value: "0" },
  { value: "1", label: "서울", isLeaf: false },
  { value: "2", label: "인천", isLeaf: false },
  { value: "3", label: "대전", isLeaf: false },
  { value: "4", label: "대구", isLeaf: false },
  { value: "5", label: "광주", isLeaf: false },
  { value: "6", label: "부산", isLeaf: false },
  { value: "7", label: "울산", isLeaf: false },
  { value: "8", label: "세종", isLeaf: false },
  { value: "31", label: "경기도", isLeaf: false },
  { value: "32", label: "강원도", isLeaf: false },
  { value: "33", label: "충청북도", isLeaf: false },
  { value: "34", label: "충청남도", isLeaf: false },
  { value: "35", label: "경상북도", isLeaf: false },
  { value: "36", label: "경상남도", isLeaf: false },
  { value: "37", label: "전라북도", isLeaf: false },
  { value: "38", label: "전라남도", isLeaf: false },
  { value: "39", label: "제주도", isLeaf: false },
];

// 지역 데이터를 정의
const regionData = {
  1: [
    { value: "1", label: "강남구" },
    { value: "2", label: "강동구" },
    { value: "3", label: "강북구" },
    { value: "4", label: "강서구" },
    { value: "5", label: "관악구" },
    { value: "6", label: "광진구" },
    { value: "7", label: "구로구" },
    { value: "8", label: "금천구" },
    { value: "9", label: "노원구" },
    { value: "10", label: "도봉구" },
    { value: "11", label: "동대문구" },
    { value: "12", label: "동작구" },
    { value: "13", label: "마포구" },
    { value: "14", label: "서대문구" },
    { value: "15", label: "서초구" },
    { value: "16", label: "성동구" },
    { value: "17", label: "성북구" },
    { value: "18", label: "송파구" },
    { value: "19", label: "양천구" },
    { value: "20", label: "영등포구" },
    { value: "21", label: "용산구" },
    { value: "22", label: "은평구" },
    { value: "23", label: "종로구" },
    { value: "24", label: "중구" },
    { value: "25", label: "중랑구" },
  ],
  2: [
    { value: "1", label: "강화군" },
    { value: "2", label: "계양구" },
    { value: "3", label: "미추홀구" },
    { value: "4", label: "남동구" },
    { value: "5", label: "동구" },
    { value: "6", label: "부평구" },
    { value: "7", label: "서구" },
    { value: "8", label: "연수구" },
    { value: "9", label: "옹진군" },
    { value: "10", label: "중구" },
  ],
  3: [
    { value: "1", label: "대덕구" },
    { value: "2", label: "동구" },
    { value: "3", label: "서구" },
    { value: "4", label: "유성구" },
    { value: "5", label: "중구" },
  ],
  4: [
    { value: "1", label: "남구" },
    { value: "2", label: "달서구" },
    { value: "3", label: "달성군" },
    { value: "4", label: "동구" },
    { value: "5", label: "북구" },
    { value: "6", label: "서구" },
    { value: "7", label: "수성구" },
    { value: "8", label: "중구" },
    { value: "9", label: "군위군" },
  ],
  5: [
    { value: "1", label: "광산구" },
    { value: "2", label: "남구" },
    { value: "3", label: "동구" },
    { value: "4", label: "북구" },
    { value: "5", label: "서구" },
  ],
  6: [
    { value: "1", label: "강서구" },
    { value: "2", label: "금정구" },
    { value: "3", label: "기장군" },
    { value: "4", label: "남구" },
    { value: "5", label: "동구" },
    { value: "6", label: "동래구" },
    { value: "7", label: "부산진구" },
    { value: "8", label: "북구" },
    { value: "9", label: "사상구" },
    { value: "10", label: "사하구" },
    { value: "11", label: "서구" },
    { value: "12", label: "수영구" },
    { value: "13", label: "연제구" },
    { value: "14", label: "영도구" },
    { value: "15", label: "중구" },
    { value: "16", label: "해운대구" },
  ],
  7: [
    { value: "1", label: "중구" },
    { value: "2", label: "남구" },
    { value: "3", label: "동구" },
    { value: "4", label: "북구" },
    { value: "5", label: "울주군" },
  ],
  8: [{ value: "1", label: "세종특별자치시" }],
  31: [
    { value: "1", label: "가평군" },
    { value: "2", label: "고양시" },
    { value: "3", label: "과천시" },
    { value: "4", label: "광명시" },
    { value: "5", label: "광주시" },
    { value: "6", label: "구리시" },
    { value: "7", label: "군포시" },
    { value: "8", label: "김포시" },
    { value: "9", label: "남양주시" },
    { value: "10", label: "동두천시" },
    { value: "11", label: "부천시" },
    { value: "12", label: "성남시" },
    { value: "13", label: "수원시" },
    { value: "14", label: "시흥시" },
    { value: "15", label: "안산시" },
    { value: "16", label: "안성시" },
    { value: "17", label: "안양시" },
    { value: "18", label: "양주시" },
    { value: "19", label: "양평군" },
    { value: "20", label: "여주시" },
    { value: "21", label: "연천군" },
    { value: "22", label: "오산시" },
    { value: "23", label: "용인시" },
    { value: "24", label: "의왕시" },
    { value: "25", label: "의정부시" },
    { value: "26", label: "이천시" },
    { value: "27", label: "파주시" },
    { value: "28", label: "평택시" },
    { value: "29", label: "포천시" },
    { value: "30", label: "하남시" },
    { value: "31", label: "화성시" },
  ],
  32: [
    { value: "1", label: "강릉시" },
    { value: "2", label: "고성군" },
    { value: "3", label: "동해시" },
    { value: "4", label: "삼척시" },
    { value: "5", label: "속초시" },
    { value: "6", label: "양구군" },
    { value: "7", label: "양양군" },
    { value: "8", label: "영월군" },
    { value: "9", label: "원주시" },
    { value: "10", label: "인제군" },
    { value: "11", label: "정선군" },
    { value: "12", label: "철원군" },
    { value: "13", label: "춘천시" },
    { value: "14", label: "태백시" },
    { value: "15", label: "평창군" },
    { value: "16", label: "홍천군" },
    { value: "17", label: "화천군" },
    { value: "18", label: "횡성군" },
  ],
  33: [
    { value: "1", label: "괴산군" },
    { value: "2", label: "단양군" },
    { value: "3", label: "보은군" },
    { value: "4", label: "영동군" },
    { value: "5", label: "옥천군" },
    { value: "6", label: "음성군" },
    { value: "7", label: "제천시" },
    { value: "8", label: "진천군" },
    { value: "9", label: "청원군" },
    { value: "10", label: "청주시" },
    { value: "11", label: "충주시" },
    { value: "12", label: "증평군" },
  ],
  34: [
    { value: "1", label: "공주시" },
    { value: "2", label: "금산군" },
    { value: "3", label: "논산시" },
    { value: "4", label: "당진시" },
    { value: "5", label: "보령시" },
    { value: "6", label: "부여군" },
    { value: "7", label: "서산시" },
    { value: "8", label: "서천군" },
    { value: "9", label: "아산시" },
    { value: "11", label: "예산군" },
    { value: "12", label: "천안시" },
    { value: "13", label: "청양군" },
    { value: "14", label: "태안군" },
    { value: "15", label: "홍성군" },
    { value: "16", label: "계룡시" },
  ],
  35: [
    { value: "1", label: "경산시" },
    { value: "2", label: "경주시" },
    { value: "3", label: "고령군" },
    { value: "4", label: "구미시" },
    { value: "6", label: "김천시" },
    { value: "7", label: "문경시" },
    { value: "8", label: "봉화군" },
    { value: "9", label: "상주시" },
    { value: "10", label: "성주군" },
    { value: "11", label: "안동시" },
    { value: "12", label: "영덕군" },
    { value: "13", label: "영양군" },
    { value: "14", label: "영주시" },
    { value: "15", label: "영천시" },
    { value: "16", label: "예천군" },
    { value: "17", label: "울릉군" },
    { value: "18", label: "울진군" },
    { value: "19", label: "의성군" },
    { value: "20", label: "청도군" },
    { value: "21", label: "청송군" },
    { value: "22", label: "칠곡군" },
    { value: "23", label: "포항시" },
  ],
  36: [
    { value: "1", label: "거제시" },
    { value: "2", label: "거창군" },
    { value: "3", label: "고성군" },
    { value: "4", label: "김해시" },
    { value: "5", label: "남해군" },
    { value: "6", label: "마산시" },
    { value: "7", label: "밀양시" },
    { value: "8", label: "사천시" },
    { value: "9", label: "산청군" },
    { value: "10", label: "양산시" },
    { value: "12", label: "의령군" },
    { value: "13", label: "진주시" },
    { value: "14", label: "진해시" },
    { value: "15", label: "창녕군" },
    { value: "16", label: "창원시" },
    { value: "17", label: "통영시" },
    { value: "18", label: "하동군" },
    { value: "19", label: "함안군" },
    { value: "20", label: "함양군" },
    { value: "21", label: "합천군" },
  ],
  37: [
    { value: "1", label: "고창군" },
    { value: "2", label: "군산시" },
    { value: "3", label: "김제시" },
    { value: "4", label: "남원시" },
    { value: "5", label: "무주군" },
    { value: "6", label: "부안군" },
    { value: "7", label: "순창군" },
    { value: "8", label: "완주군" },
    { value: "9", label: "익산시" },
    { value: "10", label: "임실군" },
    { value: "11", label: "장수군" },
    { value: "12", label: "전주시" },
    { value: "13", label: "정읍시" },
    { value: "14", label: "진안군" },
  ],
  38: [
    { value: "1", label: "강진군" },
    { value: "2", label: "고흥군" },
    { value: "3", label: "곡성군" },
    { value: "4", label: "광양시" },
    { value: "5", label: "구례군" },
    { value: "6", label: "나주시" },
    { value: "7", label: "담양군" },
    { value: "8", label: "목포시" },
    { value: "9", label: "무안군" },
    { value: "10", label: "보성군" },
    { value: "11", label: "순천시" },
    { value: "12", label: "신안군" },
    { value: "13", label: "여수시" },
    { value: "16", label: "영광군" },
    { value: "17", label: "영암군" },
    { value: "18", label: "완도군" },
    { value: "19", label: "장성군" },
    { value: "20", label: "장흥군" },
    { value: "21", label: "진도군" },
    { value: "22", label: "함평군" },
    { value: "23", label: "해남군" },
    { value: "24", label: "화순군" },
  ],
  39: [
    { value: "1", label: "남제주군" },
    { value: "2", label: "북제주군" },
    { value: "3", label: "서귀포시" },
    { value: "4", label: "제주시" },
  ],
};

export { areaData, regionData };
