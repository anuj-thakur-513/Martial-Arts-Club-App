package com.example.martialartsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.martialartsclub.model.DatabaseHelper;
import com.example.martialartsclub.model.MartialArt;

public class AddMartialArtActivity extends AppCompatActivity {

    //    Variable Declaration
    EditText edtMartialArtName, edtMartialArtPrice, edtMartialArtColor;
    Button btnAddMartialArt;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_martial_art);

        edtMartialArtName = (EditText) findViewById(R.id.edt_martial_art_name);
        edtMartialArtPrice = (EditText) findViewById(R.id.edt_martial_art_price);
        edtMartialArtColor = (EditText) findViewById(R.id.edt_martial_art_color);
        btnAddMartialArt = (Button) findViewById(R.id.btn_add_martial_art);

        databaseHelper = new DatabaseHelper(this);

        // Checking whether the text fields are empty or not
        edtMartialArtName.addTextChangedListener(btnTextWatcher);
        edtMartialArtPrice.addTextChangedListener(btnTextWatcher);
        edtMartialArtColor.addTextChangedListener(btnTextWatcher);
        btnAddMartialArt.setEnabled(false);

        // onClickListener for the Add the Martial Art Button
        btnAddMartialArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMartialArtObjectToDatabase();
                Toast.makeText(AddMartialArtActivity.this, "Martial Art added.", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    Method to add the entered martial art in the SQLite Database
    private void addMartialArtObjectToDatabase(){
        String martialArtName = edtMartialArtName.getText().toString();
        String martialArtPrice = edtMartialArtPrice.getText().toString();
        String martialArtColor = edtMartialArtColor.getText().toString();

        try {
            double priceValue = Double.parseDouble(martialArtPrice);
            Log.i("price", String.valueOf(priceValue));
            MartialArt martialArtObject = new MartialArt(0, martialArtName, priceValue, martialArtColor);

            databaseHelper.addMartialArt(martialArtObject);
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Please enter a valid value!", Toast.LENGTH_SHORT).show();
        }
    }

    // TextWatcher to check whether the textFields are empty or not
    private TextWatcher btnTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = edtMartialArtName.getText().toString().trim();
            String priceInput = edtMartialArtPrice.getText().toString().trim();
            String colorInput = edtMartialArtColor.getText().toString().trim();
            btnAddMartialArt.setEnabled(!nameInput.isEmpty() && !priceInput.isEmpty() && !colorInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}