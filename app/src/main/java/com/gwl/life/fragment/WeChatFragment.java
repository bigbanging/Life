package com.gwl.life.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwl.life.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeChatFragment extends Fragment {


    public WeChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_we_chat, container, false);
        return view;
    }

}