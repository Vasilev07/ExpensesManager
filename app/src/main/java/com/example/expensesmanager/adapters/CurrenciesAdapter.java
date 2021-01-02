package com.example.expensesmanager.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensesmanager.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CurrenciesAdapter extends BaseAdapter {
    Context context;
    List<String> countryList;
    List<String> flags;
    List<Double> values;

    LayoutInflater inflter;

    public CurrenciesAdapter(Context applicationContext, List<String> countryList, List<String> flags, List<Double> values) {
        this.context = context;
        this.countryList = countryList;
        this.flags = flags;
        this.values = values;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.currency, null);
        TextView country = (TextView) convertView.findViewById(R.id.currency_name);
        TextView countryValue = (TextView) convertView.findViewById(R.id.currency_value);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.flag_image);

        country.setText(countryList.get(position));
        countryValue.setText(String.valueOf(values.get(position)));

        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(flags.get(position));
        return convertView;
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
