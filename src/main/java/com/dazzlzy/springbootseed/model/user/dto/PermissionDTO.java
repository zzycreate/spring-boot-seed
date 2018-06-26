package com.dazzlzy.springbootseed.model.user.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
@Data
public class PermissionDTO {
    /**
     * 权限ID
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
     * 权限名
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限描述
     */
    private String description;

}