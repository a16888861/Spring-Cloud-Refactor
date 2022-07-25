
package com.peri.fashion.common.util;

import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 队列属性复制
 *
 * @param <T>
 * @author Elliot
 */
@Slf4j
public final class BeanUtil<T extends Serializable> {

    private BeanUtil() {
    }

    public static <T> T copyProperties(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static void copyProperties(Object source, Object target) {
        if (source == null) {
            return;
        }
        BeanUtils.copyProperties(source, target);
    }

    public static <T> List<T> copyProperties(List<?> source, Class<T> clazz) {
        if (source == null || source.size() == 0) {
            return Collections.emptyList();
        }
        List<T> res = new ArrayList<>(source.size());
        for (Object o : source) {
            T t = null;
            try {
                t = clazz.newInstance();
                BeanUtils.copyProperties(o, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
            res.add(t);
        }
        return res;
    }

    public static <T> void createBaseField(T obj, Date now, Long sysUserId, Boolean isUpdate) {
        if (!isUpdate) {
            ReflectUtil.setFieldValue(obj, "createBy", sysUserId);
            ReflectUtil.setFieldValue(obj, "createDate", now);
        }
        ReflectUtil.setFieldValue(obj, "updateBy", sysUserId);
        ReflectUtil.setFieldValue(obj, "updateDate", now);
    }
}
