package com.example.QanLyNhaTro_DuAn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.QanLyNhaTro_DuAn.Database.Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThemNguoiThueActivity extends AppCompatActivity {

    final String DATABASE_NAME = "QuanLyNhaTroNew.sqlite";

    SQLiteDatabase database;

    EditText edtTen,edtCMND,edtSDT,edtPhongThue,edtNgaySinh,edtNgayThue;
    Button btnThemKhachThue,btnChonNgaySinh,btnChonNgayThue;

    RadioButton rdoNam,rdoNu;
    RadioGroup rdoGioiTinh;


    Date dateFinish;
    Date hourFinish;

    Calendar cal;
    int maphong = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_thue);
        AnhXa();
        getDefaultInfor();
        addEnvent();

    }

    private void addEnvent()
    {

        btnThemKhachThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddKhach();
            }
        });
        btnChonNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogNgaySinh();
            }
        });
        btnChonNgayThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogNgayThue();
            }
        });

    }

    private void AnhXa()
    {
        edtTen = (EditText) findViewById(R.id.edtTenNguoiThue);
        edtSDT = (EditText)findViewById(R.id.edtSDTNguoiThue);
        edtCMND = (EditText)findViewById(R.id.edtCMND);
        edtPhongThue = (EditText)findViewById(R.id.edtPhongThue);
        edtNgaySinh = (EditText) findViewById(R.id.edtNgaySinh);
        edtNgayThue = (EditText) findViewById(R.id.edtNgayThue);
        btnThemKhachThue =(Button)findViewById(R.id.btnThemNguoiThue);
        btnChonNgaySinh = (Button)findViewById(R.id.btnChonNgaySinh);
        btnChonNgayThue = (Button)findViewById(R.id.btnChonNgayThue);

        rdoGioiTinh = (RadioGroup) findViewById(R.id.rdoGioiTinh);


        Intent intent = getIntent();
        maphong = intent.getIntExtra("MaPhong1",-1);


        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor =  database.rawQuery("select * from Phong where MaPhong = ?",new String[]{maphong+""});
        cursor.moveToFirst();
        String tenphong = cursor.getString(1);
        edtPhongThue.setText(tenphong);


    }

    private void AddKhach()
    {
        String ten = edtTen.getText().toString();
        String ngaysinh = edtNgaySinh.getText().toString();
        String sdt = edtSDT.getText().toString();
        String cmnd = edtCMND.getText().toString();
        String ngaythue = edtNgayThue.getText().toString();

        int gioitinh = 0;
        int i = rdoGioiTinh.getCheckedRadioButtonId();
        switch (i)
        {
            case R.id.rdoNam:
                gioitinh = 1;
                break;
            case R.id.rdoNu:
                gioitinh = 0;
                break;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put("TenKhach",ten);
        if(gioitinh == 1)
        {
            contentValues.put("GioiTinh",1);
        }
        else{
            contentValues.put("GioiTinh",0);
        }
        contentValues.put("NgaySinh",ngaysinh);
        contentValues.put("CMND",cmnd);
        contentValues.put("SDT",sdt);
        contentValues.put("NgayThue",ngaythue);

        Intent intent = getIntent();
        maphong = intent.getIntExtra("MaPhong1",-1);

        contentValues.put("MaPhong",String.valueOf(maphong));

        database = Database.initDatabase(this,DATABASE_NAME);
        database.insert("KhachThue",null,contentValues);
        update(maphong);
        Intent intent1 = new Intent(this, XemThanhVienActivity.class);
        intent1.putExtra("MaPhong",maphong);
        startActivity(intent1);
        Toast.makeText(ThemNguoiThueActivity.this, "Th??m ng?????i thu?? th??nh c??ng", Toast.LENGTH_LONG).show();
        onPause();
    }

    protected void onPause(){
        super.onPause();
        finish();
    }

    /*public String getDateFormat(Date d)
    {
        SimpleDateFormat dft=new
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dft.format(d);
    }*/
    public void getDefaultInfor()
    {
        //l???y ng??y hi???n t???i c???a h??? th???ng
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        //?????nh d???ng ng??y / th??ng /n??m
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        //hi???n th??? l??n giao di???n
        edtNgayThue.setText(strDate);
        edtNgaySinh.setText(strDate);

        dateFinish=cal.getTime();
        hourFinish=cal.getTime();
    }
    /**
     * H??m hi???n th??? DatePicker dialog
     */
    public void showDatePickerDialogNgaySinh()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //M???i l???n thay ?????i ng??y th??ng n??m th?? c???p nh???t l???i TextView Date
                edtNgaySinh.setText(
                        (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                /*txtNgayThue.setText(
                        (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);*/
                //L??u v???t l???i bi???n ng??y ho??n th??nh
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };
        //c??c l???nh d?????i n??y x??? l?? ng??y gi??? trong DatePickerDialog
        //s??? gi???ng v???i tr??n TextView khi m??? n?? l??n
        String s=edtNgaySinh.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                ThemNguoiThueActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Ch???n ngay sinh");
        pic.show();

    }
    public void showDatePickerDialogNgayThue()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //M???i l???n thay ?????i ng??y th??ng n??m th?? c???p nh???t l???i TextView Date

                edtNgayThue.setText(
                        (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                //L??u v???t l???i bi???n ng??y ho??n th??nh
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };
        //c??c l???nh d?????i n??y x??? l?? ng??y gi??? trong DatePickerDialog
        //s??? gi???ng v???i tr??n TextView khi m??? n?? l??n
        String s=edtNgayThue.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                ThemNguoiThueActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Ch???n ngay thue");
        pic.show();

    }
    public void update(int maphong)
    {
        database= Database.initDatabase(ThemNguoiThueActivity.this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from Phong where MaPhong = ?",new String[]{maphong+""});
        cursor.moveToFirst();
        String tenphong = cursor.getString(1);
        String lau = cursor.getString(2);
        String tiencoc = cursor.getString(3);
        String sodien = cursor.getString(4);
        String sonuoc = cursor.getString(5);

        ContentValues contentValues = new ContentValues();
        contentValues.put("TenPhong",tenphong);
        contentValues.put("Lau",lau);
        contentValues.put("TienCoc",tiencoc);
        contentValues.put("SoDien",sodien);
        contentValues.put("SoNuoc",sonuoc);
        contentValues.put("trangthai",1);
        database = Database.initDatabase(ThemNguoiThueActivity.this,DATABASE_NAME);
        database.update("Phong",contentValues,"maphong=?",new String[]{maphong+""});
    }
}