<script setup>
import { reactive } from "vue";
import { join } from "@/services/accountService";
import { useRouter } from "vue-router";

const state = reactive({
  form: {
    name: "",
    loginId: "",
    loginPw: "",
  },
});

const router = useRouter();

const submit = async () => {
  const res = await join(state.form);

  if (res.status === 200) {
    window.alert("회원가입을 완료했습니다.");
    await router.push("/");
  }
};
</script>

<template>
  <div class="join">
    <div class="container d-flex justify-content-center align-items-center py-5">
      <div class="join-card w-100">
        <form class="d-flex flex-column gap-3" @submit.prevent="submit">
          <h1 class="h5 mb-3 text-center title">
            반가워요, <span>Mini Music Market</span>에 오신 걸 환영해요!
          </h1>

          <div class="form-floating">
            <input
                type="text"
                class="form-control"
                id="name"
                placeholder="이름"
                v-model="state.form.name"
            />
            <label for="name">이름</label>
          </div>

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
            회원가입
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.join {
  background: #f7f6f2; /* 따뜻한 화이트 톤 */
  min-height: calc(100vh - 140px);

  .container {
    max-width: 480px;
  }
}

.join-card {
  background: #fffaf3; /* 살짝 베이지 섞인 화이트 */
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
    margin-top: 0.3rem;
    font-size: 0.9rem;
    color: #a47c57;
    font-weight: 500;
  }
}

/* 인풋 박스 */
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
  font-weight: 600;
  letter-spacing: 0.03em;

  &:hover {
    background-color: #a47c57;
    border-color: #a47c57;
  }
}
</style>