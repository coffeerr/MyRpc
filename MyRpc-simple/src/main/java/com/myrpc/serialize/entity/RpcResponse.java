package com.myrpc.serialize.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/16 4:49 下午
 */
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RpcResponse {
    private String message;
}
