package com.annisa.mysaran;

public class Konfigurasi {

    //Dibawah ini adalah Pengalamatan tempat tersimpannya Lokasi Skrip PHP
    //Alamatnya tertuju ke file PHP dimana PHP file tersebut disimpan
    //JANGAN LUPA gunakan IP SESUAI DENGAN IP SERVER atau nama domain ya
    public static final String URL_GET_SARAN="http://192.168.43.69/saran/data/saranlihatdata.php";
    public static final String URL_ADD="http://192.168.43.69/saran/data/sarantambah.php";
    public static final String URL_GET_DETAIL ="http://192.168.43.69/saran/data/tampilsaran.php?id_saran=";
    public static final String URL_UPDATE_SARAN ="http://192.168.43.69/saran/data/updatesaran.php";
    public static final String URL_DELETE_SARAN ="http://192.168.43.69/saran/data/hapussaran.php?id_saran=";

    public static final String URL_LOGIN = "http://192.168.43.69/saran/data/LoginActivity.php";
    public static final String URL_REGISTER = "http://192.168.43.69/saran/data/RegisterActivity.php";

    //public static final String URL_GET_SISWANIS ="http://itu.my.id/android_suhusiswa/data/tampilsiswanis.php?nis=";

    //Dibawah ini merupakan script atau perintah untuk mengirim permintaan ke dalam Skrip PHP
    public static final String KEY_ID = "id_saran";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_KELAS = "kelas";
    public static final String KEY_JURUSAN = "jurusan";
    public static final String KEY_ISISARAN = "tgl_IsiSaran";
    public static final String KEY_SARAN = "saran";
    public static final String KEY_UPSARAN = "tgl_UpSaran";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id_saran";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_KELAS = "kelas";
    public static final String TAG_JURUSAN = "jurusan";
    public static final String TAG_ISISARAN = "tgl_IsiSaran";
    public static final String TAG_SARAN = "saran";
    public static final String TAG_UPSARAN = "tgl_UpSaran";

}
