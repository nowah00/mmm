<script setup>
import {addItem} from "@/services/cartService.js";
import {useRouter} from "vue-router";
import {computed} from "vue";

const props = defineProps({
  item: {
    id: Number,
    imgPath: String,
    name: String,
    price: Number,
    discountPer: Number
  }
});

const computedItemDiscountPrice = computed(() => {
  return (props.item.price - (props.item.price * props.item.discountPer / 100)).toLocaleString() + '원';
})

const router = useRouter();

const put = async () => {
  const res = await addItem(props.item.id);

  if (res.status === 200 && window.confirm('장바구니에 상품을 담았습니다. 장바구니로 이동하시겠습니까?')) {
    await router.push("/cart");
  }
};
</script>

<template>
  <div class="card shadow-sm">
    <span class="img" :style="{backgroundImage: `url(${props.item.imgPath})`}"
          :aria-label="`상품 사진(${props.item.name})`"></span>
    <div class="card-body">
      <p class="card-text">
        <span class="me-2">{{ props.item.name }}</span>
        <span class="discount badge bg-danger">{{ props.item.discountPer }}%</span>
      </p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary btn-sm" @click="put()">장바구니 담기</button>
        <small class="price text-muted">{{ props.item.price.toLocaleString() }}원</small>
        <small class="real text-danger">{{ computedItemDiscountPrice }}</small>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.card {
  border-radius: 16px;
  overflow: hidden;
  background: #ffffff;
  border: 1px solid #e7dfd3; /* 은은한 베이지 라인 */
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease;

  &:hover {
    transform: translateY(-6px);
    box-shadow: 0 12px 26px rgba(0, 0, 0, 0.12);
    border-color: #d1b89a; /* 살짝 더 진한 우드 톤 */
  }

  /* 정사각형 이미지 영역 */
  .img {
    display: block;
    width: 100%;
    padding-bottom: 100%; /* ★ 정사각형 비율 유지 */
    background-size: cover;
    background-position: center;
    border-bottom: 1px solid #e9e1d6;

    position: relative;

    &::after {
      content: "";
      position: absolute;
      inset: 0;
      background: linear-gradient(
              to bottom,
              rgba(0, 0, 0, 0),
              rgba(0, 0, 0, 0.08)
      );
    }
  }

  .card-body {
    padding: 0.9rem 1rem;

    .card-text {
      font-size: 0.95rem;
      font-weight: 600;
      display: flex;
      align-items: center;
      gap: 8px;
      margin-bottom: 0.5rem;
      color: #543f2c; /* 우드 브라운 텍스트 */

      .discount {
        font-size: 0.7rem;
        border-radius: 999px;
        padding: 3px 7px;
        background: linear-gradient(135deg, #ff5a4f, #ff8a6b);
        border: none;
      }
    }

    .price {
      font-size: 0.85rem;
      color: #a08a73;
      text-decoration: line-through;
      margin-left: auto;
    }

    .real {
      font-size: 0.95rem;
      font-weight: 700;
      color: #c2453b;
      margin-left: 8px;
    }

    .btn {
      border-radius: 999px;
      font-size: 0.8rem;
      padding: 6px 12px;
      background-color: #b8926a;     /* 우드 톤 버튼 */
      border-color: #b8926a;
      font-weight: 600;

      &:hover {
        background-color: #a47c57;
        border-color: #a47c57;
      }
    }

    .d-flex {
      gap: 6px;
      flex-wrap: wrap;
    }
  }
}
</style>