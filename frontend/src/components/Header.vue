<script setup>
import {useAccountStore} from "@/stores/account";
import {logout} from "@/services/accountService";
import {useRouter} from "vue-router";

const accountStore = useAccountStore(); // ①

const router = useRouter(); // ②

const logoutAccount = async () => { // ③
  const res = await logout();

  if (res.status === 200) {
    accountStore.setLoggedIn(false);
    await router.push("/");
  }
};
</script>

<template>
  <header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
      <div class="container">
        <router-link to="/" class="navbar-brand fw-bold">
          Mini Music Market
        </router-link>
        <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#mainNavbar"
            aria-controls="mainNavbar"
            aria-expanded="false"
            aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="mainNavbar">
          <ul class="navbar-nav ms-auto gap-lg-2 align-items-lg-center">
            <template v-if="!accountStore.loggedIn">
              <li class="nav-item">
                <router-link class="nav-link" to="/login">로그인</router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/join">회원가입</router-link>
              </li>
            </template>

            <template v-else>
              <li class="nav-item">
                <router-link class="nav-link" to="/orders">주문 내역</router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/cart">장바구니</router-link>
              </li>
              <li class="nav-item">
                <button
                    type="button"
                    class="btn btn-outline-light btn-sm ms-lg-2"
                    @click="logoutAccount()"
                >
                  로그아웃
                </button>
              </li>
            </template>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</template>

<style lang="scss">
header {
  position: sticky;
  top: 0;
  z-index: 999;
}

.navbar {
  background: #d7b89c !important; /* 우드 라이트 */
}

.nav-link, .navbar-brand {
  color: #fff !important;
}
</style>