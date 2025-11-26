<script setup>
import {reactive} from "vue";
import {getOrders} from "@/services/orderService.js";

const state = reactive({ // ①
  orders: []
});

const load = async () => { // ②
  const res = await getOrders();

  if (res.status === 200) {
    state.orders = res.data;
  }
};

(async function onCreated() { // ③
  await load();
})();
</script>

<template>
  <div class="orders py-5">
    <div class="container">

      <div class="text-center mb-4">
        <h2 class="title mb-2">주문 내역</h2>
        <p class="subtitle">지금까지 주문하신 내역을 확인할 수 있습니다.</p>
      </div>

      <div class="card-wood">
        <table class="table orders-table mb-0">
          <thead>
          <tr>
            <th class="text-center">번호</th>
            <th>주문자명</th>
            <th>결제 수단</th>
            <th>결제 금액</th>
            <th>결제일시</th>
            <th>자세히 보기</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="(o, idx) in state.orders" :key="o.id">
            <td class="text-center">{{ state.orders.length - idx }}</td>
            <td>{{ o.name }}</td>
            <td>{{ o.payment === 'card' ? '카드' : '무통장입금' }}</td>
            <td>{{ o.amount.toLocaleString() }}원</td>
            <td>{{ o.created.toLocaleString() }}</td>
            <td>
              <router-link :to="`/orders/${o.id}`" class="detail-link">
                자세히 보기
              </router-link>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

    </div>
  </div>
</template>

<style lang="scss" scoped>
.orders {
  background: #f7f6f2;
  min-height: 70vh;

  .container {
    max-width: 950px;
  }

  .title {
    color: #6b4f3a;
  }

  .subtitle {
    color: #a3896d;
    font-size: 0.95rem;
  }

  /* 고정된 우드 카드 박스 */
  .card-wood {
    background: #fffaf3;
    border-radius: 18px;
    border: 1px solid #e7dfd3;
    box-shadow: 0 10px 26px rgba(0, 0, 0, 0.06);
    padding: 1.6rem 1.3rem;
  }

  /* 테이블 전체 스타일 */
  .orders-table {
    width: 100%;
    border-collapse: separate;
    border-spacing: 0;

    thead th {
      padding: 14px 16px;
      background: #f3e9dd;
      color: #6b4f3a;
      font-weight: 600;
      border-bottom: 1px solid #e2d4c4;
      font-size: 0.9rem;

      &:first-child {
        border-top-left-radius: 12px;
      }
      &:last-child {
        border-top-right-radius: 12px;
      }
    }

    tbody td {
      padding: 14px 16px;
      font-size: 0.9rem;
      background: #fffdf8;
      color: #5c4333;
      border-bottom: 1px solid #f1e3d5;

      &.text-center {
        text-align: center;
      }
    }

    tbody tr:last-child td:first-child {
      border-bottom-left-radius: 12px;
    }
    tbody tr:last-child td:last-child {
      border-bottom-right-radius: 12px;
    }

    /* 링크 */
    .detail-link {
      color: #b07338;
      font-weight: 600;
      text-decoration: none;
      transition: color 0.2s;

      &:hover {
        color: #915c2c;
      }
    }
  }
}
</style>