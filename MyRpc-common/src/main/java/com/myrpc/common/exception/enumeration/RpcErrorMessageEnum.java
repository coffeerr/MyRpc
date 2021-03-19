package com.myrpc.common.exception.enumeration;

import lombok.*;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/18 3:22 下午
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcErrorMessageEnum {
    SERVICE_INVOCATION_FAILEURE("服务调用失败"),
    SERVICE_CAN_NOT_BE_FOUNT("服务未找到"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("服务未被接口实现");
    private String message;

    RpcErrorMessageEnum(String message) {
    }
}
