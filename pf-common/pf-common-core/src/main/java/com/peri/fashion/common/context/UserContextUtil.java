package com.peri.fashion.common.context;

import com.peri.fashion.common.constant.CommonConstants;
import com.peri.fashion.common.dto.SysUserDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局使用的线程工具
 *
 * @author Elliot
 */
public class UserContextUtil {

    public static ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 向线程当中存储信息
     *
     * @param key   key
     * @param value value
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(5);
            THREAD_LOCAL.set(map);
        }
        map.put(key, value);
    }

    /**
     * 根据key获取线程中的信息
     *
     * @param key key
     * @return value
     */
    public static Object get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(5);
            THREAD_LOCAL.set(map);
        }
        return map.get(key);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static SysUserDTO getUserInfo() {
        return (SysUserDTO) get(CommonConstants.USER_INFO_KEY);
    }

    /**
     * 设置用户信息
     *
     * @param userDTO 用户信息
     */
    public static void setUserInfo(SysUserDTO userDTO) {
        set(CommonConstants.USER_INFO_KEY, userDTO);
    }

    /**
     * 手动回收线程
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}