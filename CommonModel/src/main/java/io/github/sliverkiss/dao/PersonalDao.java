package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Personal;
import org.apache.ibatis.annotations.Mapper;

/**
 * 人员信息表(Personal)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-05 11:45:33
 */
@Mapper
public interface PersonalDao extends BaseMapper<Personal> {

}

