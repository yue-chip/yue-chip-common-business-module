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
                <a-button type="primary" @click="search()">
                  <template #icon><SearchOutlined /></template>
                  查询
                </a-button>
                <a-button type="primary" @click="add()" >
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
      <a-table rowKey="id" :columns="columns" :data-source="dataList" :pagination="false" :row-selection="{ selectedRowKeys: selectedRowKeys }">
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'operation'">
            <a-space :size="5">
              <a-button size="small" @click="permissions()">
                <template #icon><FilterOutlined /></template>
                权限
              </a-button>

              <a-button size="small" @click="user()">
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

    <a-modal width="400px" height="600px"  v-model:visible="permissionsVisible" title="权限" cancelText="取消" okText="保存" :destroyOnClose="true" :mask="true" :maskClosable="false" @cancel="permissionsCancel" @ok="permissionSave">
      <a-tree
          v-model:selectedKeys="selectedKeys"
          v-model:checkedKeys="checkedKeys"
          default-expand-all
          checkable
          :height="600"
          :tree-data="treeData"
      >
      </a-tree>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
  import {ref, reactive, onMounted,onActivated,getCurrentInstance} from 'vue'
  import { SearchOutlined,PlusOutlined,UserAddOutlined,FilterOutlined,DeleteOutlined } from '@ant-design/icons-vue';
  import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";

  const _this:any = getCurrentInstance();
  let searchModel = ref({});
  let permissionsVisible = ref<boolean>(false);
  let visible = ref<boolean>(false);
  let addOrUpdateModel = ref({})
  let selectedRowKeys = reactive([])
  let treeData = ref([]);
  const rules:any={
    code:[{required:true,message:"请输入编码",trigger:'blur'}],
    name:[{required:true,message:"请输入名称",trigger:'blur'}]
  };
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
      title: '备注',
      dataIndex: 'remark',
      key: 'remark',
    },
    {
      title: '操作',
      key: "operation",
      width: 250,
    },
  ]
  let dataList = ref([]);

  onActivated(() => {
    search();
  });

  function search(){
    axios.axiosGet("/yue-chip-upms-serve/role/console/list",searchModel,(data:any)=>{
      dataList.value = data.data;
    },null)
  }

  function add(){
    visible.value = true;
  }

  function cancel(){
    visible.value = false;
    addOrUpdateModel.value = {};
  }

  function save(){
    _this.ctx.$refs.from.validate().then(() => {
      axios.axiosPost("/yue-chip-upms-serve/role/console/add",addOrUpdateModel.value,
          (data:any)=>{
            if (data.status === 200 ) {
              visible.value = false;
              addOrUpdateModel.value = {};
              search();
            }
          },null)
    }).catch((err: any) => {
    });
  }


  function permissions(){
    permissionsVisible.value = true;
    axios.axiosGet("/yue-chip-upms-serve/resources/console/tree",searchModel,(data:any)=>{
      treeData.value = data.data;
    },null)
  }

  function permissionsCancel(){

  }

  function permissionsSave(){

  }
</script>

<style scoped>

</style>