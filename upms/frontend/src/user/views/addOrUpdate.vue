<template>
  <a-card class="card" :bordered="false">
    <a-form ref="from" :rules="rules" :model="addOrUpdateModel" layout="horizontal" :label-col="{ span:4,offset:0 }" :wrapper-col="{ span: 16,offset:0 }" >
      <a-form-item label="账号" name="username" ref="username" >
        <a-input placeholder="请输入账号" v-bind:disabled="usernameDisabled" v-model:value="addOrUpdateModel.username" />
      </a-form-item>
      <a-form-item  label="密码" name="pass" ref="pass" >
        <a-input-password v-bind:disabled="passwordDisabled" v-model:value="addOrUpdateModel.pass" />
      </a-form-item>
      <a-form-item label="姓名" name="name" ref="name" >
        <a-input placeholder="请输入姓名" v-model:value="addOrUpdateModel.name" />
      </a-form-item>
    </a-form>
    <a-row >
      <a-col :span="24" style="text-align:center;">
        <a-form-item >
          <a-space :size="5">
            <a-button type="primary" v-bind:disabled="saveButtonDisabled" @click="save()">
              <template #icon><SaveOutlined /></template>
              保存
            </a-button>
            <a-button type="dashed" v-bind:disabled="resetDisabled" @click="reset()">
              <template #icon><UndoOutlined /></template>
              重置
            </a-button>
            <a-button type="danger" v-bind:disabled="backDisabled" @click="back">
              <template #icon><RollbackOutlined /></template>
              返回
            </a-button>
          </a-space>
        </a-form-item>
      </a-col>
    </a-row>
  </a-card>
</template>

<script setup lang="ts">
  import {ref, onMounted, getCurrentInstance} from 'vue'
  import {useRouter,useRoute} from 'vue-router'
  import { RollbackOutlined,SaveOutlined,UndoOutlined } from '@ant-design/icons-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  import {message,Card,Form,Col,FormItem,Input,Space,Button,InputPassword} from "ant-design-vue";
  import "ant-design-vue/es/message/style/index.css"

  const router=useRouter();
  const route = useRoute();
  const _this:any = getCurrentInstance();
  let saveButtonDisabled = ref(false);
  let resetDisabled = ref(false);
  let backDisabled = ref(false);
  let addOrUpdateModel = ref({});
  let passwordDisabled = ref(false);
  let usernameDisabled = ref(false);
  import {Md5} from 'ts-md5';
  const rules:any={
    username:[{required:true,message:"请输入账号",trigger:'blur'}],
    pass:[{required:true,message:"请输入密码",trigger:'blur'}],
    name:[{required:true,message:"请输入姓名",trigger:'blur'}],
  };

  onMounted(() => {
    const id = route.query.id;
    if (id) {
      rules.pass[0].required=false;
      rules.username[0].required=false;
      passwordDisabled.value=true;
      usernameDisabled.value=true;
      getInfo(id);
    }
  });

  function back():void{
    router.go(-1);
  }

  function reset(){
    addOrUpdateModel.value = {};
    _this.ctx.$refs.from.resetFields();
    const id = route.query.id;
    if (id) {
      getInfo(id);
    }
  }

  function getInfo(id: string ){
    axios.axiosGet("/yue-chip-upms-serve/upms/console/user/details",{params: {id:id}},
      (data:any)=>{
        if (data.status === 200 ) {
          addOrUpdateModel.value = data.data;
        }
      },null,null)
  }

  function save(){
    buttonDisbled(true,true,true);
    const id = route.query.id;
    if (id) {
      addOrUpdateModel.value.id = id;
    }
    _this.ctx.$refs.from.validate().then(() => {
      if (addOrUpdateModel.value.id) {
        axios.axiosPut("/yue-chip-upms-serve/upms/console/user/update",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              message.info(data.message);
            }
          },null,null)
      }else {
        addOrUpdateModel.value.passwordI = Md5.hashStr(addOrUpdateModel.value.pass);
        addOrUpdateModel.value.pass = null;
        axios.axiosPost("/yue-chip-upms-serve/upms/console/user/add",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              message.info(data.message);
            }
          },null,null)
      }
    }).catch((err: any) => {}).finally(()=>{buttonDisbled(false,false,false)});
  }

  function buttonDisbled(save:boolean,reset:boolean,back:boolean){
    saveButtonDisabled.value = save;
    resetDisabled.value = reset;
    backDisabled.value =back;
  }
</script>

<style scoped>

</style>
