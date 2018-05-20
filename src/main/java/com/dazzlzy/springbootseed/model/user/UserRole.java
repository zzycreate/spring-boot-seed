package com.dazzlzy.springbootseed.model.user;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

/**
 * 用户角色关系
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
@Data
@Table(name = "sys_user_role")
public class UserRole {
    /**
     * 用户角色关系ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 插入时间
     */
    @Column(name = "insert_time")
    private Date insertTime;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

}