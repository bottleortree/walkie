package uk.co.bottleortree.walkie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Todd on 27/11/13.
 */
public class MainFragment extends Fragment {

    TextView stepCounterDay;

    int stepCountDay = 0;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepCounterDay = (TextView)getView().findViewById(R.id.main_text_stepcounter_day);
        refreshValues();
    }

    public void refreshValues() {
        stepCounterDay.setText(Integer.toString(stepCountDay));
    }

    public void setStepCountDay(int count) {
        stepCountDay = count;
    }
}