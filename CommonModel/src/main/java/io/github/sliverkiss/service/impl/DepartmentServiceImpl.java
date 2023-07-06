package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * 部门(Department)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-06 12:50:06
 */
@Service("departmentService")
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, Department> implements DepartmentService {

}

