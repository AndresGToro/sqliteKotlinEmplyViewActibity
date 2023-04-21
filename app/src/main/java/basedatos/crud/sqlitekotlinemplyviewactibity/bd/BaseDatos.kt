package basedatos.crud.sqlitekotlinemplyviewactibity.bd

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import basedatos.crud.sqlitekotlinemplyviewactibity.objeto.Usuario

var BD = "baseDatos"

class BaseDatos(contexto: Context) : SQLiteOpenHelper(contexto, BD, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Crear tabla
        var sql = "CREATE TABLE USUARIO " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NOMBRE VARCHAR(255) NOT NULL," +
                " EDAD INTEGER NOT NULL)";
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertarDatos(usuario: Usuario): String {
        //
        val db = this.writableDatabase
        var contenedor = ContentValues();

        contenedor.put("NOMBRE", usuario.nombre)
        contenedor.put("EDAD", usuario.edad)

        var resultado = db.insert("USUARIO", null, contenedor)
        if (resultado == -1.toLong()) {
            return "existio una falla en la base de datos"

        } else {
            return "Insert (ok)"
        }
    }

    fun listarDatos(usuario: Usuario): MutableList<Usuario> {
        var lista: MutableList<Usuario> = ArrayList()
        val db = this.readableDatabase
        val sql = "SELECT * FROM USUARIO";
        var resultado = db.rawQuery("SELECT * FROM USUARIO", null)
        if (resultado.moveToFirst()) {
            do {
                var usu = Usuario();
                usu.id = resultado.getString(resultado.getColumnIndex()).toInt()
                usu.nombre = resultado.getString(resultado.getColumnIndex())
                usu.edad = resultado.getString(resultado.getColumnIndex()).toInt()
            } while (resultado.moveToNext())
            resultado.close()
            db.close();
            return lista
        }
    }//listar


    fun borrar(codigo: String) {
        if (codigo.length > 0) {
            val db = this.writableDatabase
            db.delete("USUARIO", "id=?", arrayOf(codigo))
            db.close()
        }
    }

    fun actualizar(id: String, nombre: String, edad: Int) {
        val db = this.writableDatabase
        var contenedordeValores = ContentValues();
        contenedordeValores.put("NOMBRE", nombre)
        contenedordeValores.put("EDAD", edad)
        var resultado = db.update("USUARIO", contenedordeValores, "id=?", arrayOf(id))
        if (resultado > 0) {
            return "Actualzacion realizada"
        } else {
            return "No se actualizo !!!"
        }
    }//actializar

}