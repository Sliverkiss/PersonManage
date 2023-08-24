package io.github.sliverkiss.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.controller.DTO.AssessItemQueryDTO;
import io.github.sliverkiss.dao.AssessItemDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessItem;
import io.github.sliverkiss.domain.vo.AssessItemVo;
import io.github.sliverkiss.service.AssessItemService;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

/**
 * 考核项(AssessItem)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-17 16:39:34
 */
@Service("assessItemService")
public class AssessItemServiceImpl extends ServiceImpl<AssessItemDao, AssessItem> implements AssessItemService {

    @Override
    public ResponseResult selectPage(AssessItemQueryDTO dto) {
        Page<AssessItem> page = toPage ( dto );
        String name = dto.getName ();
        try {
            // 模糊查询
            LambdaQueryWrapper<AssessItem> wrapper = Wrappers.lambdaQuery ( AssessItem.class )
                    .like ( StringUtils.isNotBlank ( name ), AssessItem::getName, name );
            // 数据隔离
            Page<AssessItem> tPage = this.page ( page, wrapper );
            IPage<AssessItemVo> voIPage = EntityUtils.toPage ( tPage, AssessItemVo::new );
            // 属性注入，员工姓名，调出部门，调入部门
            return ResponseResult.okResult ( voIPage );
        } catch (Exception e) {
            throw e;
        }
    }
}

