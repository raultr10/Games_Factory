package edu.raultirado.games_factory_crud_kotlin.data.remote

import java.sql.Connection
import java.sql.DriverManager

object DbConnection {
    private const val URL = "jdbc:jtds:sqlserver://10.0.2.2:1433;databaseName=Games_Factory;ssl=request=false"
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