<template>
  <div class="wrapper">
    <header style="position: relative; background:linear-gradient(to right,#fff1eb,#ace0f9); color:#596164;">
      <span class="back-text" @click="goHome">返回</span>
      <p>购物车</p>
    </header>
    <div v-if="cartList.length === 0" class="empty-cart">
      <!-- <img src="../assets/empty-cart.png" alt="空购物车" style="width:40vw;margin:10vw auto;display:block;"> -->
      <p>购物车空空如也~</p>
    </div>
    <div v-else>
      <div v-for="item in cartList" :key="item.cartId" class="cart-item">
        <img :src="item.goods.goodsImg" class="goods-img" />
        <div class="goods-info">
          <div class="goods-name">{{ item.goods.goodsName }}</div>
          <div class="goods-business">商家：{{ item.businessName }}</div>
          <div class="goods-price">￥{{ item.goods.goodsPrice }}</div>
          <div class="goods-quantity">
            <button @click="updateQuantity(item, -1)">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="updateQuantity(item, 1)">+</button>
          </div>
          <div class="goods-total">小计：￥{{ (item.goods.goodsPrice * item.quantity).toFixed(2) }}</div>
          <button class="delete-btn" @click="deleteItem(item)">删除</button>
        </div>
      </div>
    </div>
    <div class="cart-footer">
      <div>总价：<span class="total-price">￥{{ totalPrice }}</span></div>
      <button class="checkout-btn" @click="goToCheckout" :disabled="cartList.length === 0">去结算</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { get, post } from '@/api';
import { getSessionStorage } from '@/common';
import { ElMessage } from 'element-plus';

const router = useRouter();
const account = getSessionStorage('account');
const cartList = ref([]);

const goHome = () => {
  router.push('/');
};

const loadCart = () => {
  if (!account) {
    router.push('/login');
    return;
  }
  get(`/cart/listAllCartWithDetail/${account.accountId}`).then(res => {
    if (res.data.code === 20000) {
      cartList.value = res.data.resultdata;
    }
  });
};

const updateQuantity = (item, num) => {
  let newQuantity = item.quantity + num;
  if (newQuantity < 1) {
    deleteItem(item);
    return;
  }
  let cart = {
    goodsId: item.goodsId,
    businessId: item.businessId,
    accountId: account.accountId,
    quantity: newQuantity
  };
  post('/cart/update', cart, true).then(res => {
    if (res.data.code === 20000) {
      item.quantity = newQuantity;
    }
  });
};

const deleteItem = (item) => {
  let cart = {
    goodsId: item.goodsId,
    businessId: item.businessId,
    accountId: account.accountId
  };
  post('/cart/remove', cart, true).then(res => {
    if (res.data.code === 20000) {
      cartList.value = cartList.value.filter(c => c.cartId !== item.cartId);
    }
  });
};

const totalPrice = computed(() => {
  let s = 0;
  for (let item of cartList.value) {
    s += item.goods.goodsPrice * item.quantity;
  }
  return s.toFixed(2);
});

const goToCheckout = () => {
  if (cartList.value.length === 0) return;
  // 跳转到订单确认页，传递businessId（如需多商家可扩展）
  // 这里只处理单商家结算，若多商家可分组结算
  const first = cartList.value[0];
  router.push({ path: '/orderConfirm', query: { businessId: first.businessId } });
};

loadCart();
</script>

<style scoped>
.wrapper {
  width: 100%;
  min-height: 100vh;
  background: #f6faff;
  padding-bottom: 18vw;
}
header {
  width: 100%;
  height: 12vw;
  background: linear-gradient(to right,#fff1eb,#ace0f9 );
  color: #596164;
  font-size: 4.8vw;
  letter-spacing:2vw;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 1000;
}
.back-text {
  position: absolute;
  left: 4vw;
  top: 50%;
  transform: translateY(-50%);
  font-size: 4vw;
  color: #3cba92;
  cursor: pointer;
}
.empty-cart {
  text-align: center;
  color: #999;
  margin-top: 30vw;
}
.cart-item {
  display: flex;
  background: #fff;
  border-radius: 2vw;
  box-shadow: 0.5vw 0.5vw 3vw rgba(0,0,0,0.06);
  margin: 4vw 2vw;
  padding: 2vw;
  align-items: center;
}
.goods-img {
  width: 18vw;
  height: 18vw;
  border-radius: 2vw;
  margin-right: 3vw;
}
.goods-info {
  flex: 1;
}
.goods-name {
  font-size: 4vw;
  font-weight: 600;
  color: #001747;
}
.goods-business {
  font-size: 3vw;
  color: #80b5e6;
}
.goods-price {
  font-size: 3.5vw;
  color: #ff7e00;
}
.goods-quantity {
  display: flex;
  align-items: center;
  margin: 1vw 0;
}
.goods-quantity button {
  width: 6vw;
  height: 6vw;
  border: none;
  background: #ace0f9;
  color: #001747;
  font-size: 4vw;
  border-radius: 50%;
  margin: 0 2vw;
}
.goods-total {
  font-size: 3vw;
  color: #79859E;
}
.delete-btn {
  background: #ff4d4f;
  color: #fff;
  border: none;
  border-radius: 1vw;
  padding: 1vw 3vw;
  margin-top: 1vw;
  font-size: 3vw;
}
.cart-footer {
  width: 100%;
  height: 14vw;
  background: #fff;
  position: fixed;
  left: 0;
  bottom: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 -2px 8px rgba(0,0,0,0.04);
  padding: 0 6vw;
  font-size: 4vw;
}
.total-price {
  color: #ff7e00;
  font-weight: bold;
}
.checkout-btn {
  background: #3cba92;
  color: #fff;
  border: none;
  border-radius: 4vw;
  padding: 2vw 8vw;
  font-size: 4vw;
}
.checkout-btn:disabled {
  background: #ccc;
}
</style> 