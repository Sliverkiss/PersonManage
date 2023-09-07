package io.github.sliverkiss.controller;

import io.github.sliverkiss.controller.DTO.PostQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Post;
import io.github.sliverkiss.service.impl.PostServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/9/4
 */
@Slf4j
@RestController
@RequestMapping("/admin/department/post")
public class PostController extends BaseController<PostServiceImpl, Post> {

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
}
