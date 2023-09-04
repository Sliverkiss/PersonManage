package io.github.sliverkiss.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.controller.DTO.UserQueryDTO;
import io.github.sliverkiss.dao.RBAC.UserRoleDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.RBAC.User;
import io.github.sliverkiss.domain.entity.RBAC.UserRole;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/7
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController extends BaseController<UserServiceImpl, User> {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserRoleDao userRoleDao;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        // 合法行为判断
        if (!StringUtils.hasText ( user.getUsername () )) {
            throw new SystemException ( AppHttpCodeEnum.REQUIRE_USERNAME );
        }
        return service.login ( user );
    }

    @GetMapping("/activityRoute/{userId}")
    public ResponseResult activityRoute(@PathVariable Integer userId) {
        return service.activityRoute ( userId );
    }

    @GetMapping("/page")
    public ResponseResult selectPage(UserQueryDTO dto) {
        return service.selectPage ( dto );
    }

    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody User user) {
        if (user.getEmployeeId () != null) {
            List<User> list = service.list ( Wrappers.lambdaQuery ( User.class ).eq ( User::getEmployeeId, user.getEmployeeId () ) );
            if (list.size () > 0) {
                return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "该员工已被其他用户绑定，请重新输入员工编号～" );
            }
        }
        return service.register ( user );
    }

    @Override
    public void beforeUpdate(User user) {
        UserRole userRole = new UserRole ();
        userRole.setUserId ( user.getId () ).setRoleId ( user.getRole () );
        userRoleDao.update ( userRole, Wrappers.lambdaQuery ( UserRole.class ).eq ( UserRole::getUserId, user.getId () ) );
    }

}
