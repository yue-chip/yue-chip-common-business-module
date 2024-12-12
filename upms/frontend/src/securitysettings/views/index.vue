<template>
    <div>
        <a-card>
            <a-form ref="updatePasswordFrom" :labelCol="{ span: 3, offset: 0 }">
                <a-row>
                    <a-col :span="24">
                        <a-form-item label="超时无操作时间(分钟)" name="password" ref="password">
                            <a-input placeholder="超时无操作时间" v-model:value.number="updateModel.timeout" />
                        </a-form-item>
                    </a-col>
                </a-row>
                <a-row>
                    <a-col :span="24">
                        <a-form-item label="定期更新密码(小时)" name="password1" ref="password1">
                            <a-input placeholder="定期更新密码" v-model:value.number="updateModel.passwordTime" />
                        </a-form-item>
                    </a-col>
                </a-row>
                <a-row>
                    <a-col :span="24">
                        <a-form-item label="">
                            <a-button type="primary" @click="ok">保存</a-button>
                        </a-form-item>
                       
                    </a-col>
                </a-row>

            </a-form>
        </a-card>
    </div>
</template>

<script lang='ts' setup>
import { ref } from "vue";
import axios from "@yue-chip/yue-chip-frontend-core/axios/axios";
const updateModel = ref<any>({})
const search = () => {
    axios.axiosGet("/upms/console/safety/detail", { params: undefined }, (data: any) => {
        updateModel.value = data.data
    }, null, null)
}
search()
const ok = () => {

}
</script>

<style scoped lang='less'></style>