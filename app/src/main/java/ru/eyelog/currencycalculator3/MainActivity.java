package ru.eyelog.currencycalculator3;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.eyelog.currencycalculator3.adapters.AdapterPopupWindow;
import ru.eyelog.currencycalculator3.util_net.ValuteTO;

public class MainActivity extends MvpAppCompatActivity implements ViewState {

    @InjectPresenter
    Presenter presenter;

    Button buttonFrom, buttonTo;

    PopupWindow popupWindow;
    View popupView;
    ListView listView;

    AdapterPopupWindow adapter;

    List<ValuteTO> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFrom = findViewById(R.id.bt_currentFrom);
        buttonTo = findViewById(R.id.bt_currentTo);

        presenter.getCurrencyList();

        buttonFrom.setOnClickListener(v -> showPopup(true));
        buttonTo.setOnClickListener(v -> showPopup(false));
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

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//        popupWindow.update();
    }

    @Override
    public void setCurses(List<ValuteTO> valuteTOS) {
        data = valuteTOS;

//        Toast.makeText(this, data.get(0).getName(), Toast.LENGTH_SHORT).show();
    }
}
