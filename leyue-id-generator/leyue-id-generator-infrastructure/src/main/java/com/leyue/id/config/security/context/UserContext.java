package com.leyue.id.config.security.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户上下文，用于存储当前用户信息
 *
 * @author Trae
 * @since 2024-01-20
 */
public class UserContext {
    
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();
    
    /**
     * 设置当前用户信息
     *
     * @param userInfo 用户信息
     */
    public static void setCurrentUser(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }
    
    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    public static UserInfo getCurrentUser() {
        return userInfoThreadLocal.get();
    }
    
    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        UserInfo userInfo = getCurrentUser();
        return userInfo != null ? userInfo.getUserId() : null;
    }
    
    /**
     * 获取当前用户名
     *
     * @return 用户名
     */
    public static String getCurrentUsername() {
        UserInfo userInfo = getCurrentUser();
        return userInfo != null ? userInfo.getUsername() : null;
    }
    
    /**
     * 清除当前用户信息
     */
    public static void clear() {
        userInfoThreadLocal.remove();
    }
    
    /**
     * 用户信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        /**
         * 用户ID
         */
        private Long userId;
        
        /**
         * 用户名
         */
        private String username;
        
        /**
         * 角色列表
         */
        private Set<String> roles;
        
        /**
         * 菜单列表
         */
        private List<Map<String, Object>> menus;
    }
} 