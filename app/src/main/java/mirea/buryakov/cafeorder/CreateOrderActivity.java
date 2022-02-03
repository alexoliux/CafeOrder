package mirea.buryakov.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private String name;
    private String password;
    private String drink;
    private StringBuilder builderAddtions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        if (intent.hasExtra("name") && intent.hasExtra("password")){
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        } else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }
        drink = getString(R.string.tea);
        textViewHello = findViewById(R.id.textViewHello);
        String hello = String.format(getString(R.string.hello_user), name);
        textViewHello.setText(hello);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        String additions = String.format(getString(R.string.additions),drink);
        textViewAdditions.setText(additions);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        builderAddtions = new StringBuilder();
    }

    public void onClickChangeOrder(View view) {
        RadioButton button = (RadioButton) view;
        int id =  button.getId();
        if (id == R.id.radioButtonTea){
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
            String additions = String.format(getString(R.string.additions),drink);
            textViewAdditions.setText(additions);
        } else if ( id == R.id.radioButtonCoffee){
            drink = getString(R.string.coffee);
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffee.setVisibility(View.VISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
            String additions = String.format(getString(R.string.additions),drink);
            textViewAdditions.setText(additions);
        }

    }

    public void sendOrder(View view) {
        builderAddtions.setLength(0);
        if (checkBoxMilk.isChecked()) {
            builderAddtions.append(getString(R.string.milk)).append(" ");
        }
        if (checkBoxSugar.isChecked()) {
            builderAddtions.append(getString(R.string.sugar)).append(" ");
        }
        if (checkBoxLemon.isChecked() && drink.equals(getString(R.string.tea))) {
            builderAddtions.append(getString(R.string.lemon)).append(" ");
        }
        String  optionOfDrink = "";
        if (drink.equals(getString(R.string.tea))){
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        } else {
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order), name, password,drink, optionOfDrink);
        String additions;
        if (builderAddtions.length() > 0 ){
            additions ="\n" + getString(R.string.need_additions) + builderAddtions.toString();
        } else {
            additions = "";
        }
        String fullOrder = order + additions;
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order",fullOrder);
        startActivity(intent);
    }
}