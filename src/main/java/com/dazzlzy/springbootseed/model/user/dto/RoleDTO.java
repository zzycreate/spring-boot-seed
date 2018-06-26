package com.dazzlzy.springbootseed.model.user.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
@Data
public class RoleDTO {
    /**
     * 角色ID
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
     * 角色名
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色拥有的权限
     */
    private List<PermissionDTO> permissions;

}