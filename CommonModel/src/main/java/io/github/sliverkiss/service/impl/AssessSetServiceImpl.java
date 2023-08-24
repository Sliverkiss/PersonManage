package io.github.sliverkiss.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.controller.DTO.AssessSetQueryDTO;
import io.github.sliverkiss.dao.AssessAndItemDao;
import io.github.sliverkiss.dao.AssessItemDao;
import io.github.sliverkiss.dao.AssessSetDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessAndItem;
import io.github.sliverkiss.domain.entity.assess.AssessItem;
import io.github.sliverkiss.domain.entity.assess.AssessSet;
import io.github.sliverkiss.domain.vo.AssessSetVo;
import io.github.sliverkiss.service.AssessSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 绩效考核计划(AssessSet)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-17 16:39:11
 */
@Service("assessSetService")
public class AssessSetServiceImpl extends ServiceImpl<AssessSetDao, AssessSet> implements AssessSetService {

    @Autowired
    private AssessItemDao assessItemDao;
    @Autowired
    private AssessAndItemDao assessAndItemDao;

    @Override
    public ResponseResult selectPage(AssessSetQueryDTO dto) {
        Page<AssessSet> page = toPage ( dto );
        // 用户数据隔离
        Integer userEmpId = dto.getUserEmpId ();
        Integer userRole = dto.getUserRole ();
        String title = dto.getTitle ();
        try {
            // 模糊查询
            LambdaQueryWrapper<AssessSet> wrapper = Wrappers.lambdaQuery ( AssessSet.class )
                    .like ( StringUtils.isNotBlank ( title ), AssessSet::getTitle, title );
            // 数据隔离
            Page<AssessSet> tPage = this.page ( page, wrapper );
            IPage<AssessSetVo> voIPage = EntityUtils.toPage ( tPage, AssessSetVo::new );
            // 属性注入
            this.getAssessItems ( voIPage );
            return ResponseResult.okResult ( voIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    public void getAssessItems(IPage<AssessSetVo> voIPage) {
        voIPage.getRecords ().forEach ( e -> {
            this.getItemsBySet ( e );
        } );
    }

    /**
     * 根据考核计划，获取考核项集合以及考核项id集合，注入AssessSet
     *
     * @param set 集
     */
    public void getItemsBySet(AssessSet set) {
        List<Integer> itemIds = assessAndItemDao.selectList ( Wrappers.lambdaQuery ( AssessAndItem.class )
                .eq ( AssessAndItem::getAssessId, set.getId () ) ).stream ().map ( AssessAndItem::getItemId ).collect ( Collectors.toList () );
        List<AssessItem> items = assessItemDao.selectBatchIds ( itemIds );
        set.setItemIdList ( itemIds );
        set.setItems ( items );
    }

}

