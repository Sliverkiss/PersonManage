package io.github.sliverkiss.utils;


import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * bean复制工具类
 *
 * @author 谭礼赞 2038940123
 * @date 2023/01/05
 * @since 2023 -01-05-2023/1/5
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {

    }

    /**
     * 复制bean
     *
     * @param source the source
     * @param clazz  the clazz
     * @return the v
     */
    @NotNull
    @SneakyThrows
    public static <V> V copyBean(Object source, Class<V> clazz) {
        //创建目标对象
        V result = clazz.newInstance ();
        //实现属性拷贝
        BeanUtils.copyProperties ( source, result );
        //返回结果
        return result;
    }

    /**
     * 复制bean列表
     * Copy bean list list.
     *
     * @param list  the list
     * @param clazz the clazz
     *
     * @return the list
     */
    public static <O, V> List<V> copyBeanList(@NotNull List<O> list, Class<V> clazz) {
        return list.stream ()
                .map ( o -> copyBean ( o, clazz ) )
                .collect ( Collectors.toList () );
    }


}
