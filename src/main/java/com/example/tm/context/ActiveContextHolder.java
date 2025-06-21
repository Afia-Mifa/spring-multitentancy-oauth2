package com.example.tm.context;

import com.example.tm.model.AuthenticatedUser;
import com.example.tm.system.config.ClientDatabase;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActiveContextHolder {

    private static final String AUTH_USER = "auth_user";
    private static final String TENANT = "tenant";

    private static final ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<>();

    private static Map<String, Object> getContext() {
        Map<String, Object> context = CONTEXT.get();
        if (Objects.isNull(context)) {
            context = new HashMap<>();
            CONTEXT.set(context);
        }
        return context;
    }

    public static void remove() {
        getContext().clear();
        CONTEXT.remove();
    }

    public static void setContext(AuthenticatedUser authenticatedUser) {
        setAuthUser(authenticatedUser);
        setClientDatabase(authenticatedUser.clientDatabase());
    }

    public static void setAuthUser(AuthenticatedUser authenticatedUser) {
        Assert.notNull(authenticatedUser, "Auth user cannot be null");
        getContext().put(AUTH_USER, authenticatedUser);
    }

    public static AuthenticatedUser getAuthUser() {
        return (AuthenticatedUser) getContext().get(AUTH_USER);
    }

    public static void setClientDatabase(ClientDatabase clientDatabase) {
        Assert.notNull(clientDatabase, "Client database cannot be null");
        getContext().put(TENANT, clientDatabase);
    }

    public static ClientDatabase getClientDatabase() {
        return (ClientDatabase) getContext().get(TENANT);
    }
}
