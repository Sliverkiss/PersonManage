package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.IndicatorDao;
import io.github.sliverkiss.domain.DTO.IndicatorDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Indicator;
import io.github.sliverkiss.domain.vo.IndicatorVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 绩效考核标准表(Indicator)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-05 15:53:06
 */
@Service("indicatorService")
public class IndicatorServiceImpl extends ServiceImpl<IndicatorDao, Indicator> implements IndicatorService {
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public ResponseResult selectPage(IndicatorDTO indicatorDTO) {
        Page<Indicator> page = toPage ( indicatorDTO );
        Integer departmentId = indicatorDTO.getDepartmentId ();
        String type = indicatorDTO.getType ();
        try {
            // 查询条件
            LambdaQueryWrapper<Indicator> wrapper = Wrappers.lambdaQuery ( Indicator.class )
                    .eq ( departmentId != null, Indicator::getDepartmentId, departmentId )
                    .eq ( StringUtils.isNotBlank ( type ), Indicator::getType, type );
            Page<Indicator> resignationPage = this.page ( page, wrapper );
            IPage<IndicatorVo> resignationVoIPage = EntityUtils.toPage ( resignationPage, IndicatorVo::new );
            // 注入属性
            this.indicatorInnerJoinDepartment ( resignationVoIPage );
            return ResponseResult.okResult ( resignationVoIPage );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
        }
    }

    /**
     * 绩效考核标准化表连接部门表，查询部门名称
     *
     * @param indicatorVoIPage 绩效考核标准视图
     */
    public void indicatorInnerJoinDepartment(IPage<IndicatorVo> indicatorVoIPage) {
        List<Integer> departmentIds = indicatorVoIPage.getRecords ().stream ().map ( Indicator::getDepartmentId ).collect ( Collectors.toList () );
        if (!departmentIds.isEmpty ()) {
            indicatorVoIPage.getRecords ().forEach ( indicatorVo -> {
                Department department = departmentDao.selectById ( indicatorVo.getDepartmentId () );
                Optional.ofNullable ( department ).ifPresent ( e -> indicatorVo.setDepartmentName ( e.getDepartmentName () ) );
            } );
        }
    }
}

