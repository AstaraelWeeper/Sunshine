package com.missionroom.rachelgriffiths.sunshine;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;


public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //add to call SettingsActivity.java
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
       private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private String mForecastStr;


        public DetailFragment() {
            setHasOptionsMenu(true);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            //adding intent reading
            Intent intent  = getActivity().getIntent();
            if (intent !=null && intent.hasExtra(Intent.EXTRA_TEXT)){
                mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT); //here and below forecastStr was replaced by mForecastStr
                ((TextView) rootView.findViewById(R.id.detail_text)).setText(mForecastStr); //linking the text from the intent to the TextView in fragment_detail.xml to open the detailed forecast view

            }


            return rootView;
        }

        //new for lesson 3.13 - adding share provider to fragment

        private Intent createShareForecastIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET); //this is so you go back to your app and not to the one handling the intent
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    mForecastStr + (getString(R.string.hashtag)));
            return shareIntent;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detailfragment, menu); //inflating the required menu to add items to the action bar

            //retrieve share menu item
            MenuItem menuItem = menu.findItem(R.id.action_share);

            //get the provider
            ShareActionProvider mShareActionProvider = (ShareActionProvider) menuItem.getActionProvider(); //non static error - have removed MenuItem from the brackets.

            //attach intent above to the share provider
            if (mShareActionProvider !=null){
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }else{
                Log.d(LOG_TAG, "Share Action Provider is null");
            }

        }
    }
}
