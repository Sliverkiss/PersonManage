package io.github.sliverkiss.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.AssessDeclareQueryDTO;
import io.github.sliverkiss.dao.*;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import io.github.sliverkiss.domain.entity.assess.AssessItem;
import io.github.sliverkiss.domain.entity.assess.AssessSet;
import io.github.sliverkiss.domain.vo.AssessDeclareVo;
import io.github.sliverkiss.service.AssessDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 绩效申报(AssessDeclare)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-17 16:40:04
 */
@Service("assessDeclareService")
public class AssessDeclareServiceImpl extends ServiceImpl<AssessDeclareDao, AssessDeclare> implements AssessDeclareService {

    @Autowired
    private AssessSetDao setDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private AssessItemDao itemDao;
    ;

    @Override
    public boolean existDeclare(AssessDeclare declare) {
        // 验证三要素，assessId、employeeId、type=0
        AssessDeclare res = this.getDeclare ( declare.getAssessId (), declare.getEmployeeId (), 0 );
        if (!res.getStatus ().equals ( "未申报" )) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseResult selectPage(AssessDeclareQueryDTO dto) {
        Page<AssessDeclare> page = toPage ( dto );
        String title = dto.getTitle ();
        String status = dto.getStatus ();
        // 用户数据隔离
        Integer userEmpId = dto.getUserEmpId ();
        Integer userRole = dto.getUserRole ();
        try {
            // 状态模糊查询
            LambdaQueryWrapper<AssessDeclare> wrapper = Wrappers.lambdaQuery ( AssessDeclare.class )
                    .eq ( AssessDeclare::getType, SystemConstants.DECLARE_TYPE_ROOT )
                    .like ( StringUtils.isNotBlank ( status ), AssessDeclare::getStatus, status );
            // 标题模糊查询
            if (StringUtils.isNotBlank ( title )) {
                List<Integer> setIds = setDao.selectList ( Wrappers.lambdaQuery ( AssessSet.class ).like ( AssessSet::getTitle, title ) )
                        .stream ().map ( AssessSet::getId ).collect ( Collectors.toList () );
                if (setIds.size () > 0) {
                    wrapper.in ( AssessDeclare::getAssessId, setIds );
                }
            }
            // 数据隔离
            if (userRole != null && userRole.equals ( UserContants.ROLE_USER ) && userEmpId != null) {
                wrapper.eq ( AssessDeclare::getEmployeeId, userEmpId );
            }
            Page<AssessDeclare> tPage = this.page ( page, wrapper );
            IPage<AssessDeclareVo> voIPage = EntityUtils.toPage ( tPage, AssessDeclareVo::new );
            // 属性注入
            this.setTableFieldBatch ( voIPage.getRecords () );
            return ResponseResult.okResult ( voIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据考核计划id和员工id获取declare整体计划
     *
     * @param assessId   评估id
     * @param emploeyyId emploeyy id
     *
     * @return {@link AssessDeclare}
     */
    @Override
    public AssessDeclare getDeclare(Integer assessId, Integer emploeyyId, Integer type) {
        AssessDeclare res = this.getOne ( Wrappers.lambdaQuery ( AssessDeclare.class )
                .eq ( AssessDeclare::getAssessId, assessId )
                .eq ( AssessDeclare::getEmployeeId, emploeyyId )
                .eq ( AssessDeclare::getType, type ) );
        this.setTableField ( res );
        res.setDeclareList ( getDeclareChildren ( res ) );
        return res;
    }

    @Override
    public <T extends AssessDeclare> List<T> getDeclareChildren(T entity) {
        List<AssessDeclare> list = this.list ( Wrappers.lambdaQuery ( AssessDeclare.class )
                .eq ( AssessDeclare::getAssessId, entity.getAssessId () )
                .eq ( AssessDeclare::getEmployeeId, entity.getEmployeeId () )
                .eq ( AssessDeclare::getType, 1 ) );
        this.setTableFieldBatch ( list );
        return (List<T>) list;
    }

    /**
     * 列表注入额外字段
     *
     * @param list 列表
     */
    public <T extends AssessDeclare> void setTableFieldBatch(List<T> list) {
        list.forEach ( this::setTableField );
    }

    /**
     * 实体注入额外字段
     *
     * @param entity 实体
     */
    public <T extends AssessDeclare> void setTableField(T entity) {
        // 四表联查
        AssessSet set = setDao.selectById ( entity.getAssessId () );
        Employee employee = employeeDao.selectById ( entity.getEmployeeId () );
        Personal personal = personalDao.selectById ( employee.getPersonalId () );
        Department department = departmentDao.selectById ( employee.getDepartmentId () );
        AssessItem item = itemDao.selectById ( entity.getItemId () );
        entity.setAssessSet ( set )
                .setAssessTitle ( set.getTitle () )
                .setEmployeeName ( personal.getName () )
                .setDeptId ( department.getId () )
                .setDeptName ( department.getDepartmentName () );
        Optional.ofNullable ( item ).ifPresent ( e -> entity.setItemName ( e.getName () ) );
    }

}

