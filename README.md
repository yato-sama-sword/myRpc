# RPC (Remote Procedure Call Protocol)

RPC，远程过程调用协议，可以像调用本地服务一样调用远端服务

远程过程调用，通常由服务端、客户端、通信网络三部分组成

RPC协议的核心：通信协议、编码协议、序列化格式

# 手动模拟一个rpc框架

### rpc-server

服务端模块，用来提供服务

### rpc-client

客户端模块，用来调用服务

### rpc-common

公共模块，用来给接口提供动态代理类

### rpc-business

用来定义client和server之间交互的接口

## 数据交互流程

主要是rpc-client和rpc-server之间的数据交互，client调用business里提供的接口，server实现了business里的接口。client和server之间使用socket进行数据传输

