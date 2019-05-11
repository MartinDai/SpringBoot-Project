package com.doodl6.springboot.common.check.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldNotEmpty {

    int minLength() default -1;

    int maxLength() default -1;

    String name() default "fieldName";

}
