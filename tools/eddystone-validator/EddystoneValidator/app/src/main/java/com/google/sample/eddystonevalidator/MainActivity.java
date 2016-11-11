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
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


/**
 * MainActivity for the Eddystone Validator sample app.
 */
public class MainActivity extends Activity implements SetInfoDialog.InfoDialogListener {

  SetInfoDialog dialogInfo;

    private String UserName;
    private String Server;
    private String Group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogInfo = new SetInfoDialog();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        UserName =dialogInfo.GetUsername();
        Group = dialogInfo.GetGroup();
        Server = dialogInfo.GetServer();
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
                dialogInfo.show(getFragmentManager(), "Info");
                return true;
            case R.id.Disconnect:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
