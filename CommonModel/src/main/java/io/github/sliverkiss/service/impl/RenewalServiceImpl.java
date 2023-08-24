package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.RenewalQueryDTO;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.RenewalDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.Renewal;
import io.github.sliverkiss.domain.vo.RenewalVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.RenewalService;
import io.github.sliverkiss.utils.DateUtil;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.sliverkiss.utils.CheckUtil.checkStr;

/**
 * 续约申请表(Renewal)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-07 11:44:20
 */
@Service("renewalService")
public class RenewalServiceImpl extends ServiceImpl<RenewalDao, Renewal> implements RenewalService {

    @Resource
    private PersonalDao personalDao;
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private DepartmentDao departmentDao;

    /**
     * 分页获取续约记录表
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult selectRenewalPage(RenewalQueryDTO renewalQueryDTO) {
        Page<Renewal> page = toPage ( renewalQueryDTO );
        String name = renewalQueryDTO.getName ();
        String departmentId = renewalQueryDTO.getDepartmentId ();
        String employeeId = renewalQueryDTO.getEmployeeId ();
        String status = renewalQueryDTO.getStatus ();
        // 用户数据隔离
        Integer userEmpId = renewalQueryDTO.getUserEmpId ();
        Integer userRole = renewalQueryDTO.getUserRole ();
        // 如果员工姓名不为空，则按姓名进行模糊查询
        LambdaQueryWrapper<Personal> personalWrapper = Wrappers.lambdaQuery ( Personal.class )
                .like ( StringUtils.isNotBlank ( name ), Personal::getName, name );
        // 获取查询到的员工信息id集合
        List<Integer> personalIds = personalDao.selectList ( personalWrapper )
                .stream ().map ( Personal::getId ).collect ( Collectors.toList () );
        // 多条件模糊查询员工工作表，包括personalId、departmentId、employeeId
        List<Integer> employeeIds = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class )
                        .in ( personalIds.size () > 0, Employee::getPersonalId, personalIds )
                        .eq ( Employee::getDepartmentId, departmentId ) )
                .stream ().map ( Employee::getId ).collect ( Collectors.toList () );
        // 后端数据校验
        if (checkStr ( name, personalIds ) || checkStr ( employeeId, employeeIds )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.FIND_NOT_FOUND );
        }
        try {
            // 对续约表 进行模糊查询
            LambdaQueryWrapper<Renewal> renewalWrapper = Wrappers.lambdaQuery ( Renewal.class )
                    .like ( StringUtils.isNotBlank ( employeeId ), Renewal::getEmployeeId, employeeId )
                    .in ( employeeIds.size () > 0, Renewal::getEmployeeId, employeeIds )
                    .eq ( StringUtils.isNotBlank ( status ), Renewal::getState, status )
                    .orderByDesc ( Renewal::getApprovedDate );
            // 将模糊查询结果进行分页
            // 数据隔离
            if (userRole.equals ( UserContants.ROLE_USER )) {
                renewalWrapper.eq ( userEmpId != null, Renewal::getEmployeeId, userEmpId );
            }
            Page<Renewal> renewalPage = this.page ( page, renewalWrapper );
            IPage<RenewalVo> renewalVoIPage = EntityUtils.toPage ( renewalPage, RenewalVo::new );
            // 多表联查
            this.renewalInnerJoinEmployee ( renewalVoIPage );
            // 获取员工合同列表
            return ResponseResult.okResult ( renewalVoIPage );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
        }

    }

    /**
     * 续约表连接员工表，根据员工工作编号查询部门名称，根据员工信息编号查询员工姓名
     *
     * @param renewalVoIPage 续约视图
     *
     * @Note 查询流程：employeeIds->{departmentId->departmentName,personalId->name}
     */
    public void renewalInnerJoinEmployee(IPage<RenewalVo> renewalVoIPage) {
        // 获取员工编号集合
        List<Integer> employeeIds = renewalVoIPage.getRecords ().stream ().map ( Renewal::getEmployeeId ).collect ( Collectors.toList () );
        // 如果不为空，则将部门名称和员工姓名注入 到续约视图
        if (!employeeIds.isEmpty ()) {
            List<Employee> employeList = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class )
                    .in ( Employee::getId, employeeIds ) );
            Map<Integer, Employee> map = EntityUtils.toMap ( employeList, Employee::getId, e -> e );
            renewalVoIPage.getRecords ().forEach ( renewalVo -> {
                Employee employee = map.get ( renewalVo.getEmployeeId () );
                Personal personal = personalDao.selectById ( employee.getPersonalId () );
                Department department = departmentDao.selectById ( employee.getDepartmentId () );
                Optional.ofNullable ( department ).ifPresent ( e -> renewalVo.setDepartmentName ( e.getDepartmentName () ) );
                Optional.ofNullable ( personal ).ifPresent ( e -> renewalVo.setName ( e.getName () ) );
            } );
        }
    }

    @Override
    public void afterSave(Renewal renewal) {
        if (StringUtils.isBlank ( renewal.getState () )) {
            return;
        }
        // 判断续约申请是否通过
        if (SystemConstants.SYSTEM_STATUS_YES.equals ( renewal.getState () )) {
            Employee employee = employeeDao.selectById ( renewal.getEmployeeId () );
            // 获取当前日期
            Optional.ofNullable ( employee ).ifPresent ( e -> {
                // 当前终止日期为起始日期，合同年限为续约年限
                e.setStartContract ( e.getEndContract () ).setContractTerm ( renewal.getRenewalAge () );
                // 自动计算并注入合同截止日期
                e.setEndContract ( DateUtil.endContract ( e.getStartContract (), e.getContractTerm () ) );
            } );
            employeeDao.updateById ( employee );
        }
    }
}

