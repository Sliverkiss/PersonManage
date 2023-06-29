package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * 员工(Employee)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:55:32
 */
@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {

}

