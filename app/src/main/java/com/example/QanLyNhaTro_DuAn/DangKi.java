package com.example.QanLyNhaTro_DuAn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.QanLyNhaTro_DuAn.DAO.NguoiDungDao;
import com.example.QanLyNhaTro_DuAn.Model.NguoiDung;
import com.example.QanLyNhaTro_DuAn.SQLite.MySQLite;

public class DangKi extends AppCompatActivity {
    String rphone="0\\d{9,10}";
    EditText edHoten,edUser,edPass,edPass1,edPhone;
    Button btnDangKy,button5;
    MySQLite mySQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        edHoten=findViewById(R.id.edHoten);
        edUser=findViewById(R.id.edUser);
        edPass=findViewById(R.id.edPass);
        edPass1=findViewById(R.id.edPass1);
        edPhone=findViewById(R.id.edPhone);
        mySQLite=new MySQLite(DangKi.this);
        btnDangKy=findViewById(R.id.btnDangKy);
        button5=findViewById(R.id.button5);


        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguoiDungDao nguoiDungDao=new NguoiDungDao(mySQLite);
                if (edHoten.getText().toString().trim().length()==0|| edPass.getText().toString().trim().length()==0||
                        edPass1.getText().toString().trim().length()==0|| edPhone.getText().toString().trim().length()==0){
                    Toast.makeText(DangKi.this, "Vui Lòng Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (edPass.getText().toString().trim().equals(edPass1.getText().toString().trim())){

                        if (!edPhone.getText().toString().matches(rphone)){
                            Toast.makeText(DangKi.this, "Sai định dạng số điện thoại", Toast.LENGTH_SHORT).show();
                            return;
                        }else {

                            NguoiDung nguoiDung=new NguoiDung();

                            nguoiDung.username=edUser.getText().toString().trim();
                            nguoiDung.name=edHoten.getText().toString().trim();
                            nguoiDung.pass=edPass.getText().toString().trim();
                            nguoiDung.phone=edPhone.getText().toString().trim();
                            nguoiDungDao.addND(nguoiDung);
                            Toast.makeText(DangKi.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                            Log.e("TC","TC");
                            startActivity(new Intent(DangKi.this,ManDangNhap.class));

                        }
                    }else {
                        Toast.makeText(DangKi.this, "Mật Khẩu Không Khớp", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKi.this,ManDangNhap.class));
            }
        });

    }
}