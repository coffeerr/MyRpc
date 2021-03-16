package per.chris.rpc.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: Desmand
 * @time: 2021/3/16 3:07 下午
 */

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("server connected!");
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());) {
                    Message message = (Message) objectInputStream.readObject();
                    logger.info("server receive message :" + message.getContent());
                    message.setContent("content");
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();

                } catch (Exception e) {
                    logger.error("err:" + e);
                }
            }
        } catch (Exception e) {
            logger.error("err:" + e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8089);
    }

}
