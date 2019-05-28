package com.vitraining.odoosales;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCClient;

public class OdooUtility {
    private URL url;
    private XMLRPCClient client;

    OdooUtility(String serverAddress, String path){
        try{
            url = new URL(serverAddress + "/xmlrpc/2/" + path);
            client = new XMLRPCClient(url);
        } catch (Exception ex){
            Log.e("Odoo utility: ", ex.getMessage());
        }
    }

    public long login(XMLRPCCallback listener,
                      String db,
                      String username,
                      String password){
        Map<String, Object> emptyMap = new HashMap<String, Object>();
        long id = client.callAsync(listener,
                "authenticate",
                db,
                username,
                password,
                emptyMap);
        return id;
    }

    public void MessageDialog(Context c, String msg){
        AlertDialog.Builder adb = new AlertDialog.Builder(c);
        adb.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
    }


    public long search_read(XMLRPCCallback listener,
                            String db,
                            String uid,
                            String password,
                            String object,
                            List condition,
                            Map<String, List> fields){

        long id = client.callAsync(listener, "execute_kw", db, Integer.parseInt(uid), password, object,
                "search_read", condition, fields);
        return id;

    }
}
