package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.PostQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Post;

import java.util.List;

/**
 * 岗位信息(Post)表服务接口
 *
 * @author tistzach
 * @since 2023-09-04 19:59:36
 */
public interface PostService extends ICrudService<Post> {

    ResponseResult selectPage(PostQueryDTO dto);

    public List<Employee> getEmpByPost(String post);
}

