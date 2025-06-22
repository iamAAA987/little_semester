<template>
  <div class="wrapper">
    <header>
        <p>用户注册</p>
    </header>

    <el-form ref="registerForm" :model="account" label-width="80px" class="form-box" :rules="rules" @submit.prevent>
        <el-form-item label="账户" prop="accountId">
            <el-input v-model="account.accountId" placeholder="请输入注册账户(手机号)" @blur="checkAccountId()"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input v-model="account.password" type="password" show-password placeholder="请输入注册密码" />
        </el-form-item>
        <el-form-item label="确认密码" prop="password2">
            <el-input v-model="account.password2" type="password" show-password placeholder="请输入注册确认密码" />
        </el-form-item>
        <el-form-item label="昵称" prop="accountName">
            <el-input v-model="account.accountName" placeholder="请输入注册昵称" />
        </el-form-item>
        <el-form-item label="性别" prop="accountSex">
            <el-radio-group v-model="account.accountSex">
                <el-radio label="1">男性</el-radio>
                <el-radio label="0">女性</el-radio>
            </el-radio-group>
        </el-form-item>
        <div class="button-login">
            <button @click="register()">注册</button>
        </div>
        <div class="button-register">
            <button @click="toLogin()">去登录</button>
        </div>
    </el-form>
    <Footer></Footer>
  </div>
</template>

<script setup>
import Footer from '@/components/Footer.vue';
import {reactive,ref} from "vue"
import {useRouter} from "vue-router"
import {get, post} from "@/api/index"
import { ElMessage } from 'element-plus'

const router = useRouter();

const passward2 = ref('');

const account = reactive({
    accountId:'',
    password:'',
    password2:'',
    accountName:'',
    accountSex:'',
    accountImg:'',
});

const rules = reactive({
    accountId:[
        { required: true, message: '请输入登录账户', trigger: 'blur' }
    ],
    password:[
        { required: true, message: '请输入密码', trigger: 'blur' }
    ],
    accountName:[
        { required: true, message: '请输入昵称', trigger: 'blur' }
    ],
    accountSex:[
        { required: true, message: '请选择性别', trigger: 'blur' }
    ],
    password2:[
        { required: true, message: '请输入确认密码', trigger: 'blur' },
        {
          validator:(rule,value,callback)=>{
            if(value !== account.password){
              callback(new Error('两次输入密码不一致'));
            }else{
              callback();
            }
          }
        }
    ],
});

const register = ()=>{
    registerForm.value.validate((v)=>{
        if(v){
            let url = "/account/register";
            post(url, account, true).then(res=>{
              if(res.data.code == 20000){
                ElMessage({
                  message:"注册成功，请登录",
                  type: 'success',
                })
                router.push('/login')
              }
            });
        }else{
            return false;
        }
    });
}

const toLogin = ()=>{
    router.push('/login');
}

//获得zhuce表单对象
const registerForm = ref(null);

//验证手机号码
const checkAccountId=()=>{
  if(account.accountId !=''){
    let url = `/account/check/${account.accountId}`;
    get(url).then(res =>{
      if(res.data.code == 20005){
        ElMessage({
          message: res.data.message,
          type: 'error',
        });

        account.accountId='';//填入手机号清空
      }
    })
  }else{

  }
}

</script>

<style scoped>
.wrapper header{
  width:100%; height:12vw;
  background: linear-gradient(to right,#fff1eb,#ace0f9);
  color:#596164;
  font-size: 5vw;
  position: fixed; left:0; top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.wrapper header p{
  letter-spacing: 2vw;
}

/* 注册表单部分样式代码 */
.wrapper .form-box{
  width:100%; 
  margin-top:20vw;
  padding: 5vw;
  box-sizing: border-box;

}

.wrapper .form-box .el-input{
  width:90%;
}


.wrapper .button-login{
  width:100%;
  box-sizing: border-box;
  padding:4vw 4vw 0 4vw;
}


.wrapper .button-login button{
  width:100%;
  height:10vw;
  font-size: 3.8vw;
  background-color: #3cba92;
  border:none;
  border-radius: 1.5vw;
  color:#efefef;
  outline: none;
  letter-spacing: 2vw;
}

.wrapper .button-register{
  width:100%;
  box-sizing: border-box;
  padding:4vw;
}

.wrapper .button-register button{
  width:100%;
  height:10vw;
  font-size: 3.8vw;
  background-color: #fdfcfb;
  border:0.3vw solid #e2d1c3;
  border-radius: 1.5vw;
  color:#5f72bd;
  outline: none;
  letter-spacing: 2vw;
}

/** 用户头像上传  **/
  .upload{
     width:42vw;
     height:42vw;
     background-color: #efefef;
     border-radius: 4vw;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
    border-radius: 3vw;
  }
</style>