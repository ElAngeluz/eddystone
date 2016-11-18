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
    super(context, resource, allBeacons);
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
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext())
          .inflate(R.layout.beacon_list_item, parent, false);
    }

    // Note: this is a listView and the convertView object here is likely to be
    // a recycled view of some other row that isn't in view. You need to set every
    // field regardless of emptiness to avoid displaying erroneous data.

    final Beacon beacon = getItem(position);

    TextView deviceAddress = (TextView) convertView.findViewById(R.id.deviceAddress);
    deviceAddress.setText(beacon.deviceAddress);

    TextView rssi = (TextView) convertView.findViewById(R.id.rssi);
    rssi.setText(String.valueOf(beacon.rssi));

    LinearLayout frameStatusGroup = (LinearLayout) convertView.findViewById(R.id.frameStatusGroup);
    if (!beacon.frameStatus.getErrors().isEmpty()) {
      TextView frameStatus = (TextView) convertView.findViewById(R.id.frameStatus);
      frameStatus.setText(beacon.frameStatus.toString());
      frameStatusGroup.setVisibility(View.VISIBLE);
    } else {
      frameStatusGroup.setVisibility(View.GONE);
    }

    return convertView;
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
}
