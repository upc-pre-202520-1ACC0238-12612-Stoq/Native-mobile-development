package com.stoq.StockWise.shared.infrastructure.network

/**
 * Configuration for API endpoints used across all bounded contexts.
 * This belongs to Shared Kernel as it's a common concern for all contexts.
 */
object ApiConfig {
    const val BASE_URL = "http://34.39.181.148:8080"
    const val API_VERSION = "api"

    // Token de autenticación temporal
    const val AUTH_TOKEN = "eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NjA1NzYxODIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6IjEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiS2V2aW4iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJBZG1pbmlzdHJhdG9yIiwicm9sZSI6IkFkbWluaXN0cmF0b3IiLCJpYXQiOjE3NTk5NzEzODIsIm5iZiI6MTc1OTk3MTM4Mn0.MSuOVMRg2Vfdpg-vDHOqKg5SXOpUKVtmruXPk0toMcs"

    // Timeout configurations
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L

    // Endpoints by context
    object IdentityAccess {
        private const val BASE = "$API_VERSION/v1/authentication"
        const val LOGIN = "$BASE/sign-in"
        const val REGISTER = "$BASE/sign-up"
        const val REFRESH_TOKEN = "$BASE/refresh-token"
        const val PROFILE = "$BASE/profile"
    }

    object LegacyInventory {
        private const val BASE = "$API_VERSION"
        const val PRODUCTS = "$BASE/products"
        const val PRODUCT_DETAIL = "$BASE/products/{id}"
        const val INVENTORY = "$API_VERSION/inventory"
        const val INVENTORY_MOVEMENTS = "$API_VERSION/inventory/movements"
    }

    object ProductCatalog {
        private const val BASE = "$API_VERSION/v1/products"
        const val GET_ALL = BASE
        const val GET_BY_ID = "$BASE/{id}"
        const val CREATE = BASE
        const val UPDATE = "$BASE/{id}"
        const val DELETE = "$BASE/{id}"
    }

    object Inventory {
        private const val BASE = "$API_VERSION/v1/inventory"
        const val GET_ALL = BASE
        const val GET_BY_PRODUCT = "$BASE/product/{productId}"
        const val UPDATE_STOCK = "$BASE/update"
        const val GET_MOVEMENTS = "$BASE/movements"
    }

    object Alerts {
        private const val BASE = "$API_VERSION/v1/alerts"
        const val GET_ALL = BASE
        const val GET_ACTIVE = "$BASE/active"
        const val CREATE = BASE
        const val MARK_RESOLVED = "$BASE/{id}/resolve"
    }

    object AuditReport {
        private const val BASE = "$API_VERSION/v1/reports"
        const val GET_STOCK_REPORT = "$BASE/stock"
        const val GET_MOVEMENT_REPORT = "$BASE/movements"
        const val GET_ALERT_REPORT = "$BASE/alerts"
        const val GENERATE_REPORT = "$BASE/generate"
    }
}
