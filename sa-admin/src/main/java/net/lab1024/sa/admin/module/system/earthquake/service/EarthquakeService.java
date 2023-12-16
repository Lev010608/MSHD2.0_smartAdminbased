package net.lab1024.sa.admin.module.system.earthquake.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import net.lab1024.sa.admin.module.system.department.domain.entity.DepartmentEntity;
import net.lab1024.sa.admin.module.system.department.domain.vo.DepartmentVO;
import net.lab1024.sa.admin.module.system.earthquake.dao.EarthquakeDao;
import net.lab1024.sa.admin.module.system.earthquake.domain.form.EarthquakeAddForm;
import net.lab1024.sa.admin.module.system.earthquake.domain.form.EarthquakeQueryForm;
import net.lab1024.sa.admin.module.system.earthquake.domain.vo.EarthquakeVO;
import net.lab1024.sa.admin.module.system.earthquake.manager.EarthquakeManager;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.domain.form.*;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.code.UserErrorCode;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.enumeration.UserTypeEnum;
import net.lab1024.sa.common.common.util.SmartBeanUtil;
import net.lab1024.sa.common.common.util.SmartPageUtil;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EarthquakeService {

    @Autowired
    private EarthquakeDao earthquakeDao;

    @Autowired
    private EarthquakeManager earthquakeManager;

    @Autowired
    private TokenService tokenService;


    public EarthquakeVO getByCode(String code) {
        return earthquakeDao.getEarthquakeByCode(code);
    }


    /**
     * 查询震情列表
     *
     * @param earthquakeQueryForm
     * @return
     */
    public ResponseDTO<PageResult<EarthquakeVO>> queryEarthquake(EarthquakeQueryForm earthquakeQueryForm) {
        earthquakeQueryForm.setDeletedFlag(false);
        Page pageParam = SmartPageUtil.convert2PageQuery(earthquakeQueryForm);

        List<EarthquakeVO> earthquakeList = earthquakeDao.queryEarthquake(pageParam, earthquakeQueryForm);
        if (CollectionUtils.isEmpty(earthquakeList)) {
            PageResult<EarthquakeVO> PageResult = SmartPageUtil.convert2PageResult(pageParam, earthquakeList);
            return ResponseDTO.ok(PageResult);
        }

        List<String> earthquakeCodeList = earthquakeList.stream().map(EarthquakeVO::getCode).collect(Collectors.toList());

        PageResult<EarthquakeVO> PageResult = SmartPageUtil.convert2PageResult(pageParam, earthquakeList);
        return ResponseDTO.ok(PageResult);
    }

    /**
     * 新增震情
     *
     * @param employeeAddForm
     * @return
     */
    public synchronized ResponseDTO<String> addEmployee(EmployeeAddForm employeeAddForm) {
        // 校验名称是否重复
        EmployeeEntity employeeEntity = employeeDao.getByLoginName(employeeAddForm.getLoginName(), null);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("登录名重复");
        }
        // 校验姓名是否重复
        employeeEntity = employeeDao.getByActualName(employeeAddForm.getActualName(), null);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("姓名重复");
        }
        // 校验电话是否存在
        employeeEntity = employeeDao.getByPhone(employeeAddForm.getPhone(), null);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("手机号已存在");
        }
        // 部门是否存在
        Long departmentId = employeeAddForm.getDepartmentId();
        DepartmentEntity department = departmentDao.selectById(departmentId);
        if (department == null) {
            return ResponseDTO.userErrorParam("部门不存在");
        }

        EmployeeEntity entity = SmartBeanUtil.copy(employeeAddForm, EmployeeEntity.class);
        // 设置密码 默认密码
        String password = randomPassword();
        entity.setLoginPwd(getEncryptPwd(password));

        // 保存数据
        entity.setDeletedFlag(Boolean.FALSE);
        employeeManager.saveEmployee(entity, employeeAddForm.getRoleIdList());

        return ResponseDTO.ok(password);
    }

    /**
     * 更新员工
     *
     * @param employeeUpdateForm
     * @return
     */
    public synchronized ResponseDTO<String> updateEmployee(EmployeeUpdateForm employeeUpdateForm) {

        Long employeeId = employeeUpdateForm.getEmployeeId();
        EmployeeEntity employeeEntity = employeeDao.selectById(employeeId);
        if (null == employeeEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        // 部门是否存在
        Long departmentId = employeeUpdateForm.getDepartmentId();
        DepartmentEntity departmentEntity = departmentDao.selectById(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.userErrorParam("部门不存在");
        }


        EmployeeEntity existEntity = employeeDao.getByLoginName(employeeUpdateForm.getLoginName(), null);
        if (null != existEntity && !Objects.equals(existEntity.getEmployeeId(), employeeId)) {
            return ResponseDTO.userErrorParam("登录名重复");
        }

        existEntity = employeeDao.getByPhone(employeeUpdateForm.getPhone(), null);
        if (null != existEntity && !Objects.equals(existEntity.getEmployeeId(), employeeId)) {
            return ResponseDTO.userErrorParam("手机号已存在");
        }

        existEntity = employeeDao.getByActualName(employeeUpdateForm.getActualName(), null);
        if (null != existEntity && !Objects.equals(existEntity.getEmployeeId(), employeeId)) {
            return ResponseDTO.userErrorParam("姓名重复");
        }

        // 不更新密码
        EmployeeEntity entity = SmartBeanUtil.copy(employeeUpdateForm, EmployeeEntity.class);
        entity.setLoginPwd(null);

        // 更新数据
        employeeManager.updateEmployee(entity, employeeUpdateForm.getRoleIdList());

        return ResponseDTO.ok();
    }

    /**
     * 更新禁用/启用状态
     *
     * @param employeeId
     * @return
     */
    public ResponseDTO<String> updateDisableFlag(Long employeeId) {
        if (null == employeeId) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        EmployeeEntity employeeEntity = employeeDao.selectById(employeeId);
        if (null == employeeEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        boolean disableFlag=!employeeEntity.getDisabledFlag();
        employeeEntity.setDisabledFlag(disableFlag);
        employeeDao.updateDisableFlag(employeeId, disableFlag);

        if (employeeEntity.getDisabledFlag()) {
            tokenService.batchRemoveRedisToken(employeeId, UserTypeEnum.ADMIN_EMPLOYEE);
        }

        return ResponseDTO.ok();
    }

    /**
     * 批量删除员工
     *
     * @param employeeIdList 员工ID列表
     * @return
     */
    public ResponseDTO<String> batchUpdateDeleteFlag(List<Long> employeeIdList) {
        if (CollectionUtils.isEmpty(employeeIdList)) {
            return ResponseDTO.ok();
        }
        List<EmployeeEntity> employeeEntityList = employeeManager.listByIds(employeeIdList);
        if (CollectionUtils.isEmpty(employeeEntityList)) {
            return ResponseDTO.ok();
        }
        // 更新删除
        List<EmployeeEntity> deleteList = employeeIdList.stream().map(e -> {
            EmployeeEntity updateEmployee = new EmployeeEntity();
            updateEmployee.setEmployeeId(e);
            updateEmployee.setDeletedFlag(true);
            return updateEmployee;
        }).collect(Collectors.toList());
        employeeManager.updateBatchById(deleteList);

        for (Long employeeId : employeeIdList) {
            tokenService.batchRemoveRedisToken(employeeId, UserTypeEnum.ADMIN_EMPLOYEE);
        }
        return ResponseDTO.ok();
    }


    /**
     * 批量更新部门
     *
     * @param batchUpdateDepartmentForm
     * @return
     */
    public ResponseDTO<String> batchUpdateDepartment(EmployeeBatchUpdateDepartmentForm batchUpdateDepartmentForm) {
        List<Long> employeeIdList = batchUpdateDepartmentForm.getEmployeeIdList();
        List<EmployeeEntity> employeeEntityList = employeeDao.selectBatchIds(employeeIdList);
        if (employeeIdList.size() != employeeEntityList.size()) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        // 更新
        List<EmployeeEntity> updateList = employeeIdList.stream().map(e -> {
            EmployeeEntity updateEmployee = new EmployeeEntity();
            updateEmployee.setEmployeeId(e);
            updateEmployee.setDepartmentId(batchUpdateDepartmentForm.getDepartmentId());
            return updateEmployee;
        }).collect(Collectors.toList());
        employeeManager.updateBatchById(updateList);

        return ResponseDTO.ok();
    }


    /**
     * 获取某个部门的员工信息
     *
     * @param departmentId
     * @return
     */
    public ResponseDTO<List<EmployeeVO>> getAllEmployeeByDepartmentId(Long departmentId, Boolean disabledFlag) {
        List<EmployeeEntity> employeeEntityList = employeeDao.selectByDepartmentId(departmentId, disabledFlag);
        if (disabledFlag != null) {
            employeeEntityList = employeeEntityList.stream().filter(e -> e.getDisabledFlag().equals(disabledFlag)).collect(Collectors.toList());
        }

        if (CollectionUtils.isEmpty(employeeEntityList)) {
            return ResponseDTO.ok(Collections.emptyList());
        }

        DepartmentVO department = departmentService.getDepartmentById(departmentId);

        List<EmployeeVO> voList = employeeEntityList.stream().map(e -> {
            EmployeeVO employeeVO = SmartBeanUtil.copy(e, EmployeeVO.class);
            if (department != null) {
                employeeVO.setDepartmentName(department.getName());
            }
            return employeeVO;
        }).collect(Collectors.toList());
        return ResponseDTO.ok(voList);
    }



    /**
     * 查询全部员工
     *
     * @return
     */
    public ResponseDTO<List<EmployeeVO>> queryAllEmployee(Boolean disabledFlag) {
        List<EmployeeVO> employeeList = employeeDao.selectEmployeeByDisabledAndDeleted(disabledFlag, Boolean.FALSE);
        return ResponseDTO.ok(employeeList);
    }

    /**
     * 根据登录名获取员工
     *
     * @param loginName
     * @return
     */
    public EmployeeEntity getByLoginName(String loginName) {
        return employeeDao.getByLoginName(loginName, null);
    }

}
