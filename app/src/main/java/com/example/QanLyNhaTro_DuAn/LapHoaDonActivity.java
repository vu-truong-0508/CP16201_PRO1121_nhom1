package com.example.QanLyNhaTro_DuAn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.QanLyNhaTro_DuAn.Database.Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LapHoaDonActivity extends AppCompatActivity {


    final String DATABASE_NAME = "QuanLyNhaTroNew.sqlite";
    SQLiteDatabase database;
    int maphong =-1;
    EditText edtSoNuocMoi_HoaDon,edtSoDienMoi_HoaDon,edtTienKhac,edtTienInterNet,edtLyDoThu;
    TextView txtTenPhong_HoaDon,txtTienPhong_HoaDon,txtSoNuocCu_HoaDon,txtTongSoNuoc_HoaDon,
            txtSoDienCu_HoaDon,txtSoNuocMoi_HoaDon,txtSoDienMoi_HoaDon,txtTongSoDien_HoaDon,txtNgayLap;
    Button btnLapHoaDon;
    Calendar cal = Calendar.getInstance();

    int day = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH) + 1;
    int year = cal.get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_hoa_don);
        AnhXa();
        setUI();
        btnLapHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LapHoaDon();
            }
        });
    }

    private void AnhXa()
    {
        edtSoNuocMoi_HoaDon = (EditText) findViewById(R.id.edtSoNuocMoi_HoaDon);
        edtSoDienMoi_HoaDon = (EditText) findViewById(R.id.edtSoDienMoi_HoaDon);
        edtTienKhac = (EditText) findViewById(R.id.edtTienDVKhac_HoaDon);
        edtTienInterNet = (EditText) findViewById(R.id.edtTienInternet_HoaDon);
        edtLyDoThu = (EditText) findViewById(R.id.edtLyDoThu);

        txtTenPhong_HoaDon =(TextView) findViewById(R.id.txtTenPhong_HoaDon);
        txtTienPhong_HoaDon =(TextView) findViewById(R.id.txtTienPhong_HoaDon);
        txtSoNuocCu_HoaDon =(TextView) findViewById(R.id.txtSoNuocCu_HoaDon);
        txtSoDienCu_HoaDon =(TextView) findViewById(R.id.txtSoDienCu_HoaDon);
        txtNgayLap =(TextView) findViewById(R.id.txtNgayLapHoaDon_HoaDon);

        btnLapHoaDon = (Button) findViewById(R.id.btnLapHoaDon);
    }

    private void setUI()
    {
        Intent intent1 = getIntent();
        maphong = intent1.getIntExtra("MaPhong",-1);
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor =  database.rawQuery("select * from Phong where MaPhong = ?",new String[]{maphong+""});
        cursor.moveToLast();
        String tenphong = cursor.getString(1);
        String tienphong =cursor.getString(3);
        String sonuoccu =cursor.getString(5);
        String sodiencu =cursor.getString(4);
        txtTenPhong_HoaDon.setText(tenphong);
        txtTienPhong_HoaDon.setText(tienphong);
        txtSoNuocCu_HoaDon.setText(sonuoccu);
        txtSoDienCu_HoaDon.setText(sodiencu);
        cal=Calendar.getInstance();//lấy ngày hiện tại của hệ thống
        SimpleDateFormat dft=null;
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());//Định dạng ngày / tháng /năm
        String strDate=dft.format(cal.getTime());
        txtNgayLap.setText(strDate);//hiển thị lên giao diện
    }
    private void LapHoaDon()
    {
        Intent intent1 = getIntent();
        maphong = intent1.getIntExtra("MaPhong",-1);

        database= Database.initDatabase(LapHoaDonActivity.this,DATABASE_NAME);
        String tienphong = txtTienPhong_HoaDon.getText().toString();
        final String sodienmoi = edtSoDienMoi_HoaDon.getText().toString();
        String sonuocmoi = edtSoNuocMoi_HoaDon.getText().toString();
        String internet = edtTienInterNet.getText().toString();
        String dvkhac = edtTienKhac.getText().toString();
        String lydothu = edtLyDoThu.getText().toString();
        String nl  = "01"+"/"+month+"/"+year;




        String trangthai = "0";

       /* Cursor cursor3 = database.rawQuery("select * from ChiTietHoaDon where MaPhong = ?",new String[]{maphong+""});
        cursor3.moveToLast();
        String thangcu = cursor3.getString(7);
        String thangtruoc = thangcu.substring(3,5);*/


        Cursor cursor1 = database.rawQuery("select * from BangGia where Ma = ?",new String[]{1+""});
        cursor1.moveToFirst();
        int giadien = cursor1.getInt(1);
        int gianuoc = cursor1.getInt(2);

        Cursor cursor = database.rawQuery("select * from Phong where MaPhong = ?",new String[]{maphong+""});
        cursor.moveToFirst();
        int sodiencu = cursor.getInt(4);
        int sonuoccu = cursor.getInt(5);

        long TienDien = (long) (Integer.valueOf(sodienmoi)  - sodiencu) * giadien;
        long TienNuoc = (long) (Integer.valueOf(sonuocmoi)  - sonuoccu) * gianuoc;
        long TongTienThang = (long) Long.valueOf(tienphong) + TienDien + TienNuoc + Long.valueOf(internet)
                + Long.valueOf(dvkhac);

       /* if(thangnay.equals(thangtruoc))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(LapHoaDonActivity.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Phòng này đã lập hóa đơn của tháng "+thangnay+" rồi!");

            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog =builder.create();
            dialog.show();
        }*/
        ContentValues contentValues = new ContentValues();
        contentValues.put("TienPhong",tienphong);
        contentValues.put("TienDien",TienDien);
        contentValues.put("TienNuoc",TienNuoc);
        contentValues.put("TienInternet",internet);
        contentValues.put("TienDVKhac",dvkhac);
        contentValues.put("LyDoThu",lydothu);
        contentValues.put("NgayLapHoaDon",nl);
        contentValues.put("TrangThai",trangthai);
        contentValues.put("MaPhong",maphong);
        contentValues.put("TongTien",TongTienThang);
        contentValues.put("SoDien",Integer.valueOf(sodienmoi)  - sodiencu);
        contentValues.put("SoNuoc",Integer.valueOf(sonuocmoi)  - sonuoccu);

        database = Database.initDatabase(this,DATABASE_NAME);
        database.insert("ChiTietHoaDon",null,contentValues);
        update(maphong,sodienmoi,sonuocmoi);
        Intent intent = new Intent(LapHoaDonActivity.this, DanhSachHoaDonActivity.class);
        intent.putExtra("MaPhong",maphong);
        startActivity(intent);
        Toast.makeText(LapHoaDonActivity.this,"Lập hóa đơn thành công",Toast.LENGTH_LONG).show();
        finish();
    }

    public void update(int maphong,String sodien,String sonuoc)
    {
        SQLiteDatabase database= Database.initDatabase(LapHoaDonActivity.this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from Phong where MaPhong = ?",new String[]{maphong+""});
        cursor.moveToFirst();
        String tenphong = cursor.getString(1);
        String lau = cursor.getString(2);
        String tiencoc = cursor.getString(3);
        String trangthai = cursor.getString(6);

        ContentValues contentValues = new ContentValues();
        contentValues.put("TenPhong",tenphong);
        contentValues.put("Lau",lau);
        contentValues.put("TienCoc",tiencoc);
        contentValues.put("SoDien",sodien);
        contentValues.put("SoNuoc",sonuoc);
        contentValues.put("TrangThai",trangthai);
        database = Database.initDatabase(LapHoaDonActivity.this,DATABASE_NAME);
        database.update("Phong",contentValues,"MaPhong = ?",new String[]{maphong+""});
    }
}