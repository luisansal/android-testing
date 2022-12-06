package com.luisansal.jetpack.core.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.luisansal.jetpack.core.domain.daos.CityDao
import com.luisansal.jetpack.core.domain.daos.CountryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


fun Fragment.loadCities() {
    val ins = resources.openRawResource(resources.getIdentifier("createtable_ciudad", "raw", requireActivity().packageName))
    val result = readTextFile(ins)
    getListFields(result).forEachIndexed { index, row ->
        val row0 = row[0].replace("(", "").trim().toLong()
        val row4 = if (row[4].replace(");", "").trim() == "NULL") 0 else row[4].trim().toInt()
        val cityDao = CityDao(
            row0,
            row[1],
            row[2],
            row[3],
            row4,
        )
        //save Data
//        CoroutineScope(Dispatchers.Default).launch {
//            locationCloudStore.saveCity(cityDao, {}, {})
//        }
    }
}

fun Fragment.loadCountries() {
    val ins = resources.openRawResource(resources.getIdentifier("createtable_pais", "raw", requireActivity().packageName))
    val result = readTextFile(ins)

    getListFields(result).forEachIndexed { index, row ->
        val row4 = if (row[4].trim() == "NULL") 0.0 else row[4].trim().toDouble()
        val row5 = if (row[5].trim() == "NULL") 0 else row[5].trim().toInt()
        val row6 = if (row[6].trim() == "NULL") 0 else row[6].trim().toInt()
        val row7 = if (row[7].trim() == "NULL") 0.0 else row[7].trim().toDouble()
        val row8 = if (row[8].trim() == "NULL") 0.0 else row[8].trim().toDouble()
        val row9 = if (row[9].trim() == "NULL") 0.0 else row[9].trim().toDouble()
        val row13 = if (row[13].trim() == "NULL") 0 else row[13].trim().toInt()
        val countryDao = CountryDao(
            row[0],
            row[1],
            row[2],
            row[3],
            row4,
            row5,
            row6,
            row7,
            row8,
            row9,
            row[10],
            row[11],
            row[12],
            row13,
            row[14]
        )
        //save Data
//        CoroutineScope(Dispatchers.Default).launch {
//            locationCloudStore.saveCountry(countryDao, {}, {})
//        }
    }
}

fun readTextFile(inputStream: InputStream): String? {
    val outputStream = ByteArrayOutputStream()
    val buf = ByteArray(1024)
    var len: Int
    try {
        while (inputStream.read(buf).also { len = it } != -1) {
            outputStream.write(buf, 0, len)
        }
        outputStream.close()
        inputStream.close()
    } catch (e: IOException) {
    }
    return outputStream.toString()
}

fun getListFields(query: String?): List<List<String>> {
    val rows = query?.replace("VALUES", "),")?.split("),\n")?.toMutableList()?.apply { removeFirst() }

    val rowsFormated = mutableListOf<List<String>>()
    for (row in rows!!) {
        val values = row.split(",").toMutableList()
        val valuesFinal = mutableListOf<String>()
        var indexSkip: Int? = null
        values.forEachIndexed { index, value ->
            if (indexSkip == index)
                return@forEachIndexed
            var detail = value.replace("('", "").replace(")'", "")
            var cantComillas = 0
            value.forEach {
                if (it.toString() == "'")
                    cantComillas++
            }
            if (cantComillas == 1) {
                detail = "$value, ${values[index + 1]}"
                indexSkip = index + 1
            }

            if (detail[0].toString() == "'") {
                detail = detail.substring(1)
            }
            if (detail[detail.length - 1].toString() == "'")
                detail = detail.substring(0, detail.length - 1)

            valuesFinal.add(detail)
        }
        println(valuesFinal.size)
        rowsFormated.add(valuesFinal)
    }
    return rowsFormated.toList()
}