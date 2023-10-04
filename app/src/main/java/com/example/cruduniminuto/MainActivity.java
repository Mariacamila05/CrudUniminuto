package com.example.cruduniminuto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText etDocumento;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etUsuario;
    private EditText etPassword;
    private ListView userLista;
    private Button btactualizar;
    int idUser;
    String password;
    String documento;
    String nombre;
    String usuario;
    String apellido;

    View view;
    private GestionDB managementDB;

    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
    }

    private void initializer(){
        etDocumento = findViewById(R.id.etID);
        etUsuario = findViewById(R.id.etUsuario);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etPassword = findViewById(R.id.etPassword);
        userLista = findViewById(R.id.lvUsuarios);
        btactualizar = findViewById(R.id.btUpdate);
        btactualizar.setEnabled(false); // Deshabilitar el botón
        // Ocultar la lista
        userLista.setVisibility(View.INVISIBLE);
    }

    public void userList(){
        Interface userInterface = new Interface(this,findViewById(R.id.lvUsuarios));
        ArrayList<Entity> toList = userInterface.getListUsers();
        ArrayAdapter<Entity> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,
                toList);
        userLista.setAdapter(adapter);
    }
    public void btnList(View v){
        userLista.setVisibility(View.VISIBLE);
        this.userList();
    }
    public boolean setData(){
        //validaciones con regex
        Context context = this;
        this.documento = etDocumento.getText().toString().trim();
        this.nombre = etNombre.getText().toString().trim();
        this.apellido = etApellido.getText().toString().trim();
        this.usuario = etUsuario.getText().toString().trim();
        this.password = etPassword.getText().toString().trim();

        if (documento.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Fill all data", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // Patrones de expresiones regulares
            Pattern documentPattern = Pattern.compile("^[0-9]{10}$"); // Por ejemplo, documento de 10 dígitos
            Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{6,}$"); // Al menos 1 letra y 1 número, mínimo 6 caracteres
            Pattern userPattern = Pattern.compile("^[A-Za-z0-9]+$"); // Letras y números
            Pattern namePattern = Pattern.compile("^[A-Za-z]+$"); // Solo letras

            // Realizar las validaciones
            boolean isDocumentValid = documentPattern.matcher(documento).matches();
            boolean isNameValid = namePattern.matcher(nombre).matches();
            boolean isLastNameValid = namePattern.matcher(apellido).matches();
            boolean isUserValid = userPattern.matcher(usuario).matches();
            boolean isPasswordValid = passwordPattern.matcher(password).matches();

            // Verificar los resultados de las validaciones
            if (!isDocumentValid) {
                Toast.makeText(context, "invalid document", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!isNameValid) {
                Toast.makeText(context, "invalid name", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!isLastNameValid) {
                Toast.makeText(context, "invalid LastName", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!isUserValid) {
                Toast.makeText(context, "invalid User", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!isPasswordValid) {
                Toast.makeText(context, "invalid Password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
    public void userRegister(View view){
        if(setData()){
            Interface userInterface = new Interface(this,view);
            Entity userEntity = new Entity(this.idUser,this.documento,this.usuario,this.nombre,this.apellido,this.password);
            boolean insertionSuccessful = userInterface.insertUser(userEntity);

            if (insertionSuccessful) {
                // Limpiar los campos de EditText
                etDocumento.getText().clear();
                etNombre.getText().clear();
                etApellido.getText().clear();
                etUsuario.getText().clear();
                etPassword.getText().clear();
                userLista.setVisibility(View.VISIBLE);
            }
            this.userList();
        }
    }

    public String getDocument(){
        Context context = this;
        this.documento = etDocumento.getText().toString().trim();
        if (documento.isEmpty()){
            Toast.makeText(context, "Fill all data", Toast.LENGTH_SHORT).show();
            return "";
        }else{
            // Patrones de expresion regular document
            Pattern documentPattern = Pattern.compile("^[0-9]{10}$"); // Por ejemplo, documento de 10
            // Realizar la validacione
            boolean isDocumentValid = documentPattern.matcher(documento).matches();
            if (!isDocumentValid) {
                Toast.makeText(context, "invalid document", Toast.LENGTH_SHORT).show();
                return "";
            }
        }
        return this.documento = etDocumento.getText().toString().trim();
    }
    public void searchUser(View view){
        String validDocument = getDocument();
        if (!validDocument.isEmpty()) {
            Interface userInterface = new Interface(this, view);
            Optional<Entity> userEntityOptional = userInterface.getUser(validDocument);

            if (userEntityOptional.isPresent()) {
                Entity userEntity = userEntityOptional.get();
                etDocumento.setText(userEntity.getDocument());
                etNombre.setText(userEntity.getName());
                etApellido.setText(userEntity.getLastName());
                etUsuario.setText(userEntity.getUser());
                etPassword.setText(userEntity.getPassword());
                btactualizar.setEnabled(true);
                etDocumento.setEnabled(false);
                userLista.setVisibility(View.VISIBLE);
            } else {
                // Manejar el caso en que no se encuentre el usuario
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
            this.userList();
        }
    }

    public void updateUser(View view){
        if(setData()){
            Interface userInterface = new Interface(this,view);
            Entity userEntity = new Entity(this.idUser,this.documento,this.usuario,this.nombre,this.apellido,this.password);
            boolean updateSuccessful = userInterface.setNewUser(userEntity);

            if (updateSuccessful) {
                // Limpiar los campos de EditText
                etDocumento .getText().clear();
                etDocumento.setEnabled(true);
                etNombre.getText().clear();
                etApellido.getText().clear();
                etUsuario.getText().clear();
                etPassword.getText().clear();
                btactualizar.setEnabled(false);
                userLista.setVisibility(View.VISIBLE);
            }
            this.userList();
        }
    }
}