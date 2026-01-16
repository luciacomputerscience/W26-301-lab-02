package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //declare the variables so that you will be able to reference them later
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Connect a list of cities to the UI
        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Build dialog for adding/deleting cities
        AlertDialog.Builder alertName = new AlertDialog.Builder(this);
        Button input_btn = findViewById(R.id.input_btn);
        final EditText editTextName1 = new EditText(MainActivity.this);

        alertName.setTitle("Submit City");
        alertName.setView(editTextName1);

        // Make the dialog window pop up when the "Submit Cities" button is pressed
        input_btn.setOnClickListener(view -> {
            showCityDialog();
        });
    }

    public String collectInput() {
        // This method collects the text the user wrote in the textbox

        String getInput = txt.getText().toString(); // sanitize

        // Make sure the user returns a valid string
        if (getInput.trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "Please select a city to add or delete", Toast.LENGTH_LONG).show();
        }
        else {
            return getInput;
        }
        return "Error";
    }

    public void addCity(String cityName) {
        // This method adds a city to the list

        if (cityName.equals("Error")) return;  // Don't add a bad input

        // Add to list if it doesn't already exist
        if (!dataList.contains(cityName)) {
            dataList.add(cityName);
            cityAdapter.notifyDataSetChanged();
        }
    }

    public void delCity(String cityName) {
        // This method deletes a city from the list

        if (cityName.equals("Error")) return;  // Don't add a bad input

        // Only attempt to delete a city that exists within the list
        if (dataList.contains(cityName)) {
            dataList.remove(cityName);
            cityAdapter.notifyDataSetChanged();
        }

        // Do nothing if the city was invalid
    }

    private void showCityDialog() {
        // This method shows the input dialog when the button on the homepage is pressed

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText input = new EditText(this);

        builder.setTitle("Submit City");
        builder.setView(input);

        // Positive button adds city
        builder.setPositiveButton("Add City", (dialog, which) -> {
            txt = input;
            String cityName = collectInput();
            addCity(cityName);
        });

        // Negative button deletes city
        builder.setNegativeButton("Delete City", (dialog, which) -> {
            txt = input;
            String cityName = collectInput();
            delCity(cityName);
        });

        builder.show();
    }

}