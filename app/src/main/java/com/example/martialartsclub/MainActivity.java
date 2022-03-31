package com.example.martialartsclub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import com.example.martialartsclub.model.DatabaseHelper;
import com.example.martialartsclub.model.MartialArt;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.martialartsclub.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private DatabaseHelper databaseHelper;
    private double totalMartialArtPrice;
    private ScrollView scrollView;
    private int martialArtButtonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        databaseHelper = new DatabaseHelper(this);
        totalMartialArtPrice = 0.0;
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        martialArtButtonWidth = screenSize.x / 2;
        updateUserInterface();
    }

    private void updateUserInterface() {
        ArrayList<MartialArt> allMartialArtObjects = databaseHelper.returnAllMartialArtObjects();

        scrollView.removeAllViewsInLayout();

        if(allMartialArtObjects.size() > 0){

            GridLayout gridLayout = new GridLayout(this);
            gridLayout.setRowCount((allMartialArtObjects.size() + 1) / 2);
            gridLayout.setColumnCount(2);

            MartialArtButton[] martialArtButtons = new MartialArtButton[allMartialArtObjects.size()];
            int index = 0;
            for (MartialArt martialArt :
                    allMartialArtObjects) {
                martialArtButtons[index] = new MartialArtButton(this, martialArt);
                martialArtButtons[index].setText(
                        String.format("%s\n%s", martialArt.getMartialArtName(),
                                martialArt.getMartialArtPrice()));
                martialArtButtons[index].setTextColor(Color.WHITE);

                switch(martialArt.getMartialArtColor()){
                    case "Red":
                        martialArtButtons[index].setBackgroundColor(Color.RED);
                        break;
                    case "Blue":
                        martialArtButtons[index].setBackgroundColor(Color.BLUE);
                        break;
                    case "Black":
                        martialArtButtons[index].setBackgroundColor(Color.BLACK);
                        break;
                    case "Yellow":
                        martialArtButtons[index].setBackgroundColor(Color.YELLOW);
                        martialArtButtons[index].setTextColor(Color.BLACK);
                        break;
                    case "Purple":
                        martialArtButtons[index].setBackgroundColor(Color.CYAN);
                        martialArtButtons[index].setTextColor(Color.BLACK);
                        break;
                    case "Green":
                        martialArtButtons[index].setBackgroundColor(Color.GREEN);
                        break;
                    default:
                        martialArtButtons[index].setBackgroundColor(Color.GRAY);
                }
                martialArtButtons[index].setOnClickListener(this::onClick);
                gridLayout.addView(martialArtButtons[index], martialArtButtonWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

            }
            scrollView.addView(gridLayout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.add_martial_art:
                Intent addMartialArtIntent = new Intent(this, AddMartialArtActivity.class);
                startActivity(addMartialArtIntent);
                return true;
            case R.id.delete_martial_art:
                Intent deleteMartialArtIntent = new Intent(this, DeleteMartialArtActivity.class);
                startActivity(deleteMartialArtIntent);
                return true;
            case R.id.update_martial_art:
                Intent updateMartialArtIntent = new Intent(this, UpdateMartialArtActivity.class);
                startActivity(updateMartialArtIntent);
                return true;
            case R.id.reset_martial_art_price:
                totalMartialArtPrice = 0.0;
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        MartialArtButton martialArtButton = (MartialArtButton) view;
        totalMartialArtPrice = totalMartialArtPrice + martialArtButton.getMartialArtPrice();
        String formattedMartialArtsPrice = NumberFormat.getCurrencyInstance().format(totalMartialArtPrice);
        Log.i("price", String.valueOf(totalMartialArtPrice));
        Log.i("price", formattedMartialArtsPrice);
        Toast.makeText(MainActivity.this, formattedMartialArtsPrice, Toast.LENGTH_SHORT).show();
    }
}