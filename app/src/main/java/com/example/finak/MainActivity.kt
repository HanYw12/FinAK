package com.example.finak


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



//        BtnInsertar.setOnClickListener {
//            Toast.makeText(this, "A ingresado "+txtMovimiento.text , Toast.LENGTH_SHORT).show()
//        }
    }
    fun clickBtnInsertar (view: View){
        val url="http://192.168.3.72/APIFinAK/insertar.php"
        val queue = Volley.newRequestQueue(this)

        val txtMovimiento =findViewById<TextView>(R.id.Movimiento)
        val txtCantidad =findViewById<TextView>(R.id.Cantidad)

//sacar de spinner informacion 1
        val txtTipoMovimiento= findViewById<Spinner>(R.id.TipoMovimientoDown)

        val checkFijo = findViewById<CheckBox>(R.id.Fijos)
        val fijo = checkFijo?.isChecked

        val txtAño = findViewById<TextView>(R.id.Año)
        val txtMes = findViewById<TextView>(R.id.Mes)
        val BtnInsertar = findViewById<Button>(R.id.Insertar) as Button


        var resultadoPost = object : StringRequest(Request.Method.POST,url,
            Response.Listener{response ->
            Toast.makeText(this,"Movimiento insertado",Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(this,"error $error",Toast.LENGTH_LONG).show()
            }){
                override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()
                parametros.put("nombre",txtMovimiento?.text.toString())
  //sacar de spinner informacion 2
                parametros.put("tipo",txtTipoMovimiento?.selectedItem.toString())

                parametros.put("cantidad",txtCantidad?.text.toString())
   //Checkbox

                parametros.put("fijo",checkFijo?.text.toString())
                parametros.put("año",txtAño?.text.toString())
                parametros.put("mes",txtMes?.text.toString())
                parametros.put("usuario",txtMes.text.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)


    }



// Ver tiempo movimiento
    fun checkBoxClick (view: View)
    {
        val Fijos: CheckBox = view as CheckBox
        val activado: Boolean = Fijos.isChecked
        val Año = findViewById<TextView>(R.id.Año)
        val Mes = findViewById<TextView>(R.id.Mes)
        if (activado == true ){
            Año.visibility= View.VISIBLE
            Mes.visibility= View.VISIBLE
        }else {
            Año.visibility = View.GONE
            Mes.visibility = View.GONE
        }
    }
}



    
