package com.example.finak


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
//cambio de pantalla
    fun clickBtnTabla(view: View){
        val intent:Intent = Intent(this,ActivityTable::class.java)
        startActivity(intent)
    }

    fun clickBtnInsertar (view: View){
        val url="http://192.168.3.72/APIFinAK/insertar.php"
        val queue = Volley.newRequestQueue(this)
        val txtMovimiento =findViewById<TextView>(R.id.Movimiento)
//CheckBox
        val cad = findViewById<CheckBox>(R.id.Fijos).isChecked
        val cad2 = cad.toString()
        Toast.makeText(this, cad2, Toast.LENGTH_SHORT).show()
        val txtCantidad =findViewById<TextView>(R.id.Cantidad)
//sacar de spinner informacion 1
        val txtTipoMovimiento= findViewById<Spinner>(R.id.TipoMovimientoDown)
        val txtAño = findViewById<TextView>(R.id.Año)
        val txtMes = findViewById<TextView>(R.id.Mes)
        var resultadoPost = object : StringRequest(Request.Method.POST,url,
            Response.Listener{response ->
                Toast.makeText(this, "Movimiento ingresado",Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(this,"error $error",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()
                parametros.put("nombre",txtMovimiento?.text.toString())
                //sacar de spinner informacion 2
                parametros.put("tipo",txtTipoMovimiento?.selectedItem.toString())
                parametros.put("cantidad",txtCantidad?.text.toString())
                //sacar Checkbox
                parametros.put("fijo", cad2)
                parametros.put("año",txtAño?.text.toString())
                parametros.put("mes",txtMes?.text.toString())
                parametros.put("usuario",txtMes?.text.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
    // checkbox de tiempo
    fun checkBoxClick(view: View) {
        val Fijos: CheckBox = view as CheckBox
        Toast.makeText(this, "Ingrese el tiempo fijo" , Toast.LENGTH_SHORT).show()
        val Año = findViewById<TextView>(R.id.Año)
        val Mes = findViewById<TextView>(R.id.Mes)
        if (Fijos.isChecked ){
            Año.visibility= View.VISIBLE
            Mes.visibility= View.VISIBLE
        }else {
            Año.visibility = View.GONE
            Mes.visibility = View.GONE
        }
    }
}
