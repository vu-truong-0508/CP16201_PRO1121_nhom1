package com.example.QanLyNhaTro_DuAn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.QanLyNhaTro_DuAn.Database.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CapNhatGiaDienNuocActivity extends AppCompatActivity {

    EditText edtGiaDien,edtGiaNuoc;
    Button btnCapNhat;
    SQLiteDatabase database;
    final String DATABASE_NAME = "QuanLyNhaTroNew.sqlite";
    FloatingActionButton ftbTrangChu, ftbHoaDon, ftbPhong, ftbBangGia;
    Animation tren, trai, xeo,back_trai,back_tren,back_xeo;
    boolean trove = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_gia_dien_nuoc);
        AnhXa();
        setUI();
        ThaoTac();

    }



    private void setUI()
    {
        int ma = 1;
        database= Database.initDatabase(CapNhatGiaDienNuocActivity.this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from BangGia where Ma = ?",new String[]{ma+""});
        cursor.moveToFirst();
        String giadien = cursor.getString(1);
        String gianuoc = cursor.getString(2);
        edtGiaDien.setText(giadien);
        edtGiaNuoc.setText(gianuoc);
    }
    private void SuaGia()
    {
        int ma =1;
        String giadien = edtGiaDien.getText().toString();
        String gianuoc = edtGiaNuoc.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("GiaDien",giadien);
        contentValues.put("GiaNuoc",gianuoc);

        SQLiteDatabase database = Database.initDatabase(CapNhatGiaDienNuocActivity.this,DATABASE_NAME);
        database.update("BangGia",contentValues,"Ma = ?",new String[]{ma+""});
        Intent intent = new Intent(CapNhatGiaDienNuocActivity.this, TrangChuActivity.class);
        startActivity(intent);
        Toast.makeText(CapNhatGiaDienNuocActivity.this,"Cập nhật giá thành công",Toast.LENGTH_LONG).show();
        finish();
    }
    private void ThaoTac()
    {
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaGia();
            }
        });

        ftbPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapNhatGiaDienNuocActivity.this,DanhSachPhongActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ftbBangGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapNhatGiaDienNuocActivity.this, CapNhatGiaDienNuocActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ftbHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapNhatGiaDienNuocActivity.this, DanhSachHoaDonActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ftbTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trove == false)
                {
                    move();
                    trove= true;
                }
                else
                {
                    Back();
                    trove=false;
                }
            }
        });
    }

    private void AnhXa()
    {
        edtGiaDien =(EditText) findViewById(R.id.edtgiadien);
        edtGiaNuoc =(EditText) findViewById(R.id.edtgianuoc);
        btnCapNhat =(Button) findViewById(R.id.btnCapNhatDienNuoc);

        ftbTrangChu = findViewById(R.id.ftbTrangChu);
        ftbHoaDon = findViewById(R.id.ftbHoaDon);
        ftbPhong = findViewById(R.id.ftbPhong);
        ftbBangGia = findViewById(R.id.ftbBangGia);

        trai = AnimationUtils.loadAnimation(this, R.anim.trai);
        tren = AnimationUtils.loadAnimation(this, R.anim.tren);
        xeo = AnimationUtils.loadAnimation(this, R.anim.xeo);

        back_trai = AnimationUtils.loadAnimation(this, R.anim.back_trai);
        back_tren = AnimationUtils.loadAnimation(this, R.anim.back_tren);
        back_xeo = AnimationUtils.loadAnimation(this, R.anim.back_xeo);
    }

    private void move() {
        FrameLayout.LayoutParams paramsTrai = (FrameLayout.LayoutParams) ftbPhong.getLayoutParams();
        paramsTrai.rightMargin = (int) (ftbPhong.getWidth() * 1.7);
        ftbPhong.setLayoutParams(paramsTrai);
        ftbPhong.startAnimation(trai);

        FrameLayout.LayoutParams paramsTren = (FrameLayout.LayoutParams) ftbBangGia.getLayoutParams();
        paramsTren.bottomMargin = (int) (ftbBangGia.getWidth() * 1.7);
        ftbBangGia.setLayoutParams(paramsTren);
        ftbBangGia.startAnimation(tren);

        FrameLayout.LayoutParams paramsXeo = (FrameLayout.LayoutParams) ftbHoaDon.getLayoutParams();
        paramsXeo.bottomMargin = (int) (ftbHoaDon.getWidth() * 1.3);
        paramsXeo.rightMargin = (int) (ftbHoaDon.getWidth() * 1.3);
        ftbHoaDon.setLayoutParams(paramsXeo);
        ftbHoaDon.startAnimation(xeo);
    }
    private void Back()
    {
        FrameLayout.LayoutParams paramsTrai = (FrameLayout.LayoutParams) ftbPhong.getLayoutParams();
        paramsTrai.rightMargin -= (int) (ftbPhong.getWidth() * 1.4);
        ftbPhong.setLayoutParams(paramsTrai);
        ftbPhong.startAnimation(back_trai);

        FrameLayout.LayoutParams paramsTren = (FrameLayout.LayoutParams) ftbBangGia.getLayoutParams();
        paramsTren.bottomMargin -= (int) (ftbBangGia.getWidth() * 1.4);
        ftbBangGia.setLayoutParams(paramsTren);
        ftbBangGia.startAnimation(back_tren);

        FrameLayout.LayoutParams paramsXeo = (FrameLayout.LayoutParams) ftbHoaDon.getLayoutParams();
        paramsXeo.bottomMargin -= (int) (ftbHoaDon.getWidth() * 1);
        paramsXeo.rightMargin -= (int) (ftbHoaDon.getWidth() * 1);
        ftbHoaDon.setLayoutParams(paramsXeo);
        ftbHoaDon.startAnimation(back_xeo);
    }
}