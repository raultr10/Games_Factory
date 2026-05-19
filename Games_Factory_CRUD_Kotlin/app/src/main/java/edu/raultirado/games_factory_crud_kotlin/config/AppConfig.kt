package edu.raultirado.games_factory_crud_kotlin.config

object AppConfig {
    const val IP_SERVIDOR = "192.168.1.37"

    const val URL_IMAGENES = "http://$IP_SERVIDOR:8085"

    const val URL_BASE_DATOS = "jdbc:jtds:sqlserver://$IP_SERVIDOR:1433;databaseName=Games_Factory"
}