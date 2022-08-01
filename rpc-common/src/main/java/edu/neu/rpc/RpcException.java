package edu.neu.rpc;

/**
 * @author yato
 */
public class RpcException extends RuntimeException{

    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
}
