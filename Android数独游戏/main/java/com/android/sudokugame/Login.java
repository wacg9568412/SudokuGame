package com.android.sudokugame;

import com.android.sudokugame.DBhelper.DBHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener
{
    private EditText name;
    private EditText password;
    private Button regester;
    private Button login;
    DBHelper db;
    Bundle b_context = new Bundle();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        name =(EditText) findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);
        regester =(Button)findViewById(R.id.regest);
        regester.setOnClickListener(this);
        db = new DBHelper(this);
    }
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId())
        {
            case R.id.login:
                if(name.getText().length() == 0)
                {
                    Toast user_empty = Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_LONG);
                    user_empty.show();
                    return;
                }
                if(password.getText().length() == 0)
                {
                    Toast password_empty =Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_LONG);
                    password_empty.show();
                    return;
                }
                Cursor cursor =  db.selectByNAME(name.getText().toString());
                if(cursor.moveToNext())
                {
                    String _name = cursor.getString(1);
                    String _password = cursor.getString(2);

                    if(_name.equals(name.getText().toString())&&_password.equals(password.getText().toString()))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_LONG);
                        toast.show();
                        Intent game_index =new Intent(this, ShuDuGame.class);
                        finish();
                        db.close();
                        cursor.close();
                        startActivity(game_index);
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "用户名或密码不正确", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "用户名不存在", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                break;
            case R.id.regest:

                if(name.getText().length() == 0)
                {
                    Toast user_empty = Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_LONG);
                    user_empty.show();
                    return;
                }
                else
                    b_context.putString("username", name.getText().toString());


                if(password.getText().length() == 0)
                {
                    Toast password_empty =Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_LONG);
                    password_empty.show();
                    return;
                }
                else
                    b_context.putString("password", password.getText().toString());
                Log.v("TAG","right input");
                Intent intent=new Intent();
                intent.putExtra("context", b_context);
                intent.setClass(Login.this, RegisterUser.class);
                startActivity(intent);
                break;
        }
    }

}
