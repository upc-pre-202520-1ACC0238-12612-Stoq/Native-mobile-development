# Ejemplos de JSON para Dashboard API

## GET /api/v1/products

Respuesta esperada para obtener todos los productos:

```json
[
  {
    "id": 1,
    "name": "Laptop Dell XPS 13",
    "description": "Laptop ultrabook de 13 pulgadas",
    "purchasePrice": 800.00,
    "salePrice": 1200.00,
    "internalNotes": "Producto estrella",
    "categoryId": 1,
    "categoryName": "Electrónica",
    "unitId": 1,
    "unitName": "Unidad",
    "unitAbbreviation": "u",
    "tags": [
      {
        "id": 1,
        "name": "Nuevo"
      }
    ],
    "createdAt": "2025-12-01T10:00:00Z"
  },
  {
    "id": 2,
    "name": "Mouse Logitech MX Master 3",
    "description": "Mouse ergonómico inalámbrico",
    "purchasePrice": 50.00,
    "salePrice": 100.00,
    "internalNotes": null,
    "categoryId": 1,
    "categoryName": "Electrónica",
    "unitId": 1,
    "unitName": "Unidad",
    "unitAbbreviation": "u",
    "tags": [],
    "createdAt": "2025-12-01T11:30:00Z"
  },
  {
    "id": 3,
    "name": "Teclado Mecánico Keychron K8",
    "description": "Teclado mecánico inalámbrico",
    "purchasePrice": 60.00,
    "salePrice": 120.00,
    "internalNotes": "Stock limitado",
    "categoryId": 1,
    "categoryName": "Electrónica",
    "unitId": 1,
    "unitName": "Unidad",
    "unitAbbreviation": "u",
    "tags": [
      {
        "id": 2,
        "name": "En oferta"
      }
    ],
    "createdAt": "2025-12-01T14:15:00Z"
  },
  {
    "id": 4,
    "name": "Monitor Samsung 27\"",
    "description": "Monitor 4K UHD de 27 pulgadas",
    "purchasePrice": 200.00,
    "salePrice": 350.00,
    "internalNotes": null,
    "categoryId": 1,
    "categoryName": "Electrónica",
    "unitId": 1,
    "unitName": "Unidad",
    "unitAbbreviation": "u",
    "tags": [],
    "createdAt": "2025-12-01T16:45:00Z"
  },
  {
    "id": 5,
    "name": "Auriculares Sony WH-1000XM4",
    "description": "Auriculares inalámbricos con cancelación de ruido",
    "purchasePrice": 150.00,
    "salePrice": 280.00,
    "internalNotes": "Muy populares",
    "categoryId": 1,
    "categoryName": "Electrónica",
    "unitId": 1,
    "unitName": "Unidad",
    "unitAbbreviation": "u",
    "tags": [
      {
        "id": 1,
        "name": "Nuevo"
      }
    ],
    "createdAt": "2025-12-02T09:20:00Z"
  }
]
```

**Notas sobre el cálculo de estadísticas:**
- `totalProducts`: Conteo total de productos en el array (5 productos)
- `lowStock`: Productos con stock ≤ 5 (se calcularía desde inventory API)
- `nextDeliveryDate`: Campo no disponible en la API actual, devolver "N/A"

## GET /api/v1/inventory

Respuesta esperada para obtener todos los movimientos de inventario:

```json
[
  {
    "id": 1,
    "productId": 1,
    "quantity": 10,
    "movementType": "IN",
    "createdAt": "2025-12-01T10:00:00Z"
  },
  {
    "id": 2,
    "productId": 2,
    "quantity": 25,
    "movementType": "IN",
    "createdAt": "2025-12-01T11:30:00Z"
  },
  {
    "id": 3,
    "productId": 3,
    "quantity": 15,
    "movementType": "IN",
    "createdAt": "2025-12-01T14:15:00Z"
  },
  {
    "id": 4,
    "productId": 4,
    "quantity": 8,
    "movementType": "IN",
    "createdAt": "2025-12-01T16:45:00Z"
  },
  {
    "id": 5,
    "productId": 5,
    "quantity": 12,
    "movementType": "IN",
    "createdAt": "2025-12-02T09:20:00Z"
  },
  {
    "id": 6,
    "productId": 1,
    "quantity": 3,
    "movementType": "OUT",
    "createdAt": "2025-12-02T10:30:00Z"
  },
  {
    "id": 7,
    "productId": 2,
    "quantity": 2,
    "movementType": "OUT",
    "createdAt": "2025-12-02T11:00:00Z"
  },
  {
    "id": 8,
    "productId": 3,
    "quantity": 1,
    "movementType": "OUT",
    "createdAt": "2025-12-02T14:20:00Z"
  }
]
```

**Notas sobre el cálculo de estadísticas:**
- `movementsToday`: Movimientos creados hoy (filtrar por `createdAt` que empiece con fecha actual)
- Para calcular stock actual por producto: agrupar por `productId` y sumar/restar según `movementType`

## Cálculo de Stock Actual por Producto

Para determinar el stock actual y calcular `lowStock`, se necesita procesar los movimientos:

```kotlin
// Ejemplo de cálculo de stock actual
val currentStock = inventory
    .groupBy { it.productId }
    .mapValues { (_, movements) ->
        movements.sumBy { movement ->
            when (movement.movementType) {
                "IN" -> movement.quantity ?: 0
                "OUT" -> -(movement.quantity ?: 0)
                else -> 0
            }
        }
    }

// Stock actual por producto:
// Product 1: 10 - 3 = 7
// Product 2: 25 - 2 = 23
// Product 3: 15 - 1 = 14
// Product 4: 8 - 0 = 8
// Product 5: 12 - 0 = 12

// lowStock (productos con stock ≤ 5): 0 productos
```

## Consideraciones Técnicas

1. **Manejo de Fechas**: Las fechas están en formato ISO 8601 con zona horaria UTC
2. **Campos Opcionales**: Todos los campos pueden ser `null`, usar operador Elvis (`?:`) para valores por defecto
3. **Movement Types**: 
   - `"IN"`: Entrada de productos (sumar al stock)
   - `"OUT"`: Salida de productos (restar del stock)
4. **Filtrado por Fecha**: Para `movementsToday`, filtrar registros donde `createdAt` empiece con la fecha actual en formato `yyyy-MM-dd`

## Estados de Error

```json
// Error 401 - No autorizado
{
  "error": "Unauthorized",
  "message": "Token de autenticación inválido o expirado"
}

// Error 500 - Error interno del servidor
{
  "error": "Internal Server Error",
  "message": "Error interno del servidor"
}
```
