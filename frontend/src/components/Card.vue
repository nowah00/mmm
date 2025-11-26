<script setup>
import {addItem} from "@/services/cartService";
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

// 상품 할인가
const computedItemDiscountPrice = computed(() => {
  return (props.item.price - (props.item.price * props.item.discountPer / 100)).toLocaleString() + '원';
})

// 라우터 객체
const router = useRouter(); // ①

// 장바구니에 상품 담기
const put = async () => { // ②
  const res = await addItem(props.item.id);

  if (res.status === 200 && window.confirm('장바구니에 상품을 담았습니다. 장바구니로 이동하시겠습니까?')) {
    await router.push("/cart");
  }
};
</script>

<template>
  <div class="card shadow-sm">
    <!-- 상품 사진 출력 -->
    <span class="img" :style="{backgroundImage: `url(${props.item.imgPath})`}"
          :aria-label="`상품 사진(${props.item.name})`"></span>
    <div class="card-body">
      <p class="card-text">
        <!-- 상품 이름 -->
        <span class="me-2">{{ props.item.name }}</span>
        <!-- 상품 할인율 -->
        <span class="discount badge bg-danger">{{ props.item.discountPer }}%</span>
      </p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary btn-sm" @click="put()">장바구니 담기</button>
        <!-- 상품 정가(숫자 데이터에 3자리마다 쉼표 표기) -->
        <small class="price text-muted">{{ props.item.price.toLocaleString() }}원</small>
        <!-- 상품 할인가 -->
        <small class="real text-danger">{{ computedItemDiscountPrice }}</small>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.card {
  border: none;
  border-radius: 16px;
  overflow: hidden;
  background: #ffffff;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.08);
  transition: all 0.25s ease;

  &:hover {
    transform: translateY(-6px);
    box-shadow: 0 12px 28px rgba(0, 0, 0, 0.12);
  }

  .img {
    width: 100%;
    height: 230px;
    background-size: cover;
    background-position: center;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);

    /* 이미지 위 살짝 오버레이 (트렌디 느낌) */
    position: relative;

    &::after {
      content: "";
      position: absolute;
      inset: 0;
      background: linear-gradient(
              to bottom,
              rgba(0, 0, 0, 0.0),
              rgba(0, 0, 0, 0.12)
      );
    }
  }

  .card-body {
    padding: 1rem 1.1rem;

    .card-text {
      font-size: 1rem;
      font-weight: 600;
      display: flex;
      align-items: center;
      gap: 8px;
      margin-bottom: 0.6rem;

      .discount {
        font-size: 0.75rem;
        border-radius: 6px;
        padding: 4px 6px;
        background: linear-gradient(135deg, #ff3d3d, #ff6b6b);
      }
    }

    .price {
      font-size: 0.9rem;
      color: #999;
      text-decoration: line-through;
      margin-left: auto;
    }

    .real {
      font-size: 1rem;
      font-weight: 700;
      color: #e63946;
    }

    .btn {
      border-radius: 10px;
      font-size: 0.85rem;
      padding: 5px 10px;
      transition: background 0.2s ease;

      &:hover {
        background-color: #4e8cff;
      }
    }
  }
}
</style>