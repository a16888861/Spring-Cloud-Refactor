package com.peri.fashion.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 日志实体
 *
 * @author Elliot
 * @date 2021/09/19 10:50 下午
 */
@Data
@ToString
@NoArgsConstructor
@TableName("sys_log")
@Accessors(chain = true)
public class SysLogEntity {

    /**
     * 表ID - 标识符(自增方式)
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * ip
     */
    private String ip;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 操作方法
     */
    private String method;
    /**
     * 操作描述
     */
    private String operation;
    /**
     * 耗时（毫秒）
     */
    private Long takeUpTime;
    /**
     * 参数
     */
    private String params;
    /**
     * 年份
     */
    private String year;
    /**
     * 操作者
     */
    private Integer createBy;
    /**
     * 操作日期
     */
    private LocalDateTime createDate;
}
