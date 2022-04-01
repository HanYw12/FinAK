package com.example.finak


import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException


class ActivityTable : AppCompatActivity() {
    private var txtNombre: EditText? = null
    private var txtIngreso: EditText? = null
    private var txtGastos: EditText? = null
    private var tbMovimientos: TableLayout? = null

    private var tbCalculos2: TableLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        txtNombre = findViewById(R.id.colNombre)
        txtIngreso = findViewById(R.id.colIngresos)
        txtGastos = findViewById(R.id.colGastos)
        tbMovimientos = findViewById(R.id.tbMovimientos)
        tbMovimientos?.removeAllViews()
        var queue = Volley.newRequestQueue(this)
        var url = "http://192.168.3.72/APIFinAK/registro.php?id=1"
        var jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                try {
                    var jsonArray: JSONArray = response.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        val registro =
                            LayoutInflater.from(this).inflate(R.layout.table_row_ak, null, false)
                        val colNombre = registro.findViewById<View>(R.id.colNombre) as TextView
                        val colIngreso = registro.findViewById<View>(R.id.colIngresos) as TextView
                        val colGasto = registro.findViewById<View>(R.id.colGastos) as TextView
                        val colEditar = registro.findViewById<View>(R.id.colEditar)
                        val colBorrar = registro.findViewById<View>(R.id.colBorrar)

                        if (jsonObject.getString("MovTipo") == "Ingreso") {
                            colIngreso.text = jsonObject.getString("MovCantidad")
                            colGasto.text = null
                        } else {
                            colIngreso.text = null
                            colGasto.text = jsonObject.getString("MovCantidad")
                        }
                        colNombre.text = jsonObject.getString("MovNombre")
                        colEditar.id = jsonObject.getString("MovId").toInt()
                        colBorrar.id = jsonObject.getString("MovId").toInt()
                        tbMovimientos?.addView(registro)

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { error ->
            })
        queue.add(jsonObjectRequest)
    }

    fun clickbtnEditar(view: View) {
//        Toast.makeText(this, view.id.toString(), Toast.LENGTH_LONG).show()
        val id =view.id.toString()
        val intent: Intent = Intent(this,MainActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
        }


    fun clickbtnBorrar(view: View) {
        Toast.makeText(this, view.id.toString(), Toast.LENGTH_LONG).show()
    }

    fun clickbtnCalcular(view: View) {
        val Calcular = findViewById<Button>(R.id.Calcular)
        Calcular.visibility = View.GONE
        tbCalculos2 = findViewById(R.id.tbCalculos)
        tbCalculos2?.removeAllViews()
        var queue1 = Volley.newRequestQueue(this)
        //var queue2=Volley.newRequestQueue(this)
        var url1 = "http://192.168.3.72/APIFinAK/gasto.php?id=1"
        val jsonObjectRequest1 = JsonObjectRequest(Request.Method.GET, url1, null,
            Response.Listener { response ->
                var jsonArray: JSONArray = response.getJSONArray("data")
                var jsonObject = jsonArray.getJSONObject(1)
                var jsonObject2 = jsonArray.getJSONObject(0)
                val registro2 =
                    LayoutInflater.from(this).inflate(R.layout.table_row_calculos, null, false)
                val colCalIng = registro2.findViewById<View>(R.id.colCalIngText) as TextView
                val colCalGas = registro2.findViewById<View>(R.id.colCalGasText) as TextView
                colCalIng.text = jsonObject.getString("Suma")
                colCalGas.text = jsonObject2.getString("Suma")
                val colIng = jsonObject.getString("MovTipo")
                val colGas = jsonObject2.getString("MovTipo")

                if (colIng.toString() == "Ingreso") {
                    if (colGas.toString() == "Gasto") {
                        colCalIng.text = jsonObject.getString("Suma")
                        colCalGas.text = jsonObject2.getString("Suma")
                        tbCalculos2?.addView(registro2)
                    }
                } else {
                    colCalIng.text = jsonObject2.getString("Suma")
                    colCalGas.text = jsonObject.getString("Suma")

                    tbCalculos2?.addView(registro2)

                }
            }, Response.ErrorListener { error -> })
        queue1.add(jsonObjectRequest1)
    }

}