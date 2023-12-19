/*
 * 支援灾情
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 21:59:15
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { getRequest, postRequest } from '/@/lib/axios';

export const supportDisasterApi = {
  /**
   * @description: 查询所有支援灾情
   * @param {*}
   * @return {*}
   */
  queryAll: () => {
    return getRequest('/support/queryAll');
  },
  /**
   * @description: 支援灾情查询
   * @param {*}
   * @return {*}
   */
  querySupportDisaster: (params) => {
    return postRequest('/support/query', params);
  },
  /**
   * @description: 根据位置查询支援灾情
   * @param {SupportDisasterAddTo} params
   * @return {*}
   */
  getByLocation: (location) => {
    return getRequest(`/support/getByLocation/${location}`);
  },
  /**
   * @description: 根据范围查询支援灾情
   * @param {string} extent
   * @return {*}
   */
  getByExtent: (extent) => {
    return getRequest(`/support/getByExtent/${extent}`);
  },
  /**
   * @description: 根据标签查询支援灾情
   * @param {string} label
   * @return {*}
   */
  getByLabel: (label) => {
    return getRequest(`/support/getByLabel/${label}`);
  },
  /**
   * @description: 添加支援灾情
   * @param {SupportDisasterAddto} params
   * @return {*}
   */
  addSupportDisaster: (params) => {
    return postRequest('/support/add', params);
  },
  /**
   * @description: 添加员工
   * @param {SupportDisasterUpdateDto} params
   * @return {*}
   */
  updateSupportDisaster: (params) => {
    return postRequest('/support/update', params);
  },
  /**
   * @description: 删除支援灾情
   * @param {number} SupportDisasterId
   * @return {*}
   */
  deleteSupportDisaster: (id) => {
    return getRequest(`/support/delete/${id}`);
  },
  /**
   * @description: 批量删除支援灾情
   * @param {number} IdList
   * @return {*}
   */
  batchDeleteSupportDisaster: (IdList) => {
    return postRequest('/support/update/batch/delete', IdList);
  },
  /**
   * @description: 查询支援灾情-根据ID
   * @param {number} id
   * @return {*}
   */
  getSupportDisasterById: (id) => {
    return getRequest(`/support/getB
    yId/${id}`);
  },
};
