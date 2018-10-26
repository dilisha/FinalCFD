 package com.example.resourcetrack1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.TextView;

        import org.w3c.dom.Text;


/**
 * Adapter to bind a CampDetails List to a view
 */
public class DeleteCampsAdapter extends ArrayAdapter<CampDetails> {

    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public DeleteCampsAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    /**
     * Returns the view for a specific item on the list
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        final CampDetails currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);
  /*      final TextView x = (TextView) row.findViewById(R.id.loc);
        x.setText(currentItem.getLocation());
        final TextView y = (TextView) row.findViewById(R.id.phn);
        y.setText(currentItem.getPhno().toString());
*/

        final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkDeleteCamps);
        checkBox.setText(currentItem.getLocation()+"    "+ currentItem.getPhno().toString());
        checkBox.setChecked(false);
        checkBox.setEnabled(true);

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (checkBox.isChecked()) {
                    checkBox.setEnabled(false);
                    if (mContext instanceof DeleteCamps) {
                        DeleteCamps activity = (DeleteCamps) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }
        });

        return row;
    }

}