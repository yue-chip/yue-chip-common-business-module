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
      <a-form-item label="所属机构" name="organizationalId" ref="organizationalId" >
        <a-tree-select
          v-model:value="addOrUpdateModel.organizationalId"
          tree-data-simple-mode
          style="width: 100%"
          :tree-data="treeData"
          allow-clear
          :show-checked-strategy="SHOW_PARENT"
          placeholder="请选择所属机构"
          tree-node-filter-prop="label"
        />
      </a-form-item>
      <a-form-item label="电话号码" name="phoneNumber" ref="phoneNumber" >
        <a-input placeholder="请输入姓名" v-model:value="addOrUpdateModel.phoneNumber" />
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
            <a-button type="dashed" v-bind:disabled="backDisabled" @click="back">
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
  import {ref, onMounted} from 'vue'
  import {useRouter,useRoute} from 'vue-router'
  import { RollbackOutlined,SaveOutlined,UndoOutlined } from '@ant-design/icons-vue';
  import type { FormInstance,TreeSelectProps } from 'ant-design-vue';
  import { TreeSelect,message } from 'ant-design-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  const router=useRouter();
  const route = useRoute();
  const from = ref<FormInstance>();
  let saveButtonDisabled = ref(false);
  let resetDisabled = ref(false);
  let backDisabled = ref(false);
  let addOrUpdateModel = ref({});
  let passwordDisabled = ref(false);
  let usernameDisabled = ref(false);
  const SHOW_PARENT = TreeSelect.SHOW_PARENT;
  let treeData: TreeSelectProps['treeData'] = ref([]);
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
      // rules.username[0].required=false;
      passwordDisabled.value=true;
      usernameDisabled.value=true;
      getInfo(id);
    }
    axios.axiosGet("/upms/console/organizational/tree/select",{params: {id:id}},
      (data:any)=>{
        if (data.status === 200 ) {
          treeData.value = data.data;
        }
      },null,null)
  });

  function back():void{
    router.go(-1);
  }

  function reset(){
    addOrUpdateModel.value = {};
    from.value.resetFields();
    const id = route.query.id;
    if (id) {
      getInfo(id);
    }
  }

  async function getInfo(id: string ){
    axios.axiosGet("/upms/console/user/details",{params: {id:id}},
      (data:any)=>{
        if (data.status === 200 ) {
          addOrUpdateModel.value = data.data;
        }
      },null,null)
  }

  function save(){
    buttonDisabled(true,true,true);
    const id = route.query.id;
    if (id) {
      addOrUpdateModel.value.id = id;
    }
    from.value.validateFields().then(() => {
      if (addOrUpdateModel.value.id) {
        axios.axiosPut("/upms/console/user/update",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              message.info(data.message);
            }
          },null,null)
      }else {
        addOrUpdateModel.value.passwordI = Md5.hashStr(addOrUpdateModel.value.pass);
        addOrUpdateModel.value.pass = null;
        axios.axiosPost("/upms/console/user/add",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              message.info(data.message);
            }
          },null,null)
      }
    }).catch((err: any) => {}).finally(()=>{buttonDisabled(false,false,false)});
  }

  function buttonDisabled(save:boolean,reset:boolean,back:boolean){
    saveButtonDisabled.value = save;
    resetDisabled.value = reset;
    backDisabled.value =back;
  }
</script>

<style scoped>

</style>
