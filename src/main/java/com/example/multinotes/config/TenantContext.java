package com.example.multinotes.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TenantContext {

    private static final ThreadLocal<UUID> currentTenant = new ThreadLocal<>();

    public static void setTenantId(UUID tenantId) {
        currentTenant.set(tenantId);
    }

    public static UUID getTenantId() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }

}