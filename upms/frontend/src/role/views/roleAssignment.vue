<template>
    <div>
        <a-page-header :style="{ background: '#fff' }" :title="`分配账号｜${$route.query.name}`" @back="back">
            <!-- <template #extra>
                <a-button type="primary" @click="save">保存</a-button>
            </template> -->
            <a-card style="margin-bottom: 0px;border: 0;border-radius: 0px 8px;">
                <a-row :gutter="[20, 20]">
                    <a-col :xs="12" :lg="4" :md="6" :sm="6">
                        <a-config-provider :theme="{
                            /*  token: {
                                 colorBgContainer: '#142030',
                                 colorText: '#08F7FC',
 
                             },
                             algorithm: theme.darkAlgorithm, */
                        }">
                            <a-input v-model:value="searchinfo.name" placeholder="姓名" allow-clear></a-input>
                        </a-config-provider>
                    </a-col>
                    <a-col :xs="12" :lg="4" :md="6" :sm="6">
                        <a-config-provider :theme="{
                            /*  token: {
                                 colorPrimary: '#08F7FC',
                                 colorText: '#08F7FC',
                                 colorBgContainer: '#142030',
                             },
                             algorithm: theme.darkAlgorithm, */
                        }">
                            <a-input v-model:value="searchinfo.phone" placeholder="号码" allow-clear></a-input>
                        </a-config-provider>
                    </a-col>

                    <a-col :flex="'375px'">
                        <a-space>
                            <a-button type="primary" @click="searchdata">搜索</a-button>
                            <a-button @click="reset">重置</a-button>
                            <a-button type="primary" @click="add">添加授权账号</a-button>
                            <a-button type="primary" danger
                                @click="del(selectedRowKeys, $route.query.roleId)">批量取消授权</a-button>
                        </a-space>
                    </a-col>
                </a-row>
            </a-card>
            <a-card style="margin-bottom: 20px;border: 0;">
                <a-config-provider :theme="{

                }" :locale="zhCN">
                    <a-table rowKey="id" :row-selection="rowSelection" :columns="columns" :data-source="dataList"
                        :pagination="pagination" :loading="loading">
                        <template #bodyCell="{ column, text, record }">
                            <template v-if="column.key === 'operation'">

                                <a-button type="primary" danger @click="del(record.id, $route.query.roleId)">

                                    取消授权
                                </a-button>

                            </template>
                        </template>
                    </a-table>
                </a-config-provider>
            </a-card>
            <a-modal v-model:open="open" title="授权账号" width="1000px" cancelText="取消" okText="保存"
                @ok="save(selectedRowKeys1)">
                <a-row :gutter="[20, 20]" style="margin-bottom: 20px;">
                    <a-col :xs="12" :lg="4" :md="6" :sm="6">
                        <a-config-provider :theme="{
                            /*  token: {
                                 colorBgContainer: '#142030',
                                 colorText: '#08F7FC',
 
                             },
                             algorithm: theme.darkAlgorithm, */
                        }">
                            <a-input v-model:value="searchinfo1.name" placeholder="姓名" allow-clear></a-input>
                        </a-config-provider>
                    </a-col>
                    <a-col :xs="12" :lg="4" :md="6" :sm="6">
                        <a-config-provider :theme="{
                            /*  token: {
                                 colorPrimary: '#08F7FC',
                                 colorText: '#08F7FC',
                                 colorBgContainer: '#142030',
                             },
                             algorithm: theme.darkAlgorithm, */
                        }">
                            <a-input v-model:value="searchinfo1.phone" placeholder="号码" allow-clear></a-input>
                        </a-config-provider>
                    </a-col>

                    <a-col :flex="'150px'">
                        <a-space>
                            <a-button type="primary" @click="searchdata1">搜索</a-button>
                            <a-button @click="reset1">重置</a-button>
                        </a-space>
                    </a-col>
                </a-row>
                <a-config-provider :theme="{
                }" :locale="zhCN">
                    <a-table rowKey="id" :row-selection="rowSelection1" :columns="columns1" :data-source="dataList1"
                        :pagination="pagination1" :loading="loading">
                    </a-table>
                </a-config-provider>

            </a-modal>
        </a-page-header>
    </div>
</template>

<script lang='ts' setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
import { TableProps, Modal, message, FormInstance } from "ant-design-vue";
import zhCN from "ant-design-vue/es/locale/zh_CN"
let selectedRowKeys: string[] = [];
let selectedRowKeys1: string[] = [];
const loading = ref(false);
const open = ref(false);
const rowSelection: any = {
    onChange: (_selectedRowKeys: string[], _selectedRows: any[]) => {
        selectedRowKeys = _selectedRowKeys;
    },
    /*  getCheckboxProps: (record: any) => ({
         disabled: record.username === 'admin'
     }), */
};
const rowSelection1: any = {
    onChange: (_selectedRowKeys: string[], _selectedRows: any[]) => {
        selectedRowKeys1 = _selectedRowKeys;
    },
    /*  getCheckboxProps: (record: any) => ({
         disabled: record.username === 'admin'
     }), */
};
const router = useRouter();
const route = useRoute();
let searchModel = ref({ pageSize: 10, pageNumber: 1 });
let searchModel1 = ref({ pageSize: 10, pageNumber: 1 });
const dataList = ref<any[]>([]);
const dataList1 = ref<any[]>([]);
const searchinfo = ref<any>({
    roleId: route.query.roleId,
    name: undefined,
    phone: undefined,
})
const pagination = ref({
    current: searchModel.value.pageNumber ? searchModel.value.pageNumber : 1,
    pageSize: searchModel.value.pageSize ? searchModel.value.pageSize : 30,
    total: 0,
    onChange: (pageNumber: number, pageSize: number) => { searchModel.value.pageSize = pageSize; searchModel.value.pageNumber = pageNumber; search() },
});
const columns: any = [
    {
        title: '账号',
        dataIndex: 'username',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '姓名',
        dataIndex: 'name',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '电话号码',
        dataIndex: 'phoneNumber',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '所属机构',
        dataIndex: 'organizationalName',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '状态',
        dataIndex: ['state', 'desc'],
        fixed: 'left',
        key: 'name',
    },
    {
        title: '创建时间',
        dataIndex: 'createDateTime',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '最后登录时间',
        dataIndex: 'lastLoginTime',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '操作',
        key: "operation",
        fixed: 'right',
        width: '80px',
    },
]
const columns1: any = [
    {
        title: '账号',
        dataIndex: 'username',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '姓名',
        dataIndex: 'name',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '电话号码',
        dataIndex: 'phoneNumber',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '所属机构',
        dataIndex: 'organizationalName',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '状态',
        dataIndex: ['state', 'desc'],
        fixed: 'left',
        key: 'name',
    },
    {
        title: '创建时间',
        dataIndex: 'createDateTime',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '最后登录时间',
        dataIndex: 'lastLoginTime',
        fixed: 'left',
        key: 'name',
    },

]
const searchinfo1 = ref<any>({
    roleId: route.query.roleId,
    name: undefined,
    phone: undefined,
})
const pagination1 = ref({
    current: searchModel1.value.pageNumber ? searchModel1.value.pageNumber : 1,
    pageSize: searchModel1.value.pageSize ? searchModel1.value.pageSize : 30,
    total: 0,
    onChange: (pageNumber: number, pageSize: number) => { searchModel1.value.pageSize = pageSize; searchModel1.value.pageNumber = pageNumber; search1() },
});
const search = () => {
    loading.value = true;
    axios.axiosGet("/upms/console/role/user/list", { params: { ...searchModel.value, ...searchinfo.value } }, (data: any) => {
        dataList.value = data.data;
        pagination.value.total = data.totalElements;
        pagination.value.current = data.pageNumber;
        loading.value = false;
    }, null, null)
}
const search1 = () => {
    console.log(searchinfo1.value);
    console.log(route.query.roleId);

    loading.value = true;
    axios.axiosGet("/upms/console/role/user/unbind/list", { params: { ...searchModel1.value, ...searchinfo1.value } }, (data: any) => {
        dataList1.value = data.data;
        pagination1.value.total = data.totalElements;
        pagination1.value.current = data.pageNumber;
        loading.value = false;
    }, null, null)
}
const searchdata = () => {
    pagination.value.current = 1;
    search()
}
const reset = () => {
    pagination.value.current = 1;
    pagination.value.pageSize = 10;
    searchinfo.value = {
        roleId: useRoute().query.roleId,
        name: undefined,
        phone: undefined,
    }
    search()
}
const searchdata1 = () => {
    pagination1.value.current = 1;
    search1()
}
const reset1 = () => {
    pagination1.value.current = 1;
    pagination1.value.pageSize = 10;
    searchinfo1.value = {
        roleId: route.query.roleId,
        name: undefined,
        phone: undefined,
    }
    search()
}
search()
const back = () => {
    router.push({ path: '/' });
}
const add = () => {
    search1()
    selectedRowKeys1 = [];
    searchinfo1.value = {
        roleId: route.query.roleId,
        name: undefined,
        phone: undefined,
    }
    open.value = true;
}
function del(id: string[], roleId: string) {

    if (!id || id.length === 0) {
        message.error("请选择要删除的数据！")
        return;
    }
    Modal.confirm({
        title: '是否要删除该数据?',
        // content: '',
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk() {
            let params: any = {
                userIds: [id]
            };
            if (id instanceof Array) {
                params.userIds = id
            } else {
                params = {
                    userIds: [id]
                };
            }
            axios.axiosPost("/upms/console/role/user/delete", { ...params, roleId: roleId }, (data: any) => {
                if (data.status === 200) {
                    message.info(data.message);
                    selectedRowKeys = []
                    search();
                }
            }, null, null);
        },
        onCancel() {
        },
    });
}
const save = (id: any) => {
    console.log(111);
    
    if (!id || id.length === 0) {
        message.error("请选择要授权的用户！")
        return;
    }
    Modal.confirm({
        title: '是否要授权用户?',
        // content: '',
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk() {
            let params: any = {
                userIds: [id]
            };
            if (id instanceof Array) {
                params.userIds = id
            } else {
                params = {
                    userIds: [id]
                };
            }
            axios.axiosPost("/upms/console/user/role/add", { ...params, roleId: route.query.roleId }, (data: any) => {
                if (data.status === 200) {
                    message.info(data.message);
                    selectedRowKeys1 = []
                    search1();
                }
            }, null, null);
        },
        onCancel() {
        },
    });
}
</script>

<style scoped lang='less'></style>