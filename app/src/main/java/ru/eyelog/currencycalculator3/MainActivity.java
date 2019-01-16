package ru.eyelog.currencycalculator3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.eyelog.currencycalculator3.util_net.ValuteTO;

public class MainActivity extends MvpAppCompatActivity implements ViewState {

    @InjectPresenter
    Presenter presenter;

    Button buttonFrom;

    List<ValuteTO> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFrom = findViewById(R.id.bt_currentFrom);

        presenter.getCurrencyList();

        buttonFrom.setOnClickListener(v -> presenter.getCurrencyList());
    }

    @Override
    public void setCurses(List<ValuteTO> valuteTOS) {
        data = valuteTOS;

        Toast.makeText(this, data.get(0).getName(), Toast.LENGTH_SHORT).show();
    }
}
