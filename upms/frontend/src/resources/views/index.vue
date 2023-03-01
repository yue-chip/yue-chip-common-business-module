<template>
  <div>
    <a-card >
      <a-form ref="from" :model="searchModel" :labelCol="{span: 5,offset:0}" >
        <a-row >
          <a-col :span="6">
            <a-form-item label="作用域" name="scope" ref="scope" >
              <a-select  v-model:value="searchModel.scope" >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
          </a-col>
          <a-col :span="6">
          </a-col>
          <a-col :span="6">
          </a-col>
        </a-row>
        <a-row style="height: 20px;">
          <a-col :span="24" style="text-align:right;">
            <a-form-item>
              <a-space :size="5">
                <a-button type="primary" @click="search()">
                  <template #icon><SearchOutlined /></template>
                  查询
                </a-button>
                <a-button type="primary" @click="add(0,0)" >
                  <template #icon><PlusOutlined /></template>
                  添加
                </a-button>
              </a-space>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-card>

    <a-card>
      <a-table :columns="columns" :data-source="dataList" :pagination="false">
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'operation'">
            <a-space :size="size">
              <a-button size="small" @click="getDetails(record.id)">
                <template #icon><EditOutlined /></template>
                修改
              </a-button>
              <a-button size="small" @click="add(record.id,0)">
                <template #icon><PlusOutlined /></template>
                添加目录
              </a-button>
              <a-button size="small" @click="add(record.id,1)">
                <template #icon><PlusOutlined /></template>
                添加菜单
              </a-button>
              <a-button size="small" @click="add(record.id,2)">
                <template #icon><PlusOutlined /></template>
                添加功能
              </a-button>
              <a-button v-if="record.isDefault === false" size="small" type="danger" @click="del(record.id)">
                <template #icon><DeleteOutlined /></template>
                删除
              </a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal width="800px" v-model:visible="visible" title="添加/修改资源" cancelText="取消" okText="保存" :destroyOnClose="true" :mask="true" :maskClosable="false" @cancel="cancel" @ok="save">
      <a-form ref="from" :rules="rules" :model="addOrUpdateModel" :labelCol="{span: 5,offset:0}" >
        <a-row >
          <a-col :span="12">
            <a-form-item label="作用域" name="scope" ref="scope" >
              <a-select disabled  v-model:value="addOrUpdateModel.scope" >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="类型" name="type" ref="type" >
              <a-select disabled  v-model:value="addOrUpdateModel.type" >
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="名称" name="name" ref="name">
              <a-input placeholder="请输入名称" v-model:value="addOrUpdateModel.name" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="编码" name="code" ref="code">
              <a-input placeholder="请输入编码" v-model:value="addOrUpdateModel.code" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="URL" name="url" ref="url">
              <a-input v-bind:disabled="urlDisabled" placeholder="请输入URL" v-model:value="addOrUpdateModel.url" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="排序" name="sort" ref="sort">
              <a-input-number  v-model:value="addOrUpdateModel.sort" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import {ref, reactive, onMounted,onActivated} from 'vue'
  import { SearchOutlined,PlusOutlined } from '@ant-design/icons-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";

  let visible = ref<boolean>(false);
  let urlDisabled = ref<boolean>(false);
  const searchModel = reactive({})
  let addOrUpdateModel = reactive({})
  const columns = [
    {
      title: '名称',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '编码',
      dataIndex: 'code',
      key: 'code',
    },
    {
      title: 'url',
      dataIndex: 'url',
      key: 'url',
      width: 200,
    },
    {
      title: '备注',
      dataIndex: 'remark',
      key: 'remark',
      width: 300,
    },
    {
      title: '作用域',
      dataIndex: ["scope","desc"],
      key: ["scope","name"],
    },
    {
      title: '类型',
      dataIndex: ["type","desc"],
      key: ["type","name"],
    },
    {
      title: '状态',
      dataIndex: ["state","desc"],
      key: ["state","name"],
    },
    {
      title: '操作',
      key: "operation",
      width: 450,
    },
  ]
  let dataList = ref([]);

  const rules:any={
    code:[{required:true,validator:(rule :any, value:string) => {return checkCodeIsExist(rule,value,this)},trigger:'blur'}],
    name:[{required:true,validator:(rule :any, value:string) => {return checkNameIsExist(rule,value,this)},trigger:'blur'}],
    url:[{validator:(rule :any, value:string) => {return checkUrlIsExist(rule,value,this)},trigger:'blur'}],
  };

  async function checkCodeIsExist(rule :any, value:string, _this:any){
    let promise:any = null;
    if (!value || value.trim() === ''){
      return Promise.reject('请输入编码');
    }else if (value && value.trim() !== ''){
      await axios.axiosGet("/yue-chip-upms-serveresources/console/check/code/exist",{params:{"code":_this.addOrUpdateModel.code,"id":_this.addOrUpdateModel.id}},(data:any)=>{
        if (data.status ===200 && data.data) {
          promise = Promise.reject("该编码已存在");
        }else {
          promise = Promise.resolve();
        }
      },null)
    }
    return promise;
  }

  /**
   * 检查名称是否存在
   * @param rule
   * @param value
   * @param callback
   */
  async function checkNameIsExist(rule :any, value:string, _this:any){
    let promise:any = null;
    if (!value || value.trim() === ''){
      return Promise.reject("请输入名称")
    }else if (value && value.trim() !== ''){
      await axios.axiosGet("/yue-chip-upms-serve/resources/console/check/name/exist",{params:{"code":_this.addOrUpdateModel.name,"id":_this.addOrUpdateModel.id}},(data:any)=>{
        if (data.status === 200 && data.data) {
          promise = Promise.reject("名称在同节点已存在");
        }else {
          promise = Promise.resolve();
        }
      },null)
    }
    return promise;
  }

  /**
   * 检查url是否存在
   * @param rule
   * @param value
   * @param callback
   */
  async function checkUrlIsExist(rule :any, value:string, _this:any){
    let promise:any = null;
    if (addOrUpdateModel.type ==='MENU' && (!value || value.trim() === '')){
      return Promise.reject("请输入url")
    }else if (addOrUpdateModel.type ==='MENU' && value && value.trim() !== ''){
      await axios.axiosGet("/yue-chip-upms-serve/resources/console/check/url/exist",{params:{"code":_this.addOrUpdateModel.url,"id":_this.addOrUpdateModel.id}},(data:any)=>{
        if (data.status === 200 && data.data) {
          promise = Promise.reject("该url已存在");
        }else {
          promise = Promise.resolve();
        }
      },null)
    }
    return promise;
  }

  onActivated(() => {
    search();
  });

  function search(){
    axios.axiosGet("/yue-chip-upms-serve/resources/console/tree",searchModel,(data:any)=>{
      dataList.value = data.data;
    },null)
  }

  function add(){
    visible.value = true;
  }

  function cancel(){
    visible.value = false;
    addOrUpdateModel = {};
  }
</script>

<style scoped>

</style>