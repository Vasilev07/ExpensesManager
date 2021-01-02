package com.example.expensesmanager.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanager.R;
import com.example.expensesmanager.adapters.CurrenciesAdapter;
import com.example.expensesmanager.models.Currency;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CurrencyInfoActivity extends AppCompatActivity {
    ListView listView;
    TextView currencyName;
    TextView currencyValue;
    ImageView image;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("currency_data");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(strObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject nvp = null;
        try {
            nvp = jsonObject.getJSONObject("nameValuePairs");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject rates = null;
        try {
            rates = nvp.getJSONObject("rates");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject ratesNVP = null;
        try {
            ratesNVP = rates.getJSONObject("nameValuePairs");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Iterator<String> keys = ratesNVP.keys();
        List<Currency> currencies = new ArrayList<>();
        while(keys.hasNext()) {
            String key = keys.next();
            try {
                String country = key.toLowerCase().substring(0, key.length() - 1);
                String flagURL = "https://www.countryflags.io/" + country + "/flat/64.png";
                Currency currency = new Currency(key, (Double) ratesNVP.get(key), flagURL);
                currencies.add(currency);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        setContentView(R.layout.currency_info);
        listView = findViewById(R.id.currency_data);
        currencyName = findViewById(R.id.currency_name);
        currencyValue = findViewById(R.id.currency_value);
        image = findViewById(R.id.flag_image);

        List<String> countryList = new ArrayList<>();
        List<String> flags = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (Currency currency : currencies) {
            countryList.add(currency.getName());
            flags.add(currency.getFlagURL());
            values.add(currency.getRate());
        }

        CurrenciesAdapter customAdapter = new CurrenciesAdapter(getApplicationContext(), countryList, flags, values);
        listView.setAdapter(customAdapter);
    }
}
