<template>
    <div>
        <a-card>
            <a-form ref="updatePasswordFrom" :labelCol="{ span: 6, offset: 0 }">
                <a-row>
                    <a-col :span="24">
                        <a-form-item label="超时无操作时间(分钟)" >
                            <a-input placeholder="超时无操作时间" v-model:value.number="updateModel.timeout" />
                        </a-form-item>
                    </a-col>
                </a-row>
                <a-row>
                    <a-col :span="24">
                        <a-form-item label="定期更新密码(小时)" >
                            <a-input placeholder="定期更新密码" v-model:value.number="updateModel.passwordTime" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-item label="密码最小长度">
                            <a-input placeholder="密码最小长度" v-model:value.number="updateModel.passwordLength" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-item label="是否禁止连续使用同一字符" >
                            <a-switch v-model:checked="updateModel.sameChar" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="24">
                        <a-form-item label="是否包含三种字符以上" >
                            <a-switch v-model:checked="updateModel.threeChar" />
                        </a-form-item>
                    </a-col>
                </a-row>
                <a-row>
                    <a-col :span="24">
                        <a-form-item :wrapper-col="{  span: 6,offset: 6, }">
                            <a-button type="primary" @click="ok">保存</a-button>
                        </a-form-item>

                    </a-col>
                </a-row>

            </a-form>
        </a-card>
    </div>
</template>

<script lang='ts' setup>
import { ref, nextTick } from "vue";
import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
import { message } from "ant-design-vue";
const updateModel = ref<any>({})
const search = () => {
    axios.axiosGet("/upms/console/safety/detail", { params: undefined }, (data: any) => {


        nextTick(() => {
            updateModel.value = data.data
        })
    }, null, null)
}
search()
const ok = () => {
    console.log(updateModel.value);
    
    axios.axiosGet("/upms/console/safety/update", { params: { ...updateModel.value } }, (data: any) => {
        message.success(data.message)
        search()
    }, null, null)
}
</script>

<style scoped lang='less'></style>