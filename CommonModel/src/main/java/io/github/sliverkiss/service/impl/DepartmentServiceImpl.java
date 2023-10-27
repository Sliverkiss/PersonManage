package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.controller.DTO.DepartmentQueryDTO;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Post;
import io.github.sliverkiss.domain.vo.DepartmentVo;
import io.github.sliverkiss.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门(Department)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-06 12:50:06
 */
@Service("departmentService")
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, Department> implements DepartmentService {

    @Resource
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public ResponseResult selectPage(DepartmentQueryDTO departmentQueryDTO) {
        Page<Department> page = toPage ( departmentQueryDTO );
        Integer departmentId = departmentQueryDTO.getDepartmentId ();
        String manager = departmentQueryDTO.getManager ();
        try {
            // 模糊查询
            LambdaQueryWrapper<Department> wrapper = Wrappers.lambdaQuery ( Department.class )
                    .in ( departmentId != null, Department::getId, departmentId )
                    .like ( StringUtils.isNotBlank ( manager ), Department::getManager, manager );
            // 获取列表
            Page<Department> departmentPage = this.page ( page, wrapper );
            IPage<DepartmentVo> departmentVoIPage = EntityUtils.toPage ( departmentPage, DepartmentVo::new );
            // 属性注入
            this.setTableFiled ( departmentVoIPage.getRecords () );
            return ResponseResult.okResult ( departmentVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 属性注入->岗位列表
     *
     * @param list
     */
    public <T extends DepartmentVo> void setTableFiled(List<T> list) {
        list.forEach ( e -> {
            Department department = this.getById ( e.getParentId () );
            Employee employee = employeeDao.getEmployeeById ( e.getManager () );
            List<Post> postList = departmentDao.getPostList ( e.getId () );
            e.setPostList ( postList );
            e.setParentDepartment ( department );
            e.setManagerName ( employee.getPersonal ().getName () );
        } );
    }

}

