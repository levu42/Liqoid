/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.quadrillenschule.liquidroid.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import de.quadrillenschule.liquidroid.AreasTabActivity;
import de.quadrillenschule.liquidroid.LiqoidApplication;

public class AreasListAdapter extends ArrayAdapter<Area> {

    private Areas areas;
    private int viewId;
    private AreasTabActivity activity;

    public AreasListAdapter(AreasTabActivity activity, Areas values, int viewId) {
        super(activity, NO_SELECTION, values);
        //  this.context = pcontext;
        this.areas = values;
        this.activity = activity;

    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        CheckBox retval = new CheckBox(activity);

        retval.setTextColor(Color.BLACK);
        retval.setBackgroundColor(Color.argb(255, 245, 245, 245));
        retval.setText(areas.get(position).getName());
        retval.setChecked(areas.isSelected(position));

        retval.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Area myarea = areas.getByName(((CheckBox) arg0).getText().toString());
                boolean value=((CheckBox) arg0).isChecked();
                areas.setSelectedArea(myarea,value);

              //  areas.getByName(((CheckBox) arg0).getText().toString()).setSelected(!areas.getByName(((CheckBox) arg0).getText().toString()).isSelected());
             //   ((LiqoidApplication) activity.getApplication()).toast(activity, areas.instancePrefs.getString("selectedareas",""));
            }
        });

        activity.registerForContextMenu(retval);


        return retval;
    }
}
