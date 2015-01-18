package com.c4d3r.reddit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Maxim on 18/01/2015.
 */
public class DetailFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);

        String url = getActivity().getIntent().getStringExtra("url");

        WebView webview = (WebView)rootView.findViewById(R.id.webview_detail);
        webview.loadUrl(url);

        return rootView;
    }
}
