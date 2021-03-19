package com.myrpc.common.exception.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseCode {
    SUCCESS(200, "调用方法成功"),
    FAILED(500, "调用方法失败"),
    NOT_FOUND_METHOD(500, "未找到指定方法"),
    NOT_FOUND_CLASS(500, "未找到指定类");
    private int code;
    private String msg;


    RpcResponseCode(int i, String msg) {
    }
}
