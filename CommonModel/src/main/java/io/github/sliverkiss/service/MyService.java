package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.dao.MyMapper;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/6/28
 */
public abstract class MyService<T> implements IService<T> {
    protected MyMapper<T> baseMapper;
    //实现对业务层对缓冲
    public MyService(MyMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }
}
