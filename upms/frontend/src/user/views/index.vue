<template>
  <div>
    <a-card >
      <a-form ref="from" :model="searchModel" :label-col="{span: 4,offset:0}" >
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
                <a-button type="primary" @click="searchModel.pageNumber=1;search()">
                  <template #icon><SearchOutlined /></template>
                  查询
                </a-button>
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
      <a-table rowKey="id" :row-selection="rowSelection" :columns="columns" :data-source="dataList" :pagination="pagination" :loading="loading" :scroll="{ y: 440 }" >
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'isSms'">
            <a-switch @change="smsChange(record)" v-model:checked="record.isSms" checked-children="开" un-checked-children="关" />
          </template>
          <template v-if="column.key === 'isCall'">
            <a-switch @change="callChange(record)" v-model:checked="record.isCall" checked-children="开" un-checked-children="关" />
          </template>
          <template v-if="column.key === 'operation'">
            <a-space :size="5">
              <a-button size="small" @click="edit(record.id)">
                <template #icon><EditOutlined /></template>
                修改
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

  const columns = [
    {
      title: '姓名',
      dataIndex: 'name',
      fixed: 'left',
      key: 'name',
    },
    {
      title: '帐号',
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: '电话',
      dataIndex: 'phoneNumber',
      key: 'phoneNumber',
    },
    {
      title: '所属机构',
      dataIndex: 'organizationalName',
      key: 'organizationalName',
    },
    {
      title: '状态',
      dataIndex: ['state','desc'],
      key: ['state','name'],
    },
    {
      title: '接收短信通知',
      key: "isSms",
    },
    {
      title: '接收紧急呼叫',
      key: 'isCall',
    },
    {
      title: '创建时间',
      dataIndex: 'createDateTime',
      key: 'createDateTime',
      width: '170px',
    },
    {
      title: '最后登录时间',
      dataIndex: 'lastLoginTime',
      key: 'lastLoginTime',
      width: '170px',
    },
    {
      title: '操作',
      key: "operation",
      fixed: 'right',
      width: '155px',
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

  const rowSelection: TableProps['rowSelection'] = {
    onChange: (_selectedRowKeys: string[], _selectedRows: any[]) => {
      selectedRowKeys = _selectedRowKeys;
    },
    getCheckboxProps: (record: any) => ({
      disabled: record.username === 'admin'
    }),
  };

  function search(){
    loading.value=true;
    axios.axiosGet("/upms/console/user/list",{params:searchModel.value},(data:any)=>{
      dataList.value = data.data;
      pagination.value.total = data.totalElements;
      pagination.value.current = data.pageNumber;
      pagination.value.pageSize = data.pageSize;
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
        axios.axiosDelete("/upms/console/user/delete",params,(data:any)=>{
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

  function smsChange(data:any){
    axios.axiosPut("/upms/console/user/update",data,
      (data:any)=>{
        if (data.status === 200 ) {
          message.info(data.message);
          search();
        }
      },null,null)
  }

  function callChange(data: any){
    axios.axiosPut("/upms/console/user/update",data,
        (data:any)=>{
          if (data.status === 200 ) {
            message.info(data.message);
            search();
          }
        },null,null)
  }

</script>

<style scoped>

</style>
