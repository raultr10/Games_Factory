----- DESCRIPCIÓN DEL PROYECTO ----- 
Aplicación de Kotlin (CRUD): Para gestionar los empleados, vieojuegos y noticias de GAMES_FACTORY.

----- TECNOLOGÍAS UTILIZADAS -----
Móvil: Kotlin, Jetpack Compose, MVVM, jTDS (conexión JDBC).
Backend: Microsoft SQL Server, Servidor web Nginx, Docker y Docker Compose.

----- ARQUITECTURA GENERAL DEL SISTEMA -----
El sistema sigue una arquitectura Cliente-Servidor descentralizada en red local.
Las aplicaciones cliente (Android y WPF) se conectan directamente a un servidor de base de datos SQL Server centralizado.
Además, consumen recursos multimedia (carátulas y fotos) a través de peticiones HTTP a un servidor web Nginx aislado mediante contenedores.

----- REQUISITOS PREVIOS -----
Es necesario tener instalado lo siguiente:
Docker Desktop 
Visual Studio 2022 (con carga de trabajo .NET de escritorio). 
Android Studio (versión Jellyfish o superior) o dispositivo físico conectado.
Conexión a red local (para la sincronización de IP).

----- INSTALACIÓN Y EJECUCIÓN -----
APLICACIÓN MÓVIL:
Paso 1: Configurar la Infraestructura (Docker)
El proyecto utiliza Docker para levantar la Base de Datos y el Servidor de Imágenes.
1. Abrir una terminal en la carpeta `GamesFactoryServer` del repositorio.
2. Ejecuta el comando para iniciar los contenedores:
   docker compose up -d
3. Verifica  que ambos contenedores (sql_server_games y servidor_imagenes) están en ejecución (el servidor de imágenes expone el puerto 8085 y SQL el 1433).

Paso 2: Configurar la conexión (Sincronización de IP)
Como los servidores corren en local, la aplicación Android necesita saber la IP exacta de tu máquina para conectarse a la base de datos y a las fotos.
1. Abre tu consola (cmd) y averigua tu dirección IPv4 local ejecutando `ipconfig` (ej: 192.168.1.XX).
2. Abre el proyecto en Android Studio.
3. Ve al archivo de configuración centralizado: `app/src/main/java/edu/.../config/AppConfig.kt`.
4. Cambia el valor de la variable `IP_SERVIDOR` por tu dirección IPv4 actual.

Paso 3: Ejecutar la aplicación
1. Sincroniza el proyecto con los archivos de Gradle (Make Project).
2. Selecciona un emulador (API 33+) o un dispositivo físico conectado.
3. Pulsa el botón "Run App" (Play) en la barra superior de Android Studio.

----- ESTRUCTURA DEL REPOSITORIO -----
APLICACIÓN MÓVIL:
El código de la aplicación móvil está estructurado siguiendo el patrón MVVM:
`config/`: Archivo `AppConfig.kt` que centraliza la IP, la cadena de conexión JDBC y la URL del servidor Nginx.
`data/`: Modelos de datos (Videojuego, Noticia, Empleado) y la clase `RemoteDatasource` que maneja las consultas SQL directas.
`ui/`: Contiene las pantallas (`screens/`), los componentes visuales reutilizables como las tarjetas (`components/`) y la navegación.
`viewmodel/`: Clases que manejan la lógica de estado y la asincronía (Corrutinas) para no bloquear la interfaz gráfica.

----- USO DEL SISTEMA -----
APLICACIÓN MÓVIL:
1. Login: Acceso mediante correo y contraseña.
2. Catálogo y Listas: Visualización de Noticias, Videojuegos y Empleados en listas asíncronas con carga de imágenes mediante Coil.
3. Buscador: Cada lista incluye una barra de búsqueda predictiva en la parte superior.
4. Gestión (CRUD): Botón flotante para añadir nuevos registros. Pulsación sobre las tarjetas para editar, e icono de papelera integrado en cada tarjeta para borrar elementos con cuadro de diálogo de confirmación.

----- CREDENCIALES DE PRUEBA -----
Para evaluar la aplicación sin necesidad de registrar un usuario desde cero, utiliza estas credenciales de acceso:

* Perfil Administrador (Modo Admin):
  - Correo: frank@gmail.com
  - Contraseña: 0123

* Perfil Empleado Estándar (Modo Normal):
  - Correo: raul@gmail.com
  - Contraseña: 1234

*(Importante: Si se prueba la función de registro para crear un usuario nuevo, el correo electrónico exige obligatoriamente un formato válido con extensión final, ej: usuario@dominio.com.
Además, el campo del DNI sigue requiriendo el formato exacto validado: 2 números, punto, 3 números, punto, 3 números, 1 letra mayúscula. Ej: 12.345.678X).*
