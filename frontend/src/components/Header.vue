<script setup>
import {useAccountStore} from "@/stores/account";
import {logout} from "@/services/accountService";
import {useRouter} from "vue-router";

// 계정 스토어
const accountStore = useAccountStore(); // ①

// 라우터 객체
const router = useRouter(); // ②

// 로그아웃
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
    <div class="navbar navbar-dark bg-dark text-white shadow-sm">
      <div class="container">
        <router-link to="/" class="navbar-brand">
          <strong>Mini Music Market</strong>
        </router-link>
        <div class="menus d-flex gap-3">
          <template v-if="!accountStore.loggedIn">  <!-- ④ -->
            <router-link to="/login">로그인</router-link>
            <router-link to="/join">회원가입</router-link>
          </template>
          <template v-else>
            <a @click="logoutAccount()">로그아웃</a>
            <router-link to="/orders">주문 내역</router-link>
            <router-link to="/cart">장바구니</router-link>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>

<style lang="scss">
header {
  position: sticky;
  top: 0;
  z-index: 999;

  .navbar {
    backdrop-filter: blur(12px);
    background: rgba(20, 20, 20, 0.7);
    border-bottom: 1px solid rgba(255, 255, 255, 0.08);
    padding: 0.9rem 0;

    .navbar-brand {
      font-weight: 700;
      letter-spacing: -0.5px;
      font-size: 1.25rem;
      transition: color 0.2s ease;

      &:hover {
        color: #a2d2ff !important;
      }
    }

    .menus {
      display: flex;
      align-items: center;
      gap: 1.2rem;

      a {
        color: #f5f5f5;
        font-size: 0.95rem;
        padding: 6px 10px;
        border-radius: 8px;
        transition: all 0.25s ease;

        &:hover {
          background: rgba(255, 255, 255, 0.12);
          color: #ffffff;
        }
      }
    }
  }
}
</style>