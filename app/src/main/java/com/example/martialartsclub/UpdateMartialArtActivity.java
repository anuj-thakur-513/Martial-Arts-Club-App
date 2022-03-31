package com.example.martialartsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.martialartsclub.model.DatabaseHelper;
import com.example.martialartsclub.model.MartialArt;

import java.util.ArrayList;

public class UpdateMartialArtActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_martial_art);

        databaseHelper = new DatabaseHelper(this);
        updateUserInterface();
    }

    private void updateUserInterface() {
        ArrayList<MartialArt> allMartialArtObjects = databaseHelper.returnAllMartialArtObjects();

        if(allMartialArtObjects.size() > 0){
            ScrollView scrollView = new ScrollView(this);
            GridLayout gridLayout = new GridLayout(this);
            // Setting the rows and columns of the grid layout
            gridLayout.setRowCount(allMartialArtObjects.size());
            gridLayout.setColumnCount(4);

            // Declaring the EditText fields and Buttons for the grid layout
            EditText[][] edtNamesPricesAndColors = new EditText[allMartialArtObjects.size()][4];
            Button[] updateButtons = new Button[allMartialArtObjects.size()];

            // Getting the screen size of the device
            Point screenSize = new Point();
            getWindowManager().getDefaultDisplay().getSize(screenSize);
            int screenWidth = screenSize.x;

            int index = 0;

            for (MartialArt martialArtObject :
                    allMartialArtObjects) {
                edtNamesPricesAndColors[index][0] = new EditText(this);
                edtNamesPricesAndColors[index][1] = new EditText(this);
                edtNamesPricesAndColors[index][2] = new EditText(this);

                // Setting the text in the EditText fields
                edtNamesPricesAndColors[index][0].setText(martialArtObject.getMartialArtName());
                edtNamesPricesAndColors[index][1].setText(String.format("%s", martialArtObject.getMartialArtPrice()));
                edtNamesPricesAndColors[index][2].setText(martialArtObject.getMartialArtColor());

                edtNamesPricesAndColors[index][1].setInputType(InputType.TYPE_CLASS_NUMBER);

                // Setting the ID for the EditText fields
                edtNamesPricesAndColors[index][0].setId(martialArtObject.getMartialArtId() + 10);
                edtNamesPricesAndColors[index][1].setId(martialArtObject.getMartialArtId() + 20);
                edtNamesPricesAndColors[index][2].setId(martialArtObject.getMartialArtId() + 30);

                // Setting the text in Buttons
                updateButtons[index] = new Button(this);
                updateButtons[index].setText("Update");
                updateButtons[index].setGravity(Gravity.CENTER);
                updateButtons[index].setId(martialArtObject.getMartialArtId());
                updateButtons[index].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateObject(martialArtObject);
                    }
                });

                // Adding the views to the Grid Layout
                gridLayout.addView(edtNamesPricesAndColors[index][0], (int) (screenWidth * 0.3),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(edtNamesPricesAndColors[index][1], (int) (screenWidth * 0.22),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(edtNamesPricesAndColors[index][2], (int) (screenWidth * 0.22),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(updateButtons[index], (int) (screenWidth * 0.26),
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                gridLayout.setPadding(20, 0, 20, 0);

                index++;
            }
            scrollView.addView(gridLayout);
            setContentView(scrollView);
        }
    }

    private void updateObject(MartialArt martialArtObject){

        EditText edtName = findViewById(martialArtObject.getMartialArtId() + 10);
        EditText edtPrice = findViewById(martialArtObject.getMartialArtId() + 20);
        EditText edtColor = findViewById(martialArtObject.getMartialArtId() + 30);

        try {
            String name = edtName.getText().toString();
            Double priceValue = Double.valueOf(edtPrice.getText().toString());
            String color = edtColor.getText().toString();
            databaseHelper.modifyMartialArtObject(martialArtObject.getMartialArtId(),
                    name, priceValue, color);
            Toast.makeText(UpdateMartialArtActivity.this, "Martial Art updated.", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(UpdateMartialArtActivity.this, "Please enter a valid value!", Toast.LENGTH_SHORT).show();
        }
    }
}