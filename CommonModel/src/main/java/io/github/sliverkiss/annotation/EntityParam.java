package io.github.sliverkiss.annotation;

import java.lang.annotation.*;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface EntityParam {
}
