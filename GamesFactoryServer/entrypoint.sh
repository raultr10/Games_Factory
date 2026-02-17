#!/bin/bash
# entrypoint.sh

# 1. Iniciamos SQL Server en segundo plano
/opt/mssql/bin/sqlservr &
pid=$!

# 2. Esperamos a que el motor de base de datos arranque (damos 30 seg de margen)
echo "Esperando a que SQL Server arranque..."
sleep 30s

# 3. Ejecutamos tu script Games_Factory (init.sql)
# -S localhost: Servidor local del contenedor
# -U sa: Usuario Administrador
# -P: Contraseña (la coge de la variable de entorno)
# -d master: Conecta a master inicialmente para poder crear la nueva DB
echo "Ejecutando script de inicialización Games_Factory..."
/opt/mssql-tools18/bin/sqlcmd -C -S localhost -U sa -P $MSSQL_SA_PASSWORD -d master -i /usr/src/app/init.sql

# 4. Mantenemos el proceso activo para que Docker no se cierre
wait $pid