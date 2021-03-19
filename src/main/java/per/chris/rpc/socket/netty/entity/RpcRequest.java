package per.chris.rpc.socket.netty.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/16 4:48 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcRequest {
    private String interfaceName;
    private String methodName;
}
