package com.example.QanLyNhaTro_DuAn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ManDangNhap extends AppCompatActivity {
    EditText edtUser,edtPass;
    Button button2;


    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);



//        List<NguoiDung> nguoiDungList=nguoiDungDao.dangNhap();
        edtUser=findViewById(R.id.edtUser);
        edtPass=findViewById(R.id.edtPass);
        button2=findViewById(R.id.button2);

        checkBox=findViewById(R.id.checkBox);
        this.load();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUser.getText().length() != 0 && edtPass.getText().length() != 0  ) {
                    if (edtUser.getText().toString().equals("quanlynhatro") && edtPass.getText().toString().equals("123")) {
                           Toast.makeText(ManDangNhap.this,"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(ManDangNhap.this,TrangChuActivity.class);
                           startActivity(intent);
                    }else {
                        Toast.makeText(ManDangNhap.this,"Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(ManDangNhap.this,"Mời bạn nhập đủ thông tin", Toast.LENGTH_SHORT).show();

                }
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