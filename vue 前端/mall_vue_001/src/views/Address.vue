<template>
    <div class="wrapper">
  
      <!-- header部分 -->
      <header>
        <p>地址管理</p>
      </header>
  
      <!-- 地址列表部分 -->
      <ul class="addresslist">
        <template v-if="addresslist.length > 0"> 
          <li v-for="(item,index) in addresslist" :key="item.daId" @click="setDefaultAddress(item)">
              <div class="addresslist-left">
                <h3><em>{{ item.contactName }}</em> {{ $fmtSex(item.contactSex) }} 
                  <i>{{item.contactTel}}</i></h3>
                <p>{{item.address}}</p>
              </div>
              <div class="addresslist-right">
                <!-- <i class="fa fa-edit" @click="editUserAddress(index)"></i> -->
                <el-icon><Edit /></el-icon>
                <el-icon><Delete /></el-icon>
              </div>
          </li>
        </template>
        <template v-else>
           <p class="none">还没有添加配送地址</p>
        </template> 
      </ul>
  
      <!-- 新增地址部分 -->
      <div class="addbtn" >
        <el-icon><Select /></el-icon>
        <p>新增收货地址</p>
      </div>
  
      <!-- 底部菜单部分 -->
      <Footer></Footer>
    </div>
  </template>
  
  <script setup>
    import Footer from '../components/Footer.vue'
    import {ref,computed} from "vue"
  import {useRouter,useRoute} from "vue-router" 
  import {ElMessage} from 'element-plus'
  import {get,post} from "@/api";
  import { getSessionStorage ,setLocalStorage} from "@/common.js";

  const router = useRouter();
  const route = useRoute();

const businessId = route.query.businessId;
const account = getSessionStorage('account');

  const addresslist = ref([]);

 //设置默认配送地址的方法
 const setDefaultAddress = (da) =>{
    setLocalStorage(account.accountId,da);
    //默认配送地址设置成功，返回上一页(确认订单)
    ElMessage({
        message: '配送信息设置成功',
        type: 'success',
    })
    router.push({path:'/orderConfirm',query:{businessId:businessId}});
 }

  const init =()=>{
    let url=`/deliveryaddress/list/${account.accountId}`;
    get(url).then(res=>{
        if(res.data.code == 20000){
            addresslist.value = res.data.resultdata
        }
    })
  }
  init();
  </script>
  
  <style scoped>
    /*************** 总容器 ***************/
    .wrapper {
      width: 100%;
      height: 100%;
      
      padding: 2vw;
      box-sizing: border-box;
    }
  
    /*************** header ***************/
    .wrapper header {
      width: 100%;
      height: 12vw;
      background:linear-gradient(to right,#fff1eb,#ace0f9 ); color:#596164;
      display: flex;
      justify-content: space-around;
      align-items: center;
      letter-spacing:1vw;
      font-size: 4.8vw;
      position: fixed;
      left: 0;
      top: 0;
      /*保证在最上层*/
      z-index: 1000;
    }
  
    /*************** addresslist ***************/
    .wrapper .addresslist {
      width: 100%;
      margin-top: 12vw;
    }
    
    .wrapper .addresslist .none{
      text-align: center; color:#c9c9c9;
    }
    .wrapper .addresslist li {
      width: 100%;
      box-sizing: border-box;
      box-shadow: 0.5vw 0.5vw 3vw rgba(0, 0, 0, 0.1);
      padding: 3vw;
      display: flex;
      background-color: #fff;
      border-radius: 1.6vw;
      margin:3vw auto;
    }
  
    .wrapper .addresslist li .addresslist-left {
      flex: 5;
      /*左边这块区域是可以点击的*/
      user-select: none;
      cursor: pointer;
    }
  
    .wrapper .addresslist li .addresslist-left h3 {
      font-size: 3.8vw;
      font-weight: 300;
      color: #79859E;
      margin:0.5vw;
    }

    .wrapper .addresslist li .addresslist-left h3 em{
      color:#3cba92;
      font-style: normal;
      font-weight: 400;
    }
  
    .wrapper .addresslist li .addresslist-left p {
      font-size: 3.4vw;
      color: #35624a;
      margin:0.5vw;
    }
  
    .wrapper .addresslist li .addresslist-right {
      flex: 1;
      font-size: 4.6vw;
      color: #79859E;
      cursor: pointer;
      padding-left: 4vw;
      display: flex;
      justify-content: space-around;
      align-items: center;
    }

    .wrapper .addresslist li .addresslist-right i{
      display: block;
      width: 6.4vw;
      height: 6.4vw;
      background-position: center;
      background-size: cover;
      user-select: none;
      cursor: pointer;
      margin-left:1.5vw;
      
    }


    .wrapper .addresslist li .addresslist-right .edit_icon{
      background-image: url(../assets/edit.png); 
    }

    
    .wrapper .addresslist li .addresslist-right .remove_icon{
      background-image: url(../assets/remove.png);
    }

    .wrapper .addresslist li .addresslist-right .el-icon-delete {
      color: #ff0066;
    }
  
    /*************** 新增地址部分 ***************/
    .wrapper .addbtn {
      width: 100%;
      height: 14vw;
      border-top: solid 1px #fff;
      margin-top: 4vw;
  
      display: flex;
      justify-content: center;
      align-items: center;
  
      font-size: 4vw;

      user-select: none;
      cursor: pointer;

      background-color: #fdfcfb;
      border:0.3vw solid #e2d1c3;
      border-radius: 1.5vw;
      color:#5f72bd;
    }
  
    .wrapper .addbtn p {
      margin-left: 2vw;
    }
    .wrapper .addbtn .fa-plus-circle {
      color: #2c3035;
    }
  </style>
  