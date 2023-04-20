<template>
  <div>
    <a-card >
      <a-form ref="from" :model="searchModel" :labelCol="{span: 5,offset:0}" >
        <a-row >
          <a-col :span="6">
            <a-form-item label="作用域" name="scope" ref="scope" >
              <a-select  v-model:value="searchModel.scope" >
                <a-select-option value="APP">APP</a-select-option>
                <a-select-option value="CONSOLE">后台</a-select-option>
                <a-select-option value="FRONT">前端</a-select-option>
                <a-select-option value="WE_CHAT">微信</a-select-option>
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
                <a-button type="primary" @click="add(null,'0',searchModel.scope,'CATALOG')" >
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
      <a-table rowKey="id" :columns="columns" :data-source="dataList" :loading="loading" :pagination="false" :scroll="{y:500}">
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'operation'">
            <a-space :size="5">
              <a-button size="small" @click="details(record.id)">
                <template #icon><EditOutlined /></template>
                修改
              </a-button>
<!--              <a-button  size="small" @click="add(record.id,0)">-->
<!--                <template #icon><PlusOutlined /></template>-->
<!--                添加目录-->
<!--              </a-button>-->
              <a-button v-if="record.type.name === 'CATALOG'" size="small" @click="add(null,record.id,searchModel.scope,'MENU')">
                <template #icon><PlusOutlined /></template>
                添加菜单
              </a-button>
              <a-button v-if="record.type.name === 'MENU'" size="small" @click="add(null,record.id,searchModel.scope,'FUNCTION')">
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
                <a-select-option value="APP">APP</a-select-option>
                <a-select-option value="CONSOLE">后台</a-select-option>
                <a-select-option value="FRONT">前端</a-select-option>
                <a-select-option value="WE_CHAT">微信</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="类型" name="type" ref="type" >
              <a-select disabled  v-model:value="addOrUpdateModel.type" >
                <a-select-option value="CATALOG">目录</a-select-option>
                <a-select-option value="MENU">菜单</a-select-option>
                <a-select-option value="FUNCTION">功能</a-select-option>
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
              <a-input v-bind:disabled="addOrUpdateModel.type === 'FUNCTION' || addOrUpdateModel.type === 'CATALOG'" placeholder="请输入URL" v-model:value="addOrUpdateModel.url" />
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
  import {ref, onActivated,getCurrentInstance} from 'vue'
  import { SearchOutlined,PlusOutlined,DeleteOutlined } from '@ant-design/icons-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  import {
      Card,
      Modal,
      Select,
      Tree,
      Form,
      Col,
      FormItem,
      Input,
      Space,
      Button,
      SelectOption,
      message
  } from "ant-design-vue";
  const _this:any = getCurrentInstance();
  let visible = ref<boolean>(false);
  let permissionsVisible = ref<boolean>(false);
  const searchModel = ref({scope:"CONSOLE"})
  let addOrUpdateModel = ref({})
  const loading = ref(false);
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
      width: 250,
    },
  ]
  let dataList = ref([]);

  const rules:any={
    code:[{required:true,validator:checkCodeIsExist,trigger:'blur'}],
    name:[{required:true,validator:checkNameIsExist,trigger:'blur'}],
    url:[{validator:checkUrlIsExist,trigger:'blur'}],
  };

  async function checkCodeIsExist(rule :any, value:string){
    let promise:any = null;
    if (!value || value.trim() === ''){
      return Promise.reject('请输入编码');
    }else if (value && value.trim() !== ''){
      await axios.service.get("/yue-chip-upms-serve/upms/console/resources/check/code/exist",{params:{"code":addOrUpdateModel.value.code,"id":addOrUpdateModel.value.id}})
          .then((data)=>{
            if (data.status === 200 && data.data) {
              promise = Promise.reject("该编码已存在");
            }else {
              promise = Promise.resolve();
            }
          })
          .catch(obj => {
          })
          .finally(()=>{
          });
    }
    return promise;
  }

  /**
   * 检查名称是否存在
   * @param rule
   * @param value
   * @param callback
   */
  async function checkNameIsExist(rule :any, value:string){
    let promise:any = null;
    if (!value || value.trim() === ''){
      return Promise.reject("请输入名称")
    }else if (value && value.trim() !== ''){
      await axios.service.get("/yue-chip-upms-serve/upms/console/resources/check/name/exist",{params:{"name":addOrUpdateModel.value.name,"parentId":addOrUpdateModel.value.parentId,"id":addOrUpdateModel.value.id}})
          .then((data)=>{
            if (data.status === 200 && data.data) {
              promise = Promise.reject("名称在同节点已存在");
            }else {
              promise = Promise.resolve();
            }
          })
          .catch(obj => {
          })
          .finally(()=>{
          });
    }
    return promise;
  }

  /**
   * 检查url是否存在
   * @param rule
   * @param value
   * @param callback
   */
  async function checkUrlIsExist(rule :any, value:string){
    let promise:any = null;
    if (addOrUpdateModel.value.type ==='MENU' && (!value || value.trim() === '')){
      return Promise.reject("请输入url")
    }else if (addOrUpdateModel.value.type ==='MENU' && value && value.trim() !== ''){
      await axios.service.get("/yue-chip-upms-serve/upms/console/resources/check/url/exist",{params:{"url":addOrUpdateModel.value.url,"id":addOrUpdateModel.value.id}})
          .then((data)=>{
            if (data.status === 200 && data.data) {
              promise = Promise.reject("该url已存在");
            }else {
              promise = Promise.resolve();
            }
          })
          .catch(obj => {
          })
          .finally(()=>{
          });
    }
    return promise;
  }

  onActivated(() => {
    search();
  });

  function search(){
    loading.value = true;
    axios.axiosGet("/yue-chip-upms-serve/upms/console/resources/tree/list",{params:searchModel.value},(data:any)=>{
      dataList.value = data.data;
      loading.value = false;
    },null,null)
  }

  function add(id:string,parentId:string,scope:string,type:string){
    if (id) {
      addOrUpdateModel.value.id = id;
    }
    if (parentId) {
      addOrUpdateModel.value.parentId = parentId;
    }
    if (scope) {
      addOrUpdateModel.value.scope = scope;
    }
    if (type) {
      addOrUpdateModel.value.type = type;
    }
    visible.value = true;
  }

  function cancel(){
    visible.value = false;
    addOrUpdateModel.value = {};
  }

  function save(){
    _this.ctx.$refs.from.validate().then(() => {
      if (addOrUpdateModel.value.id) {
        axios.axiosPut("/yue-chip-upms-serve/upms/console/resources/update", addOrUpdateModel.value,
          (data: any) => {
            if (data.status === 200) {
              visible.value = false;
              addOrUpdateModel.value = {};
              message.info(data.message);
              search();
            }
          }, null,null)
      }else {
        axios.axiosPost("/yue-chip-upms-serve/upms/console/resources/add", addOrUpdateModel.value,
          (data: any) => {
            if (data.status === 200) {
              visible.value = false;
              addOrUpdateModel.value = {};
              message.info(data.message);
              search();
            }
        }, null,null)
      }
    }).catch((err: any) => {
    });
  }

  function details(id:string) {
    axios.axiosGet("/yue-chip-upms-serve/upms/console/resources/details", {params:{id:id}},
      (data:any)=>{
        if (data.status === 200 ) {
          addOrUpdateModel.value = data.data;
          addOrUpdateModel.value.scope = data.data.scope.name;
          addOrUpdateModel.value.type = data.data.type.name;
          addOrUpdateModel.value.state = data.data.state.name;
          visible.value = true;
        }
      },null,null)
  }

  function del(id:string){
      Modal.confirm({
          title: '是否要删除该数据?(错误的操作会带来灾难性的后果)',
          // content: '',
          okText: 'Yes',
          okType: 'danger',
          cancelText: 'No',
          onOk() {
              axios.axiosDelete("/yue-chip-upms-serve/upms/console/resources/delete",{params:{id:id}},(data:any)=>{
                  if (data.status === 200 ) {
                      message.info(data.message);
                      search();
                  }
              },null,null);
          },
          onCancel() {
          },
      });
  }
</script>

<style scoped>

</style>
