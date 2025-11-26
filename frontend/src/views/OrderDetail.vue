<script setup>
import { reactive } from "vue";
import { getOrder } from "@/services/orderService";
import { useRoute } from "vue-router";

const route = useRoute();

const state = reactive({
  order: {
    id: 0,
    name: "",
    address: "",
    payment: "",
    amount: 0,
    created: "",
    items: [],
  },
});

(async function onCreated() {
  const id = route.params.id;
  const res = await getOrder(id);

  if (res.status === 200) {
    state.order = res.data;
  }
})();
</script>

<template>
  <div class="order-detail py-5">
    <div class="container">
      <div class="text-center mb-4">
        <h2 class="title mb-2">주문 상세</h2>
        <p class="subtitle mb-0">
          주문하신 내역과 결제 정보를 확인할 수 있습니다.
        </p>
      </div>

      <div class="row g-4">
        <!-- 주문 정보 -->
        <div class="order col-lg-8">
          <div class="card-wood h-100">
            <div class="h5 mb-3">
              <b>주문 내용</b>
            </div>
            <table class="table order-table mb-0">
              <tbody>
              <tr>
                <th>주문 ID</th>
                <td>{{ state.order.id }}</td>
              </tr>
              <tr>
                <th>주문자명</th>
                <td>{{ state.order.name }}</td>
              </tr>
              <tr>
                <th>주소</th>
                <td>{{ state.order.address }}</td>
              </tr>
              <tr>
                <th>결제 금액</th>
                <td>{{ state.order.amount.toLocaleString() }}원</td>
              </tr>
              <tr>
                <th>결제 수단</th>
                <td>
                  {{
                    state.order.payment === "card"
                        ? "카드"
                        : "무통장입금(한국은행 123-456789-777)"
                  }}
                </td>
              </tr>
              <tr>
                <th>결제 일시</th>
                <td>{{ state.order.created.toLocaleString() }}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- 주문 상품 -->
        <div class="items col-lg-4">
          <div class="card-wood h-100">
            <div class="h5 mb-3 d-flex justify-content-between align-items-center">
              <b>주문 상품</b>
              <span class="badge count-badge">
                {{ state.order.items.length }}
              </span>
            </div>
            <table class="table items-table mb-0">
              <thead>
              <tr>
                <th>번호</th>
                <th>상품명</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item, idx) in state.order.items" :key="item.id">
                <td>{{ idx + 1 }}</td>
                <td>{{ item.name }}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.order-detail {
  background: #f7f6f2;

  .container {
    max-width: 960px;
  }
}

.title {
  color: #6b4f3a;
}

.subtitle {
  color: #a3896d;
  font-size: 0.95rem;
}

/* 공통 우드 카드 */
.card-wood {
  background: #fffaf3;
  border-radius: 18px;
  border: 1px solid #e7dfd3;
  box-shadow: 0 10px 26px rgba(0, 0, 0, 0.06);
  padding: 1.8rem 1.6rem;
}

/* 주문 정보 테이블 */
.order-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;

  th,
  td {
    padding: 12px 18px;
    font-size: 0.9rem;
    vertical-align: middle;
  }

  th {
    width: 140px;
    background: #f3e9dd;
    color: #6b4f3a;
    font-weight: 600;
    border-bottom: 1px solid #e3d4c4;
    border-right: 1px solid #e3d4c4;
  }

  td {
    background: #fffdf8;
    color: #5c4333;
    border-bottom: 1px solid #f0e3d5;
  }

  tr:first-child th {
    border-top-left-radius: 12px;
  }

  tr:first-child td {
    border-top-right-radius: 12px;
  }

  tr:last-child th {
    border-bottom-left-radius: 12px;
  }

  tr:last-child td {
    border-bottom-right-radius: 12px;
  }
}

/* 주문 상품 테이블 */
.items-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;

  thead th {
    font-size: 0.85rem;
    font-weight: 600;
    color: #8b6a4f;
    background: #f5ede1;
    border-bottom: 1px solid #e2d4c4;
    padding: 10px 14px;
  }

  tbody td {
    font-size: 0.9rem;
    padding: 9px 14px;
    border-bottom: 1px solid #f0e3d5;
    background: #fffdf8;
    color: #5c4333;
  }

  tbody tr:last-child td {
    border-bottom: 0;
  }
}

/* 배지 */
.count-badge {
  background: #b8926a;
  color: #fff;
  font-size: 0.8rem;
  padding: 4px 10px;
  border-radius: 999px;
}
</style>