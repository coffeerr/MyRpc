package per.chris.rpc.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/16 3:09 下午
 */

public class Client {
    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    public Object send(Message message, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Err:" + e);
        }
        return null;
    }

    public static void main(String[] args) {
        Client client = new Client();
        Message message = new Message();
        message.setContent("Message from client");
        Message receiveMsg = (Message) client.send(message, "127.0.0.1", 8089);
        System.out.println(receiveMsg.getContent());
    }
}
