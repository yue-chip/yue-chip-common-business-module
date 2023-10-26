<template>
  <div>
    <a-card >
      <a-form ref="from" :model="searchModel" :labelCol="{span: 4,offset:0}" >
        <a-row :gutter="24">
          <a-col :span="6">
            <a-form-item label="名称" name="name" ref="name" >
              <a-input placeholder="请输入名称" v-model:value="searchModel.name" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="负责人" name="manager" ref="manager" >
              <a-input placeholder="请输入负责人" v-model:value="searchModel.manager" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="状态" name="state" ref="state" >
              <a-input placeholder="请选择状态" v-model:value="searchModel.state" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="联系电话" name="phoneNumber" ref="phoneNumber" >
              <a-input placeholder="请输入联系电话" v-model:value="searchModel.phoneNumber" />
            </a-form-item>
          </a-col>

        </a-row>
        <a-row :gutter="24" style="height: 20px;">
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
          <template v-if="column.key === 'state'">
            <a-switch  @change="stateChange(record.id,record.stateTmp)" v-model:checked="record.stateTmp" checked-children="正常" un-checked-children="禁用" />
          </template>
          <template v-if="column.key === 'operation'">
            <a-space :size="5">
              <a-button size="small" @click="details(record.id)">
                <template #icon><EditOutlined /></template>
                修改
              </a-button>
              <a-button size="small" type="primary" danger @click="del(record.id)">
                <template #icon><DeleteOutlined /></template>
                删除
              </a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal width="800px" v-model:visible="visible" title="添加/修改租户" cancelText="取消" okText="保存" :destroyOnClose="true" :mask="true" :maskClosable="false" @cancel="cancel" @ok="save">
      <a-form ref="fromAddOrUpdate" :rules="rules" :model="addOrUpdateModel" :labelCol="{span: 3,offset:0}" >
        <a-row >
          <a-col :span="24">
            <a-form-item label="名称" name="name" ref="name">
              <a-input placeholder="请输入租户名称" v-model:value="addOrUpdateModel.name" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="负责人" name="manager" ref="manager">
              <a-input placeholder="请输入负责人" v-model:value="addOrUpdateModel.manager" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="联系电话" name="phoneNumber" ref="phoneNumber">
              <a-input placeholder="请输入负责人" v-model:value="addOrUpdateModel.phoneNumber" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
  import {ref, onActivated} from 'vue'
  import {FormInstance, message, Modal, TableProps} from "ant-design-vue";
  import { SearchOutlined,PlusOutlined,UserAddOutlined,FilterOutlined,DeleteOutlined ,EditOutlined} from '@ant-design/icons-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
  import qs from "qs";
  const fromAddOrUpdate = ref<FormInstance>();
  let loading = ref(false);
  let searchModel = ref({pageSize:10,pageNumber:1});
  let visible = ref<boolean>(false);
  let addOrUpdateModel = ref({})
  let selectedRowKeys:string[] = [];
  const rules:any={
    name:[{required:true,message:"请输入租户名称",trigger:'blur'}],
    manager:[{required:true,message:"请输入名称",trigger:'blur'}],
    phoneNumber:[{required:true,message:"请输入联系电话",trigger:'blur'}],
  };
  const columns = [
    {
      title: '租户编码',
      dataIndex: 'id',
      fixed: 'left',
      key: 'id',
    },
    {
      title: '租户名称',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '状态',
      key: 'state',
    },
    {
      title: '负责人',
      dataIndex: 'manager',
      key: 'manager',
    },
    {
      title: '联系电话',
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
      width: '160px',
    },
  ]
  let dataList = ref([]);

  const pagination = ref({
      current: searchModel.value.pageNumber?searchModel.value.pageNumber:1,
      pageSize: searchModel.value.pageSize?searchModel.value.pageSize:30,
      onChange:(pageNumber: number, pageSize: number)=>{ searchModel.value.pageSize=pageSize ;searchModel.value.pageNumber=pageNumber ;search()},
  });

  const rowSelection: TableProps['rowSelection'] = {
    onChange: (_selectedRowKeys: string[], _selectedRows: any[]) => {
      selectedRowKeys = _selectedRowKeys;
    }
  };

  onActivated(() => {
    search();
  });


  function search(){
    loading.value=true;
    axios.axiosGet("/upms/console/tenant/list",{params:searchModel.value},(data:any)=>{
      dataList.value = data.data;
      pagination.value.total = data.totalElements;
      pagination.value.current = data.pageNumber;
      pagination.value.pageSize = data.pageSize;
      loading.value=false;
    },null,null)
  }

  function add(){
    visible.value = true;
  }

  function cancel(){
    visible.value = false;
    addOrUpdateModel.value = {};
  }

  function details(id:string) {
    axios.axiosGet("/upms/console/tenant/details", {params:{id:id}},
      (data:any)=>{
        if (data.status === 200 ) {
          addOrUpdateModel.value = data.data;
          visible.value = true;
        }
      },null,null)
  }

  function save(){
    fromAddOrUpdate.value.validateFields().then(() => {
      if (addOrUpdateModel.value.id) {
        axios.axiosPut("/upms/console/tenant/update",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              visible.value = false;
              addOrUpdateModel.value = {};
              message.info(data.message);
              search();
            }
          },null,null)
      }else {
        axios.axiosPost("/upms/console/tenant/add",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              visible.value = false;
              addOrUpdateModel.value = {};
              message.info(data.message);
              search();
            }
          },null,null)
      }
    }).catch((err: any) => {
    });


  }

  function del(id:string){
    if (!id || id.length === 0) {
      message.error("请选择要删除的数据！")
      return;
    }
    Modal.confirm({
      title: '是否要删除该数据?(错误的操作会带来灾难性的后果)',
      // content: '',
      okText: 'Yes',
      okType: 'danger',
      cancelText: 'No',
      onOk() {
        const params = {params:{ids:id},
          paramsSerializer: (params: any) => {
            return qs.stringify(params, { indices: false })
          }};
        axios.axiosDelete("/upms/console/tenant/delete",params,(data:any)=>{
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

  function stateChange(id: string,stateTmp: any){
    let state = stateTmp?1:0;
    axios.service.put('/upms/console/tenant/update/state', {"id":id,"state":state}, { headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8' }})
      .then((data:any) => {
        if (data.status === 200 ) {
          message.info(data.message);
        }
        search();
      })
      .catch(() => {});
  }

</script>

<style scoped>

</style>
