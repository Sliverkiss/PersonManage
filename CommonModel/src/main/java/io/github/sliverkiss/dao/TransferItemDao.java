package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.TransferItem;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cache.decorators.ScheduledCache;

import java.util.List;

/**
 * 调岗审核子表(TransferItem)表数据库访问层
 *
 * @author tistzach
 * @since 2023-09-30 15:17:11
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface TransferItemDao extends ICrudDao<TransferItem> {

    @Select("select * from hros.transfer_item where transfer_id=#{transferId} and del_flag=0 order by approve_type asc")
    public List<TransferItem> getItemsByTransferId(Integer transferId);

}

