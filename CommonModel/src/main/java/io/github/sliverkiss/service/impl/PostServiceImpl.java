package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.controller.DTO.PostQueryDTO;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.PostDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Post;
import io.github.sliverkiss.domain.vo.PostVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Optional;

/**
 * 岗位信息(Post)表服务实现类
 *
 * @author tistzach
 * @since 2023-09-04 19:59:36
 */
@Service("postService")
public class PostServiceImpl extends ServiceImpl<PostDao, Post> implements PostService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public ResponseResult selectPage(PostQueryDTO dto) {
        Page<Post> page = toPage ( dto );
        String name = dto.getName ();
        String departmentId = dto.getDepartmentId ();
        try {
            // 对续约表 进行模糊查询
            LambdaQueryWrapper<Post> wrapper = Wrappers.lambdaQuery ( Post.class );
            // 模糊查询岗位信息
            if (StringUtils.isNotBlank ( name )) {
                wrapper.like ( Post::getName, name );
            }
            // 查询部门
            if (StringUtils.isNotBlank ( departmentId )) {
                wrapper.like ( Post::getDepartmentId, departmentId );
            }
            // 将模糊查询结果进行分页
            Page<Post> resPage = this.page ( page, wrapper );
            IPage<PostVo> voIPage = EntityUtils.toPage ( resPage, PostVo::new );
            this.setTaleFiled ( voIPage.getRecords () );
            // 多表联查
            return ResponseResult.okResult ( voIPage );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
        }
    }

    /**
     * 注入所属部门属性
     *
     * @param list
     */
    public <T extends Post> void setTaleFiled(List<T> list) {
        list.forEach ( e -> {
            Department department = departmentDao.selectById ( e.getDepartmentId () );
            // 注入部门属性
            Optional.ofNullable ( department ).ifPresent ( item -> e.setDepartment ( item ) );
        } );
    }
}

