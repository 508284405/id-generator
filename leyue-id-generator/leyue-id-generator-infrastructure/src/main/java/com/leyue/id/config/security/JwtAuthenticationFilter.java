package com.leyue.id.config.security;

import com.leyue.id.config.security.context.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JWT 认证过滤器
 * 用于验证请求中的 JWT Token
 *
 * @author Trae
 * @since 2024-01-20
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Resource
    private JwtConfig jwtConfig;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws ServletException, IOException {
        try {
            // 获取 Authorization 头
            String authHeader = request.getHeader("Authorization");
            
            // 如果没有 Authorization 头或者不是以 Bearer 开头，无权限，报错
            if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("无权限");
            }
            
            // 提取 Token
            String token = authHeader.substring(7);
            
            // 验证 Token
            Claims claims = validateTokenAndGetClaims(token);
            
            // 从 Token 中获取用户 ID
            Long userId = Long.parseLong(claims.getSubject());
            
            // 从 Token 中获取用户名
            String username = (String) claims.get("username");
            
            // 从 Token 中获取角色列表
            List<?> roles = (List<?>) claims.get("roles");
            Set<String> roleNames = new HashSet<>();
            
            // 构建授权信息
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            
            if (roles != null && !roles.isEmpty()) {
                for (Object role : roles) {
                    if (role instanceof Map) {
                        String roleName = (String) ((Map<?, ?>) role).get("name");
                        if (StringUtils.isNotBlank(roleName)) {
                            roleNames.add(roleName);
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
                        }
                    }
                }
            }
            
            // 从 Token 中获取菜单列表
            List<Map<String, Object>> menus = (List<Map<String, Object>>) claims.get("menus");
            
            // 设置用户上下文
            UserContext.setCurrentUser(UserContext.UserInfo.builder()
                    .userId(userId)
                    .username(username)
                    .roles(roleNames)
                    .menus(menus)
                    .build());
            
            // 设置 Spring Security 上下文
            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 继续过滤器链
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("JWT认证失败", e);
            // 清除上下文
            UserContext.clear();
            SecurityContextHolder.clearContext();
            // 继续过滤器链
            chain.doFilter(request, response);
        } finally {
            // 请求完成后清除ThreadLocal
            UserContext.clear();
        }
    }
    
    /**
     * 验证Token并获取Claims
     */
    private Claims validateTokenAndGetClaims(String token) {
        PublicKey publicKey = getPublicKey();
        
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .setAllowedClockSkewSeconds(jwtConfig.getClockSkew())
                .build()
                .parseClaimsJws(token);
        
        return claimsJws.getBody();
    }
    
    /**
     * 获取RSA公钥
     */
    private PublicKey getPublicKey() {
        try {
            String publicKeyPEM = jwtConfig.getPublicKey()
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("无法加载RSA公钥", e);
        }
    }
} 