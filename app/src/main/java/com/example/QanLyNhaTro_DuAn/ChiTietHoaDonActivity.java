package com.example.QanLyNhaTro_DuAn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.QanLyNhaTro_DuAn.Database.Database;

import java.text.NumberFormat;
import java.util.Locale;

public class ChiTietHoaDonActivity extends AppCompatActivity {

    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
    final String DATABASE_NAME = "QuanLyNhaTroNew.sqlite";
    SQLiteDatabase database;
    int macthd =-1;
    TextView txtTenPhong_CTHD,txtNgayLapHoaDOn_CTHD,txtTienPhong_CTHD,txtSoNuoc_CTHD,txtGiaNuoc_CTHD,
            txtTienNuoc_CTHD,txtSoDien_CTHD,txtGiaDien_CTHD,txtTienDien_CTHD,txtTienInternet_CTHD,
            txtTienDVKhac_CTHD,txtLyDoThu_CTHD,txtTongTien_CTHD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        AnhXa();
        setUI();
    }

    private void AnhXa()
    {
        txtTenPhong_CTHD = (TextView) findViewById(R.id.txtTenPhong_CTHD);
        txtNgayLapHoaDOn_CTHD = (TextView) findViewById(R.id.txtNgayLapHoaDOn_CTHD);
        txtTienPhong_CTHD = (TextView) findViewById(R.id.txtTienPhong_CTHD);
        txtSoNuoc_CTHD = (TextView) findViewById(R.id.txtSoNuoc_CTHD);
        txtGiaNuoc_CTHD = (TextView) findViewById(R.id.txtGiaNuoc_CTHD);
        txtTienNuoc_CTHD = (TextView) findViewById(R.id.txtTienNuoc_CTHD);
        txtSoDien_CTHD = (TextView) findViewById(R.id.txtSoDien_CTHD);
        txtGiaDien_CTHD = (TextView) findViewById(R.id.txtGiaDien_CTHD);
        txtTienDien_CTHD = (TextView) findViewById(R.id.txtTienDien_CTHD);
        txtTienInternet_CTHD = (TextView) findViewById(R.id.txtTienInternet_CTHD);
        txtTienDVKhac_CTHD = (TextView) findViewById(R.id.txtTienDVKhac_CTHD);
        txtLyDoThu_CTHD = (TextView) findViewById(R.id.txtLyDoThu_CTHD);
        txtTongTien_CTHD = (TextView) findViewById(R.id.txtTongTien_CTHD);

    }

    private void setUI()
    {
        Intent intent = getIntent();
        macthd = intent.getIntExtra("MaCTHD",-1);
        SQLiteDatabase database= Database.initDatabase(ChiTietHoaDonActivity.this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from ChiTietHoaDon where MaCTHD = ?",new String[]{macthd+""});
        cursor.moveToFirst();
        long tienphong = Long.valueOf(cursor.getString(1));
        String str1 = currencyVN.format(tienphong);

        long tiendien = Long.valueOf(cursor.getString(2));
        String str2 = currencyVN.format(tiendien);

        long tiennuoc = Long.valueOf(cursor.getString(3));
        String str3 = currencyVN.format(tiennuoc);


        long tieninternet = Long.valueOf(cursor.getString(4));
        String str4 = currencyVN.format(tieninternet);


        long tiendvkhac = Long.valueOf(cursor.getString(5));
        String str5 = currencyVN.format(tiendvkhac);


        String lydothu = cursor.getString(6);
        String ngaylaphoadon = cursor.getString(7);
        String trangthai = cursor.getString(8);
        String maphong = cursor.getString(9);
        long tongtien = Long.valueOf(cursor.getString(10));
        String str10 = currencyVN.format(tongtien);
        String sodien = cursor.getString(11);
        String sonuoc = cursor.getString(12);


        Cursor cursor1 = database.rawQuery("select * from Phong where MaPhong = ?",new String[]{maphong+""});
        cursor1.moveToFirst();
        String tenphong = cursor1.getString(1);

        Cursor cursor2 = database.rawQuery("select * from BangGia where Ma = ?",new String[]{1+""});
        cursor2.moveToFirst();
        String giadien = cursor2.getString(1);
        String gianuoc = cursor2.getString(2);

        txtTenPhong_CTHD.setText(tenphong);
        txtNgayLapHoaDOn_CTHD.setText(ngaylaphoadon);

        txtTienPhong_CTHD.setText(str1);
        txtSoNuoc_CTHD.setText(sonuoc);
        txtGiaNuoc_CTHD.setText(gianuoc);
        txtTienNuoc_CTHD.setText(str3);
        txtSoDien_CTHD.setText(sodien);
        txtGiaDien_CTHD.setText(giadien);
        txtTienDien_CTHD.setText(str2);
        txtTienInternet_CTHD.setText(str4);
        txtTienDVKhac_CTHD.setText(str5);
        txtLyDoThu_CTHD.setText(lydothu);
        txtTongTien_CTHD.setText(str10);


    }
}