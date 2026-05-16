package edu.raultirado.games_factory_crud_kotlin.data.remote

import edu.raultirado.games_factory_crud_kotlin.config.AppConfig
import java.sql.Connection
import java.sql.DriverManager

object DbConnection {
    private const val URL = AppConfig.URL_BASE_DATOS
    private const val USER = "sa"
    private const val PASS = "Password123!"

    fun getConnection(): Connection? {
        return try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            DriverManager.getConnection(URL, USER, PASS)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}