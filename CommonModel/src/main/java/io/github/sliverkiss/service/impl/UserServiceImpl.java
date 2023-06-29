package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.UserDao;
import io.github.sliverkiss.domain.entity.User;
import io.github.sliverkiss.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户(User)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:57:36
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

