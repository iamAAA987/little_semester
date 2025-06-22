<template>
    <ul class="footer">
        <li @click="toHome()">
            <i class="icon icon-home" />
            <p>首页</p>
        </li>
        <li @click="toCart()">
            <i class="icon icon-cart" />
            <p>购物车</p>
        </li>
        <li @click="toOrder()">
            <i class="icon icon-order"/>
            <p>订单</p>
        </li>
        <li @click="toUserCenter()">
            <i class="icon icon-me" />
            <p>我的</p>
        </li>
    </ul>
</template>

<script setup>
import {useRouter} from "vue-router"

const router = useRouter();

const toHome = ()=>{
    router.push('/');
}

const toUserCenter = ()=>{
    router.push('/login');
}

const toCart = ()=>{
    router.push('/cart');
}
// export default {
//     data() {
//         return {
//         };
//     },
//     methods: {
//         toHome(){
//             // this.$router获得路由对象，调用push() 跳转至指定路径
//             // 重复路由报错，处理异常，加上 .catch(e=>{})
//             this.$router.push('/').catch(e=>{})
//         },
//         toUserCenter(){
//             this.$router.push('/login').catch(e=>{})
//         },
//         toCart(){
//             this.$router.push('/cart').catch(e=>{})
//         },
//         toOrder(){
//             this.$router.push('/orderList').catch(e=>{})
//         },
//         toCenter(){
//             this.$router.push('/center').catch(e=>{});
//         }
//     },
// };
</script>

<style scoped>
.icon{ width:8vw; height: 8vw; display: block; background-size: cover; }
.icon-me{ background-image: url(../assets/me.png); }
.icon-order{ background-image: url(../assets/order.png); }
.icon-home{ background-image: url(../assets/home.png); }
.icon-cart{ background-image: url(../assets/cart.png); }
/*/////底部导航栏/////*/
.wrapper .footer{
    width: 100%;
    height: 14vw;
    border-top: solid 1px #ddd;
    background-color: #fff;
    position: fixed;
    left: 0;
    bottom: -0.1vw;
    display: flex;
    justify-content: space-around;
    align-items: center;
}
.wrapper .footer li{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #79859E;
    user-select: none;
    cursor: pointer;
}
.wrapper .footer li p{ font-size:2.8vw; }
.wrapper .footer li i{ font-size: 5vw; }
</style>