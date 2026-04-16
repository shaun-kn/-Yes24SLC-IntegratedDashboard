<template>
  <router-view />
</template>

<script setup>
import OsrDashboardView from './views/OsrDashboardView.vue'
</script>

<style>
html, body, #app {
  margin: 0;
  width: 100%;
  height: 100%;
}
body {
  overflow: hidden;
  font-family: Arial, Helvetica, sans-serif;
}

/*@font-face {*/
/*  font-family: 'DSEG7';*/
/*  src: url('/font/DSEG7Classic-Bold.ttf') format('truetype');*/
/*}*/

/*.digital {*/
/*  font-family: 'DSEG7';*/
/*  font-size: 60px;*/
/*  color: #00ffcc;*/
/*}*/
</style>