package com.dazzlzy.springbootseed.model.user.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
@Data
public class UserDTO {
    /**
     * ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 启用状态
     */
    private Integer stateCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * Email
     */
    private String email;

    /**
     * 座机
     */
    private String phone;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 角色列表
     */
    private List<RoleDTO> roles;

    /**
     * 权限列表
     */
    private List<PermissionDTO> permissions;

}