<script setup>
import { computed, reactive } from "vue";
import { addOrder } from "@/services/orderService";
import { useRouter } from "vue-router";
import { getItems } from "@/services/cartService.js";

const router = useRouter();

const state = reactive({
  items: [],
  form: {
    name: "",
    address: "",
    payment: "card",
    cardNumber: "",
  },
});

const computedTotalPrice = computed(() => {
  let result = 0;

  state.items.forEach((i) => {
    result += i.price - (i.price * i.discountPer) / 100;
  });

  return result;
});

const submit = async () => {
  if (state.form.payment !== "card") {
    state.form.cardNumber = "";
  }

  state.form.itemIds = state.items.map((i) => i.id);
  const res = await addOrder(state.form);

  if (res.status === 200) {
    const messages = ["주문이 완료되었습니다."];

    if (state.form.payment === "bank") {
      const price = computedTotalPrice.value.toLocaleString();
      messages.push(
          `한국은행 123-456789-777 계좌로 ${price}원을 입금해주시기 바랍니다.`
      );
    }

    window.alert(messages.join("\n"));
    await router.push("/");
  }
};

(async function onCreated() {
  const res = await getItems();

  if (res.status === 200) {
    state.items = res.data;
  }
})();
</script>

<template>
  <form class="order-form" @submit.prevent="submit">
    <div class="container">
      <div class="py-5 text-center">
        <div class="h4 title">
          <b>주문하기</b>
        </div>
        <p class="h6 mt-3 subtitle">
          주문 내역을 확인하시고 <br class="d-md-none" />
          <span>결제하기</span> 버튼을 눌러주세요.
        </p>
      </div>

      <div class="row g-4 g-lg-5">
        <div class="col-md-5 col-lg-4 order-md-last">
          <div class="order-summary card-wood">
            <div class="d-flex justify-content-between align-items-center mb-3">
              <span class="h6 mb-0">
                <b>구입 목록</b>
              </span>
              <span class="badge rounded-pill count-badge">
                {{ state.items.length }}
              </span>
            </div>

            <ul class="items list-group mb-3">
              <li
                  class="p-3 list-group-item d-flex justify-content-between align-items-center"
                  v-for="i in state.items"
                  :key="i.id"
              >
                <div class="me-3">
                  <h6 class="my-0 item-name">{{ i.name }}</h6>
                </div>
                <span class="item-price">
                  {{
                    (i.price - (i.price * i.discountPer) / 100).toLocaleString()
                  }}원
                </span>
              </li>
            </ul>

            <div class="total-price mt-3">
              <span>합계</span>
              <b>{{ computedTotalPrice.toLocaleString() }}원</b>
            </div>

            <button type="submit" class="w-100 btn btn-primary py-3 mt-4">
              결제하기
            </button>
          </div>
        </div>

        <div class="col-md-7 col-lg-8">
          <div class="order-info card-wood">
            <div class="h6 mb-3">
              <b>주문자 정보</b>
            </div>

            <div class="row g-3">
              <div class="col-12">
                <label for="name" class="form-label">이름</label>
                <input
                    type="text"
                    class="form-control"
                    id="name"
                    v-model="state.form.name"
                />
              </div>
              <div class="col-12">
                <label for="address" class="form-label">주소</label>
                <input
                    type="text"
                    class="form-control"
                    id="address"
                    v-model="state.form.address"
                />
              </div>
            </div>

            <div class="h6 mt-4 mb-2">
              <b>결제 수단</b>
            </div>

            <div class="my-2">
              <div class="form-check">
                <input
                    id="card"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    value="card"
                    v-model="state.form.payment"
                />
                <label class="form-check-label" for="card">신용카드</label>
              </div>
              <div class="form-check">
                <input
                    id="bank"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    value="bank"
                    v-model="state.form.payment"
                />
                <label class="form-check-label" for="bank">무통장입금</label>
              </div>
            </div>

            <div class="mt-3" v-if="state.form.payment === 'card'">
              <label for="cardNum" class="form-label">카드 번호</label>
              <input
                  type="text"
                  class="form-control"
                  id="cardNum"
                  v-model="state.form.cardNumber"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </form>
</template>

<style lang="scss" scoped>
.order-form {
  background: #f7f6f2; // 전체 배경
  padding: 3rem 0 4rem;

  .container {
    max-width: 960px;
  }
}

.title {
  color: #6b4f3a;
}

.subtitle {
  color: #a3896d;

  span {
    font-weight: 600;
    color: #8f684a;
  }
}

/* 공통 우드 카드 스타일 */
.card-wood {
  background: #fffaf3;
  border-radius: 18px;
  border: 1px solid #e7dfd3;
  box-shadow: 0 10px 28px rgba(0, 0, 0, 0.06);
  padding: 1.8rem 1.5rem;
}

/* 주문 요약 카드 */
.order-summary {
  .count-badge {
    background: #b8926a;
    color: #fff;
    font-weight: 600;
  }

  .items {
    border-radius: 12px;
    overflow: hidden;
    border: 1px solid #eee1d3;
    background: #fffdf8;
  }

  .list-group-item {
    border: 0;
    border-bottom: 1px solid #f0e3d5;
    background: transparent;
  }

  .list-group-item:last-child {
    border-bottom: 0;
  }

  .item-name {
    font-size: 0.9rem;
    color: #5c4333;
  }

  .item-price {
    font-size: 0.9rem;
    color: #8c6849;
    font-weight: 600;
  }

  .total-price {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #f3e9dd;
    border-radius: 12px;
    padding: 0.9rem 1rem;
    font-size: 1rem;
    color: #6b4f3a;

    b {
      font-size: 1.05rem;
    }
  }
}

/* 주문자 정보 카드 */
.order-info {
  .form-label {
    font-size: 0.85rem;
    color: #8b6a4f;
  }

  .form-control {
    border-radius: 12px;
    border-color: #e0d4c2;
    background-color: #fdfaf5;
    font-size: 0.9rem;
    padding: 0.7rem 0.9rem;

    &:focus {
      border-color: #b8926a;
      box-shadow: 0 0 0 0.15rem rgba(184, 146, 106, 0.25);
      background-color: #fff;
    }
  }

  .form-check-input {
    border-radius: 999px;
    border-color: #d1bba2;

    &:checked {
      background-color: #b8926a;
      border-color: #b8926a;
    }
  }

  .form-check-label {
    font-size: 0.9rem;
    color: #6b4f3a;
  }
}

/* 우드톤 버튼 */
.btn-primary {
  border-radius: 999px;
  background-color: #b8926a;
  border-color: #b8926a;
  font-weight: 600;
  letter-spacing: 0.04em;

  &:hover {
    background-color: #a47c57;
    border-color: #a47c57;
  }
}
</style>