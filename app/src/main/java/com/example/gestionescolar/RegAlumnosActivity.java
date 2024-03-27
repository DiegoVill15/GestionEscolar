package com.example.gestionescolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegAlumnosActivity extends AppCompatActivity {

    private EditText et_matricula,et_nombre,et_carrera,et_InicioFin,et_NoMaterias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_alumnos);

        et_matricula=(EditText)findViewById(R.id.txt_matricula);
        et_nombre=(EditText)findViewById(R.id.txt_nombre);
        et_carrera=(EditText)findViewById(R.id.txt_carrera);
        et_InicioFin=(EditText)findViewById(R.id.txt_InicioFin);
        et_NoMaterias=(EditText)findViewById(R.id.txt_NoMaterias);

    }

    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, '4');


    //Alta de datos
    public void Registrar(View view){

        /*AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, '4');*/
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        String matricula= et_matricula.getText().toString();
        String nombre= et_nombre.getText().toString();
        String carrera= et_carrera.getText().toString();
        String InicioFin= et_InicioFin.getText().toString();
        String NoMaterias= et_NoMaterias.getText().toString();

        Cursor fila=BaseDeDatos.rawQuery
                ("select matricula from datoAlum where matricula="+matricula, null);
        if (fila.moveToFirst()){
            Toast.makeText(this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
        }else{
            if (!matricula.isEmpty() && !nombre.isEmpty()&&!carrera.isEmpty()&&!InicioFin.isEmpty()&&!NoMaterias.isEmpty()){
                ContentValues registro=new ContentValues();

                registro.put("matricula", matricula);
                registro.put("nombre",nombre);
                registro.put("carrera",carrera);
                registro.put("InicioFin",InicioFin);
                registro.put("NoMaterias",NoMaterias);

                BaseDeDatos.insert("datoAlum",null, registro);
                BaseDeDatos.close();

                et_matricula.setText("");
                et_nombre.setText("");
                et_carrera.setText("");
                et_InicioFin.setText("");
                et_NoMaterias.setText("");

                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        }




    }

    //Búsqueda de registros
    public void Buscar(View view){
       /* AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, '4');*/
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        String matricula=et_matricula.getText().toString();

        if (!matricula.isEmpty()){
            Cursor fila=BaseDeDatos.rawQuery
                    ("select nombre, carrera,InicioFin,NoMaterias from datoAlum where matricula="+matricula, null);

            if (fila.moveToFirst()){
                et_nombre.setText(fila.getString(0));
                et_carrera.setText(fila.getString(1));
                et_InicioFin.setText(fila.getString(2));
                et_NoMaterias.setText(fila.getString(3));
                BaseDeDatos.close();
            }else {
                Toast.makeText(this, "No existe la matricula", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else {
            Toast.makeText(this, "Debes introducir tu matricula", Toast.LENGTH_SHORT).show();
        }
    }

    //Eliminación de datos
    public void Eliminar(View view){
        /*AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, '4');*/
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String matricula = et_matricula.getText().toString();
        if (!matricula.isEmpty()){

            int cantidad = BaseDeDatos.delete("datoAlum", "matricula="+matricula,null);
            BaseDeDatos.close();

            et_matricula.setText("");
            et_nombre.setText("");
            et_carrera.setText("");
            et_InicioFin.setText("");
            et_NoMaterias.setText("");

            if (cantidad==1){
                Toast.makeText(this, "Registro Eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Debes introducir la matricula", Toast.LENGTH_SHORT).show();
        }

    }

    //Modificación de datos
    public void Modificar (View view){
        /*AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, '4');*/
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String matricula = et_matricula.getText().toString();
        String nombre= et_nombre.getText().toString();
        String carrera= et_carrera.getText().toString();
        String InicioFin= et_InicioFin.getText().toString();
        String NoMaterias= et_NoMaterias.getText().toString();

        if (!matricula.isEmpty()&& !nombre.isEmpty()&&!carrera.isEmpty()&&!InicioFin.isEmpty()&&!NoMaterias.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("matricula",matricula);
            registro.put("nombre",nombre);
            registro.put("carrera",carrera);
            registro.put("InicioFin",InicioFin);
            registro.put("NoMaterias",NoMaterias);

            int cantidad=BaseDeDatos.update("datoAlum",registro,"matricula ="+matricula,null);
            BaseDeDatos.close();

            et_matricula.setText("");
            et_nombre.setText("");
            et_carrera.setText("");
            et_InicioFin.setText("");
            et_NoMaterias.setText("");

            if (cantidad==1){
                Toast.makeText(this, "Registro modificado correctamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void RegresarMenu(View view){
        Intent RegresarMenu = new Intent(this, MainActivity.class);
        startActivity(RegresarMenu);
    }

}