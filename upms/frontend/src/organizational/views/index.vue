<template>
  <div>
    <a-card >
      <a-form ref="from" :model="searchModel" :label-col="{span: 4,offset:0}" >
        <a-row style="height: 20px;">
          <a-col :span="24" style="text-align:right;">
            <a-form-item>
              <a-space :size="5">
                <a-button type="primary" @click="add()" >
                  <template #icon><PlusOutlined /></template>
                  添加
                </a-button>
                <a-button type="primary" danger @click="del(selectedRowKeys)">
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
      <a-table rowKey="id"  :columns="columns" :data-source="dataList"  :loading="loading" :scroll="{ y: 440 }" >
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'state'">
            <a-switch  @change="stateChange(record.id,record.stateTmp,record.phoneNumber,record.name,record.parentId)" v-model:checked="record.stateTmp" checked-children="正常" un-checked-children="禁用" />
          </template>
          <template v-if="column.key === 'operation'">
            <a-space :size="5">
              <a-button size="small" @click="edit(record.id)">
                <template #icon><EditOutlined /></template>
                修改
              </a-button>
              <a-button @click="showLeaderForm(record)" size="small" >
                <template #icon><EditOutlined /></template>
                设置负责人
              </a-button>
              <a-button v-if="record.username !== 'admin'" size="small" type="primary" danger @click="del(record.id)">
                <template #icon><DeleteOutlined /></template>
                删除
              </a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal width="500px" v-model:visible="visible" title="部门负责人" cancelText="取消" okText="保存" :destroyOnClose="true" :mask="true" :maskClosable="false" @cancel="cancel" @ok="update">
      <a-form ref="leaderForm"  :model="addOrUpdateModel" :labelCol="{span: 0,offset:0}" >
        <a-row >
          <a-col :span="24">
            <a-form-item label="负责人" name="scope" ref="scope" >
              <a-select :options="options" allowClear v-model:value="addOrUpdateModel.leaderId" >
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
  import {ref, onActivated,getCurrentInstance} from 'vue'
  import {useRouter} from 'vue-router'
  import { SearchOutlined,PlusOutlined,DeleteOutlined,EditOutlined } from '@ant-design/icons-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  import {TableProps,Modal,message} from "ant-design-vue";
  import qs from "qs";
  const _this:any = getCurrentInstance();
  const router=useRouter();
  let loading = ref(false);
  let searchModel = ref({pageSize:10,pageNumber:1});
  let selectedRowKeys:string[] = [];
  let visible = ref<boolean>(false);
  let addOrUpdateModel = ref({})
  let options:any[] = ref<any>([]);
  const columns = [
    {
      title: '机构名称',
      dataIndex: 'name',
      fixed: 'left',
      key: 'name',
    },
    {
      title: '排序',
      dataIndex: 'sort',
      key: 'sort',
    },
    {
      title: '状态',
      // dataIndex: ['state','desc'],
      key: 'state',
    },
    {
      title: '负责人',
      dataIndex: 'leaderName',
      key: 'leaderName',
    },
    {
      title: '紧急联系电话',
      dataIndex: 'phoneNumber',
      key: 'phoneNumber',
    },
    {
      title: '创建时间',
      dataIndex: 'createDateTime',
      key: 'createDateTime',
    },
    {
      title: '操作',
      key: "operation",
      fixed: 'right',
      width: '280px',
    },
  ]
  let dataList = ref([]);


  onActivated(() => {
    search();
  });

  function search(){
    loading.value=true;
    axios.axiosGet("/upms/console/organizational/tree/list",{params:searchModel.value},(data:any)=>{
      dataList.value = data.data;
      loading.value=false;
    },null,null)
  }

  function add(){
    router.push('/addOrUpdate');
  }

  function edit(id:string) {
    router.push({ path: '/addOrUpdate', query: { id: id }});
  }

  function del(id:string[]){
    if (!id || id.length === 0) {
      message.error("请选择要删除的数据！")
      return;
    }
    Modal.confirm({
      title: '是否要删除该数据?',
      // content: '',
      okText: 'Yes',
      okType: 'danger',
      cancelText: 'No',
      onOk() {
        const params = {params:{ids:id},
          paramsSerializer: (params: any) => {
            return qs.stringify(params, { indices: false })
          }};
        axios.axiosDelete("/upms/console/organizational/delete",params,(data:any)=>{
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

  function stateChange(id: string,stateTmp: any,phoneNumber:string,name:string,parentId:number){
    let state = stateTmp?1:0;
    axios.axiosPut("/upms/console/organizational/update",{"id":id,"state":state,phoneNumber:phoneNumber,name:name,parentId:parentId},
        (data:any)=>{
          if (data.status === 200 ) {
            message.info(data.message);
            search();
          }
        },null,null)
  }

  function showLeaderForm(data:any) {
    if (!data.stateTmp) {
      message.warn("该机构被禁用，禁止操作")
      return;
    }
    addOrUpdateModel.value = data;
    visible.value=true;
    axios.axiosGet("/upms/console/organizational/user/select/list",{params: {organizationalId:data.id}},(data:any)=>{
      options.value = data.data;
      loading.value=false;
    },null,null)
  }

  function cancel(){
    visible.value = false;
    addOrUpdateModel.value=[];
  }

  function update(){
    axios.axiosPut("/upms/console/organizational/update", addOrUpdateModel.value,(data:any)=>{
      if (data.status === 200) {
        message.info(data.message);
        cancel();
        search();
      }
    },null,null)
  }

</script>

<style scoped>

</style>
