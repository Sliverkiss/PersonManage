package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * dao层缓冲接口映射器
 *
 * @author SliverKiss
 * @apiNote 提供一个缓冲的父Mapper类MyMapper<T>，用于提供MP框架提供的BaseMapper<> 这个类没有自己想要且多个子类都有的方法
 * @date 2023/6/28
 */
public interface MyMapper<T> extends BaseMapper<T> {
   //这个方法本身并没有意义，只是做个演示
    T abc(T t);
}
