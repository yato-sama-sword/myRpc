package edu.neu.rpc;

import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author yato
 */
@Slf4j
public class ProxyHandler implements InvocationHandler {

    private static final String HELLO_METHOD = "sayHello";
    private final String ip;
    private final int port;

    public ProxyHandler(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * 动态代理方法，主要目的就是给接口生成动态代理类，可以像调用本地方法一样调用远程方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //创建socket对象，连接目标服务器，用来进行数据传输
        try(Socket socket = new Socket(ip, port)) {
            //获取客户端调用的接口
            Class<?> callInterface = proxy.getClass().getInterfaces()[0];
            //获取客户端调用的方法名
            String name = method.getName();
            //获取该方法的参数类型列表，因为方法可以重载，如果没有类型列表的话，就无法确定到底调用的是哪个方法
            Class<?>[] types = method.getParameterTypes();
            if (HELLO_METHOD.equals(name)) {
                log.info("Hello");
            }
            //输出流是向服务端传输数据使用的
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //输入流是从服务端读取数据使用的
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(callInterface);
            out.writeUTF(name);
            out.writeObject(types);
            //args是参数值
            out.writeObject(args);
            //主动刷新流数据，把数据推送到服务端
            out.flush();
            //接收服务端返回的对象
            Object object = in.readObject();
            //判断返回的对象是否是正常对象
            if (object instanceof Throwable) {
                throw (Throwable) object;
            }
            socket.shutdownOutput();
            return object;
        } catch (SocketException se) {
            throw new RpcException("get socket failed", se);
        }
    }
}

