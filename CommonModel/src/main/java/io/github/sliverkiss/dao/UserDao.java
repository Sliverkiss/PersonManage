package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户(User)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-03 13:12:51
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}

