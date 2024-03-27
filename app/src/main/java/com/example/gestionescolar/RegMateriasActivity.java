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

public class RegMateriasActivity extends AppCompatActivity {

    private  EditText et_idmateria, et_nombre, et_carrera, et_semestre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_materias);

        et_idmateria=(EditText)findViewById(R.id.txt_idmateria);
        et_nombre=(EditText)findViewById(R.id.txt_nombremat);
        et_carrera=(EditText)findViewById(R.id.txt_carreramat);
        et_semestre=(EditText)findViewById(R.id.txt_semestremat);

    }
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, '4');

    public void RegistroMat(View view){

        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        String idmateria =et_idmateria.getText().toString();
        String nombre = et_nombre.getText().toString();
        String carrera = et_carrera.getText().toString();
        String semestre = et_semestre.getText().toString();

        Cursor fila=BaseDeDatos.rawQuery
                ("select id_materia from datoMat where id_materia="+idmateria, null);
        if (fila.moveToFirst()){
            Toast.makeText(this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
        }else{

            if(!idmateria.isEmpty()&& !nombre.isEmpty() && !carrera.isEmpty() && !semestre.isEmpty()){

                ContentValues registro = new ContentValues();
                registro.put("id_materia", idmateria);
                registro.put("nombre", nombre);
                registro.put("carrera",carrera);
                registro.put("semestre",semestre);

                BaseDeDatos.insert("datoMat",null, registro);
                BaseDeDatos.close();

                et_idmateria.setText("");
                et_nombre.setText("");
                et_carrera.setText("");
                et_semestre.setText("");


                Toast.makeText(this,"Registro realizado con exito", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this,"Debes llenar todos los datos", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void BuscarMat(View view){

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String idmateria =et_idmateria.getText().toString();

        if (!idmateria.isEmpty()){
            Cursor fila=BaseDeDatos.rawQuery
                    ("select nombre, carrera,semestre from datoMat where id_materia="+idmateria, null);


            if (fila.moveToFirst()){
                et_nombre.setText(fila.getString(0));
                et_carrera.setText(fila.getString(1));
                et_semestre.setText(fila.getString(2));
                BaseDeDatos.close();
            }else {
                Toast.makeText(this, "No existe la materia", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else {
            Toast.makeText(this, "Debes introducir el id de la materia", Toast.LENGTH_SHORT).show();
        }
    }

    public void Modificar(View view){
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        String idmateria =et_idmateria.getText().toString();
        String nombre = et_nombre.getText().toString();
        String carrera = et_carrera.getText().toString();
        String semestre = et_semestre.getText().toString();

        if(!idmateria.isEmpty()&& !nombre.isEmpty() && !carrera.isEmpty() && !semestre.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("id_materia", idmateria);
            registro.put("nombre", nombre);
            registro.put("carrera",carrera);
            registro.put("semestre",semestre);

            int cantidad=BaseDeDatos.update("datoMat",registro,"id_materia ="+idmateria,null);
            BaseDeDatos.close();

            et_idmateria.setText("");
            et_nombre.setText("");
            et_carrera.setText("");
            et_semestre.setText("");


            if (cantidad==1){
                Toast.makeText(this, "Registro modificado correctamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View view){

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String idmateria =et_idmateria.getText().toString();

        if (!idmateria.isEmpty()){

            int cantidad = BaseDeDatos.delete("datoMat", "id_materia="+idmateria,null);
            BaseDeDatos.close();

            et_idmateria.setText("");
            et_nombre.setText("");
            et_semestre.setText("");
            et_carrera.setText("");

            if (cantidad==1){
                Toast.makeText(this, "Registro Eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Debes introducir la id de la materia", Toast.LENGTH_SHORT).show();
        }

    }



    public void RegresarMenu(View view){
        Intent RegresarMenu = new Intent(this, MainActivity.class);
        startActivity(RegresarMenu);
    }
}