package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.BaseEntity;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */
public interface ICrudDao<T extends BaseEntity> extends BaseMapper<T> {

}
