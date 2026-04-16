<template>
  <div class="kpi-block">

    <div class="kpi-label">{{ label }}</div>

    <div v-if="type === 'percent'" class="kpi-value-row">
      <span class="digital large">{{ value }}</span>
      <span class="digital percent">%</span>
    </div>

    <div v-else-if="type === 'fraction'" class="kpi-value-row">
      <span class="digital large">{{ value }}</span>
      <span class="digital slash">/</span>
      <span class="digital medium">{{ secondaryValue }}</span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  label: { type: String, required: true },
  type: { type: String, required: true },
  value: { type: [String, Number], required: true },
  secondaryValue: { type: [String, Number], default: '' }
})
</script>

<style scoped>
@font-face {
  font-family: "DSEG7Classic";
  src: url("/fonts/DSEG7Classic-Bold.ttf") format("truetype");
  font-weight: normal;
  font-style: normal;
}


/* 팁: 가장 첫 번째 KPI 블록에는 구분선이 필요 없으므로 부모에서 제어하거나
   아래와 같은 가상 클래스를 사용할 수 있습니다. */
.kpi-block:first-child .divider {
  display: none;
}

.kpi-block {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  height: 450px;

}

.kpi-label {
  position: absolute;
  top: 0px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 36px;
  font-weight: 700;
  color: #111111;
  white-space: nowrap;
}

.loc-label {
  position: absolute;
  top: 0px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 36px;
  font-weight: 700;
  color: #111111;
  white-space: nowrap;
}

.kpi-value-row {
  display: flex;
  align-items: flex-end;


}

.digital {
  font-family: "DSEG7Classic", monospace;
  color: #111111;
  line-height: 0.9;
}

.digital.large {
  font-size: 200px;
  color: Green;
}

.digital.medium {
  font-size: 120px;
  margin-bottom: 28px;
  color: #707070;
}

.digital.percent {
  font-size: 250px;
  /*margin-left: 10px;*/
  /*margin-bottom: 42px;*/
  color: Green;
}

.digital.slash {
  font-size: 120px;
  margin: 0 16px 42px;
  color: #707070;
}
</style>
