package edu.neu.rpc;

import java.lang.reflect.Proxy;

/**
 * @author yato
 */
public class RpcClient {
    /**
     *  获取接口的代理类对象
     */
    public static <T> T getService(Class<T> clazz, String ip, int port){
        ProxyHandler handler = new ProxyHandler(ip, port);
        Object o = Proxy.newProxyInstance(RpcClient.class.getClassLoader(),
                new Class<?>[]{clazz}, handler);
        return (T)o;
    }
}

