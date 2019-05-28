package com.vitraining.odoosales;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

public class CustomerListActivity extends AppCompatActivity {
    private OdooUtility odoo;
    private String uid;
    private String password;
    private String serverAddress;
    private String database;

    private long searchTaskId;

    ListView listViewPartner;
    List arrayListPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        uid = SharedData.getKey(CustomerListActivity.this, "uid");
        password = SharedData.getKey(CustomerListActivity.this, "password");
        serverAddress = SharedData.getKey(CustomerListActivity.this, "serverAddress");
        database = SharedData.getKey(CustomerListActivity.this, "database");

        odoo = new OdooUtility(serverAddress, "object");

        arrayListPartner =  new ArrayList();
        listViewPartner = (ListView) findViewById(R.id.listPartner);

    }



    public void onClickSearchPartner(View v){
        EditText editKeyword = (EditText) findViewById(R.id.editKeyword);
        String keyword = editKeyword.getText().toString();


        // [ [ ['name','ilike',keyword] ] ]
        List condition = Arrays.asList(
                Arrays.asList(
                        Arrays.asList("name","ilike",keyword)
                )

        );

        //{'fields': ['id', 'display_name']}
        Map fields = new HashMap() {{
           put("fields", Arrays.asList("id","display_name"));
        }};

        searchTaskId = odoo.search_read(listener, database, uid, password,
                "res.partner", condition, fields);

    }

    private void fillListPartner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                arrayListPartner);

        listViewPartner.setAdapter(adapter);

        listViewPartner.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int itemPosition = position;
                String itemValue = (String) listViewPartner.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        "Position "+itemPosition + " ListItem " + itemValue,
                        Toast.LENGTH_LONG).show();

            }
        });
    }


    XMLRPCCallback listener = new XMLRPCCallback() {
        @Override
        public void onResponse(long id, Object result) {
            Looper.prepare();

            if (id == searchTaskId){
                Object[] classObjs = (Object[])result;
                int length = classObjs.length;

                if (length>0){
                    arrayListPartner.clear();
                    for (int i=0; i<length; i++){
                        Map<String, Object> classObj = (Map<String, Object>) classObjs[i];
                        arrayListPartner.add(classObj.get("display_name"));

                    }
                    //["si a","si b","si c"]

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fillListPartner();
                        }
                    });


                }

            }
            Looper.loop();
        }

        @Override
        public void onError(long id, XMLRPCException error) {
            Looper.prepare();
            Log.e("SEARCH", error.getMessage());
            odoo.MessageDialog(CustomerListActivity.this, error.getMessage());
            Looper.loop();
        }

        @Override
        public void onServerError(long id, XMLRPCServerException error) {
            Looper.prepare();
            Log.e("SEARCH", error.getMessage());
            odoo.MessageDialog(CustomerListActivity.this, error.getMessage());
            Looper.loop();
        }
    };




}
