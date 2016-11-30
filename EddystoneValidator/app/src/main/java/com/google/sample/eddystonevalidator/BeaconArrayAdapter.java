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

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple ArrayAdapter to manage the UI for displaying validation results.
 */
public class BeaconArrayAdapter extends ArrayAdapter<Beacon> implements Filterable {

  private List<Beacon> allBeacons;
  private List<Beacon> filteredBeacons;

  public BeaconArrayAdapter(Context context, int resource, List<Beacon> allBeacons) {
    super(context, resource,allBeacons);
    this.allBeacons = allBeacons;
    this.filteredBeacons = allBeacons;
  }

  @Override
  public int getCount() {
    return filteredBeacons.size();
  }

  @Override
  public Beacon getItem(int position) {
    return filteredBeacons.get(position);
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        List<Beacon> filteredBeacons;
        if (constraint != null && constraint.length() != 0) {
          filteredBeacons = new ArrayList<>();
          for (Beacon beacon : allBeacons) {
            if (beacon.contains(constraint.toString())) {
              filteredBeacons.add(beacon);
            }
          }
        } else {
          filteredBeacons = allBeacons;
        }
        results.count = filteredBeacons.size();
        results.values = filteredBeacons;
        return results;
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        filteredBeacons = (List<Beacon>) results.values;
        if (results.count == 0) {
          notifyDataSetInvalidated();
        } else {
          notifyDataSetChanged();
        }
      }
    };
  }

  public Boolean ExistBeacon(String _address){
    for (int x=0;x<filteredBeacons.size();x++)
      if (filteredBeacons.get(x).deviceAddress.equalsIgnoreCase(_address))
        return true;
    return false;
  }

  public Beacon getItem(String _address){
    for (int x=0;x<filteredBeacons.size();x++)
      if (filteredBeacons.get(x).deviceAddress.equalsIgnoreCase(_address))
        return filteredBeacons.get(x);

    return null;
  }
}
