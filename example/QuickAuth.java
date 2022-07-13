package demo.demo.aop;

import java.lang.annotation.*;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 标记注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface QuickAuth {
}
