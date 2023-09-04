package io.github.sliverkiss.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.TreeConstants;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.UserQueryDTO;
import io.github.sliverkiss.dao.RBAC.*;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.RBAC.Permission;
import io.github.sliverkiss.domain.entity.RBAC.RolePermission;
import io.github.sliverkiss.domain.entity.RBAC.User;
import io.github.sliverkiss.domain.entity.RBAC.UserRole;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.domain.vo.UserVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.Comparator;
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
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private EmployeeService employeeService;

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
        // 设置用户绑定员工信息
        if (loginUser.getEmployeeId () != null) {
            EmployeeVo employeeVo = employeeService.getEmployeeVo ( loginUser.getEmployeeId () );
            loginUser.setEmployeeVo ( employeeVo );
        }
        // 从用户权限通过用户id查询所有的资源信息
        this.setPermissionByUser ( loginUser );

        return ResponseResult.okResult ( loginUser );
    }

    @Override
    public ResponseResult selectPage(UserQueryDTO dto) {
        Page<User> page = toPage ( dto );
        String username = dto.getUsername ();
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery ( User.class );
        if (StringUtils.isNotBlank ( username )) {
            wrapper.like ( User::getUsername, username );
        }
        Page<User> aPage = this.page ( page, wrapper );
        IPage<UserVo> voIPage = EntityUtils.toPage ( aPage, UserVo::new );
        // 属性注入
        this.setTableFiled ( voIPage.getRecords () );
        return ResponseResult.okResult ( voIPage );
    }

    /**
     * 注入列表属性
     *
     * @param list 列表
     */
    public <E extends User> void setTableFiled(List<E> list) {
        list.forEach ( user -> {
            if (user.getEmployeeId () != null) {
                EmployeeVo employeeVo = employeeService.getEmployeeVo ( user.getEmployeeId () );
                user.setEmployeeVo ( employeeVo );
            }
            // 从用户权限通过用户id查询所有的资源信息
            this.setPermissionByUser ( user );
        } );

    }

    /**
     * 动态路由
     *
     * @param userId 用户id
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult activityRoute(Integer userId) {
        try {
            User user = this.getById ( userId );
            if (Objects.isNull ( user )) {
                return ResponseResult.errorResult ( AppHttpCodeEnum.FIND_NOT_FOUND );
            }
            // 从用户权限通过用户id查询所有的资源信息
            this.setPermissionByUser ( user );
            return ResponseResult.okResult ( user );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }

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
        // 新建用户
        userDao.insert ( user );
        // 为用户分配角色
        UserRole userRole = new UserRole ();
        userRole.setUserId ( user.getId () ).setRoleId ( user.getRole () );
        userRoleDao.insert ( userRole );

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
        List<Permission> permissionList = permissionDao.selectList ( Wrappers.lambdaQuery ( Permission.class )
                .in ( Permission::getId, permissionIds ) );
        List<Permission> collect = permissionList.stream ()
                .filter ( o -> TreeConstants.TREE_INVITE_TENDER.equals ( o.getParentMenu () ) )
                .peek ( o -> o.setChildren ( getChildList ( o, permissionList ) ) )
                .sorted ( Comparator.comparing ( Permission::getId ) )
                .collect ( Collectors.toList () );
        // 设置用户权限
        loginUser.setPermissions ( collect );
    }

    /**
     * 递归调用获取children生成tree
     *
     * @param permission 许可
     * @param list       列表
     *
     * @return {@link List}<{@link Permission}>
     */
    public List<Permission> getChildList(Permission permission, List<Permission> list) {
        return list.stream ().filter ( o -> permission.getId ().equals ( o.getParentMenu () ) )
                .peek ( o -> o.setChildren ( getChildList ( o, list ) ) ).collect ( Collectors.toList () );
    }

}


