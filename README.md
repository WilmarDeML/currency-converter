# currency-converter

# Conversor de Monedas con API de ExchangeRate-API

## 📌 Índice
1. [Descripción](#-descripción)
2. [Funcionalidades](#-funcionalidades)  
3. [Tecnologías](#-tecnologías-utilizadas)
4. [Requisitos](#-requisitos-del-sistema)
5. [Instalación](#-instalación)
6. [Uso](#%EF%B8%8F-uso)
7. [Estructura](#%EF%B8%8F-estructura-del-código)
8. [Flujo](#-flujo-de-la-aplicación)
9. [API](#-api-externa)
10. [Mejoras](#-posibles-mejoras)
11. [Desarrollador](#-desarrollador)

## 🌟 Descripción
Aplicación Java CLI para conversión monetaria en tiempo real usando [ExchangeRate-API](https://www.exchangerate-api.com/).

**Características principales**:
- Consulta tasas actualizadas
- Conversión entre 160+ divisas
- Interfaz intuitiva por consola

## 🚀 Funcionalidades
| Función | Descripción |
|---------|-------------|
| Conversión en tiempo real | Usa tasas actualizadas de la API |
| Gestión de moneda base | Permite cambiar la moneda de referencia |
| Validación de inputs | Controla errores en entradas |
| Listado completo | Muestra todas las divisas disponibles |
| Historial | Muestra todas las conversiones hechas de cada sesión |

## 💻 Tecnologías Utilizadas
```java
// Stack técnico
- Java 11+
- Google Gson (JSON)
- Java HttpClient
```

## 📋 Requisitos del Sistema
- JDK 11 o superior
- API Key de ExchangeRate-API
- Conexión a internet
- IDE IntelliJ IDEA

## 🔧 Instalación
```bash
# 1. Clonar repositorio
git clone https://github.com/WilmarDeML/currency-converter.git
cd currency-converter

# 2. Configurar API Key (en ExchangeService.java)
private static final String API_KEY = "tu-key-aquí";

# 3. Descargar el archivo JAR de Gson

    ° En IntelliJ IDEA:

    ° File > Project Structure > Modules > Dependencies

    ° Click en "+" > JARs or directories

    ° Seleccionar el archivo gson-2.X.X.jar
```

## 🖥️ Uso
1. Seleccionar moneda base (ej: COP) también puede ser en minúsculas<br>
   ![image](https://github.com/user-attachments/assets/59f771e1-95b0-41ab-bb20-81bac34cce17)
2. Ingresar cantidad a convertir<br>
   ![image](https://github.com/user-attachments/assets/0b132764-e1b5-4fda-a713-4e67bd3dd89f)
3. Elegir moneda destino (ej: USD)<br>
   ![image](https://github.com/user-attachments/assets/51acb11b-6334-4c2e-b0e1-22bc9486cd91)
4. Ver resultado de conversión<br>
   ![image](https://github.com/user-attachments/assets/ede51108-8842-41e4-b1ee-d16bb4560196)
5. Opciones posteriores:<br>
    ![image](https://github.com/user-attachments/assets/c863cc00-90cf-46b4-b294-75374db53f94)
   - Convertir otro valor con la misma base [BASE] (1)<br>
     ![image](https://github.com/user-attachments/assets/07b4af5e-7aa3-4c32-884e-48c364ad008e)
   - Cambiar base (2)<br>
     ![image](https://github.com/user-attachments/assets/7fd3fb4f-6878-4cc5-b060-2d3b4255dc40)
   - Salir (9)<br>
     ![image](https://github.com/user-attachments/assets/5a2f8d9c-229e-4395-912c-37aca11cb46b)
   - Después de salir muestra el historial<br>
     ![image](https://github.com/user-attachments/assets/ede00fbe-c2da-4238-b39e-beef696659e3)



## 🏗️ Estructura del Código
| Archivo | Responsabilidad |
|---------|-----------------|
| `Main.java` | Punto de entrada |
| `MainService.java` | Lógica de UI y flujo |
| `ExchangeService.java` | Conexión con API |
| `MyHttpClient.java` | Cliente HTTP |

## 🔄 Flujo de la Aplicación
```mermaid
graph TD
    A[Inicio] --> B[Mostrar header]
    B --> C{Obtener tasas}
    C -->|Éxito| D[Menú principal]
    C -->|Error| E[Mostrar error]
    D --> F[Convertir moneda]
    F --> G[Mostrar resultado]
    G --> H{¿Nueva operación?}
    H -->|Sí| D
    H -->|No| I[Salir]
```

## 🌐 API Externa
**Endpoint**: `https://v6.exchangerate-api.com/v6/{API_KEY}/latest/{BASE_CODE}`

**Ejemplo de respuesta**:
```json
{
  "conversion_rates": {
    "USD": 1.0,
    "EUR": 0.92,
    "COP": 4100.0
    ...
  }
}
```

## 🔮 Posibles Mejoras
1. ✅ Historial de conversiones
2. 🖥️ Interfaz gráfica
3. 💾 Cache local de tasas
4. 🧪 Suite de pruebas unitarias

## ✨ Desarrollador

**Creado por**: WilmarDeMelquisedecLisbet

[![GitHub](https://img.shields.io/badge/GitHub-Profile-blue?style=flat&logo=github)](https://github.com/WilmarDeML)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://linkedin.com/in/wilmardeml-dev)

📌 *¿Problemas o sugerencias?*  
Abre un [issue](https://github.com/WilmarDeML/currency-converter/issues) en el repositorio.


## 📄 Licencia
MIT License - Ver archivo [LICENSE](LICENSE)
