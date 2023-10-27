package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.TransferItemDao;
import io.github.sliverkiss.domain.entity.TransferItem;
import io.github.sliverkiss.service.TransferItemService;
import org.springframework.stereotype.Service;

/**
 * 调岗审核子表(TransferItem)表服务实现类
 *
 * @author tistzach
 * @since 2023-09-30 15:17:11
 */
@Service("transferItemService")
public class TransferItemServiceImpl extends ServiceImpl<TransferItemDao, TransferItem> implements TransferItemService {

}

