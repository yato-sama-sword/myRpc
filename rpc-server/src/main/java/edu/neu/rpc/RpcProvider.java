package edu.neu.rpc;

import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yato
 */
@Slf4j
 public class RpcProvider {

    private int port = 8080;

    public void start() throws Exception{
        ServerSocket serverSocket = new ServerSocket(port);
        log.info("服务端开启成功");

        //死循环，让程序一直运行，持续监听客户端的请求
        while (true){
            //accept方法会阻塞，知道有请求过来为止
            Socket socket = serverSocket.accept();
            log.info("收到客户端请求");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            //注意！！！服务端读取数据的顺序，一定要与客户端写入的顺序一致
            Class callInterface = (Class)in.readObject();
            String name = in.readUTF();
            Class[] types = (Class[]) in.readObject();
            Object[] args = (Object[]) in.readObject();

            out.writeObject("ok");
            out.flush();

            out.close();
            in.close();
            socket.close();
        }
    }


    public static void main(String[] args) throws Exception {
        RpcProvider provider = new RpcProvider();
        provider.start();
    }
}