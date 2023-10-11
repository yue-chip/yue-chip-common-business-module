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
<!--            <a-switch @change="updateState(record.id,record.key)" v-model:checked="record.key===1" checked-children="正常" un-checked-children="禁用" />-->
          </template>
          <template v-if="column.key === 'operation'">
            <a-space :size="5">
              <a-button size="small" @click="edit(record.id)">
                <template #icon><EditOutlined /></template>
                修改
              </a-button>
              <a-button size="small" >
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
      title: '机构名称',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '排序',
      dataIndex: 'sort',
      key: 'sort',
    },
    {
      title: '状态',
      dataIndex: ['state','desc'],
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
      width: '155px',
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

  function updateState(id: string,state: any){
    axios.axiosPut("/upms/console/organizational/update",{"id":id,"state":state},
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
