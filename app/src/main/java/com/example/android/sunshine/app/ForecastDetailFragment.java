package com.example.android.sunshine.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ForecastDetailFragment extends Fragment {

    Bundle extras;

    public ForecastDetailFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        extras = activity.getIntent().getExtras();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast_detail, container, false);

        TextView txt_detail = (TextView) rootView.findViewById(R.id.txt_detail);

        txt_detail.setText(extras.getString("detail"));

        return rootView;
    }
}