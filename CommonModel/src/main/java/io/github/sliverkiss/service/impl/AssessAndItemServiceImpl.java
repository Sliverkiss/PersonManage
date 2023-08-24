package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.AssessAndItemDao;
import io.github.sliverkiss.domain.entity.assess.AssessAndItem;
import io.github.sliverkiss.service.AssessAndItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 考核计划中间表(AssessAndItem)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-17 16:40:30
 */
@Service("assessAndItemService")
public class AssessAndItemServiceImpl extends ServiceImpl<AssessAndItemDao, AssessAndItem> implements AssessAndItemService {

    @Autowired
    private AssessAndItemDao assessAndItemDao;

    @Override
    public void deleteBatchIds(List<Integer> itemIdList) {
        assessAndItemDao.deleteBatchIds ( itemIdList );
    }
}

