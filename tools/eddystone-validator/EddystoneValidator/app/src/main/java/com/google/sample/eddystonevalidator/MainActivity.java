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
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import static android.os.Process.*;


/**
 * MainActivity for the Eddystone Validator sample app.
 */
public class MainActivity extends Activity implements SetInfoDialog.InfoDialogListener {

  SetInfoDialog dialogInfo;
    int mStackLevel=0;

    private String UserName;
    private String Server;
    private String Group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogInfo = new SetInfoDialog();

        dialogInfo.show(getFragmentManager(), "Info");
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
                showDialog();
                return true;
            case R.id.Disconnect:
                killProcess(myPid());
                super.onDestroy();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void showDialog() {

        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("info");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = dialogInfo.newInstance(mStackLevel);
        newFragment.show(ft, "info");
    }

    @Override
    public void onDialogPositiveClick(String _User, String _Server, String _Group) {
        UserName = _User;
        Server = _Server;
        Group  = _Group;
    }
}
