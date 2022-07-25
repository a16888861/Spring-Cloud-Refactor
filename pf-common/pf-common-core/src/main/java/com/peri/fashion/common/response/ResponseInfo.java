package com.peri.fashion.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 通用的响应
 *
 * @author Elliot
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("响应信息实体")
public class ResponseInfo<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码", name = "statusCode")
    private int statusCode;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "响应信息", name = "message", position = 1)
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据", name = "data", position = 2)
    private T data;

    /**
     * 自定义返回信息
     *
     * @param code    状态码
     * @param message 返回信息
     */
    public ResponseInfo(int code, String message) {
        // 这里利用了构造方法
        this(code, message, null);
    }
}