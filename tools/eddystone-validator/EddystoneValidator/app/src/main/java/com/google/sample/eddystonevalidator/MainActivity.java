// Copyright 2015 Google Inc. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sample.eddystonevalidator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import static android.os.Process.*;


/**
 * MainActivity for the Eddystone Validator sample app.
 */
public class MainActivity extends Activity {

    private String UserName;
    private String Server;
    private String Group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showDialog2();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    public String getUserName() {
        return UserName;
    }

    public String getGroup() {
        return Group;
    }

    public String getServer() {
        return Server;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Connect:
                showDialog2();
                return true;
            case R.id.Disconnect:
                killProcess(myPid());
                super.onDestroy();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Procedimiento que llama al alert Dialog para capturar los datos de incio de sesion en el servidor
     */
    void showDialog2(){

        final View inflator = getLayoutInflater().inflate(R.layout.dialog_username, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Login");
        alert.setView(inflator);

        final EditText et1 = (EditText) inflator.findViewById(R.id.usernameText);
        final EditText et2 = (EditText) inflator.findViewById(R.id.groupnameText);
        final EditText et3 = (EditText) inflator.findViewById(R.id.serverText);

        alert.setPositiveButton("Ready", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if (et1.getText().toString().isEmpty())
                    et1.setText(getResources().getString(R.string.usernameDefault));

                if (et3.getText().toString().isEmpty())
                    et3.setText(getResources().getString(R.string.serverDefault));

                if (et2.getText().toString().isEmpty())
                    et2.setText(getResources().getString(R.string.groupDefault));

                UserName = et1.getText().toString();
                Server = et3.getText().toString();
                Group = et2.getText().toString();
            }
        });
        alert.show();
    }
}
