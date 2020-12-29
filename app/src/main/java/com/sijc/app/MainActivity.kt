package com.sijc.app

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //boton que perite registrar datos hacia la tabla contacto
        button.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion", null,1)
            val datosDB = admin.writableDatabase
            val registro = ContentValues()
            registro.put("codigo",txt_cod.text.toString())
            registro.put("nombre", txt_nomb.text.toString())
            registro.put("fono", txt_fono.text.toString())
            datosDB.insert("contacto", null, registro)
            datosDB.close()
            txt_cod.text.clear()
            txt_nomb.text.clear()
            txt_fono.text.clear()
            Toast.makeText(this, "Datos regstrados correctamente!!",Toast.LENGTH_SHORT).show()
        }
        //boton que me permite realizar la consulta de datos a partir del codigo
        button2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val datosDB = admin.writableDatabase
            //objeto que permite realizar las consultas
            val consultasql = datosDB.rawQuery("select nombre,fono from contacto where codigo = ${txt_cod.text.toString()}",null)
            //extraer los datos y mostrar
            if (consultasql.moveToFirst()){
                txt_nomb.setText(consultasql.getString(0))
                txt_fono.setText(consultasql.getString(1))
            } else{
                Toast.makeText(this, "Codigo inexisttente",Toast.LENGTH_SHORT).show()
                datosDB.close()
            }
        }
        //boton que pemrite modificar los datos
        button3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val datosdb = admin.writableDatabase
            val actualizar = ContentValues()
            // obtener los datos
            actualizar.put("nombre",txt_nomb.text.toString())
            actualizar.put("fono",txt_fono.text.toString())
            // objeto que pemrite actualizr
            val datos = datosdb.update("contacto",actualizar,"codigo = ${txt_cod.text.toString()}",null)
            datosdb.close()
            txt_cod.text.clear()
            txt_nomb.text.clear()
            txt_fono.text.clear()
            if (datos == 1){
                Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Codigo invalido o inexisten", Toast.LENGTH_SHORT).show()
            }
        }
        //boton que permite elimnar los datos
        button4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion",null,1)
            val datosDB = admin.writableDatabase
            val eliminar = datosDB.delete("contacto","codigo = ${txt_cod.text.toString()}",null)
            datosDB.close()
            txt_cod.text.clear()
            txt_nomb.text.clear()
            txt_fono.text.clear()
            if (eliminar == 1)
                Toast.makeText(this, "Datos eliminados con e codigo", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Codigo no existe", Toast.LENGTH_SHORT).show()
        }
    }
}