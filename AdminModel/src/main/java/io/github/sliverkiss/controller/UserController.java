package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.entity.User;
import io.github.sliverkiss.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/6/27
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @GetMapping("/test/{id}")
    public User getUserById(@PathVariable Integer id){
       return userService.getById (id);
    }
}
