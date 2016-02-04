package com.thompson.tony.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.psdev.licensesdialog.LicensesDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LicensesDialogFragment fragment = LicensesDialogFragment.newInstance(R.raw.notices, false, true);
        fragment.show(getFragmentManager(), null);
    }
}
