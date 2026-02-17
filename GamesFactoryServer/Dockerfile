FROM mcr.microsoft.com/mssql/server:2022-latest
USER root
WORKDIR /usr/src/app
# Copiamos tus scripts locales al contenedor
COPY init.sql .
COPY entrypoint.sh .
RUN chmod +x entrypoint.sh
USER mssql
# Al arrancar, se ejecutará el entrypoint que cargará el init.sql
CMD /bin/bash ./entrypoint.sh