<template>
  <div class="dashboard">
    <header class="top-bar">
      <div class="logo-wrap">
        <img class="logo yes24" src="/resources/yes24_logo.png" alt="YES24" />
      </div>
      <h1 class="page-title">{{ pageTitle }}</h1>
    </header>

    <main class="content">
      <router-view />
    </main>

    <footer class="bottom-bar">
      <div class="bottom-left">
        {{ dateText }}
        <span class="day">{{ dayName }}요일</span>
        {{ timeText }}
      </div>

      <div class="bottom-right">
        <img class="logo knr" src="/resources/knr_logo.png" alt="KNR" />
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed, onMounted, onBeforeUnmount, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const isOnline = ref(true)
const now = ref(new Date())

// const pageTitle = computed(() => {
//   if (route.path === '/osr2' || route.path === '/osr/2') return 'OSR2 운영현황'
//   if (route.path === '/osr3' || route.path === '/osr/3') return 'OSR3 운영현황'
//   return 'OSR 운영현황'
// })

const pageTitle = computed(() => route.meta.osrName || 'OSR 운영현황')
const week = ['일', '월', '화', '수', '목', '금', '토'];
const dateText = computed(() => {
  const m = String(now.value.getMonth() + 1).padStart(2, '0')
  const d = String(now.value.getDate()).padStart(2, '0')
  return `${m}월${d}일`
})

const timeText = computed(() => {
  const hh = String(now.value.getHours()).padStart(2, '0')
  const mm = String(now.value.getMinutes()).padStart(2, '0')
  const ss = String(now.value.getSeconds()).padStart(2, '0')
  return `${hh}:${mm}:${ss}`
})

const dayName = computed(() => week[now.value.getDay()])
// const nowText = computed(() => {
//
//
//   const y = now.value.getFullYear()
//   const m = String(now.value.getMonth() + 1).padStart(2, '0')
//   const d = String(now.value.getDate()).padStart(2, '0')
//   const hh = String(now.value.getHours()).padStart(2, '0')
//   const mm = String(now.value.getMinutes()).padStart(2, '0')
//   const ss = String(now.value.getSeconds()).padStart(2, '0')
//   const dayName = week[now.value.getDay()];
//   //return `${y}.${m}.${d} ${hh}:${mm}:${ss}`
//   return `${m}월${d}일 ${'  '}${dayName}요일  ${hh}:${mm}:${ss} `
// })

let timer = null

onMounted(() => {
  timer = window.setInterval(() => {
    now.value = new Date()
  }, 1000)
})

onBeforeUnmount(() => {
  if (timer) window.clearInterval(timer)
})
</script>

<style scoped>
.dashboard {
  width: 100%;
  height: 100vh;
  display: grid;
  grid-template-rows: 160px 1fr 70px;
  background: #ffffff;
  overflow: hidden;
  font-family: Arial, Helvetica, sans-serif;
}

.top-bar {
  position: relative;
  background: rgb(131, 204, 235);
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-wrap {
  position: absolute;
  left: 20px;
  top: 50%;
  transform: translateY(-50%);

  padding: 10px 14px;
  border-radius: 10px;
}

.logo {
  display: block;
  object-fit: contain;

}

.logo.yes24 {
  width: 260px;
  height: 90px;
  border: 2px solid #808080; /* 중회색 테두리 */
  border-radius: 8px;        /* 모서리를 살짝 둥글게 (선택사항) */
  padding: 5px;              /* 로고가 테두리에 너무 붙지 않게 여백 추가 */
}

.logo.knr {
  height: 40px;
}

.page-title {
  margin: 0;
  font-size: 100px;
  font-weight: 900;
  line-height: 1;
  color: #707070;
  padding-left: 70px;
}

.content {
  min-height: 0;
}

.bottom-bar {
  background: rgb(0, 32, 96);
  color: #ffffff;
  display: grid;
  grid-template-columns: 1fr 1fr;
  align-items: center;
  padding: 0 20px;
  box-sizing: border-box;
}

.bottom-left {
  font-size: 50px;
  font-weight: 700;
}

.bottom-right {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.network {
  position: absolute;
  right: 24px;
  top: 24px;
}

.led {
  width: 18px;
  height: 18px;
  border-radius: 50%;
  display: inline-block;
}

.green {
  background: #00c853;
  box-shadow: 0 0 10px #00c853;
}

.red {
  background: #ff1744;
  box-shadow: 0 0 10px #ff1744;
}

.day {
  color: yellow;
  font-weight: bold; /* 선택 */
}
</style>
