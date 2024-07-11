<template>
    <div>
        <a-card>
            <a-button size="small" @click="back()">
                <template #icon>
                    <LeftOutlined />
                </template>
                返回
            </a-button>
        </a-card>
        <a-card>
            <a-form ref="from" :model="searchModel" :label-col="{ span: 4, offset: 0 }">
                <a-row>
                    <a-col :span="6">
                        <a-form-item label="网格名称" name="name" ref="name">
                            <a-input placeholder="请输入网格名称" v-model:value="searchModel.name" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="6">
                        <a-form-item label="姓名" name="userName" ref="userName">
                            <a-input placeholder="请输入姓名" v-model:value="searchModel.userName" />
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
                                <a-button type="primary" @click="searchModel.pageNumber = 1; search()">
                                    <template #icon>
                                        <SearchOutlined />
                                    </template>
                                    查询
                                </a-button>
                                <a-button type="primary" @click="add()">
                                    <template #icon>
                                        <PlusOutlined />
                                    </template>
                                    添加
                                </a-button>
                                <a-button type="primary" danger @click="del(selectedRowKeys)">
                                    <template #icon>
                                        <DeleteOutlined />
                                    </template>
                                    删除
                                </a-button>
                            </a-space>
                        </a-form-item>
                    </a-col>
                </a-row>
            </a-form>
        </a-card>

        <a-card>
            <a-table rowKey="id" :row-selection="rowSelection" :columns="columns" :data-source="dataList"
                :pagination="undefined" :loading="loading" :scroll="{ y: 440 }">
                <template #bodyCell="{ column, text, record }">
                    <template v-if="column.key === 'username'">
                       <div v-for="item in record.user" :key="item.id">
                        {{ item.name }} {{ item.phoneNumber }} 
                       </div>
                    </template>
                    
                    <template v-if="column.key === 'operation'">
                        <a-space :size="5">
                            <a-button size="small" @click="edit(record)">
                                <template #icon>
                                    <EditOutlined />
                                </template>
                                修改
                            </a-button>
                            <a-button v-if="record.username !== 'admin'" size="small" type="primary" danger
                                @click="del(record.id)">
                                <template #icon>
                                    <DeleteOutlined />
                                </template>
                                删除
                            </a-button>
                        </a-space>
                    </template>
                </template>
            </a-table>
        </a-card>



        <a-modal width="500px" v-model:visible="visible" title="添加/修改网格管理员" cancelText="取消" okText="保存"
            :destroyOnClose="true" :mask="true" :maskClosable="false" @cancel="cancel" @ok="save">
            <a-form ref="fromAddOrUpdate" :rules="rules" :model="addOrUpdateModel" :labelCol="{ span: 5, offset: 0 }">
                <a-row>
                    <a-col :span="24">
                        <a-form-item label="上级网格" name="userId" ref="userId">
                            <a-tree-select v-model:value="addOrUpdateModel.parentId" show-search
                                style="width: 100%" :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                                placeholder="选择上级网格" allow-clear tree-default-expand-all :tree-data="dataList"
                                tree-node-filter-prop="label" :field-names="{
                children: 'children',
                label: 'name',
                value: 'id',
            }">
                            </a-tree-select>

                        </a-form-item>
                        <a-form-item label="名称" name="name" ref="name">
                            <a-input placeholder="请输入网格名称" v-model:value="addOrUpdateModel.name" />
                        </a-form-item>
                        
                    </a-col>
                </a-row>
                <a-row>
                    <a-col :span="24">
                        <a-form-item label="网格员" name="userId" ref="userId">
                            <a-select :options="options" allowClear v-model:value="addOrUpdateModel.userIds" mode="tags"
                                placeholder="选择网格员">
                            </a-select>
                        </a-form-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-item label="排序" name="userId" ref="userId">
                            <a-input placeholder="请输入排序" v-model:value="addOrUpdateModel.sort" />
                        </a-form-item>
                    </a-col>
                </a-row>
            </a-form>
        </a-modal>
    </div>
</template>

<script setup lang="ts">
import { ref, onActivated, getCurrentInstance } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { SearchOutlined, PlusOutlined, DeleteOutlined, EditOutlined, LeftOutlined } from '@ant-design/icons-vue';
import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
import { TableProps, Modal, message, FormInstance } from "ant-design-vue";
import qs from "qs";
const _this: any = getCurrentInstance();
const router = useRouter();
const route = useRoute();
const fromAddOrUpdate = ref<FormInstance>();
let loading = ref(false);
let searchModel = ref({});
let selectedRowKeys: string[] = [];
let visible = ref<boolean>(false);
let addOrUpdateModel = ref({})
let organizationalId: string = undefined;
let options: any[] = ref<any>([]);
const columns = [
    {
        title: '网格名称',
        dataIndex: 'name',
        fixed: 'left',
        key: 'name',
    },
    {
        title: '网格员',
        
        key: 'username',
    },
    {
        title: '操作',
        key: "operation",
        fixed: 'right',
        width: '160px',
    },
]
let dataList = ref([]);



onActivated(() => {
    organizationalId = route.query.organizationalId;
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

function search() {
    loading.value = true;
    searchModel.value.organizationalId = organizationalId;
    axios.axiosGet("/upms/console/grid/list/tree", { params: searchModel.value }, (data: any) => {
        dataList.value = data.data;

        loading.value = false;
    }, null, null)
}

function save() {
    visible.value = true;
    fromAddOrUpdate.value.validateFields().then(() => {
        if (addOrUpdateModel.value.id) {
            axios.axiosPut("/upms/console/grid/update2", addOrUpdateModel.value,
                (data: any) => {
                    if (data.status === 200) {
                        message.info(data.message);
                        cancel();
                    }
                }, null, null)
        } else {
            addOrUpdateModel.value.organizationalId = organizationalId;
            axios.axiosPost("/upms/console/grid/add2", addOrUpdateModel.value,
                (data: any) => {
                    if (data.status === 200) {
                        message.info(data.message);
                        cancel();
                    }
                }, null, null)
        }
    }).catch((err: any) => { }).finally(() => { });
}

function edit(item: any) {
    visible.value = true;
    addOrUpdateModel.value = {
        name: item.name,
        parentId: item.parentId==0?undefined:item.parentId,
        userIds: item.user?item.user.map((user: any) => user.id):undefined,
        sort: item.sort,
        id: item.id,
        organizationalId:item.organizationalId
    };
    showUserSelect(); 
    
    /* axios.axiosGet("/upms/console/grid/details", { params: { id: id } }, (data: any) => {
        addOrUpdateModel.value = data.data;
    }, null, null) */
}

function del(id: string[]) {
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
            const params = {
                params: { ids: id },
                paramsSerializer: (params: any) => {
                    return qs.stringify(params, { indices: false })
                }
            };
            axios.axiosDelete("/security/pc/store/grid/delete", params, (data: any) => {
                if (data.status === 200) {
                    message.info(data.message);
                    search();
                }
            }, null, null);
        },
        onCancel() {
        },
    });
}

function add() {
    visible.value = true;
    addOrUpdateModel.value = {};
    showUserSelect();
}

async function showUserSelect() {
    await axios.axiosGet("/upms/console/organizational/user/select/list", { params: { organizationalId: organizationalId } }, (data: any) => {
        options.value = data.data;
    }, null, null)
}

function cancel() {
    visible.value = false;
    addOrUpdateModel.value = {};
    search();
}

function back() {
    router.go(-1);
}

</script>

<style scoped></style>
