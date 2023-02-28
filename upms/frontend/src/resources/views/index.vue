<template>
  <div>
    <a-card >
      <a-form ref="from" :model="searchModel" :labelCol="{span: 5,offset:0}" >
        <a-row >
          <a-col :span="6">
            <a-form-item label="作用域" name="scope" ref="scope" >
              <a-select  v-model:value="searchModel.scope" >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="作用域" name="scope" ref="scope" >
              <a-select  v-model:value="searchModel.scope" >
              </a-select>
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
                <a-button type="primary" >
                  <template #icon><SearchOutlined /></template>
                  查询
                </a-button>
                <a-button type="primary" >
                  <template #icon><PlusOutlined /></template>
                  添加
                </a-button>
              </a-space>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card >
      dfsdfsdf
    </a-card>
  </div>
</template>

<script setup lang="ts">
  import {ref ,reactive} from 'vue'
  import { SearchOutlined,PlusOutlined } from '@ant-design/icons-vue';

  const searchModel = reactive({})
</script>

<style scoped>

</style>