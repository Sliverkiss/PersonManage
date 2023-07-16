package io.github.sliverkiss.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.domain.entity.Employee;
import lombok.SneakyThrows;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */

public class ArrayUtils {
    @Resource
    private EmployeeDao employeeDao;

    @SneakyThrows
    public Map<Integer, Employee> idsToMap(List<Integer> Ids) {
        Map<Integer, Employee> map = null;
        if (!Ids.isEmpty ()) {
            List<Employee> employeList = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class )
                    .in ( Employee::getId, Ids ) );
            map = EntityUtils.toMap ( employeList, Employee::getId, e -> e );
        }
        return map;
    }
}
