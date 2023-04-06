<template>
  <div>
    <a-card >
      <a-form ref="from" :model="searchModel" :labelCol="{span: 3,offset:0}" >
        <a-row >
          <a-col :span="6">
            <a-form-item label="名称" name="name" ref="name" >
              <a-input placeholder="请输入名称" v-model:value="searchModel.name" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="编码" name="code" ref="code" >
              <a-input placeholder="请输入编码" v-model:value="searchModel.code" />
            </a-form-item>
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
                <a-button type="primary" @click="searchModel.pageNumber=1;search()">
                  <template #icon><SearchOutlined /></template>
                  查询
                </a-button>
                <a-button type="primary" @click="add()" >
                  <template #icon><PlusOutlined /></template>
                  添加
                </a-button>
<!--                <a-button type="danger" @click="">-->
<!--                  <template #icon><DeleteOutlined /></template>-->
<!--                  删除-->
<!--                </a-button>-->
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
              <a-button size="small" @click="details(record.id)">
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

    <a-modal width="800px" v-model:visible="visible" title="添加/修改角色" cancelText="取消" okText="保存" :destroyOnClose="true" :mask="true" :maskClosable="false" @cancel="cancel" @ok="save">
      <a-form ref="from" :rules="rules" :model="addOrUpdateModel" :labelCol="{span: 3,offset:0}" >
        <a-row >
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
          <a-col :span="24">
            <a-form-item :labelCol="{span: 1.9,offset:0}" label="备注" name="remark" ref="remark">
              <a-textarea placeholder="请输入备注" :auto-size="{ minRows: 5, maxRows: 5 }" v-model:value="addOrUpdateModel.remark" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>

    <a-modal width="400px" height="600px"  v-model:visible="permissionsVisible" title="权限" cancelText="取消" okText="保存" :destroyOnClose=true :mask=true :maskClosable=false @cancel="permissionsCancel" @ok="permissionsSave">
      <a-tree
          v-model:checkedKeys="permissionsCheckedKeys"
          default-expand-all
          checkable
          :height="600"
          :tree-data="treeData"
      >
      </a-tree>
    </a-modal>

    <a-modal width="400px" height="600px"  v-model:visible="userVisible" title="人员" cancelText="取消" okText="保存" :destroyOnClose=true :mask=true :maskClosable=false @cancel="userCancel" @ok="userSave">
      <a-select
          v-model:value="roleUser"
          mode="tags"
          style="width: 100%"
          placeholder="请选择人员"
          :options="userOptions"
      ></a-select>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
  import {ref, onActivated,getCurrentInstance} from 'vue'
  import { SearchOutlined,PlusOutlined,UserAddOutlined,FilterOutlined,DeleteOutlined } from '@ant-design/icons-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  import {message,Card,Modal,Select,Tree,Form,Col,FormItem,Input,Space,Button,SelectOption} from "ant-design-vue";
  import qs from "qs";
  import "ant-design-vue/es/message/style/index.css"
  const _this:any = getCurrentInstance();
  let loading = ref(false);
  let searchModel = ref({pageSize:10,pageNumber:1});
  let permissionsVisible = ref<boolean>(false);
  let visible = ref<boolean>(false);
  let addOrUpdateModel = ref({})
  let treeData = ref([]);
  let permissionsCheckedKeys = ref([]);
  let roleId: any;
  let userVisible =ref<boolean>(false);
  let roleUser = ref<string[]>([]);
  let userOptions = ref<any>([]);
  const rules:any={
    code:[{required:true,message:"请输入编码",trigger:'blur'}],
    name:[{required:true,message:"请输入名称",trigger:'blur'}]
  };
  const columns = [
    {
      title: '名称',
      dataIndex: 'name',
      fixed: 'left',
      key: 'name',
    },
    {
      title: '编码',
      dataIndex: 'code',
      key: 'code',
    },
    {
      title: '备注',
      dataIndex: 'remark',
      key: 'remark',
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
    axios.axiosGet("/yue-chip-upms-serve/upms/console/role/list",{params:searchModel.value},(data:any)=>{
      dataList.value = data.data;
      pagination.value.total = data.totalElements;
      pagination.value.current = data.pageNumber;
      pagination.value.pageSize = data.pageSize;
      loading.value=false;
    },null)
  }

  function add(){
    visible.value = true;
  }

  function cancel(){
    visible.value = false;
    addOrUpdateModel.value = {};
  }

  function details(id:string) {
    axios.axiosGet("/yue-chip-upms-serve/upms/console/role/details", {params:{id:id}},
      (data:any)=>{
        if (data.status === 200 ) {
          addOrUpdateModel.value = data.data;
          addOrUpdateModel.value.state = data.data.state.name;
          visible.value = true;
        }
      },null)
  }

  function save(){
    _this.ctx.$refs.from.validate().then(() => {
      if (addOrUpdateModel.value.id) {
        axios.axiosPut("/yue-chip-upms-serve/upms/console/role/update",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              visible.value = false;
              addOrUpdateModel.value = {};
              search();
            }
          },null)
      }else {
        axios.axiosPost("/yue-chip-upms-serve/upms/console/role/add",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              visible.value = false;
              addOrUpdateModel.value = {};
              search();
            }
          },null)
      }
    }).catch((err: any) => {
    });


  }

  function del(id:string){
      Modal.confirm({
          title: '是否要删除该数据?(错误的操作会带来灾难性的后果)',
          // content: '',
          okText: 'Yes',
          okType: 'danger',
          cancelText: 'No',
          onOk() {
              axios.axiosDelete("/yue-chip-upms-serve/upms/console/role/delete",{params:{id:id}},(data:any)=>{
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
    roleId = _roleId;
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
