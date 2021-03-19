package com.myrpc.common.exception;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/18 3:20 下午
 */

public class SerializeException extends RuntimeException {
    public SerializeException(String message) {
        super(message);
    }
}
