package ru.eyelog.currencycalculator3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.eyelog.currencycalculator3.adapters.AdapterPopupWindow;
import ru.eyelog.currencycalculator3.util_net.ValuteTO;

// TODO set default values
// TODO make counting logic
// TODO and make some test =)
public class MainActivity extends MvpAppCompatActivity implements ViewState {

    public static final String SP_CURRENCY_LOC = "curcurrency";
    public static final String SP_CUR_FROM = "currencyfrom";
    public static final String SP_CUR_TO = "currencyto";

    @InjectPresenter
    Presenter presenter;

    SharedPreferences curPreference;
    SharedPreferences.Editor editor;

    Button buttonFrom, buttonTo;

    PopupWindow popupWindow;
    View popupView;
    ListView listView;

    TextView textView;
    EditText editText;

    AdapterPopupWindow adapter;

    List<ValuteTO> data, tempData;
    ValuteTO valuteFrom, valuteTo, valuteTemp;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFrom = findViewById(R.id.bt_currentFrom);
        buttonTo = findViewById(R.id.bt_currentTo);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.etCalc);

        presenter.getCurrencyList();
        curPreference = getSharedPreferences(SP_CURRENCY_LOC, Context.MODE_PRIVATE);
        editor = curPreference.edit();

        buttonFrom.setOnClickListener(v -> showPopup(true));
        buttonTo.setOnClickListener(v -> showPopup(false));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(s);
            }
        });
    }

    @SuppressLint("InflateParams")
    private void showPopup(boolean doFrom){
        popupView = LayoutInflater.from(this).inflate(R.layout.popup_cur_list, null);
        popupWindow = new PopupWindow(popupView);
        listView = popupView.findViewById(R.id.listView);
        popupWindow = new PopupWindow(
                popupView,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        if(Build.VERSION.SDK_INT>=21) {
            popupWindow.setElevation(5.0f);
        }
        popupWindow.setFocusable(true);

        adapter = new AdapterPopupWindow(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String stTempXmlID;
            if(doFrom){
                valuteFrom = data.get(position);
                stTempXmlID = valuteFrom.getXmlID();
                editor.putString(SP_CUR_FROM, stTempXmlID);
                buttonFrom.setText(valuteFrom.getName());
            }else {
                valuteTo = data.get(position);
                stTempXmlID = valuteTo.getXmlID();
                editor.putString(SP_CUR_TO, stTempXmlID);
                buttonTo.setText(valuteTo.getName());
            }
            editor.apply();
            refreshTheList(doFrom, stTempXmlID);
            popupWindow.dismiss();
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//        popupWindow.update();
    }

    private void refreshTheList(boolean doFrom, String xmlID){
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getXmlID().equals(xmlID)){
                if(doFrom){
                    valuteTemp = valuteFrom;
                }else {
                    valuteTemp = valuteTo;
                }
                data.remove(i);
                data.add(i, valuteTemp);
            }
        }
    }



    @Override
    public void setCurses(List<ValuteTO> valuteTOS) {
        data = valuteTOS;
        tempData = valuteTOS;

//        Toast.makeText(this, data.get(0).getName(), Toast.LENGTH_SHORT).show();
    }
}
