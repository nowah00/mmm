<script setup>
import { reactive } from "vue";
import { login } from "@/services/accountService";
import { useRouter } from "vue-router";

const state = reactive({
  form: {
    loginId: "",
    loginPw: "",
  },
});

const router = useRouter();

const submit = async () => {
  const res = await login(state.form);

  switch (res.status) {
    case 200:
      await router.push("/");
      break;
    case 404:
      window.alert("입력하신 정보와 일치하는 회원이 없습니다.");
      break;
  }
};
</script>

<template>
  <div class="login">
    <div class="container d-flex justify-content-center align-items-center py-5">
      <div class="login-card w-100">
        <form class="d-flex flex-column gap-3" @submit.prevent="submit">
          <h1 class="h5 mb-3 text-center title">
            다시 만난 음악, <span>Mini Music Market</span>
          </h1>

          <div class="form-floating">
            <input
                type="email"
                class="form-control"
                id="loginId"
                placeholder="이메일"
                v-model="state.form.loginId"
            />
            <label for="loginId">이메일</label>
          </div>

          <div class="form-floating">
            <input
                type="password"
                class="form-control"
                id="loginPw"
                placeholder="패스워드"
                v-model="state.form.loginPw"
            />
            <label for="loginPw">패스워드</label>
          </div>

          <button type="submit" class="w-100 h6 btn py-3 btn-primary">
            로그인
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login {
  background: #f7f6f2; // 따뜻한 화이트
  min-height: calc(100vh - 140px); // 헤더/풋터 뺀 높이 대충 맞추기

  .container {
    max-width: 480px;
  }
}

.login-card {
  background: #fffaf3; // 살짝 베이지 섞인 화이트
  border-radius: 18px;
  border: 1px solid #e7dfd3;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.06);
  padding: 2.5rem 2rem;
}

.title {
  color: #6b4f3a;
  letter-spacing: 0.03em;

  span {
    display: block;
    font-size: 0.9rem;
    margin-top: 0.25rem;
    color: #a47c57;
    font-weight: 500;
  }
}

/* 인풋 감성 */
.form-floating {
  .form-control {
    border-radius: 12px;
    border-color: #e0d4c2;
    background-color: #fdfaf5;
    font-size: 0.9rem;

    &:focus {
      border-color: #b8926a;
      box-shadow: 0 0 0 0.15rem rgba(184, 146, 106, 0.25);
      background-color: #fff;
    }
  }

  label {
    color: #b39a7d;
  }
}

/* 버튼 우드톤 */
.btn-primary {
  border-radius: 999px;
  background-color: #b8926a;
  border-color: #b8926a;
  letter-spacing: 0.05em;

  &:hover {
    background-color: #a47c57;
    border-color: #a47c57;
  }
}
</style>