package com.example.finak


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException


class ActivityTable : AppCompatActivity() {
    private var txtNombre:EditText?=null
    private var txtIngreso: EditText?=null
    private var txtGastos:EditText?=null
    private var tbMovimientos:TableLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        txtNombre=findViewById(R.id.colNombre)
        txtIngreso=findViewById(R.id.colIngresos)
        txtGastos=findViewById(R.id.colGastos)
        tbMovimientos=findViewById(R.id.tbMovimientos)
        tbMovimientos?.removeAllViews()
        var queue=Volley.newRequestQueue(this)
        var url="http://192.168.3.72/APIFinAK/registro.php?id=1"
        var jsonObjectRequest =  JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener{ response ->
                try {
                    var jsonArray:JSONArray =response.getJSONArray("data")
                    for(i in 0 until jsonArray.length() ){
                        var jsonObject = jsonArray.getJSONObject(i)
                        val registro = LayoutInflater.from(this).inflate(R.layout.table_row_ak, null, false)
                        val colNombre = registro.findViewById<View>(R.id.colNombre) as TextView
                        val colIngreso =registro.findViewById<View>(R.id.colIngresos) as TextView
                        val colGasto = registro.findViewById<View>(R.id.colGastos) as TextView
                        val colEditar = registro.findViewById<View>(R.id.colEditar)
                        val colBorrar = registro.findViewById<View>(R.id.colBorrar)
                        if (i == 0){
                            colNombre.text="Nombre"
                            colIngreso.text="Ingreso"
                            colGasto.text="Gasto"

                            tbMovimientos?.addView(registro)
                        }else {
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
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                }
            },Response.ErrorListener { error ->
            })
        queue.add(jsonObjectRequest)
    }
    fun clickbtnEditar(view: View){
        Toast.makeText(this,view.id.toString(),Toast.LENGTH_LONG).show()
    }
    fun clickbtnBorrar(view: View){
        Toast.makeText(this,view.id.toString(),Toast.LENGTH_LONG).show()
    }

}