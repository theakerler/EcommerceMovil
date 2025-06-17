package com.proyecto.ecommercemovil.data.repository.direccion

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proyecto.ecommercemovil.data.model.direccion.DepartamentoConProvincias
import com.proyecto.ecommercemovil.data.model.direccion.Distrito
import com.proyecto.ecommercemovil.data.model.direccion.Provincia
import com.proyecto.ecommercemovil.data.model.direccion.ProvinciaConDistritos

inline fun <reified T> loadJsonFromAsset(context: Context, fileName: String): T? {
    return try {
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        Gson().fromJson<T>(json, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        null
    }
}
// Nueva función para desanidar provincias
fun cargarProvinciasDesdeJsonAnidado(context: Context, fileName: String): List<Provincia> {
    val departamentosJson = loadJsonFromAsset<List<DepartamentoConProvincias>>(context, fileName) ?: emptyList()
    return departamentosJson.flatMap { dep ->
        dep.provincias.map { prov ->
            Provincia(id = prov.id, nombre = prov.nombre, departamento_id = dep.departamento_id)
        }
    }
}

fun cargarDistritosDesdeJsonAnidado(context: Context, fileName: String): List<Distrito> {
    val provinciasPorDepartamento = loadJsonFromAsset<List<List<ProvinciaConDistritos>>>(context, fileName) ?: emptyList()
    return provinciasPorDepartamento.flatten().flatMap { prov ->
        prov.distritos.map { dist ->
            Distrito(
                id = dist.id,
                nombre = dist.nombre,
                provincia_id = prov.provincia_id,
                departamento_id = prov.departamento_id
            )
        }
    }
}
