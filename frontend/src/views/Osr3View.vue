<template>
  <main class="content">
    <OsrKpiPanel :used="used" :capacity="capacity" />

    <section class="right-panel osr3-panel">
      <div class="wave-board">
        <div class="board-header">Wave</div>

        <div class="board-subheader">
          <div class="sub-left">Wave</div>
          <div class="sub-right">Tote Count</div>
        </div>

        <div class="wave-rows">
          <div
              v-for="(item, index) in displayWaves"
              :key="`${item.wave || 'empty'}-${index}`"
              class="wave-row"
          >
            <div class="wave-name">{{ item.wave || '' }}</div>
            <div class="wave-count">{{ item.toteCount == null ? '' : item.toteCount }}</div>
          </div>
        </div>

        <div v-if="errorMessage" class="error-box">
          {{ errorMessage }}
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import OsrKpiPanel from '../components/OsrKpiPanel.vue'
import {apiGet} from "../api/http";

const used = ref(0)
const capacity = ref(0)
const waves = ref([])
const errorMessage = ref('')

let timer = null

async function fetchDashboard() {
  try {
    const data = await apiGet('/api/osr/3')

    used.value = data.used || 0
    capacity.value = data.capacity || 0
    waves.value = Array.isArray(data.waves) ? data.waves : []
    errorMessage.value = ''
  } catch (error) {
    console.error('API 에러:', error)
    console.error('응답:', error.response?.data)
    errorMessage.value = '데이터를 불러오지 못했습니다.'
  }
}

const displayWaves = computed(() => {
  const rows = waves.value.slice(0, 6)

  while (rows.length < 6) {
    rows.push({
      wave: '',
      toteCount: null
    })
  }

  return rows
})

onMounted(() => {
  fetchDashboard()

  timer = window.setInterval(() => {
    fetchDashboard()
  }, 3000)
})

onBeforeUnmount(() => {
  if (timer) {
    window.clearInterval(timer)
  }
})
</script>

<style scoped>
.content {
  display: grid;
  grid-template-columns: 2fr 1.4fr;
  min-height: 0;
  height: 100%;
}

.right-panel {
  padding: 12px;
  box-sizing: border-box;
  min-height: 0;
}

.osr3-panel {
  background: #17324d;
  color: #ffffff;
}

.wave-board {
  height: 100%;
  display: grid;
  grid-template-rows: auto auto 1fr auto;
  color: #ffffff;
}

.board-header {
  text-align: center;
  font-size: 32px;
  font-weight: 900;
  padding: 8px 0 14px;
}

.board-subheader {
  display: grid;
  grid-template-columns: 2fr 1fr;
  align-items: center;
  padding: 8px 12px 14px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.sub-left,
.sub-right {
  text-align: center;
  font-size: 24px;
  font-weight: 800;
}

.wave-rows {
  display: grid;
  grid-template-rows: repeat(6, 1fr);
  min-height: 0;
}

.wave-row {
  display: grid;
  grid-template-columns: 2fr 1fr;
  align-items: center;
  padding: 0 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
  min-height: 0;
}

.wave-name {
  display: flex;
  align-items: center;
  justify-content: center;

  font-size: 42px;     /* 🔥 크게 */
  font-weight: 900;

  text-align: center;
  word-break: break-all;
}

.wave-count {
  display: flex;
  align-items: center;
  justify-content: center;

  font-size: 64px;     /* 🔥 핵심 숫자 크게 */
  font-weight: 900;

  text-align: center;
}

/*.wave-name {*/
/*  display: flex;*/
/*  align-items: center;*/
/*  justify-content: center;*/
/*  font-size: 28px;*/
/*  font-weight: 900;*/
/*  text-align: center;*/
/*  word-break: break-all;*/
/*}*/

/*.wave-count {*/
/*  display: flex;*/
/*  align-items: center;*/
/*  justify-content: center;*/
/*  font-size: 34px;*/
/*  font-weight: 900;*/
/*  text-align: center;*/
/*}*/

.error-box {
  margin-top: 8px;
  padding: 8px 10px;
  font-size: 14px;
  color: #ffd5d5;
  text-align: center;
}
</style>