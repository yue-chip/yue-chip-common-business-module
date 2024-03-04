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
        <a-input placeholder="请输入电话号码" v-model:value="addOrUpdateModel.phoneNumber" />
      </a-form-item>
      <a-form-item label="邮箱" name="email" ref="email">
        <a-input placeholder="请输入电子邮箱" v-model:value="addOrUpdateModel.email" />
      </a-form-item>
      <a-form-item label="证件类型" name="IdCardType" ref="IdCardType">
        <a-select v-model:value="addOrUpdateModel.IdCardType">
          <a-select-option value="0">身份证</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="证书编号" name="certificateNumber" ref="certificateNumber">
        <a-input placeholder="请输入证书编号" v-model:value="addOrUpdateModel.certificateNumber" />
      </a-form-item>
      <a-form-item label="身份证号码" name="identificationNumber" ref="identificationNumber">
        <a-input placeholder="请输入身份证号码" v-model:value="addOrUpdateModel.identificationNumber" />
      </a-form-item>
      <a-form-item label="头像" >
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
            <loading-outlined v-if="loading"></loading-outlined>
            <plus-outlined v-else></plus-outlined>
            <div class="ant-upload-text">Upload</div>
          </div>
        </a-upload>
      </a-form-item>
      <a-form-item label="其它照片" >
        <a-upload
          v-model:file-list="otherPhotoUrlList"
          name="avatar"
          list-type="picture-card"
          class="avatar-uploader"
          :show-upload-list="false"
          action="/api/common/file/upload"
          :before-upload="beforeUpload"
          :headers="headers"
          @change="handleChange1"
        >
          <img width="100" height="100" v-if="otherPhotoUrl" :src="otherPhotoUrl" alt="avatar" />
          <div v-else>
            <loading-outlined v-if="otherPhotoLoading"></loading-outlined>
            <plus-outlined v-else></plus-outlined>
            <div class="ant-upload-text">Upload</div>
          </div>
        </a-upload>
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
  import {ref, onMounted,computed} from 'vue'
  import {useRouter,useRoute} from 'vue-router'
  import { RollbackOutlined,SaveOutlined,UndoOutlined } from '@ant-design/icons-vue';
  import type { FormInstance,TreeSelectProps,UploadChangeParam, UploadProps } from 'ant-design-vue';
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
  const fileList = ref([]);
  const loading = ref<boolean>(false);
  let imageUrl = ref<string>('');
  let profilePhoto = ref<string>('');
  const otherPhotoUrlList =ref([])
  const otherPhotoLoading = ref<boolean>(false);
  let otherPhotoUrl = ref<string>('');
  let otherPhoto = ref<string>('');
  import {Md5} from 'ts-md5';
  const rules:any={
    username:[{required:true,message:"请输入账号",trigger:'blur'}],
    pass:[{required:true,message:"请输入密码",trigger:'blur'}],
    name:[{required:true,message:"请输入姓名",trigger:'blur'}],
  };
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

  const handleChange = (info: UploadChangeParam) => {
    if (info.file.status === 'uploading') {
      loading.value = true;
      return;
    }
    if (info.file.status === 'done') {
      if (info.file.response.status === 200) {
        const data = info.file.response.data;
        addOrUpdateModel.value.profilePhotoId = data[0].id;
        // profilePhoto.value = "/api"+data[0].url;
        getBase64(info.file.originFileObj, (base64Url: string) => {
          imageUrl.value = base64Url;
          loading.value = false;
        });
      }


    }
    if (info.file.status === 'error') {
      loading.value = false;
      message.error('upload error');
    }
  };

  const handleChange1 = (info: UploadChangeParam) => {
    if (info.file.status === 'uploading') {
      otherPhotoLoading.value = true;
      return;
    }
    if (info.file.status === 'done') {
      if (info.file.response.status === 200) {
        const data = info.file.response.data;
        addOrUpdateModel.value.otherPhotoId = data[0].id;
        // profilePhoto.value = "/api"+data[0].url;
        getBase64(info.file.originFileObj, (base64Url: string) => {
          otherPhotoUrl.value = base64Url;
          otherPhotoLoading.value = false;
        });
      }


    }
    if (info.file.status === 'error') {
      loading.value = false;
      message.error('upload error');
    }
  };

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

  function getBase64(img: Blob, callback: (base64Url: string) => void) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result as string));
    reader.readAsDataURL(img);
  }
  // const ispaly = () => {
  //   ismute.value = !ismute.value
  //   emit('ispaly', ismute.value)
  //
  // }
</script>

<style scoped>

</style>
