package edu.neu.rpc;


import lombok.extern.slf4j.Slf4j;

/**
 * @author yato
 */
@Slf4j
public class RpcConsumer {
    public static void main(String[] args) {
        HelloService helloService = RpcClient.getService(HelloService.class, "127.0.0.1", 7896);
        String result = helloService.sayHello();
        log.info(result);
    }
}
