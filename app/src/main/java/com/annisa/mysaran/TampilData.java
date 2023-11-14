package com.annisa.mysaran;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilData extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING;

    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        getJSON();

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TampilData.this, TambahData.class));
                finish();
            }
        });
    }

    private void showDataSaran() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new
                ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result =
                    jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID);
                String nama = jo.getString(Konfigurasi.TAG_NAMA);
                String saran = jo.getString(Konfigurasi.TAG_SARAN);
                String kelas = jo.getString(Konfigurasi.TAG_KELAS);
                String jurusan = jo.getString(Konfigurasi.TAG_JURUSAN);


                HashMap<String, String> lihatdatasaran = new HashMap<>();
                lihatdatasaran.put(Konfigurasi.TAG_ID, id);
                lihatdatasaran.put(Konfigurasi.TAG_NAMA, nama);
                lihatdatasaran.put(Konfigurasi.TAG_SARAN, saran);
                lihatdatasaran.put(Konfigurasi.TAG_KELAS, kelas);
                lihatdatasaran.put(Konfigurasi.TAG_JURUSAN, jurusan);

                list.add(lihatdatasaran);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilData.this, list, R.layout.list_item,
                new String[]{Konfigurasi.TAG_ID, Konfigurasi.TAG_NAMA, Konfigurasi.TAG_SARAN,Konfigurasi.TAG_KELAS,Konfigurasi.TAG_JURUSAN},
                new int[]{R.id.idIsiSaran, R.id.tv_nama, R.id.tv_saran, R.id.tv_kelas, R.id.tv_jurusan});

        listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilData.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDataSaran();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_SARAN);
                return s;
            }

        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilDetail.class);
        HashMap <String, String> map = (HashMap) parent.getItemAtPosition(position);
        String id_saran = map.get(Konfigurasi.TAG_ID).toString();

        intent.putExtra(Konfigurasi.KEY_ID, id_saran);

        startActivity(intent);
        finish();
    }


}