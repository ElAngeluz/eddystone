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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * MainActivity for the Eddystone Validator sample app.
 */
public class MainActivity extends Activity implements SetInfoDialog.InfoDialogListener {
  public String username;
  public String server;

  private TextView tvUsername;
  private TextView tvServer;

  DialogFragment dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    dialog = new SetInfoDialog();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.Connect:
        dialog.show(getFragmentManager(), "Info");
        return true;
      case R.id.Disconnect:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }


  @Override
  public void onDialogPositiveClick(DialogFragment dialog) {
    //username = dialog.
  }

  @Override
  public void onDialogNegativeClick(DialogFragment dialog) {

  }
}
