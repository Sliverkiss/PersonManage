package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Renewal;
import org.apache.ibatis.annotations.Mapper;

/**
 * 续约申请表(Renewal)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-07 11:44:19
 */
@Mapper
public interface RenewalDao extends BaseMapper<Renewal> {

}

