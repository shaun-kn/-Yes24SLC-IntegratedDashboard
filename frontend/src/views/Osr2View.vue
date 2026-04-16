<template>
  <main class="content">
    <OsrKpiPanel :used="used" :capacity="capacity" />

    <section class="right-panel">
      <div class="order-board">
        <div class="board-subheader">
          <div class="sub-col">오 더</div>
          <div class="sub-col">수 량</div>
          <div class="sub-col">입 고</div>
          <div class="sub-col">상 태</div>
        </div>

        <div class="order-rows">
          <div
              v-for="(item, index) in leftOrders"
              :key="'L' + index"
              class="order-row"
              :class="{
              ejecting: item.status === '배출중',
              delayed: item.status === '배출중' && isDelayed(item.updateTime)
            }"
          >
            <div class="order-no">{{ item.orderNo }}</div>
            <span class="qty">{{ item.totalCount ?? '' }}</span>
            <span class="qty">{{ item.enteredCount ?? '' }}</span>
            <span class="status">{{ item.status || '' }}</span>
          </div>
        </div>
      </div>

      <div class="order-board">
        <div class="board-subheader">
          <div class="sub-col">오 더</div>
          <div class="sub-col">수 량</div>
          <div class="sub-col">입 고</div>
          <div class="sub-col">상 태</div>
        </div>

        <div class="order-rows">
          <div
              v-for="(item, index) in rightOrders"
              :key="'R' + index"
              class="order-row"
              :class="{
              ejecting: item.status === '배출중',
              delayed: item.status === '배출중' && isDelayed(item.updateTime)
            }"
          >
            <div class="order-no">{{ item.orderNo }}</div>
            <span class="qty">{{ item.totalCount ?? '' }}</span>
            <span class="qty">{{ item.enteredCount ?? '' }}</span>
            <span class="status">{{ item.status || '' }}</span>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import OsrKpiPanel from '../components/OsrKpiPanel.vue'
import { apiGet } from '../api/http'

const used = ref(0)
const capacity = ref(0)
const orders = ref([])
const errorMessage = ref('')
const now = ref(Date.now())

const ROWS_PER_BOARD = 22
const TOTAL_ROWS = ROWS_PER_BOARD * 2

let timer = null
let clockTimer = null


async function fetchDashboard() {
  try {
    const data = await apiGet('/api/osr/2')

    used.value = data.used || 0
    capacity.value = data.capacity || 0

    let list = Array.isArray(data.orders) ? data.orders : []

    // 👉 테스트용 데이터 채우기 (22 * 2 = 44개)
    // const TOTAL = 44
    // let i = list.length
    //
    // while (list.length < TOTAL) {
    //   const isEjecting = Math.random() > 0.5
    //
    //   // 0 ~ 9분 전으로만 생성 → 20분 초과 안 됨
    //   const minutesAgo = Math.floor(Math.random() * 10)
    //
    //   const updateTime = new Date(Date.now() - minutesAgo * 60 * 1000)
    //       .toISOString()
    //       .slice(0, 19) // "YYYY-MM-DDTHH:mm:ss"
    //
    //   list.push({
    //     orderNo: `TEST-${i + 1}`,
    //     totalCount: Math.floor(Math.random() * 10) + 1,
    //     enteredCount: Math.floor(Math.random() * 10),
    //     status: isEjecting ? '배출중' : '',
    //     updateTime: updateTime
    //   })
    //
    //   i++
    // }

    orders.value = list
    errorMessage.value = ''
  } catch (error) {
    errorMessage.value = '데이터를 불러오지 못했습니다.'
  }
}
async function fetchDashboard111() {
  try {
    const data = await apiGet('/api/osr/2')

    used.value = data.used || 0
    capacity.value = data.capacity || 0



    orders.value = Array.isArray(data.orders) ? data.orders : []




    errorMessage.value = ''
  } catch (error) {
    console.error(error)
    errorMessage.value = '데이터를 불러오지 못했습니다.'
  }
}

function emptyRow() {
  return {
    orderNo: '',
    totalCount: null,
    enteredCount: null,
    status: '',
    updateTime: null
  }
}

function fillOrders(list, size) {
  const result = [...list]
  while (result.length < size) {
    result.push(emptyRow())
  }
  return result
}

const paddedOrders = computed(() => {
  return fillOrders(orders.value.slice(0, TOTAL_ROWS), TOTAL_ROWS)
})

const leftOrders = computed(() => {
  return paddedOrders.value.slice(0, ROWS_PER_BOARD)
})

const rightOrders = computed(() => {
  return paddedOrders.value.slice(ROWS_PER_BOARD, TOTAL_ROWS)
})

function parseDateTime(value) {
  if (!value) return null

  if (value instanceof Date) {
    return Number.isNaN(value.getTime()) ? null : value
  }

  if (typeof value !== 'string') return null

  const normalized = value.trim().replace(' ', 'T')
  const date = new Date(normalized)

  if (!Number.isNaN(date.getTime())) {
    return date
  }

  return null
}

function isDelayed(updateTime) {
  const target = parseDateTime(updateTime)
  if (!target) return false

  const diffMinutes = (now.value - target.getTime()) / 1000 / 60
  return diffMinutes >= 20
}

onMounted(() => {
  fetchDashboard()

  timer = window.setInterval(fetchDashboard, 1000)

  clockTimer = window.setInterval(() => {
    now.value = Date.now()
  }, 1000)
})

onBeforeUnmount(() => {
  if (timer) window.clearInterval(timer)
  if (clockTimer) window.clearInterval(clockTimer)
})
</script>

<style scoped>
.content {
  display: grid;
  grid-template-columns: 1fr 1.4fr;
  height: 100vh;
  overflow: hidden;
  align-items: stretch;
}

.right-panel {
  display: flex;
  gap: 20px;
  padding: 20px;
  background: #ffffff;
  box-sizing: border-box;
  height: 100%;
  align-items: stretch;
}

.order-board {
  flex: 1;
  min-width: 450px;
  height: 100%;
  display: grid;
  grid-template-rows: auto 1fr;
  border: 1px solid #7e350e;
  box-sizing: border-box;
}

.board-subheader,
.order-row {
  display: grid;
  grid-template-columns: 1.5fr 0.7fr 0.7fr 0.7fr;
  align-items: center;
}

.board-subheader {
  background: #7e350e;
  color: white;
  padding: 5px 0;
  font-weight: 800;
  text-align: center;
  border-bottom: 1px solid #7e350e;
}

.sub-col {
  font-size: 20px;
}

.order-rows {
  display: grid;
  grid-template-rows: repeat(22, minmax(24px, 1fr));
  height: 100%;
}

.order-row {
  border-top: 1px solid #eee;
  border-bottom: 1px solid #7e350e;
  padding: 0 4px;
  box-sizing: border-box;
  min-height: 28px;
  background: #fff;
}

.order-no {
  font-size: 20px;
  font-weight: 700;
  text-align: center;
  word-break: break-all;
  line-height: 1.1;
  padding: 0 5px;
}

.qty,
.status {
  font-size: 20px;
  font-weight: 700;
  text-align: center;
  line-height: 1.1;
}

.status {
  color: #333;
}

/* 배출중 - 20분 안 지난 경우만 초록 깜빡임 */
.order-row.ejecting:not(.delayed) {
  animation: blink-green 1s infinite;
}

.order-row.ejecting:not(.delayed) .order-no,
.order-row.ejecting:not(.delayed) .qty,
.order-row.ejecting:not(.delayed) .status {
  color: black;
  font-weight: 800;
}

/* 배출중 + 20분 초과는 빨강 깜빡임 우선 */
.order-row.delayed {
  animation: blink-danger 0.8s step-end infinite !important;
}

.order-row.delayed .order-no,
.order-row.delayed .qty,
.order-row.delayed .status {
  /*color: #ffffff !important;*/
  font-weight: 900;
}

@keyframes blink-green {
  0% {
    background-color: #fff5f5;
  }
  50% {
    background-color: greenyellow;
  }
  100% {
    background-color: #fff5f5;
  }
}

@keyframes blink-danger {
  0%, 100% {
    background-color: #ffffff;
  }
  50% {
    background-color: #ff0000;
  }
}
</style>