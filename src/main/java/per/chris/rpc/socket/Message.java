package per.chris.rpc.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/16 3:21 下午
 */
@Data
@NoArgsConstructor
public class Message implements Serializable {
    private String Content;
}
