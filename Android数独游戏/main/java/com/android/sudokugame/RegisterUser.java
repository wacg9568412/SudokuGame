package com.android.sudokugame;
import java.util.ArrayList;
import java.util.List;
import com.android.sudokugame.DBhelper.DBHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class RegisterUser extends Activity {

    private ListView listview;
    private Button button_ok;
    String _username ;
    String _password ;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeruser);

        listview  = (ListView)findViewById(R.id.rg_ing);
        button_ok = (Button)findViewById(R.id.ok_button);

        db = new DBHelper(this);

        Intent intent=this.getIntent();
        Bundle b=intent.getBundleExtra("context");

        List list =  new ArrayList();

        _username = b.getString("username");
        _password = b.getString("password");

        list.add("用户名:" + _username);
        list.add("密码:" + _password);


        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listview.setAdapter(adapter);


        button_ok.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                db.insert(_username, _password, 0+"");
                db.close();
                finish();
            }
        });

    }

}
