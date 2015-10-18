package com.example.karen.lop_android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by hebi5 on 10/17/2015.
 */
public class DialogFragmentTestFragment extends Fragment {

    View rootView;
    LinearLayout ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final MyDialog myDialog = new MyDialog();


        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));

        Button b = new Button(getActivity());
        b.setText("go");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.show(getActivity().getFragmentManager(), "shitfragment");
            }
        });


        ll.addView(b);

        rootView = ll;
        return rootView;
    }
}
