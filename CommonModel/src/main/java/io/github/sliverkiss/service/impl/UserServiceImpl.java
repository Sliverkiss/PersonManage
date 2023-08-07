package io.github.sliverkiss.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.dao.RBAC.PermissionDao;
import io.github.sliverkiss.dao.RBAC.RoleDao;
import io.github.sliverkiss.dao.RBAC.RolePermissionDao;
import io.github.sliverkiss.dao.RBAC.UserDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.RBAC.Permission;
import io.github.sliverkiss.domain.entity.RBAC.RolePermission;
import io.github.sliverkiss.domain.entity.RBAC.User;
import io.github.sliverkiss.domain.entity.RBAC.UserRole;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户(User)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-03 13:12:53
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionDao permissionDao;

    /**
     * 登录
     *
     * @param user 用户
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult login(User user) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery ( User.class )
                .eq ( User::getUsername, user.getUsername () )
                .eq ( User::getPassword, user.getPassword () );
        User loginUser = this.getOne ( wrapper );
        // 用户不存在，返回报错信息
        if (Objects.isNull ( loginUser )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.LOGIN_ERROR );
        }
        // 从用户权限通过用户id查询所有的资源信息
        this.setPermissionByUser ( loginUser );

        return ResponseResult.okResult ( loginUser );
    }

    /**
     * 注册
     *
     * @param user 用户
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult register(User user) {
        if (StringUtils.isBlank ( user.getPassword () )) {
            user.setPassword ( UserContants.DEFAULT_PASS );
        }
        // user.setPassword ( SecureUtil.md5(user.getPassword()) )
        this.save ( user );
        return ResponseResult.okResult ();
    }

    /**
     * 设置权限用户
     *
     * @param loginUser 登录用户
     */
    public void setPermissionByUser(User loginUser) {
        // 从用户权限通过用户id查询所有的资源信息
        Integer userId = loginUser.getId ();
        List<Integer> roleIds = roleDao.getUserRoleByUserId ( userId ).stream ().map ( UserRole::getRoleId ).collect ( Collectors.toList () );
        List<Integer> permissionIds = rolePermissionDao.selectList ( Wrappers.lambdaQuery ( RolePermission.class )
                .in ( RolePermission::getRoleId, roleIds ) ).stream ().map ( RolePermission::getPermissionId ).collect ( Collectors.toList () );
        List<Permission> permissionList = permissionDao.selectList ( Wrappers.lambdaQuery ( Permission.class ).in ( Permission::getId, permissionIds ) );
        // 资源去重
        HashSet<Permission> permissionSet = new HashSet<> ( permissionList );
        // 设置用户权限
        loginUser.setPermissions ( permissionSet );
    }
}


