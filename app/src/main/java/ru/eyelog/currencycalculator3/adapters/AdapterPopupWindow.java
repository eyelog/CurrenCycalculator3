package ru.eyelog.currencycalculator3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.eyelog.currencycalculator3.R;
import ru.eyelog.currencycalculator3.util_net.ValuteTO;

public class AdapterPopupWindow extends BaseAdapter {

    Context context;
    View rootView;
    LayoutInflater inflater;

    TextView tvName, tvValue;

    private List<ValuteTO> data;

    public AdapterPopupWindow(Context context, List<ValuteTO> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_rv, parent, false);
        }

        tvName = convertView.findViewById(R.id.id_name);
        tvValue = convertView.findViewById(R.id.id_value);

        tvName.setText(data.get(position).getName());
        tvValue.setText(data.get(position).getValue());


        return convertView;
    }
}
