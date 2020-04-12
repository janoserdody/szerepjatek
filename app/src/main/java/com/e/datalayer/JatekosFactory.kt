package com.e.datalayer

import android.content.Context
import com.e.jatekter.JatekTer
import com.e.keret.CommandProcessor
import com.e.szabalyok.GepiJatekos
import com.e.szabalyok.GonoszGepiJatekos
import com.e.szabalyok.Jatekos
import com.e.szerepjatek.MainActivity
import java.io.*
import org.json.simple.JSONArray as JSONArray1
import org.json.simple.JSONObject as JSONObject1
import org.json.simple.parser.JSONParser as JSONParser1
import org.json.simple.parser.ParseException as ParseException1
import java.io.IOException

class JatekosFactory(val context: Context) {
    private lateinit var karakterList: JSONArray1
    private var karakterNevList = ArrayList<String>()

    init{
        beolvas()
    }

    fun beolvas() {
        //JSON parser object to parse read file
        val jsonParser = JSONParser1()

        val file_name = "karakterek.json"

try {
    var json_string = context.assets.open("karakterek.json").bufferedReader(Charsets.UTF_8).use {
        it.readText()
    }
            var obj =  jsonParser.parse(json_string)

            karakterList = obj as JSONArray1

            for (karakter in karakterList){
                var k = karakter as JSONObject1
                var data = k["data"] as JSONObject1
                karakterNevList.add(data["nev"] as String)
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException1) {
            e.printStackTrace()
        }
    }

    fun getNevLista(): ArrayList<String>{
        return karakterNevList
    }

    fun createJatekos(
        x: Int, y: Int, jatekTer: JatekTer, nev: String,
        commandProcessor: CommandProcessor
    ): Jatekos? {
        var jatekos: Jatekos? = null

        for (karakter in karakterList) {
            var k = karakter as JSONObject1
            var data = k["data"] as JSONObject1
            if (data["nev"] == nev) {
                if (k["type"] == "GonoszGepiJatekos")
                {
                    jatekos = createGonoszGepiJatekos(x, y, jatekTer, nev, commandProcessor, data)
                }
                else if  (k["type"] == "GepiJatekos")
                {
                    jatekos = createGepiJatekos(x, y, jatekTer, nev, commandProcessor, data)
                }
                else {
                    jatekos = Jatekos(x, y, jatekTer, nev, commandProcessor, data)
                }
                break
            }
        }
        return jatekos
    }

    private fun createGepiJatekos(
        x: Int, y: Int, jatekTer: JatekTer, nev: String,
        commandProcessor: CommandProcessor, karakter: JSONObject1
    ): GepiJatekos {

        var jatekos = GepiJatekos(x, y, jatekTer, nev, 1, commandProcessor, karakter)

        return jatekos
    }

    private fun createGonoszGepiJatekos(
        x: Int, y: Int, jatekTer: JatekTer, nev: String,
        commandProcessor: CommandProcessor, karakter: JSONObject1
    ): GonoszGepiJatekos {

        var jatekos = GonoszGepiJatekos(x, y, jatekTer, nev, 1, commandProcessor, karakter)

        return jatekos
    }
}