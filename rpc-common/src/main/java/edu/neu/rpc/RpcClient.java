package edu.neu.rpc;

import java.lang.reflect.Proxy;

/**
 * @author yato
 */
public class RpcClient {

    private RpcClient() {

    }

    /**
     *  获取接口的代理类对象
     */
    public static <T> T getService(Class<T> clazz, String ip, int port){
        ProxyHandler handler = new ProxyHandler(ip, port);
        Object obj = Proxy.newProxyInstance(RpcClient.class.getClassLoader(), new Class<?>[]{clazz}, handler);
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        throw new RpcException("can not cast" + obj.getClass() + "to: " +clazz.getName());
    }
}

