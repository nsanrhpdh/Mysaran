package com.annisa.mysaran;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import java.util.HashMap;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TambahData extends AppCompatActivity {

    private EditText editTextNama;
    private EditText editTextSaran;
    private Button buttonAdd;
    private Button buttonView;

    String item1;

    String item2;


    String[] items = {"RPL","SIJA","MEKA","TOI","TEK","IOP","TEI","PSPT","TPTUP"};

    String[] items2 = {"X-A","X-B","X-C","XI-A","XI-B","XI-C","XII-A","XII-B","XII-C"};

    EditText tgl_IsiSaran;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    AutoCompleteTextView dropdown_jurusan;
    AutoCompleteTextView dropdown_kelas;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        editTextNama = (EditText) findViewById(R.id.edt_nama);
        editTextSaran = (EditText) findViewById(R.id.edt_saran);

        dropdown_jurusan = (AutoCompleteTextView) findViewById(R.id.dropdown_jurusan);
        dropdown_kelas = (AutoCompleteTextView) findViewById(R.id.dropdown_kelas);
        tgl_IsiSaran = (EditText) findViewById(R.id.tgl_IsiSaran);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        tgl_IsiSaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {showDateDialog();
            }
        });

        adapterItems2 = new ArrayAdapter<String>(this,R.layout.list_menu,items2);
        dropdown_kelas.setAdapter(adapterItems2);

        dropdown_kelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item1,Toast.LENGTH_SHORT).show();
            }
        });


        adapterItems = new ArrayAdapter<String>(this,R.layout.list_menu,items);
        dropdown_jurusan.setAdapter(adapterItems);

        dropdown_jurusan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item2,Toast.LENGTH_SHORT).show();
            }
        });

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama = editTextNama.getText().toString();
                final String saran = editTextSaran.getText().toString();
                final String kelas = item1;
                final String jurusan = item2;
                final String tgl_saran = tgl_IsiSaran.getText().toString();

                class AddSaran extends AsyncTask<Void, Void, String> {

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(TambahData.this,
                                "Menambahkan...", "Tunggu...", false, false);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(TambahData.this, s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(Konfigurasi.KEY_NAMA, nama);
                        params.put(Konfigurasi.KEY_KELAS, kelas);
                        params.put(Konfigurasi.KEY_JURUSAN, jurusan);
                        params.put(Konfigurasi.KEY_ISISARAN, tgl_saran);
                        params.put(Konfigurasi.KEY_SARAN, saran);

                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                        return res;
                    }
                }

                AddSaran ae = new AddSaran();
                ae.execute();
            }
        });

        buttonView = findViewById(R.id.buttonView);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TambahData.this, TampilData.class));
                finish();
            }
        });
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                tgl_IsiSaran.setText(dateFormatter.format(newDate.getTime()));
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