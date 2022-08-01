package edu.neu.rpc;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author yato
 */
@Slf4j
 public class RpcProvider {

    private static final int PORT = 7896;

    public void start() throws Exception{
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("服务端开启成功");
            //死循环，让程序一直运行，持续监听客户端的请求
            int loop = Integer.MAX_VALUE;
            while (loop-- > 0){
                //accept方法会阻塞，知道有请求过来为止
                Socket socket = serverSocket.accept();
                log.info("收到客户端请求");
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                //注意！！！服务端读取数据的顺序，一定要与客户端写入的顺序一致
                Class<?> callInterface = (Class<?>)in.readObject();
                String name = in.readUTF();
                Class<?>[] types = (Class<?>[]) in.readObject();
                Object[] args = (Object[]) in.readObject();
                // 测试能否读取到接口和方法信息
                log.info(callInterface.getName());
                log.info(name);
                if (args != null) {
                    for (Class<?> type: types) {
                        log.info(type.getName());
                    }
                    log.info(JSONUtil.toJsonStr(args));
                } else {
                    log.info("方法无参数");
                }
                out.writeObject("ok");
                out.flush();
                in.close();
                out.close();
                // 不需要socket.close(); 会自动进行
            }
        } catch (SocketException se) {
            throw new RpcException("ServerSocket init failed", se);
        }
    }


    public static void main(String[] args) throws Exception {
        RpcProvider provider = new RpcProvider();
        provider.start();
    }
}