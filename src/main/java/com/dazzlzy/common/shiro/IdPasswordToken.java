package com.dazzlzy.common.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @author zhaozhenyao
 * @date 2018/5/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdPasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    /**
     * 用户ID
     */
    private String id;
    /**
     * 用户密码
     */
    private char[] password;
    /**
     * 是否记住
     */
    private boolean rememberMe;
    /**
     * IP
     */
    private String host;

    public IdPasswordToken(String id, String password, boolean rememberMe, String host) {
        this.id = id;
        this.password = password == null ? null : password.toCharArray();
        this.rememberMe = rememberMe;
        this.host = host;
    }

    /**
     * 获取身份
     *
     * @return 身份
     */
    @Override
    public Object getPrincipal() {
        return null;
    }

    /**
     * 获取凭证
     *
     * @return 凭证
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 清除信息
     */
    public void clear() {
        this.id = null;
        this.host = null;
        this.rememberMe = false;
        if (this.password != null) {
            for (int i = 0; i < this.password.length; ++i) {
                this.password[i] = '\0';
            }
            this.password = null;
        }
    }

    @Override
    public String toString() {
        return "IdPasswordToken{" +
                "id='" + id + '\'' +
                ", rememberMe=" + rememberMe +
                ", host='" + host + '\'' +
                '}';
    }
}
