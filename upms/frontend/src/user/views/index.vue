<template>
  <div>
    <a-card >
      <a-form ref="from" :model="searchModel" :labelCol="{span: 3,offset:0}" >
        <a-row >
          <a-col :span="6">
            <a-form-item label="姓名" name="name" ref="name" >
              <a-input placeholder="请输入姓名" v-model:value="searchModel.name" />
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
                <a-button type="primary" @click="searchModel.value.pageNumber=1;search()">
                  <template #icon><SearchOutlined /></template>
                  查询
                </a-button>
                <a-button type="primary" @click="add()" >
                  <template #icon><PlusOutlined /></template>
                  添加
                </a-button>
                <a-button type="danger" @click="">
                  <template #icon><DeleteOutlined /></template>
                  删除
                </a-button>
              </a-space>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-card>

    <a-card>
      <a-table rowKey="id" :columns="columns" :data-source="dataList" :pagination="pagination" :loading="loading" :scroll="{ y: 440 }" >
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'operation'">
            <a-space :size="5">
              <a-button size="small" @click="edit(record.id)">
                <template #icon><EditOutlined /></template>
                修改
              </a-button>
              <a-button size="small" @click="permissions(record.id)">
                <template #icon><FilterOutlined /></template>
                权限
              </a-button>
              <a-button size="small" @click="user(record.id)">
                <template #icon><UserAddOutlined /></template>
                人员
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
  </div>
</template>

<script setup lang="ts">
  import {ref, onActivated,getCurrentInstance} from 'vue'
  import { SearchOutlined,PlusOutlined,UserAddOutlined,FilterOutlined,DeleteOutlined } from '@ant-design/icons-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  import {message,Modal,Card,Select,Form,Col,FormItem,Input,Space,Button} from "ant-design-vue";
  import qs from "qs";
  import "ant-design-vue/es/message/style/index.css"
  const _this:any = getCurrentInstance();
  let loading = ref(false);
  let searchModel = ref({pageSize:10,pageNumber:1});
  const columns = [
    {
      title: '姓名',
      dataIndex: 'name',
      fixed: 'left',
      key: 'name',
    },
    {
      title: '出身日期',
      dataIndex: 'birthday',
      key: 'birthday',
    },
    {
      title: '操作',
      key: "operation",
      fixed: 'right',
      width: '300px',
    },
  ]
  let dataList = ref([]);

  const pagination = ref({
      current: searchModel.value.pageNumber?searchModel.value.pageNumber:1,
      pageSize: searchModel.value.pageSize?searchModel.value.pageSize:30,
      onChange:(pageNumber: number, pageSize: number)=>{ searchModel.value.pageSize=pageSize ;searchModel.value.pageNumber=pageNumber ;search()},
  });

  onActivated(() => {
    search();
  });

  function search(){
    loading.value=true;
    axios.axiosGet("/yue-chip-upms-serve/upms/console/user/list",{params:searchModel.value},(data:any)=>{
      dataList.value = data.data;
      pagination.value.total = data.totalElements;
      pagination.value.current = data.pageNumber;
      pagination.value.pageSize = data.pageSize;
      loading.value=false;
    },null)
  }

  function del(id:string){
    Modal.confirm({
      title: '是否要删除该数据?',
      // content: '',
      okText: 'Yes',
      okType: 'danger',
      cancelText: 'No',
      onOk() {
        const params = {params:{id:id}};
        axios.axiosDelete("/yue-chip-upms-serve/upms/console/user/delete",params,(data:any)=>{
          if (data.status === 200 ) {
              message.info(data.message);
              search();
          }
        },null);
      },
      onCancel() {
      },
    });
  }


  function permissions(_roleId:string){
    permissionsVisible.value = true;
    roleId=_roleId;
    axios.axiosGet("/yue-chip-upms-serve/upms/console/resources/tree",{params: {}},(data:any)=>{
      treeData.value = data.data;
    },null)
    axios.axiosGet("/yue-chip-upms-serve/upms/console/role/resources",{params: {roleId:_roleId}},(data:any)=>{
      permissionsCheckedKeys.value = data.data;
    },null)

  }

  function permissionsCancel(){
    permissionsCheckedKeys.value = [];
    permissionsVisible.value = false;
    treeData.value=[];
    roleId = null;
  }

  function permissionsSave(){
    axios.axiosPost("/yue-chip-upms-serve/upms/console/role/resources", {"roleId":roleId,resourcesIds:permissionsCheckedKeys.value},(data:any)=>{
      if (data.status === 200) {
        message.info(data.message);
        permissionsCancel();
      }
    },null)
  }

  function user(_roleId:string) {
    roleId = _roleId;
    userVisible.value = true;
    axios.axiosGet("/yue-chip-upms-serve/upms/console/user/list", {params: {pageNumber:1,pageSize:99999}},(data:any)=>{
      const list = data.data;
      userOptions.value = [];
      for (const index:any in list) {
        userOptions.value.push({"value":list[index].id,"label":list[index].name});
      }
    },null)
    axios.axiosGet("/yue-chip-upms-serve/upms/console/role/user", {params: {roleId:_roleId}},(data:any)=>{
      roleUser.value = data.data;
    },null)
  }

  function userCancel() {
    roleId = null;
    userVisible.value = false;
    userOptions.value = [];
    roleUser.value = [];
  }

  function userSave(_roleId:string) {
    axios.axiosPost("/yue-chip-upms-serve/upms/console/user/role", {"roleId":roleId,userIds:roleUser.value},(data:any)=>{
      if (data.status === 200) {
        message.info(data.message);
        userCancel();
      }
    },null)
  }


</script>

<style scoped>

</style>
