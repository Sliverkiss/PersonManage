package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.controller.DTO.PostQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Post;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.impl.PostServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/9/4
 */
@Slf4j
@RestController
@RequestMapping("/admin/department/post")
public class PostController extends BaseController<PostServiceImpl, Post> {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 获取岗位列表
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult list() {
        return ResponseResult.okResult ( service.list () );
    }

    @GetMapping("/page")
    public ResponseResult selectPage(PostQueryDTO dto) {
        return service.selectPage ( dto );
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        Post post = service.getById ( id );
        List<Employee> employeeList = employeeService.list ( Wrappers.lambdaQuery ( Employee.class ).eq ( Employee::getPost, post.getName () ) );
        if (employeeList.size () > 0) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "该记录已被使用，无法删除～" );
        }
        return service.deleteEntity ( id );
    }

}
