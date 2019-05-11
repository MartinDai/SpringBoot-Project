package com.doodl6.springboot.common.check;

import com.doodl6.springboot.common.check.annotation.FieldNotEmpty;
import com.doodl6.springboot.common.check.annotation.FieldNotNull;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 校验工具类
 */
public class CheckUtil {

    private static Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    /**
     * 校验对象
     */
    public static void check(Object object) {
        Preconditions.checkArgument(object != null, "对象不能为空");

        Class clazz = object.getClass();
        List<Field> fieldList = Lists.newArrayList();
        do {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);

        for (Field field : fieldList) {
            checkField(object, field);
        }
    }

    /**
     * 校验单个字段
     */
    private static void checkField(Object object, Field field) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(FieldNotNull.class)) {
            checkFieldNotNull(object, field);
        } else if (field.isAnnotationPresent(FieldNotEmpty.class)) {
            checkFieldNotEmpty(object, field);
        }
    }

    /**
     * 获取字段值
     */
    private static Object getFieldValue(Object object, Field field) {
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("获取字段值异常", e);
        }

        return value;
    }

    /**
     * 检查字段非空值
     */
    private static void checkFieldNotNull(Object object, Field field) {
        FieldNotNull fieldNotNull = field.getAnnotation(FieldNotNull.class);
        String fieldName = StringUtils.defaultIfEmpty(fieldNotNull.name(), field.getName());
        Object value = getFieldValue(object, field);
        Preconditions.checkArgument(value != null, "字段【" + fieldName + "】不允许为空");
    }

    /**
     * 检查字段非空(包括长度)
     */
    private static void checkFieldNotEmpty(Object object, Field field) {
        FieldNotEmpty fieldNotEmpty = field.getAnnotation(FieldNotEmpty.class);
        String fieldName = StringUtils.defaultIfEmpty(fieldNotEmpty.name(), field.getName());
        Object value = getFieldValue(object, field);
        Preconditions.checkArgument(value != null, "字段【" + fieldName + "】不允许为空");

        int length;
        if (object instanceof String) {
            length = ((String) value).trim().length();
        } else if (object instanceof List<?>) {
            length = ((List<?>) value).size();
        } else if (object.getClass().isArray()) {
            length = ((Object[]) value).length;
        } else {
            return;
        }

        int minLength = fieldNotEmpty.minLength();
        Preconditions.checkArgument(length > 0, "字段【" + fieldName + "】不允许为空");
        if (minLength > 0) {
            Preconditions.checkArgument(length >= minLength, "字段【" + fieldName + "】长度不能少于" + minLength);
        }

        int maxLength = fieldNotEmpty.maxLength();
        if (maxLength > 0) {
            Preconditions.checkArgument(length <= maxLength, "字段【" + fieldName + "】长度不能大于" + maxLength);
        }
    }

}
