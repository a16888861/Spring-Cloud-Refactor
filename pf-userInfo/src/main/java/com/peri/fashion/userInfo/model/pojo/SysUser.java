package com.peri.fashion.userInfo.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统 - 用户表实体
 *
 * @author Elliot
 */
@Data
@ToString
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID - 标识符(自增方式)
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 年份
     */
    private String year;

    /**
     * 用户状态(0审批 1正常 2锁定)
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 删除状态(1正常 0删除)
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

}
