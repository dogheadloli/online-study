package com.rui.common.constants;

import lombok.Getter;

/**
 * @Author: ChangRui
 * @Date: 2020/4/8
 * @Description:
 */
@Getter
public enum UserRolesEnum {

    ADMIN(0, "管理员");

    private Integer code;
    private String roles;

    UserRolesEnum(Integer code, String roles) {
        this.code = code;
        this.roles = roles;
    }

    public static String getRoles(Integer code) {
        for (UserRolesEnum userRolesEnum : values()) {
            if (userRolesEnum.getCode().equals(code)) {
                return userRolesEnum.getRoles();
            }
        }
        return null;
    }

}
