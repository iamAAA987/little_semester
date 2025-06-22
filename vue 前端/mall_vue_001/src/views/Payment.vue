<template>
    <div class="wrapper">
  
      <!-- header部分 -->
      <header>
        <p>在线支付</p>
      </header>
  
      <!-- 订单信息部分 -->
      <h3>订单信息：</h3>
      <div class="order-info">
        <p>
          商家名称
 
          <el-icon v-if="!isShowDetailet" @click="detailetShow" ><CaretBottom /></el-icon>
          <el-icon v-else @click="detailetShow" ><CaretTop /></el-icon>
        </p>
        <p>&#165; 10000</p>
      </div>
  
      <ul class="order-detailet" v-show="isShowDetailet">
        <li>
          <img src="../assets/businessImg/sj01.png" alt="商家图片">
        </li>
        <!-- 订单明细部分 -->
        <li>  
          <p>商品名称 x 2 </p>
          <p>&#165; 20.0</p>
        </li>
        <li>
          <p>配送费</p>
          <p>&#165; 5</p>
        </li>
      </ul>
  
      <!-- 支付方式部分 -->
      <ul class="payment-type">
        <li @click="alipay=true">
          <img src="../assets/alipay.png">
          <el-icon v-show="alipay" :size="26" style="color:#3cba92"><CircleCheckFilled /></el-icon>
        </li>
        <li @click="alipay=false"   >
          <img src="../assets/wechat.png">
          <el-icon v-show="!alipay" :size="26" style="color:#3cba92"><CircleCheckFilled /></el-icon>
        </li>
      </ul>
      <div class="payment-button">
        <button>确认支付</button>
      </div>
  
      <!-- 底部菜单部分 -->
      <Footer></Footer>
    </div>
  </template>
  
  <script setup>import Footer from '../components/Footer.vue'
  import {ref,reactive,computed,onMounted,onUnmounted} from "vue"
  import {useRouter,useRoute} from "vue-router"
  import { get, post } from "@/api";
  import { ElMessage } from 'element-plus'
  
  //创建路由对象
  const router = useRouter();
  const route = useRoute();

  const isShowDetailet = ref(false);
  const alipay = ref(true);
  const detailetShow =()=>{
    isShowDetailet.value = !isShowDetailet.value;
  }

  
  const init = ()=>{

  }
  init();

  onMounted(() => {
      //项目购物车 一但提交，生成订单，就不能再返回上一页 订单组件见面。
      history.pushState(null,null,document.URL);
      //pushState 事件能够监控到history对象变化
      window.onpopstate=()=>{
        this.$router.push({path:'/'});
      }
  });

  // 销毁
  onUnmounted(() => {
    //当前组件销毁的钩子函数
    window.onpopstate = null;
  });
  </script>
  
  <style scoped>
    /****************** 总容器 ******************/
    .wrapper {
      width: 100%;
      height: 100%;
      padding: 3vw;
      box-sizing: border-box;
    }
  
    /****************** header部分 ******************/
    .wrapper header {
      width: 100%;
      height: 12vw;
      background:linear-gradient(to right,#fff1eb,#ace0f9 ); color:#596164;
      letter-spacing:2vw;
      font-size: 4.8vw;
  
      position: fixed;
      left: 0;
      top: 0;
      z-index: 1000;
  
      display: flex;
      justify-content: center;
      align-items: center;
    }
  
    /****************** 订单信息部分 ******************/
    .wrapper h3 {
      margin-top: 12vw;
      box-sizing: border-box;
      padding: 4vw 4vw 0;
  
      font-size: 4vw;
      font-weight: 300;
      color: #999;
    }
  
    .wrapper .order-info {
      box-sizing: border-box;
      padding: 4vw;
      font-size: 4vw;
      color: #666;
  
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  
    .wrapper .order-info p:last-child {
      color: orangered;
    }
  
    /****************** 订单明细部分 ******************/
    .wrapper .order-detailet {
      width: 100%;
    }
  
    .wrapper .order-detailet li {
      width: 100%;
      box-sizing: border-box;
      padding: 1vw 4vw;
  
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  
    .wrapper .order-detailet li img {
      width: 16vw;
      height: 16vw;
      border-radius: 5px;
    }

    
  
    .wrapper .order-detailet li p {
      font-size: 3.8vw;
      color: #79859E;
    }
  
    /****************** 支付方式部分 ******************/
    .wrapper .payment-type {
      width: 100%;
      margin-top:15vw;
      border-top:0.1vw solid #cecece;
      padding-top:4vw;
    }
  
    .wrapper .payment-type li {
      
      width: 100%;
      box-sizing: border-box;
      padding:2vw 4vw;
  
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  
    .wrapper .payment-type li img {
      width: 30vw;
      height: 8vw;
    }
  
    .wrapper .payment-type li .fa-check-circle {
      font-size: 5vw;
      color: #3cba92;
    }
  
    .wrapper .payment-button {
      width: 100%;
      box-sizing: border-box;
      padding: 4vw;
    }

    .wrapper .payment-button .el-icon-success{
        color:#3cba92;
    }
  
    .wrapper .payment-button button {
      width: 100%;
      height: 10vw;
      border: none;
      /*去掉外轮廓线*/
      outline: none;
      border-radius: 4px;
      background-color: #3cba92;
      color: #fff;
    }
  </style>
  