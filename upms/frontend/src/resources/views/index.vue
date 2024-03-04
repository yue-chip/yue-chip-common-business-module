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
              <a-button v-if="record.isDefault === false" size="small" type="dashed" @click="del(record.id)">
                <template #icon><DeleteOutlined /></template>
                删除
              </a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal width="800px" v-model:visible="visible" title="添加/修改资源" cancelText="取消" okText="保存" :destroyOnClose="true" :mask="true" :maskClosable="false" @cancel="cancel" @ok="save">
      <a-form ref="fromAddOrUpdate" :rules="rules" :model="addOrUpdateModel" :labelCol="{span: 5,offset:0}" >
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
              <a-select v-model:value="addOrUpdateModel.type" >
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
        <a-row>
          <a-col :span="12">
            <a-form-item label="icon">
              <a-upload
                v-model:file-list="fileList"
                name="avatar"
                list-type="picture-card"
                class="avatar-uploader"
                :show-upload-list="false"
                action="/api/common/file/upload"
                :before-upload="beforeUpload"
                :headers="headers"
                @change="handleChange"
              >
                <img width="100" height="100" v-if="imageUrl" :src="imageUrl" alt="avatar" />
                <div v-else>
                  <loading-outlined v-if="uploadLoading"></loading-outlined>
                  <plus-outlined v-else></plus-outlined>
                  <div class="ant-upload-text">Upload</div>
                </div>
              </a-upload>
            </a-form-item>
          </a-col>
          <a-col :span="12">
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import {ref, onActivated, getCurrentInstance, computed} from 'vue'
  import { SearchOutlined,PlusOutlined,DeleteOutlined,EditOutlined } from '@ant-design/icons-vue';
import {Modal, message, FormInstance, UploadProps, UploadChangeParam} from "ant-design-vue";
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  const _this:any = getCurrentInstance();
  const fromAddOrUpdate = ref<FormInstance>();
  let visible = ref<boolean>(false);
  let permissionsVisible = ref<boolean>(false);
  const searchModel = ref({scope:"CONSOLE"})
  let addOrUpdateModel = ref({})
  const loading = ref(false);
  const uploadLoading = ref(false);
  const fileList = ref([]);
  let imageUrl = ref<string>('');
  let headers = computed(()=>{
    const token = sessionStorage.getItem("token");
    const access_token = sessionStorage.getItem("access_token");
    if (token && token !== ''){
      return {"Token":token};
    }
    if (access_token && access_token !== ''){
      return {"Authorization":`Bearer ${access_token}`};
    }
  })
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
      width: 255,
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
      await axios.service.get("/upms/console/resources/check/code/exist",{params:{"code":addOrUpdateModel.value.code,"id":addOrUpdateModel.value.id}})
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
      await axios.service.get("/upms/console/resources/check/name/exist",{params:{"name":addOrUpdateModel.value.name,"parentId":addOrUpdateModel.value.parentId,"id":addOrUpdateModel.value.id}})
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
      await axios.service.get("/upms/console/resources/check/url/exist",{params:{"url":addOrUpdateModel.value.url,"id":addOrUpdateModel.value.id}})
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
    axios.axiosGet("/upms/console/resources/tree/list",{params:searchModel.value},(data:any)=>{
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
    imageUrl.value = '';
    fileList.value = [];
  }

  function cancel(){
    visible.value = false;
    imageUrl.value = '';
    fileList.value = [];
    addOrUpdateModel.value = {};
  }

  function save(){
    fromAddOrUpdate.value.validateFields().then(() => {
      if (addOrUpdateModel.value.id) {
        axios.axiosPut("/upms/console/resources/update", addOrUpdateModel.value,
          (data: any) => {
            if (data.status === 200) {
              visible.value = false;
              addOrUpdateModel.value = {};
              message.info(data.message);
              search();
            }
          }, null,null)
      }else {
        axios.axiosPost("/upms/console/resources/add", addOrUpdateModel.value,
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
    axios.axiosGet("/upms/console/resources/details", {params:{id:id}},
      (data:any)=>{
        if (data.status === 200 ) {
          addOrUpdateModel.value = data.data;
          addOrUpdateModel.value.scope = data.data.scope.name;
          addOrUpdateModel.value.type = data.data.type.name;
          addOrUpdateModel.value.state = data.data.state.name;
          if (data.data.iconUrl) {
            imageUrl.value = "/api/file" + data.data.iconUrl;
          }
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
              axios.axiosDelete("/upms/console/resources/delete",{params:{id:id}},(data:any)=>{
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

  const beforeUpload = (file: UploadProps['fileList'][number]) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg';
    if (!isJpgOrPng) {
      message.error('请上传正确的头像文件(jpeg/png/jpg)!');
    }
    const isLt2M = file.size / 1024 / 1024 < 10;
    if (!isLt2M) {
      message.error('请上传小与10MB的图像!');
    }
    return isJpgOrPng && isLt2M;
  };

  const handleChange = (info: UploadChangeParam) => {
    if (info.file.status === 'uploading') {
      uploadLoading.value = true;
      return;
    }
    if (info.file.status === 'done') {
      if (info.file.response.status === 200) {
        const data = info.file.response.data;
        addOrUpdateModel.value.iconId = data[0].id;
        // profilePhoto.value = "/api"+data[0].url;
          getBase64(info.file.originFileObj, (base64Url: string) => {
          imageUrl.value = base64Url;
          uploadLoading.value = false;
        });
      }
    }
    if (info.file.status === 'error') {
      uploadLoading.value = false;
      message.error('upload error');
    }
  };
  function getBase64(img: Blob, callback: (base64Url: string) => void) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result as string));
    reader.readAsDataURL(img);
  }
</script>

<style scoped>

</style>
