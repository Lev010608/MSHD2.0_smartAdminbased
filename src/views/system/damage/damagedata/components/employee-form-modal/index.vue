<!--
  *  员工 表单 弹窗
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-08 20:46:18
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-drawer
    :title="form.code ? '编辑' : '添加'"
    :width="600"
    :visible="visible"
    :body-style="{ paddingBottom: '80px' }"
    @close="onClose"
    destroyOnClose
  >
    <a-form ref="formRef" :model="form" :rules="rules" layout="vertical">
      <a-form-item label="震情码" name="code">
        <a-input v-model:value.trim="form.code" placeholder="请输入震情码" />
      </a-form-item>
<!--      <a-form-item label="日期" name="phone">-->
<!--        <a-input v-model:value.trim="form.phone" placeholder="请输入日期" />-->
<!--      </a-form-item>-->
      <a-form-item label="描述" name="remark">
        <a-input v-model:value.trim="form.remark" placeholder="请输入描述" />
      </a-form-item>
<!--      <a-form-item label="登录名" name="loginName">-->
<!--        <a-input v-model:value.trim="form.mark" placeholder="请输入描述" />-->
<!--        <p class="hint">初始密码默认为：随机</p>-->
<!--      </a-form-item>-->
<!--      <a-form-item label="性别" name="gender">-->
<!--        <smart-enum-select style="width: 100%" v-model:value="form.gender" placeholder="请选择性别" enum-name="GENDER_ENUM" />-->
<!--      </a-form-item>-->
<!--      <a-form-item label="状态" name="disabledFlag">-->
<!--        <a-select v-model:value="form.disabledFlag" placeholder="请选择状态">-->
<!--          <a-select-option :value="0">启用</a-select-option>-->
<!--          <a-select-option :value="1">禁用</a-select-option>-->
<!--        </a-select>-->
<!--      </a-form-item>-->
<!--      <a-form-item label="角色" name="roleIdList">-->
<!--        <a-select mode="multiple" v-model:value="form.roleIdList" optionFilterProp="title" placeholder="请选择角色">-->
<!--          <a-select-option v-for="item in roleList" :key="item.roleId" :title="item.roleName">{{ item.roleName }}</a-select-option>-->
<!--        </a-select>-->
<!--      </a-form-item>-->
    </a-form>
    <div class="footer">
      <a-button style="margin-right: 8px" @click="onClose">取消</a-button>
<!--      <a-button type="primary" style="margin-right: 8px" @click="onSubmit(false)">保存</a-button>-->
      <a-button  type="primary" style="margin-right: 8px" @click="onSubmit(false,false)">添加</a-button>
      <a-button v-if="form.code" type="primary" style="margin-right: 8px" @click="onSubmit(false,true)">更新</a-button>
<!--      <a-button v-if="!form.code" type="primary" @click="onSubmit(true,false)">继续添加</a-button>-->
    </div>
  </a-drawer>
</template>
<script setup>
  import { message } from 'ant-design-vue';
  import _ from 'lodash';
  import { nextTick, reactive, ref } from 'vue';
  import {earthquakeApi}from'/@/api/system/earthquake/earthquake-api'
  import { employeeApi } from '/@/api/system/employee/employee-api';
  import { roleApi } from '/@/api/system/role/role-api';
  import DepartmentTreeSelect from '/@/components/system/department-tree-select/index.vue';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';
  import { GENDER_ENUM } from '/@/constants/common-const';
  import { regular } from '/@/constants/regular-const';
  import { SmartLoading } from '/@/components/framework/smart-loading';
import { smartSentry } from '/@/lib/smart-sentry';
  import codeFilled from "@ant-design/icons-vue/lib/icons/CodeFilled";
  // ----------------------- 以下是字段定义 emits props ---------------------
  const departmentTreeSelect = ref();
  // emit
  const emit = defineEmits(['refresh', 'show-account']);

  // ----------------------- 显示/隐藏 ---------------------

  const visible = ref(false); // 是否展示抽屉
  // 隐藏
  function onClose() {
    reset();
    visible.value = false;
  }
  // 显示
  async function showDrawer(rowData) {
    Object.assign(form, formDefault);
    if (rowData && !_.isEmpty(rowData)) {
      Object.assign(form, rowData);
    }
    visible.value = true;
    nextTick(() => {
      queryAllRole();
    });
  }

  // ----------------------- 表单显示 ---------------------

  const roleList = ref([]); //角色列表
  async function queryAllRole() {
    let res = await roleApi.queryAll();
    roleList.value = res.data;
  }

  const formRef = ref(); // 组件ref
  const formDefault = {
    // id: undefined,
    addorupdate: 0,//0则添加1则更新
    deletedFlag: 0,
    code: undefined,
    remark: undefined,
    // disabledFlag: 0,
    // leaveFlag: 0,
    // gender: GENDER_ENUM.MAN.value,
    // loginName: undefined,
    // phone: undefined,
    // roleIdList: undefined,
  };

  let form = reactive(_.cloneDeep(formDefault));
  function reset() {
    Object.assign(form, formDefault);
    formRef.value.resetFields();
  }

  // ----------------------- 表单提交 ---------------------
  // 表单规则
  const rules = {
    code: [
      { required: true, message: '震情码不能为空' },
      { validator: (rule, value, callback) => {
          if (value && value.length === 36) {
            callback(); // 验证通过
          } else {
            callback(new Error('震情必须为36个字符')); // 验证不通过，返回错误信息
          }
        },
        trigger: 'blur' },
    ],
    // phone: [
    //   { required: true, message: '日期不能为空' },
    //   { max: 30,message: '请输入正确的日期格式', trigger: 'blur' },
    // ],
    // loginName: [
    //   { required: true, message: '登录账号不能为空' },
    //   { max: 30, message: '登录账号不能大于30个字符', trigger: 'blur' },
    // ],
    // gender: [{ required: true, message: '性别不能为空' }],
    remark: [{ max: 100, message: '描述不能过长',trigger: 'blur' }],
    // disabledFlag: [{ required: true, message: '状态不能为空' }],
    // leaveFlag: [{ required: true, message: '在职状态不能为空' }],
  };

  // 校验表单
  function validateForm(formRef) {
    return new Promise((resolve) => {
      formRef
        .validate()
        .then(() => {
          resolve(true);
        })
        .catch(() => {
          resolve(false);
        });
    });
  }

  // 提交数据
  async function onSubmit(keepAdding,AddorUpdate) {
    let validateFormRes = await validateForm(formRef.value);
    if (!validateFormRes) {
      message.error('参数验证错误，请仔细填写表单数据!');
      return;
    }
    SmartLoading.show();
    if(AddorUpdate){
      await updateEarthquake(keepAdding);
    }
    else {
      await addEarthquake(keepAdding);
    }
  }

  async function addEarthquake(keepAdding) {
    try {
      let { data } = await earthquakeApi.addEarthquake(form);
      message.success('添加成功');
      //emit('show-account', form.code, data);
      if (keepAdding) {
        reset();
      } else {
        onClose();
      }
      emit('refresh');
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }
  async function updateEarthquake(keepAdding) {
    try {
      let result = await earthquakeApi.updateEarthquake(form);
      message.success('更新成功');
      if (keepAdding) {
        reset();
      } else {
        onClose();
      }
      emit('refresh');
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }

  // ----------------------- 以下是暴露的方法内容 ----------------------------
  defineExpose({
    showDrawer,
  });
</script>
<style scoped lang="less">
  .footer {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e9e9e9;
    padding: 10px 16px;
    background: #fff;
    text-align: right;
    z-index: 1;
  }
  .hint {
    margin-top: 5px;
    color: #bfbfbf;
  }
</style>
