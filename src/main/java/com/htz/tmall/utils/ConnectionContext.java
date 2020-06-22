package com.htz.tmall.utils;

import java.sql.Connection;

public class ConnectionContext {
    private static ConnectionContext Instance = new ConnectionContext();
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    private ConnectionContext(){
    }

    public static ConnectionContext getInstance(){
        return Instance;
    }

    public ThreadLocal<Connection> getConnectionThreadLocal() {
        return connectionThreadLocal;
    }

    public void setConnectionThreadLocal(ThreadLocal<Connection> connectionThreadLocal) {
        this.connectionThreadLocal = connectionThreadLocal;
    }

    public void bind(Connection connection){
        connectionThreadLocal.set(connection);
    }

    public void remove(){
        connectionThreadLocal.remove();
    }

    public Connection get(){
        return connectionThreadLocal.get();
    }
}
