package com.annisa.mysaran;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TampilDetail extends AppCompatActivity implements View.OnClickListener{

    private TextView outNama, outSaran, outKelas, outJurusan, outIsiSaran, outUpSaran;

    private Button buttonUpdate;

    private FloatingActionButton buttonDelete;

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
        setContentView(R.layout.activity_tampil_detail);

        Intent intent = getIntent();

        id_saran = intent.getStringExtra(Konfigurasi.KEY_ID);
        nama = intent.getStringExtra(Konfigurasi.KEY_NAMA);
        saran = intent.getStringExtra(Konfigurasi.KEY_SARAN);
        kelas = intent.getStringExtra(Konfigurasi.KEY_KELAS);
        jurusan = intent.getStringExtra(Konfigurasi.KEY_JURUSAN);
        tgl_IsiSaran = intent.getStringExtra(Konfigurasi.KEY_ISISARAN);
        tgl_UpSaran = intent.getStringExtra(Konfigurasi.KEY_UPSARAN);

        outNama = (TextView) findViewById(R.id.outNama);
        outSaran = (TextView) findViewById(R.id.outSaran);
        outKelas = (TextView) findViewById(R.id.outKelas);
        outJurusan = (TextView) findViewById(R.id.outJurusan);
        outIsiSaran = (TextView) findViewById(R.id.outIsiSaran);
        outUpSaran = (TextView) findViewById(R.id.outUpSaran);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (FloatingActionButton) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        outNama.setText(nama);
        outSaran.setText(saran);
        outKelas.setText(kelas);
        outJurusan.setText(jurusan);
        outIsiSaran.setText(tgl_IsiSaran);
        outUpSaran.setText(tgl_UpSaran);

        getDetail();
    }

    private void getDetail(){
        class GetSaran extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilDetail.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDetail(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_GET_DETAIL,id_saran);
                return s;
            }
        }
        GetSaran ge = new GetSaran();
        ge.execute();
    }

    private void showDetail(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String nama = c.getString(Konfigurasi.TAG_NAMA);
            String kelas = c.getString(Konfigurasi.TAG_KELAS);
            String jurusan = c.getString(Konfigurasi.TAG_JURUSAN);
            String tgl_IsiSaran= c.getString(Konfigurasi.TAG_ISISARAN);
            String saran = c.getString(Konfigurasi.TAG_SARAN);
            String tgl_UpSaran = c.getString(Konfigurasi.TAG_UPSARAN);


            outNama.setText(nama);
            outSaran.setText(saran);
            outKelas.setText(kelas);
            outJurusan.setText(jurusan);
            outIsiSaran.setText(tgl_IsiSaran);
            outUpSaran.setText(tgl_UpSaran);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void deleteSiswa(){
        class DeleteSiswa extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilDetail.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilDetail.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_DELETE_SARAN, id_saran);
                return s;
            }
        }

        DeleteSiswa de = new DeleteSiswa();
        de.execute();
    }

    private void confirmDeleteSiswa(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteSiswa();
                        startActivity(new Intent(TampilDetail.this,TampilData.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if(view == buttonUpdate){
            Intent i = new Intent(getApplicationContext(), UpdateSaran.class);
            i.putExtra(Konfigurasi.KEY_ID, id_saran);
            startActivity(i);
            finish();
        }

        if(view == buttonDelete){
            confirmDeleteSiswa();
        }
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), TampilData.class);
        startActivity(i);
        finish();
    }
}