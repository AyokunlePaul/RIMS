package i.am.eipeks.rims._fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import i.am.eipeks.rims.R;


public class Terms extends Fragment {

    public interface ConfigureTheme {
        void setToolbar(View view, int resId);
        void setNewTheme(int resId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms, container, false);

//        ((ConfigureTheme)getActivity()).setToolbar(view, R.id.toolbar_terms);
//        ((ConfigureTheme)getActivity()).setNewTheme(R.style.AppTheme_NoActionBar);

        return view;
    }

}
