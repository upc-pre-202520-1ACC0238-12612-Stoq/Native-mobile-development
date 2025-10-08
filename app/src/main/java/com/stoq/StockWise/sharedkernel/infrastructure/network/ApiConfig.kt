package com.stoq.StockWise.sharedkernel.infrastructure.network

/**
 * Configuration for API endpoints used across all bounded contexts.
 * This belongs to Shared Kernel as it's a common concern for all contexts.
 */
object ApiConfig {
    const val BASE_URL = "http://34.39.181.148:8080"
    const val API_VERSION = "api"

    // Timeout configurations
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L

    // Endpoints by context
    object IdentityAccess {
        private const val BASE = "$API_VERSION/auth"
        const val LOGIN = "$BASE/login"
        const val REGISTER = "$BASE/register"
        const val REFRESH_TOKEN = "$BASE/refresh"
        const val PROFILE = "$BASE/profile"
    }

    object Inventory {
        private const val BASE = "$API_VERSION"
        const val PRODUCTS = "$BASE/products"
        const val PRODUCT_DETAIL = "$BASE/products/{id}"
        const val INVENTORY = "$API_VERSION/inventory"
        const val INVENTORY_MOVEMENTS = "$API_VERSION/inventory/movements"
    }

    object Sales {
        private const val BASE = "$API_VERSION"
        const val ORDERS = "$BASE/orders"
        const val ORDER_DETAIL = "$BASE/orders/{id}"
        const val CREATE_ORDER = "$BASE/orders"
        const val UPDATE_ORDER_STATUS = "$BASE/orders/{id}/status"
    }
}
