package com.doodl6.springboot.common.check.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldNotNull {

    String name() default "";

}
