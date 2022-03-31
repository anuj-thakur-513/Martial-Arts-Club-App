package com.example.martialartsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.martialartsclub.model.DatabaseHelper;
import com.example.martialartsclub.model.MartialArt;

import java.util.ArrayList;

public class DeleteMartialArtActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_martial_art);

        databaseHelper = new DatabaseHelper(this);
        updateTheUserInterface();
    }

    private void updateTheUserInterface() {
        ArrayList<MartialArt> allMartialArtObjects = databaseHelper.returnAllMartialArtObjects();

        // Creating the Layout programmatically
        RelativeLayout relativeLayout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);

        // Create a RadioGroup to hold the RadioButtons in it
        RadioGroup radioGroup = new RadioGroup(this);

        // Creating RadioButtons for all the MartialArtObjects using a for each loop
        for (MartialArt martialArt :
                allMartialArtObjects) {
            RadioButton currentRadioButton = new RadioButton(this);
            currentRadioButton.setId(martialArt.getMartialArtId());
            currentRadioButton.setText(martialArt.toString());

            // Add radio buttons to the radio group
            radioGroup.addView(currentRadioButton);
        }
            // Removing the Martial Art from the database
            radioGroup.setOnCheckedChangeListener(this);

            scrollView.addView(radioGroup);

            ViewGroup.LayoutParams scrollViewParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );

            relativeLayout.addView(scrollView, scrollViewParams);

            setContentView(relativeLayout);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        databaseHelper.deleteMartialArtObjectFromDatabaseById(id);
        Toast.makeText(this, "Martial Art deleted.", Toast.LENGTH_SHORT).show();
        updateTheUserInterface();
    }
}