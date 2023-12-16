package net.lab1024.sa.admin.module.system.earthquake.manager;

import net.lab1024.sa.admin.module.system.earthquake.dao.EarthquakeDao;
import net.lab1024.sa.admin.module.system.earthquake.domain.entity.EarthquakeEntity;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.role.domain.entity.RoleEmployeeEntity;
import net.lab1024.sa.admin.module.system.role.manager.RoleEmployeeManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class EarthquakeManager {
    @Autowired
    private EarthquakeDao earthquakeDao;

    @Autowired
    private RoleEmployeeManager roleEmployeeManager;

    /**
     * 保存员工
     *
     * @param employee
     */
    @Transactional(rollbackFor = Throwable.class)
    public void saveEmployee(EmployeeEntity employee, List<Long> roleIdList) {
        // 保存员工 获得id
        employeeDao.insert(employee);

        if (CollectionUtils.isNotEmpty(roleIdList)) {
            List<RoleEmployeeEntity> roleEmployeeList = roleIdList.stream().map(e -> new RoleEmployeeEntity(e, employee.getEmployeeId())).collect(Collectors.toList());
            roleEmployeeManager.saveBatch(roleEmployeeList);
        }
    }

    /**
     * 更新员工
     *
     * @param employee
     */
    @Transactional(rollbackFor = Throwable.class)
    public void updateEarthquake(EarthquakeEntity earthquake) {
        // 保存员工 获得id
        employeeDao.updateById(employee);

    }

}
