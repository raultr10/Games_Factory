# Games Factory - Sistema de Gestión Multiplataforma

## Descripción del proyecto
Games Factory es un ecosistema de software multiplataforma diseñado para la gestión integral de una tienda de videojuegos. Cuenta con dos interfaces interconectadas con propósitos distintos:

* Aplicación de Escritorio (WPF): Orientada a los clientes de la tienda. Permite a los usuarios registrarse, iniciar sesión, explorar el catálogo de videojuegos, visualizar noticias y añadir productos al carrito para realizar compras.
* Aplicación Móvil (Kotlin): Herramienta exclusiva para los empleados (CRUD). Permite consultar y gestionar el inventario (productos), los empleados y las noticias en tiempo real.

El sistema se apoya en las bases de datos alojadas en loscontenedores Docker para facilitar un entorno aislado y reproducible, junto con un servidor web independiente para la entrega de imágenes.

## Tecnologías utilizadas
* Frontend Móvil: Kotlin, Jetpack Compose, MVVM, jTDS (conexión JDBC), Coil (imágenes).
* Frontend Escritorio: C#, WPF (Windows Presentation Foundation), .NET 8.0, Entity Framework Core 9.0.
* Backend e Infraestructura: Microsoft SQL Server 2022, Servidor web Nginx, Docker Desktop y Docker Compose.

## Arquitectura general del sistema
El sistema sigue una arquitectura Cliente-Servidor descentralizada en red local:
* Clientes (WPF y Android): Se comunican de forma directa y concurrente con la base de datos SQL Server para todas las operaciones lógicas y de persistencia.
* Servidor de Imágenes (Nginx): Contenedores Docker configurados para servir localmente las imágenes de los productos. Actúan como servidores estáticos que exponen los recursos multimedia por vía HTTP para que los clientes los puedan consumir sin sobrecargar la base de datos.

## Requisitos previos
* Docker: Docker Desktop instalado y ejecutándose.
* Entorno Móvil: Android Studio (versión Jellyfish o superior) o dispositivo físico conectado.
* Entorno Escritorio: Visual Studio 2022 (con carga de trabajo .NET de escritorio) y .NET SDK 8.0+.
* Herramienta BD: SSMS (SQL Server Management Studio).
* Red: Conexión a red local (imprescindible para la sincronización de IP entre el servidor y la app móvil).

## Instalación
Sigue estos pasos para preparar tu entorno local clonando el repositorio directamente desde Visual Studio 2022:

1. Abre Visual Studio 2022.
2. En la pantalla de inicio, dirígete a la sección "Tareas iniciales" (en la columna derecha) y haz click en "Clonar un repositorio".
3. En la ventana que aparece, busca la sección "Escribir una dirección URL de repositorio de GIT".
4. En el campo "Ubicación del repositorio", pega la URL de este repositorio de GitHub.
5. En el campo "Ruta de acceso", selecciona la carpeta local donde deseas descargar el proyecto.
6. Haz click en el botón "Clonar" en la parte inferior derecha. Visual Studio descargará los archivos y abrirá el proyecto.

## Ejecución

### Evaluar la Aplicación de Escritorio (WPF)
1. Infraestructura: Asegúrate de tener Docker Desktop abierto. Abre una terminal en la carpeta `GamesServer` y ejecuta `docker-compose up -d`.
2. Inicializar la base de datos: Conéctate al servidor SQL usando una herramienta como Azure Data Studio o SSMS con la siguiente configuración exacta:
   * Server Name: `localhost,1433`
   * Authentication: Autenticación de SQL Server
   * User Name: `sa`
   * Password: `Password123!`
   * Encrypt: Optional
   * Trust Server Certificate: Marcado (activado)
   
   Una vez conectado, abre y ejecuta el script de base de datos `Games_Factory.sql` (o `init.sql`) ubicado dentro de la carpeta `GamesServer`. Esto creará la estructura de tablas y poblará la base de datos con información    inicial.
3. Aplicación: Abre la solución `Games_Factory.sln` ubicada en `Games_Factory_WPF` usando Visual Studio.
4. Establece el proyecto `Games_Factory` como proyecto de inicio e inicia la depuración presionando F5.

### Evaluar la Aplicación Móvil (Kotlin)
1. Infraestructura Docker:
   * Asegúrate de tener **Docker Desktop** abierto.
   * Abre una terminal en la carpeta `GamesFactoryServer` y ejecuta el comando `docker-compose up -d`.
2. Inicializar la base de datos: Conéctate al servidor SQL usando una herramienta como Azure Data Studio o SSMS con la siguiente configuración exacta:
   * Server Name: `localhost,1433`
   * Authentication: Autenticación de SQL Server
   * User Name: `sa`
   * Password: `Password123!`
   * Encrypt: Optional
   * Trust Server Certificate: Marcado (activado)
   Una vez conectado, abre y ejecuta el script de base de datos `init.sql` ubicado dentro de la carpeta `GamesFactoryServer`. Esto creará la estructura de tablas y poblará la base de datos con información inicial.
3. Sincronización de IP (Crítico):
   * La aplicación Android necesita conectarse a tu red local para acceder a la BD y a las imágenes.
   * Abre la consola (cmd o PowerShell), ejecuta el comando `ipconfig` y copia tu dirección IPv4 local (ej: `192.168.1.132`).
4. Configuración en Android Studio:
   * Abre la carpeta `Games_Factory_CRUD_Kotlin` usando Android Studio.
   * Navega hasta el archivo de configuración en la ruta exacta: `app/src/main/java/edu/raultirado/games_factory_crud_kotlin/config/AppConfig.kt`.
   * Reemplaza el valor de la variable `IP_SERVIDOR` por la dirección IPv4 que copiaste.
5. Ejecución:
   * Espera a que termine la sincronización de Gradle. Selecciona un emulador (o dispositivo físico) y presiona **"Run App"**.

## Estructura del repositorio
* `/Games_Factory_WPF/`: Código fuente de la app de escritorio (Modelos, Vistas, ViewModels).
* `/Games_Factory_CRUD_Kotlin/`: Código fuente de la app Android (Patrón MVVM, UI en Compose).
* `/GamesServer/`: Infraestructura Docker, servidor Nginx, imágenes y base de datos para la aplicación de Escritorio.
* `/GamesFactoryServer/`: Infraestructura Docker, servidor Nginx, imágenes y base de datos para la aplicación Móvil.

## Configuración
### Variables y Sincronización de IP (Móvil)
Como los servidores corren en local, la app Android necesita tu IP para conectarse a la base de datos y a las imágenes.
1. Abra su consola (cmd), ejecute `ipconfig` y copie su dirección IPv4 (ej: `192.168.1.XX`).
2. Cambie el valor de la variable `IP_SERVIDOR` en la aplicación por su dirección IPv4 actual.

### Servidores y Puertos
* SQL Server (Ambos entornos): Expuesto en el puerto `1433` local.
* Nginx (WPF): Expuesto en el puerto `8080`.
* Nginx (Móvil): Expuesto en el puerto `8085`. Las imágenes se alojan físicamente en la carpeta `/GamesFactoryServer/foto_CRUD/` y son mapeadas al contenedor. Cuando la app móvil necesita una foto, hace la petición a `http://IP_SERVIDOR:8085/imagen.jpg`.
* Cadena de conexión WPF: Configurada en `Models/GameStoreContext.cs`, apunta directamente a `localhost,1433`.

## Uso del sistema
* WPF (Escritorio): Tienda virtual para usuarios. El flujo principal permite a los clientes loguearse o registrarse, explorar el catálogo, ver noticias y agregar productos al carrito para realizar sus compras.
* Kotlin (Móvil): Aplicación exclusiva para empleados. Cuenta con Login de acceso, listas asíncronas para Catálogo, Noticias y Empleados (con imágenes cacheadas mediante Coil). Incluye barra de búsqueda predictiva y gestión CRUD directa desde la interfaz (botón flotante para añadir, tap para editar, icono papelera para borrar).
  * *Nota sobre imágenes:* Al crear o modificar un producto o noticia desde la aplicación móvil, el cambio de la imagen no es automático. Se deberá añadir o modificar manualmente la carátula correspondiente en las carpetas `foto_CRUD` ubicadas tanto en `GamesFactoryServer` como en `GamesServer`.

## Credenciales de prueba
La base de datos de ambas infraestructuras se inicializa con datos de prueba pre-hasheados. Utilice estas credenciales para probar los distintos roles del sistema:

* Perfil de Administrador (Control total):
  - Correo: `frank@gmail.com`
  - Contraseña: `0123`

* Perfil de Empleado Estándar:
  - Correo: `raul@gmail.com`
  - Contraseña: `1234`

* Perfil de Cliente (Tienda WPF):
  - Correo: `Juan@gmail.com`
  - Contraseña: `MiClaveSegura2026!`

## Estado del proyecto
Versión 1.0 - El proyecto se encuentra finalizada. La infraestructura de los dos clientes (móvil y escritorio) y la persistencia de datos y multimedia funcionan correctamente en local. Existe una poco de margen de mejora en la que se pueden seguir optimizando y agregando nuevas características a estas 2 aplicaciones.

## Despliegue y URL
Actualmente, el proyecto se encuentra en un entorno de desarrollo local, por lo que no dispone de una URL pública en la nube. Las instrucciones para levantar el proyecto en local y los puertos utilizados se detallan en los apartados de Instalación, Ejecución y Configuración. 

Puedes encontrar el código fuente completo del proyecto en nuestro repositorio de GitHub: 
https://github.com/raultr10/Games_Factory
