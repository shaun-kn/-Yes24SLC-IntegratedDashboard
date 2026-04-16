<template>
  <section class="left-panel">
    <KpiBlock
        label="보관율"
        type="percent"
        :value="occupancyPct"
    />

    <div class="kpi-divider-horizontal"></div>

    <KpiBlock
        label="LOC 수량"
        type="fraction"
        :value="used"
        :secondary-value="capacity"
    />
  </section>
</template>

<script setup>
import { computed } from 'vue'
import KpiBlock from './KpiBlock.vue'

const props = defineProps({
  used: { type: Number, required: true },
  capacity: { type: Number, required: true }
})

const occupancyPct = computed(() => {
  if (!props.capacity) return 0
  //return

  console.log(props.used)
  console.log(props.capacity)
  //return Math.round((props.used / props.capacity) * 100)
  return ((props.used / props.capacity) * 100).toFixed(2)
})
</script>

<style scoped>
.left-panel {
  display: flex;
  flex-direction: column; /* 위아래로 배치 */
  align-items: center;    /* 가로 중앙 정렬 */

}

.kpi-divider-horizontal {

  width: 80%;            /* 선의 가로 길이 (부모 너비의 80%) */
  height: 2px;           /* 선의 두께 */
  background-color: #cccccc; /* 선 색상 */

  opacity: 0.6;
}


</style>
