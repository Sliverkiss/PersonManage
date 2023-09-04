package io.github.sliverkiss.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/26
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataFilter {

    /** 表的别名 */
    String tableAlias() default "";

    /** true：没有本部门数据权限，也能查询本人数据 */
    boolean user() default true;

    /** 用户ID */
    String employeeId() default "employee_id";
}
