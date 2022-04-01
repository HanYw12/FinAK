package com.example.finak


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(){

    var txtMovimiento : EditText?=null
    var txtCantidad : EditText?=null
    var txtTipoMovimiento : Spinner?=null
    var txtAño : EditText?=null
    var txtMes : EditText?=null
    var cad : CheckBox?=null
    var spiTipo : Spinner?=null
    var id : String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        id = intent.getStringExtra("id")
        if (id!=null){
            val btnInsertar=findViewById<Button>(R.id.Insertar)
            btnInsertar.visibility= View.GONE
            val btnEditar = findViewById<Button>(R.id.Editar)
            btnEditar.visibility= View.VISIBLE
            val btnInsNew = findViewById<Button>(R.id.InsNew)
            btnInsNew.visibility= View.VISIBLE
            txtMovimiento=findViewById(R.id.Movimiento)
            txtCantidad=findViewById(R.id.Cantidad)
            cad=findViewById(R.id.Fijos)
            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.3.72/APIFinAK/ingreso.php?MovId=$id"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET
            ,url,null,Response.Listener{
                response->
                    txtMovimiento?.setText(response.getString("MovNombre"))
                    txtCantidad?.setText(response.getString("MovCantidad"))
//                    datos Spinner
                    val Spin = response.getString("MovTipo")
                    if(Spin=="Gasto"){
                        spiTipo = findViewById(R.id.TipoMovimientoDown)
                        spiTipo?.setSelection(1)
                    }else{
                        spiTipo?.setSelection(0)
                    }
                    var cad2 = response.getString("MovFijo")
                    cad?.isChecked = cad2.toString()=="1"
                    val Año = findViewById<TextView>(R.id.Año)
                    val Mes = findViewById<TextView>(R.id.Mes)
                    if (cad2.toString()=="1" ){
                        Año.visibility= View.VISIBLE
                        Mes.visibility= View.VISIBLE
                    }else {
                        Año.visibility = View.GONE
                        Mes.visibility = View.GONE
                    }

            },Response.ErrorListener{
                error ->
            })
            queue.add(jsonObjectRequest)
        }

    }
//cambio de pantalla
    fun clickBtnTabla(view: View){
        val intent:Intent = Intent(this,ActivityTable::class.java)
        startActivity(intent)
    }
    fun clickbtnEditar(view: View) {
        id = intent.getStringExtra("id")

        Toast.makeText(this, id.toString(), Toast.LENGTH_LONG).show()

        val url="http://192.168.3.72/APIFinAK/editar.php"
        val queue = Volley.newRequestQueue(this)
        txtMovimiento = findViewById<TextView>(R.id.Movimiento) as EditText?

//CheckBox
        cad = findViewById(R.id.Fijos)
        val cad2 = cad.toString()
        txtCantidad = findViewById<TextView>(R.id.Cantidad) as EditText?
//sacar de spinner informacion 1
        txtTipoMovimiento= findViewById(R.id.TipoMovimientoDown)
        txtAño = findViewById<TextView>(R.id.Año) as EditText?
        txtMes = findViewById<TextView>(R.id.Mes) as EditText?
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
                parametros.put("id",txtMes?.text.toString())

                return parametros
            }
        }
        queue.add(resultadoPost)



    }
    fun clickBtnInsertar (view: View){
        val url="http://192.168.3.72/APIFinAK/insertar.php"
        val queue = Volley.newRequestQueue(this)
        txtMovimiento = findViewById<TextView>(R.id.Movimiento) as EditText?

//CheckBox
        cad = findViewById(R.id.Fijos)
        val cad2 = cad.toString()
        txtCantidad = findViewById<TextView>(R.id.Cantidad) as EditText?
//sacar de spinner informacion 1
        txtTipoMovimiento= findViewById(R.id.TipoMovimientoDown)
        txtAño = findViewById<TextView>(R.id.Año) as EditText?
        txtMes = findViewById<TextView>(R.id.Mes) as EditText?
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
    fun clickBtnInsertarNew(view: View){
        var btnInsertar=findViewById<Button>(R.id.Insertar)
        btnInsertar.visibility= View.VISIBLE
        var btnEditar = findViewById<Button>(R.id.Editar)
        btnEditar.visibility= View.GONE
        var btnInsNew = findViewById<Button>(R.id.InsNew)
        btnInsNew.visibility= View.GONE

    }
}
