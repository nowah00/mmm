<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import {useAccountStore} from "@/stores/account";
import {watch} from "vue";
import {useRoute} from "vue-router";
import {check} from "@/services/accountService";

const accountStore = useAccountStore();

const route = useRoute();

const checkAccount = async () => { // ②
  const res = await check();

  if (res.status === 200) {
    accountStore.setChecked(true);
    accountStore.setLoggedIn(res.data === true);
  } else {
    accountStore.setChecked(false);
  }
};

(async function onCreated() {
  await checkAccount();
})();

watch(() => route.path, () => {
  checkAccount();
});
</script>

<template>
  <template v-if="accountStore.checked">
    <Header/>
    <main>
      <router-view></router-view>
    </main>
    <Footer/>
  </template>
</template>

<style>
body {
  background: #f7f6f2;
}
</style>