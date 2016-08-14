package com.hostitwise.pokemommas;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements LocateFragment.OnLocateListener {

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocateFragment locateFragment =
                (LocateFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.parent_locate_fragment);
        locateFragment.registerListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLocate(Location loc) {
        showStaticMap(loc);
    }

    private void showStaticMap(Location loc) {
        String position = loc.getLatitude() + "," + loc.getLongitude();
        int zoom = 12;
        int xSize = 200;
        int ySize = 200;
        String mapHtml = getHtmlAsString("static-map.html")
                .replace("$LAT_LNG$", position)
                .replace("$ZOOM$", "" + zoom)
                .replace("$SIZE$", xSize + "x" + ySize);
        WebView webview = (WebView) getView().findViewById(R.id.map);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL(null, mapHtml, "text/html", "UTF-8", null);
    }

    private String getHtmlAsString(String filename) {
        try {
            InputStream input = getView().getContext().getAssets().open(filename);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            return new String(buffer);
        } catch (IOException e) {
            //should never happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
