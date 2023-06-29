package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户(User)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:57:36
 */
@Mapper
public interface UserDao extends MyMapper<User> {

}

