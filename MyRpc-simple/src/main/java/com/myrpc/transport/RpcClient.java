package com.myrpc.transport;

import com.myrpc.serialize.entity.RpcRequest;

public interface RpcClient {
    Object sendRpcRequest(RpcRequest rpcRequest);
}
