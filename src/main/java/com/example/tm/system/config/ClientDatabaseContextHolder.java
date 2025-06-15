package com.example.tm.system.config;

import org.springframework.util.Assert;

public class ClientDatabaseContextHolder {

    private static final ThreadLocal<ClientDatabase> CONTEXT = new ThreadLocal<>();

    public static void set(ClientDatabase clientDatabase) {
        Assert.notNull(clientDatabase, "clientDatabase cannot be null");
        CONTEXT.set(clientDatabase);
    }

    public static ClientDatabase getClientDatabase() {
        return ClientDatabase.t1;
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
