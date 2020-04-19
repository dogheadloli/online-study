package com.rui.common.vo;

import com.baomidou.mybatisplus.extension.api.R;
import com.rui.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ChangRui
 * @Date: 2020/4/1
 * @Description:
 */
@Data
@ApiModel("全局统一返回结果")
public class Result {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private Result() {
    }

    public static Result ok() {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }

    public static Result ok(Map<String, Object> data) {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static Result ok(Map<String, Object> data, Integer code, String message) {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        result.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        result.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return result;
    }

    public static Result error(Integer code, String message) {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
