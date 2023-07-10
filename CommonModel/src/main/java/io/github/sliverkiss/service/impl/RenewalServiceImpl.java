package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.RenewalDao;
import io.github.sliverkiss.domain.DTO.RenewalQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.Renewal;
import io.github.sliverkiss.domain.vo.RenewalVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.RenewalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        Integer currentPage = renewalQueryDTO.getCurrentPage ();
        Integer pageSize = renewalQueryDTO.getPageSize ();
        String name = renewalQueryDTO.getName ();
        String departmentId = renewalQueryDTO.getDepartmentId ();
        String employeeId = renewalQueryDTO.getEmployeeId ();
        String status = renewalQueryDTO.getStatus ();
        if (currentPage == null || pageSize <= 0 || pageSize == null || pageSize < 1) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
        Page<Renewal> page = new Page<> ( currentPage, pageSize );
        // 如果员工姓名不为空，则按姓名进行模糊查询
        LambdaQueryWrapper<Personal> personalWrapper = Wrappers.lambdaQuery ( Personal.class )
                .like ( StringUtils.isNotBlank ( name ), Personal::getName, name );
        // 获取查询到的员工信息id集合
        List<Integer> personalIds = personalDao.selectList ( personalWrapper )
                .stream ().map ( Personal::getId ).collect ( Collectors.toList () );
        // 模糊查询部门名称
        List<Integer> departmentIds = departmentDao.selectList ( Wrappers.lambdaQuery ( Department.class )
                .eq ( Department::getId, departmentId ) ).stream ().map ( Department::getId ).collect ( Collectors.toList () );
        // 多条件模糊查询员工工作表，包括personalId、departmentId、employeeId
        List<Integer> employeeIds = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class )
                        .in ( personalIds.size () > 0, Employee::getPersonalId, personalIds )
                        .like ( StringUtils.isNotBlank ( employeeId ), Employee::getId, employeeId )
                        .in ( departmentIds.size () > 0, Employee::getDepartmentId, departmentId ) )
                .stream ().map ( Employee::getId ).collect ( Collectors.toList () );

        // 后端数据校验
        if (checkStr ( name, personalIds ) || checkStr ( employeeId, employeeIds ) || checkStr ( departmentId, departmentIds )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.FIND_NOT_FOUND );
        } else {
            try {
                // 对续约表 进行模糊查询
                LambdaQueryWrapper<Renewal> renewalWrapper = Wrappers.lambdaQuery ( Renewal.class )
                        .in ( employeeIds.size () > 0, Renewal::getEmployeeId, employeeIds )
                        .eq ( StringUtils.isNotBlank ( status ), Renewal::getState, status );
                // 将模糊查询结果进行分页
                Page<Renewal> renewalPage = this.page ( page, renewalWrapper );
                IPage<RenewalVo> renewalVoIPage = EntityUtils.toPage ( renewalPage, RenewalVo::new );
                // 多表联查
                this.renewalInnerJoinEmployee ( renewalVoIPage );
                return ResponseResult.okResult ( renewalVoIPage );
            } catch (Exception e) {
                throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
            }
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
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteRenewal(Integer id) {
        if (id != null) {
            try {
                this.removeById ( id );
                return ResponseResult.okResult ();
            } catch (Exception e) {
                throw new SystemException ( AppHttpCodeEnum.DELETE_FAILED );
            }
        } else {
            return ResponseResult.errorResult ( AppHttpCodeEnum.DELETE_FAILED );
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult saveRenewal(Renewal renewal) {
        // 获取当前日期并注入renewal
        String approvedDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( renewal ).ifPresent ( e -> e.setApprovedDate ( approvedDate ) );
        try {
            this.save ( renewal );
            return ResponseResult.okResult ();
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateRenewal(Renewal renewal) {
        // 获取当前日期并注入renewal
        String approvedDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( renewal ).ifPresent ( e -> e.setApprovedDate ( approvedDate ) );
        try {
            this.update ( renewal, Wrappers.lambdaQuery ( Renewal.class ).eq ( Renewal::getId, renewal.getId () ) );
            return ResponseResult.okResult ( AppHttpCodeEnum.SUCCESS );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
    }
}

