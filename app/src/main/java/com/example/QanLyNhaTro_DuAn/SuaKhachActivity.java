package com.example.QanLyNhaTro_DuAn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.QanLyNhaTro_DuAn.Database.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SuaKhachActivity extends AppCompatActivity {

    final String DATABASE_NAME = "QuanLyNhaTroNew.sqlite";
    int makhach = -1;
    SQLiteDatabase database;
    EditText edtTenKhach,edtNgaySinh,edtCMND,edtSDT,edtNgayThue;
    Spinner spnGioiTinh,spnPhongThue;
    Button btnSua,btnChonNgaySinh,btnChonNgayThue;
    RadioGroup rdoGioiTinh;
    RadioButton rdoNam,rdoNu;
    Date dateFinish;
    Date hourFinish;

    Calendar cal;
    int maphong = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_khach);
        AnhXa();
        initUI();
        getDefaultInfor();
        addEnvents();

    }

    private void addEnvents()
    {
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sua();
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
        edtTenKhach = (EditText) findViewById(R.id.edtTenNguoiThueSua);
        edtNgaySinh = (EditText)findViewById(R.id.edtNgaySinhSua);
        edtCMND = (EditText)findViewById(R.id.edtCMNDSua);
        edtSDT = (EditText)findViewById(R.id.edtSDTNguoiThueSua);
        edtNgayThue = (EditText)findViewById(R.id.edtNgayThueSua);

        btnChonNgaySinh = (Button)findViewById(R.id.btnChonNgaySinhSua);
        btnChonNgayThue = (Button)findViewById(R.id.btnChonNgayThueSua);
        btnSua =(Button)findViewById(R.id.btnSuaNguoiThue);

        rdoGioiTinh = (RadioGroup) findViewById(R.id.rdoGioiTinhSua) ;
        rdoNam = (RadioButton) findViewById(R.id.rdoNamSua) ;
        rdoNu = (RadioButton) findViewById(R.id.rdoNuSua) ;

        spnPhongThue = (Spinner) findViewById(R.id.spnPhongThueSua) ;
        ArrayList<String> arrPhong = new ArrayList<String>();
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor =  database.rawQuery("select * from Phong ",null);
        for(int i = 0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            String tenphong = cursor.getString(1);
            arrPhong.add(tenphong);
        }
        ArrayAdapter arrayAdapter1= new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrPhong);
        spnPhongThue.setAdapter(arrayAdapter1);
    }

    private void initUI()
    {
        Bundle bundle = getIntent().getExtras();
        makhach = bundle.getInt("MaKhach");
        SQLiteDatabase database= Database.initDatabase(SuaKhachActivity.this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from KhachThue where MaKhach = ?",new String[]{makhach+""});
        cursor.moveToFirst();
        String tenkhach = cursor.getString(1);
        String gioitinh = cursor.getString(2);
        String ngaysinh = cursor.getString(3);
        String cmnd = cursor.getString(4);
        String sdt = cursor.getString(5);
        String ngaythue = cursor.getString(6);
        int maphong = cursor.getInt(7);

        edtTenKhach.setText(tenkhach);
        edtNgaySinh.setText(ngaysinh);
        edtCMND.setText(cmnd);
        edtSDT.setText(sdt);
        edtNgayThue.setText(ngaythue);


    }

    private void Sua()
    {
        database = Database.initDatabase(this,DATABASE_NAME);
        String tenkhach = edtTenKhach.getText().toString();
        String ngaysinh = edtNgaySinh.getText().toString();
        String cmnd = edtCMND.getText().toString();
        String sdt = edtSDT.getText().toString();
        String ngaythue = edtNgayThue.getText().toString();
        String tenphong = spnPhongThue.getSelectedItem().toString();
        int gioitinh = 0;
        int i = rdoGioiTinh.getCheckedRadioButtonId();
        switch (i)
        {
            case R.id.rdoNamSua:
                gioitinh = 1;
                break;
            case R.id.rdoNuSua:
                gioitinh = 0;
                break;
        }
        Cursor cursor1 =  database.rawQuery("select * from Phong where TenPhong = ?",new String[]{tenphong});
        cursor1.moveToFirst();
        int maphong1 = cursor1.getInt(0);

        ContentValues contentValues = new ContentValues();
        contentValues.put("TenKhach",tenkhach);
        contentValues.put("CMND",cmnd);
        contentValues.put("SDT",sdt);
        contentValues.put("NgaySinh",ngaysinh);
        contentValues.put("NgayThue",ngaythue);
        if(gioitinh == 1)
        {
            contentValues.put("GioiTinh",1);
        }
        else{
            contentValues.put("GioiTinh",0);
        }
        contentValues.put("MaPhong",String.valueOf(maphong1));

        Bundle bundle = getIntent().getExtras();
        makhach = bundle.getInt("MaKhach");
        int maphongcu = bundle.getInt("MaPhong");

        SQLiteDatabase database = Database.initDatabase(SuaKhachActivity.this,DATABASE_NAME);
        database.update("KhachThue",contentValues,"MaKhach = ?",new String[]{makhach+""});

        if(Check(maphong1)==true)
        {
            update(maphong1);
        }
        if(Check(maphongcu)==false)
        {
            update1(maphongcu);
        }
        Intent intent1 = new Intent(SuaKhachActivity.this, XemThanhVienActivity.class);
        intent1.putExtra("MaPhong",maphong1);
        startActivity(intent1);

        Toast.makeText(SuaKhachActivity.this, "Sửa khách thành công", Toast.LENGTH_LONG).show();
        onPause();
    }
    public void getDefaultInfor()
    {
        //lấy ngày hiện tại của hệ thống
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        //hiển thị lên giao diện


        dateFinish=cal.getTime();
        hourFinish=cal.getTime();
    }
    public void showDatePickerDialogNgaySinh()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                edtNgaySinh.setText((dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };

        String s = edtNgaySinh.getText()+"";
        String strArrtmp[] = s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                SuaKhachActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngay sinh");
        pic.show();

    }
    public void showDatePickerDialogNgayThue()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date

                edtNgayThue.setText(
                        (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=edtNgayThue.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                SuaKhachActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngay thue");
        pic.show();

    }
    protected void onPause(){
        super.onPause();
        finish();
    }
    private boolean Check(int maphong)
    {
        boolean KT = false;
        int a = 0;
        SQLiteDatabase database= Database.initDatabase(SuaKhachActivity.this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from KhachThue",null);
        for(int i = 0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            if(cursor.getInt(7) == maphong)
            {
                a++;
            }
        }
        if(a > 0)
            return KT = true;
        return KT = false;
    }
    public void update(int maphong)
    {
        SQLiteDatabase database= Database.initDatabase(SuaKhachActivity.this,DATABASE_NAME);
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
        contentValues.put("trangthai","1");
        database = Database.initDatabase(SuaKhachActivity.this,DATABASE_NAME);
        database.update("Phong",contentValues,"maphong=?",new String[]{maphong+""});
    }
    public void update1(int maphong)
    {
        SQLiteDatabase database= Database.initDatabase(SuaKhachActivity.this,DATABASE_NAME);
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
        contentValues.put("trangthai","0");
        database = Database.initDatabase(SuaKhachActivity.this,DATABASE_NAME);
        database.update("Phong",contentValues,"maphong=?",new String[]{maphong+""});
    }
}