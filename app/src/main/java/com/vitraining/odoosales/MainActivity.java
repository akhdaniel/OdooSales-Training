package com.vitraining.odoosales;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

public class MainActivity extends AppCompatActivity {

    private OdooUtility odoo;
    private long loginTaskId;

    EditText editUsername;
    EditText editPassword;
    EditText editDatabase;
    EditText editServerURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String database = SharedData.getKey(MainActivity.this, "database");
        String serverAddress = SharedData.getKey(MainActivity.this, "serverAddress");
        String username = SharedData.getKey(MainActivity.this, "username");
        String password = SharedData.getKey(MainActivity.this, "password");

        editServerURL = (EditText) findViewById(R.id.editServerURL);
        editDatabase = (EditText) findViewById(R.id.editDatabase);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);

        editServerURL.setText(serverAddress);
        editDatabase.setText(database);
        editUsername.setText(username);
        editPassword.setText(password);
    }

    public void onClickLogin(View view){
        switch (view.getId()){
            case R.id.buttonLogin:
                String serverAddress = editServerURL.getText().toString();
                String database = editDatabase.getText().toString();
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                odoo = new OdooUtility(serverAddress, "common");
                loginTaskId = odoo.login(listener, database, username, password);

                SharedData.setKey(MainActivity.this, "serverAddress", serverAddress);
                SharedData.setKey(MainActivity.this, "database", database);
                SharedData.setKey(MainActivity.this, "username", username);
                SharedData.setKey(MainActivity.this, "password", password);

        }

    }


    XMLRPCCallback listener = new XMLRPCCallback() {
        @Override
        public void onResponse(long id, Object result) {
            Looper.prepare();
            if (id == loginTaskId){
                if(result instanceof Boolean && (Boolean) result == false){
                    odoo.MessageDialog(MainActivity.this, "Login Error");
                }
                else {
                    String uid = result.toString();
                    SharedData.setKey(MainActivity.this, "uid", uid);
                    //odoo.MessageDialog(MainActivity.this, "Login succeeded. uid=" + uid);

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    MainActivity.this.startActivity(intent);

                }
            }
            Looper.loop();
        }

        @Override
        public void onError(long id, XMLRPCException error) {
            Looper.prepare();
            odoo.MessageDialog(MainActivity.this, "Login error:" + error.getMessage());
            Looper.loop();
        }

        @Override
        public void onServerError(long id, XMLRPCServerException error) {
            Looper.prepare();
            odoo.MessageDialog(MainActivity.this, "SErver error:" + error.getMessage());
            Looper.loop();
        }
    };
}


