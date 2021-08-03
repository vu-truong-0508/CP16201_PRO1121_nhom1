package com.example.QanLyNhaTro_DuAn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.QanLyNhaTro_DuAn.DAO.NguoiDungDao;
import com.example.QanLyNhaTro_DuAn.Model.NguoiDung;
import com.example.QanLyNhaTro_DuAn.R;
import com.example.QanLyNhaTro_DuAn.SQLite.MySQLite;


import java.util.List;

public class ManDangNhap extends AppCompatActivity {
    EditText edtUser,edtPass;
    Button button2;
    TextView tvDangky;
    MySQLite mySQLite;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        mySQLite=new MySQLite(ManDangNhap.this);


//        List<NguoiDung> nguoiDungList=nguoiDungDao.dangNhap();
        edtUser=findViewById(R.id.edtUser);
        edtPass=findViewById(R.id.edtPass);
        button2=findViewById(R.id.button2);
        tvDangky=findViewById(R.id.tvDangky1);
        checkBox=findViewById(R.id.checkBox);
        this.load();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguoiDungDao nguoiDungDao=new NguoiDungDao(mySQLite);

                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                List<NguoiDung> nguoiDungList=nguoiDungDao.dangNhap(user,pass);


                if (nguoiDungList.size()==0){
                    edtUser.setError("Vui Lòng Kiểm Tra Lại Tài Khoảm và Mật Khẩu");
                }else {
                    for (NguoiDung s:nguoiDungList){
                        if (s.username.equals(user) && s.pass.equals(pass)){
                            Intent intent=new Intent(ManDangNhap.this,TrangChuActivity.class);
                            intent.putExtra("uss",user);
                            intent.putExtra("pass",pass);
                            startActivity(intent);
                            Toast.makeText(ManDangNhap.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            edtUser.setError("Vui lòng kiểm tra lại tài khoản và mật khẩu");

                        }
                    }
                }
            }

        });

        tvDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManDangNhap.this,DangKi.class);
                startActivity(intent);
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("abc",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();

                String us=edtUser.getText().toString().trim();
                String pass=edtPass.getText().toString().trim();

                editor.putString("us",us);
                editor.putString("pass",pass);
                editor.commit();
            }
        });
    }

    public void load(){
        SharedPreferences sharedPreferences=getSharedPreferences("abc",MODE_PRIVATE);
        String user=sharedPreferences.getString("us",null);
        String pass=sharedPreferences.getString("pass",null);

        edtUser.setText(String.valueOf(user));
        edtPass.setText(String.valueOf(pass));

    }
}