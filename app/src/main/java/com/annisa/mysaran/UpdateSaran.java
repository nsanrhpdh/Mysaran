package com.annisa.mysaran;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateSaran extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_nama_edit;
    private EditText edit_saran_edit;
    private AutoCompleteTextView dropdown_kelas_edit;
    private AutoCompleteTextView dropdown_jurusan_edit;
    private EditText tgl_IsiSaran_edit;
    private EditText tgl_UpSaran_edit;
    String item1;

    String item2;


    String[] items = {"RPL","SIJA","MEKA","TOI","TEK","IOP","TEI","PSPT","TPTUP"};

    String[] items2 = {"X-A","X-B","X-C","XI-A","XI-B","XI-C","XII-A","XII-B","XII-C"};

    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems2;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    private Button buttonUpdate;
    private Button buttonView;
    private String id_saran;
    private String nama;
    private String saran;
    private String kelas;
    private String jurusan;
    private String tgl_IsiSaran;
    private String tgl_UpSaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_saran);

        Intent intent = getIntent();

        id_saran = intent.getStringExtra(Konfigurasi.KEY_ID);


        edit_nama_edit = (EditText) findViewById(R.id.edit_nama_edit);
        edit_saran_edit = (EditText) findViewById(R.id.edit_saran_edit);
        dropdown_kelas_edit = (AutoCompleteTextView) findViewById(R.id.dropdown_kelas_edit);
        dropdown_jurusan_edit = (AutoCompleteTextView) findViewById(R.id.dropdown_jurusan_edit);
        tgl_IsiSaran_edit = (EditText) findViewById(R.id.tgl_IsiSaran_edit);
        tgl_UpSaran_edit = (EditText) findViewById(R.id.tgl_UpSaran_edit);


        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonView = (Button) findViewById(R.id.buttonView);

        buttonUpdate.setOnClickListener(this);
        buttonView.setOnClickListener(this);

        edit_nama_edit.setText(nama);
        edit_saran_edit.setText(saran);
        dropdown_kelas_edit.setText(kelas);
        dropdown_jurusan_edit.setText(jurusan);
        tgl_IsiSaran_edit.setText(tgl_IsiSaran);
        adapterItems2 = new ArrayAdapter<String>(this,R.layout.list_menu,items2);
        dropdown_kelas_edit.setAdapter(adapterItems2);
        dropdown_kelas_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item1,Toast.LENGTH_SHORT).show();
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        tgl_IsiSaran_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {showDateDialog();
            }
        });


        adapterItems = new ArrayAdapter<String>(this,R.layout.list_menu,items);
        dropdown_jurusan_edit.setAdapter(adapterItems);

        dropdown_jurusan_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item2,Toast.LENGTH_SHORT).show();
            }
        });



        getSiswa();
    }

    private void getSiswa(){
        class GetSiswa extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateSaran.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showSiswa(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_GET_DETAIL,id_saran);
                return s;
            }
        }
        GetSiswa ge = new GetSiswa();
        ge.execute();
    }

    private void showSiswa(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String nama = c.getString(Konfigurasi.TAG_NAMA);
            String saran = c.getString(Konfigurasi.TAG_SARAN);
            String kelas = c.getString(Konfigurasi.TAG_KELAS);
            String jurusan = c.getString(Konfigurasi.TAG_JURUSAN);
            String IsiSaran = c.getString(Konfigurasi.TAG_ISISARAN);


            edit_nama_edit.setText(nama);
            edit_saran_edit.setText(saran);
            dropdown_kelas_edit.setText(kelas);
            dropdown_jurusan_edit.setText(jurusan);
            tgl_IsiSaran_edit.setText(IsiSaran);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateSiswa(){
        final String nama = edit_nama_edit.getText().toString();
        final String saran = edit_saran_edit.getText().toString();
        final String kelas = dropdown_kelas_edit.getText().toString();
        final String jurusan = dropdown_jurusan_edit.getText().toString();
        final String tgl_IsiSaran = tgl_IsiSaran_edit.getText().toString();
        final String tgl_UpSaran = tgl_UpSaran_edit.getText().toString();


        class UpdateSiswa extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateSaran.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateSaran.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.KEY_ID,id_saran);
                hashMap.put(Konfigurasi.KEY_NAMA,nama);
                hashMap.put(Konfigurasi.KEY_SARAN,saran);
                hashMap.put(Konfigurasi.KEY_KELAS,kelas);
                hashMap.put(Konfigurasi.KEY_JURUSAN,jurusan);
                hashMap.put(Konfigurasi.KEY_ISISARAN,tgl_IsiSaran);
                hashMap.put(Konfigurasi.KEY_UPSARAN,tgl_UpSaran);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi.URL_UPDATE_SARAN,hashMap);

                return s;
            }
        }

        UpdateSiswa ue = new UpdateSiswa();
        ue.execute();
    }



    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateSiswa();
        }

        if(v == buttonView){
            Intent i = new Intent(getApplicationContext(), TampilData.class);
            i.putExtra(Konfigurasi.KEY_ID, id_saran);
            startActivity(i);
            finish();
        }
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                tgl_UpSaran_edit.setText(dateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }



    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), TampilData.class);
        startActivity(i);
        finish();
    }
}