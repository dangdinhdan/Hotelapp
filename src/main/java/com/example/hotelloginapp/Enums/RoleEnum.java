package com.example.hotelloginapp.Enums;

public enum RoleEnum {
    QUAN_LY("manager"),
    NHAN_VIEN("employee"),;

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
