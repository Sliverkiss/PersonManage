package io.github.sliverkiss.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/9
 */

public class CheckUtil {

    public static boolean checkStr(String str, List list) {
        return StringUtils.isNotBlank ( str ) && ObjectUtils.isNull ( list );
    }

    public static boolean checkInt(Integer num, List list){
            return num != null && ObjectUtils.isNull ( list );
    }

    public static boolean checkObj(Integer num, Object obj){
        return  num != null && ObjectUtils.isNull ( obj );
    }

}
