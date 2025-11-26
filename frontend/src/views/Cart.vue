<script setup>
import {getItems, removeItem} from "@/services/cartService.js"
import {reactive} from "vue";

const state = reactive({
  items: []
});

const load = async () => {
  const res = await getItems();

  if (res.status === 200) {
    state.items = res.data;
  }
}

const remove = async (itemId) => {
  const res = await removeItem(itemId);

  if (res.status === 200) {
    window.alert("선택하신 장바구니의 상품을 삭제했습니다.");
    await load();
  }
}

(async function onCreated() {
  await load();
})();
</script>

<template>
  <div class="cart py-5">
    <div class="container">
      <div class="text-center mb-4">
        <h2 class="title mb-2">장바구니</h2>
        <p class="subtitle">
          악기를 한 번 더 확인해 주세요.
        </p>
      </div>

      <template v-if="state.items.length">
        <div class="card-wood">
          <ul class="items">
            <li
                v-for="i in state.items"
                :key="i.id"
                class="cart-item d-flex align-items-center"
            >
              <!-- 썸네일 -->
              <div class="thumb-wrap">
                <img
                    :alt="`상품 사진(${i.name})`"
                    :src="i.imgPath"
                    class="thumb"
                />
              </div>

              <!-- 정보 -->
              <div class="info flex-grow-1">
                <b class="name">{{ i.name }}</b>
                <span class="price">
                  {{
                    (
                        i.price -
                        (i.price * i.discountPer) / 100
                    ).toLocaleString()
                  }}원
                </span>
              </div>

              <!-- 삭제 -->
              <button
                  type="button"
                  class="remove-btn"
                  @click="remove(i.id)"
                  title="삭제"
              >
                ×
              </button>
            </li>
          </ul>

          <div class="act mt-4">
            <router-link to="/order" class="btn btn-primary">
              주문하러 가기
            </router-link>
          </div>
        </div>
      </template>

      <div class="text-center py-5 empty" v-else>
        장바구니가 비어있습니다.
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.cart {
  background: #f7f6f2;

  .container {
    max-width: 900px;
  }

  .title {
    color: #6b4f3a;
  }

  .subtitle {
    color: #a3896d;
    font-size: 0.95rem;
  }

  .card-wood {
    background: #fffaf3;
    border-radius: 18px;
    border: 1px solid #e7dfd3;
    box-shadow: 0 10px 26px rgba(0, 0, 0, 0.06);
    padding: 1.8rem 1.5rem;
  }

  .items {
    list-style: none;
    margin: 0;
    padding: 0;

    .cart-item {
      padding: 0.9rem 0.4rem;
      border-bottom: 1px solid #f0e3d5;
      gap: 1rem;

      &:last-child {
        border-bottom: 0;
      }
    }
  }

  .thumb-wrap {
    flex: 0 0 90px;
  }

  .thumb {
    width: 90px;
    height: 90px;
    border-radius: 12px;
    object-fit: cover;
    border: 1px solid #e6d9c9;
    background-color: #f5ede3;
  }

  .info {
    display: flex;
    flex-direction: column;
    gap: 0.4rem;

    .name {
      font-size: 0.95rem;
      color: #5c4333;
    }

    .price {
      font-size: 0.9rem;
      color: #8c6849;
      font-weight: 600;
    }
  }

  .remove-btn {
    border: none;
    background: transparent;
    color: #c2453b;
    font-size: 1.6rem;
    line-height: 1;
    padding: 0 0.3rem;
    cursor: pointer;
    transition: transform 0.15s ease, color 0.15s ease;

    &:hover {
      transform: scale(1.1);
      color: #a52f2b;
    }
  }

  .act {
    display: flex;
    justify-content: flex-end;
    margin-top: 1rem;

    .btn-primary {
      min-width: 220px;
      border-radius: 999px;
      background-color: #b8926a;
      border-color: #b8926a;
      font-weight: 600;
      letter-spacing: 0.04em;
      padding: 0.9rem 1.8rem;

      &:hover {
        background-color: #a47c57;
        border-color: #a47c57;
      }
    }
  }

  .empty {
    color: #8c7a64;
    font-size: 0.95rem;
  }

  @media (max-width: 576px) {
    .card-wood {
      padding: 1.4rem 1.1rem;
    }

    .thumb-wrap {
      flex: 0 0 72px;
    }

    .thumb {
      width: 72px;
      height: 72px;
    }
  }
}
</style>