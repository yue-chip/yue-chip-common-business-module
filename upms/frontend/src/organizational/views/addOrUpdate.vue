<template>
  <a-card class="card" :bordered="false">
    <a-form ref="from" :rules="rules" :model="addOrUpdateModel" layout="horizontal" :label-col="{ span:4,offset:0 }" :wrapper-col="{ span: 16,offset:0 }" >
      <a-form-item label="机构名称" name="name" ref="name" >
        <a-input placeholder="请输入机构名称" v-model:value="addOrUpdateModel.name" />
      </a-form-item>
      <a-form-item label="紧急联系电话" name="phoneNumber" ref="phoneNumber" >
        <a-input placeholder="请输入紧急联系电话" v-model:value="addOrUpdateModel.phoneNumber" />
      </a-form-item>

      <a-form-item label="上级机构" name="parentId" ref="parentId" >
        <a-tree-select
          v-model:value="addOrUpdateModel.parentId"
          tree-data-simple-mode
          style="width: 100%"
          :tree-data="treeData"
          allow-clear
          :show-checked-strategy="SHOW_PARENT"
          placeholder="请选择上级节点"
          tree-node-filter-prop="label"
        />
      </a-form-item>
      <a-form-item label="排序" name="sort" ref="sort" >
        <a-input-number  v-model:value="addOrUpdateModel.sort" :min="0" :max="99999" />
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
  import {ref, onMounted, getCurrentInstance} from 'vue'
  import {useRouter,useRoute} from 'vue-router'
  import { RollbackOutlined,SaveOutlined,UndoOutlined } from '@ant-design/icons-vue';
  import type { TreeSelectProps } from 'ant-design-vue';
  import { TreeSelect,message } from 'ant-design-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  const router=useRouter();
  const route = useRoute();
  const _this:any = getCurrentInstance();
  const SHOW_PARENT = TreeSelect.SHOW_PARENT;
  let saveButtonDisabled = ref(false);
  let resetDisabled = ref(false);
  let backDisabled = ref(false);
  let addOrUpdateModel = ref({});
  let treeData: TreeSelectProps['treeData'] = ref([]);
  import {Md5} from 'ts-md5';
  const rules:any={
    name:[{required:true,message:"请输入机构名称",trigger:'blur'}],
    sort:[{required:true,message:"请输入机构排序",trigger:'blur'}],
    phoneNumber:[{required:true,message:"请输入紧急联系电话",trigger:'blur'}],
  };

  onMounted(() => {
    const id = route.query.id;
    if (id) {
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
    _this.ctx.$refs.from.resetFields();
    const id = route.query.id;
    if (id) {
      getInfo(id);
    }
  }

  async function getInfo(id: string ){
    axios.axiosGet("/upms/console/organizational/details",{params: {id:id}},
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
        axios.axiosPut("/upms/console/organizational/update",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              message.info(data.message);
            }
          },null,null)
      }else {
        axios.axiosPost("/upms/console/organizational/add",addOrUpdateModel.value,
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
