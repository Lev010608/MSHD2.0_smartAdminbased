/*
 *  震情
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 21:59:15
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { getRequest, postRequest } from '/@/lib/axios';

export const earthquakeApi = {
  /**
   * @description: 查询所有震情 @author Lev
   * @param {*}
   * @return {*}
   */
  queryAll: () => {
    return getRequest('/earthquake/queryAll');
  },
  /**
   * @description: 震情管理查询
   * @param {*}
   * @return {*}
   */
  queryEarthquake: (params) => {
    return postRequest('/earthquake/query', params);
  },

  //缺getByLocation、getByExtent、getByLabel
  /**
   * @description: 添加震情
   * @param {EarthquakeAddDto} params
   * @return {*}
   */
  addEarthquake: (params) => {
    return postRequest('/earthquake/add', params);
  },
  /**
   * @description: 更新震情信息
   * @param {EarthquakeUpdateDto} params
   * @return {*}
   */
  updateEarthquake: (params) => {
    return postRequest('/earthquake/update', params);
  },
  /**
   * @description: 删除震情
   * @param {number} earthquakeId
   * @return {*}
   */
  deleteEarthquake: (earthquakeId) => {
    return getRequest(`/earthquake/delete/${earthquakeId}`);
  },
  /**
   * @description: 批量删除震情
   * @param {number} earthquakeIdList
   * @return {*}
   */
  batchDeleteEarthquake: (earthquakeCodeList) => {
    return postRequest('/earthquake/update/batch/delete', earthquakeCodeList);
  },
  /**
   * @description: 批量添加震情
   * @param {number} earthquakeIdList
   * @return {*}
   */
  batchAddEarthquake:(fileKey)=>{
    return postRequest('/earthquake/batch/add',   fileKey );
  },
  /**
   * @description: 批量调整员工部门/震情的？
   * @return {*}
   */
  // batchUpdateDepartmentEmployee: (updateParam) => {
  //   return postRequest('/employee/update/batch/department', updateParam);
  // },
  /**
   * @description: 重置员工密码
   * @param {number} employeeId
   * @return {*}
   */
  // resetPassword: (employeeId) => {
  //   return getRequest(`/employee/update/password/reset/${employeeId}`);
  // },
  /**
   * @description: 修改密码
   * @param {number} employeeId
   * @return {*}
   */
  // updateEmployeePassword: (param) => {
  //   return postRequest('/employee/update/password',param);
  // },
  /**
   * @description: 更新员工禁用状态
   * @param {number} employeeId
   * @return {*}
   */
  // updateDisabled: (employeeId) => {
  //   return getRequest(`/employee/update/disabled/${employeeId}`);
  // },

  // 查询震情-根据id
  queryEarthquakeById: (earthquakeId) => {
    return getRequest(`/earthquake/getById/${earthquakeId}`);
  },
};
