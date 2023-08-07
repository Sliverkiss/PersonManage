package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.ResignationDao;
import io.github.sliverkiss.domain.DTO.ResignationDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.Resignation;
import io.github.sliverkiss.domain.vo.ResignationVo;
import io.github.sliverkiss.service.ResignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 离职记录表(Resignation)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-03 16:36:38
 */
@Service("resignationService")
public class ResignationServiceImpl extends ServiceImpl<ResignationDao, Resignation> implements ResignationService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private DepartmentDao departmentDao;
    ;

    /**
     * 多条件分页模糊查询
     *
     * @param resignationDTO 辞职dto
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult selectPage(ResignationDTO resignationDTO) {
        Page<Resignation> page = toPage ( resignationDTO );
        try {
            // 查询条件
            LambdaQueryWrapper<Resignation> wrapper = Wrappers.lambdaQuery ( Resignation.class );

            Page<Resignation> resignationPage = this.page ( page );
            IPage<ResignationVo> resignationVoIPage = EntityUtils.toPage ( page, ResignationVo::new );
            // 注入属性
            this.onePatchResignationList ( resignationVoIPage );
            this.resignationInnerJoinEmployee ( resignationVoIPage );
            return ResponseResult.okResult ( resignationVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 离职记录表连接入职员工表，查询员工姓名、员工部门，并注入属性
     *
     * @param resignationVoIPage 签证官ipage辞职
     */
    public void resignationInnerJoinEmployee(IPage<ResignationVo> resignationVoIPage) {
        if (resignationVoIPage.getRecords ().size () > 0) {
            List<Integer> employeeIds = resignationVoIPage.getRecords ().stream ().map ( Resignation::getEmployeeId ).collect ( Collectors.toList () );
            if (!employeeIds.isEmpty ()) {
                Map<Integer, Employee> map = getEmployeeMap ( employeeDao, employeeIds );
                resignationVoIPage.getRecords ().forEach ( resignationVo -> {
                    Employee employee = map.get ( resignationVo.getEmployeeId () );
                    Personal personal = personalDao.selectById ( employee.getPersonalId () );
                    Department department = departmentDao.selectById ( employee.getDepartmentId () );
                    resignationVo.setEmployeeName ( personal.getName () )
                            .setDepartmentName ( department.getDepartmentName () );
                } );
            }
        }
    }

    /**
     * 获取员工离职列表，一对多关系，一个员工对应多条离职记录
     *
     * @param resignationVoIPage 离职视图
     */
    public void onePatchResignationList(IPage<ResignationVo> resignationVoIPage) {
        resignationVoIPage.getRecords ().forEach ( e -> {
            e.setResignationList ( this.list ( Wrappers.lambdaQuery ( Resignation.class ).in ( Resignation::getEmployeeId, e.getEmployeeId () ).orderByDesc ( Resignation::getReviewDate ) ) );
        } );
    }

}

