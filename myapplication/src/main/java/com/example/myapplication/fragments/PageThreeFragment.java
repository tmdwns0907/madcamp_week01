package com.example.myapplication.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.GpsTracker;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class PageThreeFragment extends Fragment implements OnMapReadyCallback{
    private GpsTracker gpsTracker;
    private GoogleMap mMap;
    private MapView mapView=null;
    double[][][] places={{{1,1}},
            /*까페*/{{1,1},{36.367562,127.360521},{36.369990,127.359952},{36.373620,127.359062},{36.373989,127.365696},{36.369447,127.362459},{36.365628,127.361070},{36.368087,127.363864},{36.370301,127.363665},{36.368552,127.364578},},
            /*매점*/{{1,1},{36.367251,127.360938},{36.367184,127.360236},{36.368980,127.363886},{36.374023,127.365896},{36.371058,127.366563},{36.367162,127.358381},{36.368337,127.356857},{36.370293,127.355650},{36.373050,127.360183}},
            /*식당*/{{1,1},{36.369097,127.363675},{36.366873,127.360451},{36.373642, 127.359265},{36.373046, 127.360172},{36.374538,127.364776},{36.369097,127.363675}},
            /*헬스장*/{{1,1},{36.372395,127.361645},{36.367180,127.360227},{36.375219,127.359056},{36.368250,127.356897},{36.370270, 127.355679},{36.368054, 127.357113},{36.371077, 127.366589}},
     /*ATM*/{{1,1},{36.373002, 127.360044}, {36.373826, 127.359566},{36.374279, 127.365333}, {36.367317, 127.361033}, {36.370347, 127.362341}, {36.368377, 127.363706}, {36.370515, 127.361343}, {36.369929, 127.364582}, {36.367400, 127.364203}, {36.371511, 127.361874}, {36.368438, 127.365189}, {36.366111, 127.361184}, {36.368250, 127.356969},{36.369935,127.359909},{36.369404, 127.369820},{36.373698,127.361945},{36.368186,127.366679}},
     /*☆*/       {{1,1},{36.365915,127.357795},{36.373428,127.356304},{36.373741,127.357902},{36.370925,127.358217},{36.373189,127.359521},{36.367607,127.362575}}};
    double[][] places2 ={{1,1}, {36.366028, 127.363610},{36.367302, 127.364330},{36.367983, 127.365622},{36.368319,127.364776},{36.368861, 127.365178},{36.368710, 127.365695},{36.368219, 127.363896},{36.369123,127.363740},{36.369942, 127.364595},{36.370335, 127.365443},{36.371080, 127.366582},{36.369607,127.362419},{36.371631, 127.365111},{36.370419, 127.362680},{36.371242, 127.364575},{36.372475,127.366309},{36.370537, 127.361300},{36.372131, 127.362870},{36.371533, 127.361930},{36.369487,127.368508},{36.368540, 127.367977},{36.368540, 127.367977},{36.368235, 127.366778},{36.372468,127.366994},{36.369355,127.369706},{36.365763, 127.361383},{36.367202, 127.360760},{36.367514, 127.358126},{36.368022,127.357247},{36.368166, 127.357114},{36.368416, 127.356807},{36.368456, 127.356413},{36.369489,127.356394},{36.370279, 127.355532},{36.370413, 127.355965},{36.371023, 127.355839},{36.369967,127.359866},{36.370882, 127.358138},{36.371402, 127.356867},{36.371959, 127.356459},{36.368654,127.357242},{36.365588, 127.362517},{36.373421, 127.366916},{36.374104, 127.365815},{36.372904,127.363684},{36.372333, 127.361532},{36.373236, 127.362643},{36.374204, 127.363694},{36.374549,127.364767},{36.372456, 127.358703},{36.374002, 127.361664},{36.374023, 127.360366},{36.373725,127.359265},{36.373933,127.359637},{36.373015,127.360022},{36.373234,127.360742},{36.373712, 127.358467},{36.374870, 127.359916},{36.373803, 127.357646},{36.374300,127.358864},{36.374706, 127.359148},{36.373760, 127.356740},{36.375155, 127.358988},{36.375820,127.358719},{36.374797, 127.364261},{36.375367, 127.364036},{36.375358, 127.363505},{36.373661,127.361923},{36.375414, 127.361638},{36.375483, 127.360700},{36.375406, 127.362491}};
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public PageThreeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.page_three_fragment,container,false);
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }

        /*ShowLocationButton = (Button) rootview.findViewById(R.id.button);
        ShowLocationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {

                gpsTracker = new GpsTracker(getActivity());

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);
                LatLng my_pos = new LatLng(latitude,longitude);

                Toast.makeText(getActivity(), "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                mapView.getMapAsync(PageThreeFragment.this);
            }
        });*/

        final Spinner spinner2 = (Spinner)rootview.findViewById(R.id.spinner);
        final Spinner spinner22 = (Spinner)rootview.findViewById(R.id.spinner2);
        final Spinner spinnerson = (Spinner)rootview.findViewById(R.id.spinnerson);
        final String[] items = getResources().getStringArray(R.array.my_array);
        final String[] items2 = getResources().getStringArray(R.array.my_array2);
        final String[] df = getResources().getStringArray(R.array.df);
        final String[] itemscafe = getResources().getStringArray(R.array.cafe);
        final String[] itemsstore = getResources().getStringArray(R.array.store);
        final String[] itemscafeteria = getResources().getStringArray(R.array.cafeteria);
        final String[] itemsgym = getResources().getStringArray(R.array.gym);
        final String[] itemsatm = getResources().getStringArray(R.array.atm);
        final String[] itemsothers = getResources().getStringArray(R.array.others);
        final String[] infos = getResources().getStringArray(R.array.infos);



        final ArrayAdapter SpnAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,items){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                ((TextView) v).setTypeface(externalFont);
                v.setBackgroundColor(Color.WHITE);
                ((TextView) v).setTextColor(Color.BLACK);
                return v;
            }

        };

        spinner2.setAdapter(SpnAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    final ArrayAdapter SpnAdapter3 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,df){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            v.setBackgroundColor(Color.WHITE);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;
                        }

                    };
                    spinnerson.setAdapter(SpnAdapter3);
                    spinnerson.setSelection(0);
                    MarkerOptions makerOptions = new MarkerOptions();
                    makerOptions.snippet(infos[i]);
                    String text = "" + makerOptions.getSnippet();
                    TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                    tvText.setText(text);
                    return;
                }
                spinner22.setSelection(0);
                mMap.clear();
                final List<Marker> markers = new ArrayList<Marker>();
                BitmapDrawable bitmapdraw= mtchFunc(i-1);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 60, 60, false);
                for (int j = 0; j < places[i].length; j++) {
                    double latitude = places[i][j][0];
                    double longitude = places[i][j][1];

                    LatLng my_pos = new LatLng(latitude, longitude);

                    MarkerOptions makerOptions = new MarkerOptions();
                    if(i==1){
                        makerOptions
                                .position(my_pos)
                                .title(itemscafe[j])
                                .snippet(getResources().getStringArray(R.array.cafeinfo)[j]);
                    }
                    if(i==2){
                        makerOptions
                                .position(my_pos)
                                .title(itemsstore[j])
                                .snippet(getResources().getStringArray(R.array.storeinfo)[j]);
                    }
                    if(i==3){
                        makerOptions
                                .position(my_pos)
                                .title(itemscafeteria[j])
                                .snippet(getResources().getStringArray(R.array.cafeteriainfo)[j]);
                    }
                    if(i==4){
                        makerOptions
                                .position(my_pos)
                                .title(itemsgym[j])
                                .snippet(getResources().getStringArray(R.array.gyminfo)[j]);
                    }
                    if(i==5){
                        makerOptions
                                .position(my_pos)
                                .title(itemsatm[j])
                                .snippet(getResources().getStringArray(R.array.atm_info)[j]);
                    }
                    if(i==6){
                        makerOptions
                                .position(my_pos)
                                .title(itemsothers[j])
                                .snippet(getResources().getStringArray(R.array.othersinfo)[j]);
                    }
                    makerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    Marker marker = mMap.addMarker(makerOptions);
                    markers.add(marker);
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String text = "" + marker.getSnippet();
                        TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                        tvText.setText(text);
                        marker.showInfoWindow();
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                        return true;
                    }
                });
                if(i==1){
                    final ArrayAdapter SpnAdapter3 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,itemscafe){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            v.setBackgroundColor(Color.WHITE);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;
                        }

                    };
                    spinnerson.setAdapter(SpnAdapter3);
                    spinnerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                return;
                            }
                            Marker marker = markers.get(i);
                            marker.showInfoWindow();
                            LatLng my_pos = marker.getPosition();
                            String text = "" + marker.getSnippet();
                            TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                            tvText.setText(text);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if(i==2){
                    final ArrayAdapter SpnAdapter3 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,itemsstore){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            v.setBackgroundColor(Color.WHITE);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;
                        }

                    };
                    spinnerson.setAdapter(SpnAdapter3);
                    spinnerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                return;
                            }
                            Marker marker = markers.get(i);
                            marker.showInfoWindow();
                            LatLng my_pos = marker.getPosition();
                            String text = "" + marker.getSnippet();
                            TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                            tvText.setText(text);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if(i==3){
                    final ArrayAdapter SpnAdapter3 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,itemscafeteria){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            v.setBackgroundColor(Color.WHITE);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;
                        }

                    };
                    spinnerson.setAdapter(SpnAdapter3);
                    spinnerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                return;
                            }
                            Marker marker = markers.get(i);
                            marker.showInfoWindow();
                            LatLng my_pos = marker.getPosition();
                            String text = "" + marker.getSnippet();
                            TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                            tvText.setText(text);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if(i==4){
                    final ArrayAdapter SpnAdapter3 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,itemsgym){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            v.setBackgroundColor(Color.WHITE);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;
                        }

                    };
                    spinnerson.setAdapter(SpnAdapter3);
                    spinnerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                return;
                            }
                            Marker marker = markers.get(i);
                            marker.showInfoWindow();
                            LatLng my_pos = marker.getPosition();
                            String text = "" + marker.getSnippet();
                            TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                            tvText.setText(text);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if(i==5){
                    final ArrayAdapter SpnAdapter3 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,itemsatm){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            v.setBackgroundColor(Color.WHITE);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;
                        }

                    };
                    spinnerson.setAdapter(SpnAdapter3);
                    spinnerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                return;
                            }
                            Marker marker = markers.get(i);
                            marker.showInfoWindow();
                            LatLng my_pos = marker.getPosition();
                            String text = "" + marker.getSnippet();
                            TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                            tvText.setText(text);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if(i==6){
                    final ArrayAdapter SpnAdapter3 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,itemsothers){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            return v;
                        }
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View v =super.getDropDownView(position, convertView, parent);
                            Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            v.setBackgroundColor(Color.WHITE);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;
                        }

                    };
                    spinnerson.setAdapter(SpnAdapter3);
                    spinnerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                return;
                            }
                            Marker marker = markers.get(i);
                            marker.showInfoWindow();
                            LatLng my_pos = marker.getPosition();
                            String text = "" + marker.getSnippet();
                            TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                            tvText.setText(text);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter SpnAdapter2 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,items2){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonttest.ttf");
                ((TextView) v).setTypeface(externalFont);
                v.setBackgroundColor(Color.WHITE);
                ((TextView) v).setTextColor(Color.BLACK);
                return v;
            }

        };;
        spinner22.setAdapter(SpnAdapter2);
        spinner22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    MarkerOptions makerOptions = new MarkerOptions();
                    makerOptions.snippet(infos[i]);
                    String text = "" + makerOptions.getSnippet();
                    TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                    tvText.setText(text);
                    return;
                }
                spinner2.setSelection(0);
                mMap.clear();
                double latitude = places2[i][0];
                double longitude = places2[i][1];
                LatLng my_pos = new LatLng(latitude, longitude);
                MarkerOptions makerOptions = new MarkerOptions();
                makerOptions
                            .position(my_pos)
                            .title(items2[i])
                            .snippet(infos[i])
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                Marker marker = mMap.addMarker(makerOptions);
                marker.showInfoWindow();
                String text = "" + makerOptions.getSnippet();
                TextView tvText = (TextView) rootview.findViewById(R.id.Text);
                tvText.setText(text);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        mapView = (MapView) rootview.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(PageThreeFragment.this);
        return rootview;
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        mMap = googleMap;
        gpsTracker = new GpsTracker(getActivity());
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        LatLng my_pos = new LatLng(latitude,longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(my_pos));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }


    public BitmapDrawable mtchFunc(int i){
        if(i==0){
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.toil);
            return bitmapdraw;
        }
        else if(i==1){
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.str);
            return bitmapdraw;
        }
        else if(i==2){
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.cft);
            return bitmapdraw;
        }
        else if(i==3){
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.gym);
            return bitmapdraw;
        }
        else if(i==4){
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.atm);
            return bitmapdraw;
        }
        else{
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.oth);
            return bitmapdraw;
        }
    }

    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                // this
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    getActivity().finish();


                }else {

                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getActivity(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        //this
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getActivity(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getActivity(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getActivity(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    private void setPermission(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}



