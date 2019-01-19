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
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.eyelog.currencycalculator3.adapters.AdapterPopupWindow;
import ru.eyelog.currencycalculator3.util_net.ValuteTO;

// TODO make counting logic
// TODO and make some test =)
public class MainActivity extends MvpAppCompatActivity implements ViewState {

    private static final String SP_CURRENCY_LOC = "curcurrency";
    private static final String SP_CUR_FROM = "currencyfrom";
    private static final String SP_CUR_TO = "currencyto";

    private static final String DEF_CUR_XMLID = "000";
    private static final String DEF_CUR_NUMCODE = "000";
    private static final String DEF_CUR_CHARCODE = "RUR";
    private static final String DEF_CUR_NOMINAL = "1";
    private static final String DEF_CUR_NAME = "Российский рубль";
    private static final String DEF_CUR_VAlUE = "1";


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
    private boolean filledFields = false;
    private double curFrom, curTo;
    private String stOut;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFrom = findViewById(R.id.bt_currentFrom);
        buttonTo = findViewById(R.id.bt_currentTo);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.etCalc);

        curPreference = getSharedPreferences(SP_CURRENCY_LOC, Context.MODE_PRIVATE);
        editor = curPreference.edit();

        presenter.getCurrencyList();

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

                if (valuteFrom != null && valuteTo != null) {
                    filledFields = true;
                }
                if (valuteFrom == null) {
                    Toast.makeText(MainActivity.this, "Не выбрана исходящая валюта.", Toast.LENGTH_SHORT).show();
                }
                if (valuteTo == null) {
                    Toast.makeText(MainActivity.this, "Не выбрана входящая валюта.", Toast.LENGTH_SHORT).show();
                }

                if (filledFields&&!s.toString().equals("")) {

                    curFrom = getDouble(valuteFrom.getNominal()) *
                            getDouble(s.toString());

                    curTo = getDouble(valuteTo.getNominal()) *
                            getDouble(valuteTo.getValue());

                    // TODO badcode todo it good!
                    stOut = s + " " + valuteFrom.getName() + " = " + curFrom / curTo + " " + valuteTo.getName();

                    textView.setText(stOut);
                }
            }
        });
    }

    @SuppressLint("InflateParams")
    private void showPopup(boolean doFrom) {
        popupView = LayoutInflater.from(this).inflate(R.layout.popup_cur_list, null);
        popupWindow = new PopupWindow(popupView);
        listView = popupView.findViewById(R.id.listView);
        popupWindow = new PopupWindow(
                popupView,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        if (Build.VERSION.SDK_INT >= 21) {
            popupWindow.setElevation(5.0f);
        }
        popupWindow.setFocusable(true);

        adapter = new AdapterPopupWindow(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String stTempXmlID;
            if (doFrom) {
                valuteFrom = data.get(position);
                stTempXmlID = valuteFrom.getXmlID();
                editor.putString(SP_CUR_FROM, stTempXmlID);
                buttonFrom.setText(valuteFrom.getName());
            } else {
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
    }

    private void refreshTheList(boolean doFrom, String xmlID) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getXmlID().equals(xmlID)) {
                if (doFrom) {
                    valuteTemp = valuteFrom;
                } else {
                    valuteTemp = valuteTo;
                }
                data.remove(i);
                data.add(i, valuteTemp);
            }
        }
    }

    private void setRUR() {
        data = new ArrayList<>();

        ValuteTO valuteTO = new ValuteTO(
                DEF_CUR_XMLID,
                DEF_CUR_NUMCODE,
                DEF_CUR_CHARCODE,
                DEF_CUR_NOMINAL,
                DEF_CUR_NAME,
                DEF_CUR_VAlUE
        );

        data.add(valuteTO);
    }

    @Override
    public void setCurses(List<ValuteTO> valuteTOS) {

        setRUR();

        if (valuteTOS.size() > 0) {
            data.addAll(valuteTOS);
            tempData = data;
        } else {
            Toast.makeText(this, "Мировая экономика рухнула, остался только рубль", Toast.LENGTH_SHORT).show();
        }
    }

    private double getDouble(String gotSt) {
        String stParsed = "";

        for (int i = 0; i < gotSt.length(); i++) {
            if (!gotSt.substring(i, i + 1).equals("-")) {
                stParsed += (gotSt.substring(i, i + 1).equals(",") ? "." : gotSt.substring(i, i + 1));
            }
        }

        return Double.parseDouble(stParsed);
    }
}
