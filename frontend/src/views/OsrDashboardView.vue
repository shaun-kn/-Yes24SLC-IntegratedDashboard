<template>
  <div class="dashboard">
    <header class="top-bar">
      <div class="logo-wrap">
        <img class="logo yes24" src="/resources/yes24_logo.png" alt="YES24" />
      </div>
      <h1 class="page-title">{{ osrName }} 운영현황</h1>
    </header>

    <main class="content">
      <section class="left-panel">
        <div class="kpi-block">
          <div class="kpi-label">보관율</div>
          <div class="kpi-value-row">
            <span class="digital large">{{ occupancyPct }}</span>
            <span class="digital percent">%</span>
          </div>
        </div>

        <div class="divider"></div>

        <div class="kpi-block">
          <div class="kpi-label">LOC 수량</div>
          <div class="kpi-value-row">
            <span class="digital large">{{ used }}</span>
            <span class="digital slash">/</span>
            <span class="digital medium">{{ capacity }}</span>
          </div>
        </div>
      </section>

      <section class="right-panel">
        <div class="order-board">
          <div class="board-header">Order</div>

          <div class="board-subheader">
            <div class="sub-left">오더번호</div>
            <div class="sub-right">
              <div class="qty-title">수량</div>
              <div class="qty-sub">
                <span>전체</span>
                <span>진입</span>
              </div>
            </div>
          </div>

          <div class="order-rows">
            <div
              v-for="(item, index) in displayOrders"
              :key="`${item.orderNo}-${index}`"
              class="order-row"
            >
              <div class="order-no">{{ item.orderNo }}</div>
              <div class="order-qty">
                <span class="qty-all">{{ item.totalCount ?? '' }}</span>
                <span class="qty-entered">{{ item.enteredCount ?? '' }}</span>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>

    <footer class="bottom-bar">
      <div class="bottom-left">
        {{ nowText }}
      </div>

      <div class="bottom-right">
        <img class="logo knr" src="/resources/knr_logo.png" alt="KNR" />
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'

const osrName = ref('시퀀스버퍼')
const used = ref(1)
const capacity = ref(560)

const orders = ref([
  { orderNo: 'A2404100001', totalCount: 12, enteredCount: 5 },
  { orderNo: 'A2404100002', totalCount: 8, enteredCount: 2 },
  { orderNo: 'A2404100003', totalCount: 16, enteredCount: 9 },
  { orderNo: 'A2404100004', totalCount: 3, enteredCount: 1 },
  { orderNo: 'A2404100005', totalCount: 10, enteredCount: 7 },
  { orderNo: 'A2404100006', totalCount: 4, enteredCount: 0 }
])

const occupancyPct = computed(() => {
  if (!capacity.value) return 0
  return Math.round((used.value / capacity.value) * 100)
})

const displayOrders = computed(() => {
  const rows = [...orders.value]
  while (rows.length < 6) {
    rows.push({
      orderNo: '',
      totalCount: null,
      enteredCount: null
    })
  }
  return rows.slice(0, 6)
})

const now = ref(new Date())

const nowText = computed(() => {
  const y = now.value.getFullYear()
  const m = String(now.value.getMonth() + 1).padStart(2, '0')
  const d = String(now.value.getDate()).padStart(2, '0')
  const hh = String(now.value.getHours()).padStart(2, '0')
  const mm = String(now.value.getMinutes()).padStart(2, '0')
  const ss = String(now.value.getSeconds()).padStart(2, '0')
  return `${y}.${m}.${d} ${hh}:${mm}:${ss}`
})

let clockTimer = null

onMounted(() => {
  clockTimer = window.setInterval(() => {
    now.value = new Date()
  }, 1000)
})

onBeforeUnmount(() => {
  if (clockTimer) {
    window.clearInterval(clockTimer)
  }
})
</script>

<style scoped>
@font-face {
  font-family: "DSEG7Classic";
  src: url("/fonts/DSEG7Classic-Bold.ttf") format("truetype");
  font-weight: normal;
  font-style: normal;
}

.dashboard {
  width: 100%;
  height: 100%;
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
  background: #ffffff;
  padding: 10px 14px;
  border-radius: 10px;
}

.logo {
  display: block;
  object-fit: contain;
}

.logo.yes24 {
  width: 180px;
  height: 70px;
}

.logo.knr {
  height: 40px;
}

.title-wrap {
  display: flex;
  align-items: center;
  justify-content: center;
}

.page-title {
  margin: 0;
  font-size: 72px;
  font-weight: 900;
  line-height: 1;
  color: #000000;
}

.content {
  display: grid;
  grid-template-columns: 2fr 1.4fr;
  min-height: 0;
}

.left-panel {
  border: 1px solid #d7d7d7;
  display: grid;
  grid-template-rows: 1fr 6px 1fr;
  min-height: 0;
}

.kpi-block {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.kpi-label {
  font-size: 50px;
  font-weight: 700;
  margin-bottom: 8px;
  color: #111111;
}

.kpi-value-row {
  display: flex;
  align-items: flex-end;
  justify-content: center;
}

.digital {
  font-family: "DSEG7Classic", monospace;
  color: #111111;
  line-height: 0.9;
}

.digital.large {
  font-size: 260px;
}

.digital.medium {
  font-size: 120px;
  margin-bottom: 28px;
}

.digital.percent {
  font-size: 120px;
  margin-left: 10px;
  margin-bottom: 42px;
}

.digital.slash {
  font-size: 120px;
  margin: 0 16px 42px;
}

.divider {
  background: #cccccc;
  align-self: center;
  height: 2px;
  width: 100%;
}

.right-panel {
  background: #7e350e;
  padding: 12px;
  box-sizing: border-box;
  min-height: 0;
}

.order-board {
  height: 100%;
  display: grid;
  grid-template-rows: auto auto 1fr;
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
  grid-template-columns: 2fr 1.4fr;
  align-items: center;
  padding: 8px 12px 14px;
  border-bottom: 1px solid #a64d1b;
}

.sub-left {
  text-align: center;
  font-size: 24px;
  font-weight: 800;
}

.sub-right {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.qty-title {
  font-size: 24px;
  font-weight: 800;
}

.qty-sub {
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr;
  font-size: 18px;
  font-weight: 700;
  text-align: center;
}

.order-rows {
  display: grid;
  grid-template-rows: repeat(6, 1fr);
  min-height: 0;
}

.order-row {
  display: grid;
  grid-template-columns: 2fr 1.4fr;
  align-items: center;
  padding: 0 12px;
  border-bottom: 1px solid #a64d1b;
  min-height: 0;
}

.order-no {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 34px;
  font-weight: 900;
  text-align: center;
}

.order-qty {
  display: grid;
  grid-template-columns: 1fr 1fr;
  align-items: center;
  justify-items: center;
  font-size: 34px;
  font-weight: 900;
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
  font-size: 35px;
  font-weight: 700;
}

.bottom-right {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
</style>
