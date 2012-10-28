package my.experimental.rowRecorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<String>{

	public ListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(R.layout.list, null);
		}
		String s = getItem(position);
		TextView name = (TextView) view.findViewById(R.id.litem);
		name.setText(s);
		return view;
		
	}

}
