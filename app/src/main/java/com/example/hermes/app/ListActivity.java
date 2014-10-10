package com.example.hermes.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends Activity {

    //TODO: SlidingMenu============================================================================
    //SlidingMenu root;
    private String section = "US";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<String> links;
    private ArrayList<OptionHeader> optionList;
    private ListView optionView;
    private ListView listView;
    private ArrayList<ItemHeader> itemList;

    //TODO: TARGET FIX
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //These will contain list of items
        listView = (ListView) findViewById(R.id.list);
        itemList = new ArrayList<ItemHeader>();

        //These will contain the options
        optionView = (ListView) findViewById(R.id.left_drawer);
        optionList = new ArrayList<OptionHeader>();

        //TODO: Understand this and fix the red line===============================================
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        links = new ArrayList<String>();

        optionList.add(new OptionHeader("US"));
        optionList.add(new OptionHeader("World"));

        updateLinks();
        //TODO: Add in WebsiteFinder ==============================================================



        //To use ListView, you need ArrayAdapter.
        //By using Custom Adapter, you can make ur own design for each item
        ItemListAdapter itemAdapter = new ItemListAdapter(this,
                android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(itemAdapter);

        OptionListAdapter optionAdapter = new OptionListAdapter(this,
                android.R.layout.simple_list_item_1, optionList);
        optionView.setAdapter(optionAdapter);


        //Click on a item in listView to go to new page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Intent switches the activity. Remember to go to Android Manifest and add activity
                //The putExtra make it possible to send information between activities
                Intent intent = new Intent (ListActivity.this, DetailActivity.class);
                intent.putExtra("sourceLink", itemList.get(position).getSource() );
                intent.putExtra("description", itemList.get(position).getDescription());
                intent.putExtra("title", itemList.get(position).getTitle());
                intent.putExtra("companyName", itemList.get(position).getCompanyName());
                startActivity(intent);
            }
        });

        optionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //optionList.setItemChecked(position, true);
                if (position == 0)
                    section = "US";
                else
                    section = "WORLD";
                updateLinks();
                drawerLayout.closeDrawer(optionView);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        )  {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    public void updateLinks() {
        //TODO: FIX ONCE WE HAVE SLIDING MENU =====================================================
        try {
            //searches = WebsiteFinder.search("CNN Today's news");
            links = WebsiteFinder.searchCNN(section);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < links.size(); i++) {
            System.out.println("HELLOOOOOOOO" + links.get(i));
        }
        for (String string: links) {
            try {
                itemList.add(new ItemHeader(new InformationExtractor(string)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listView.invalidateViews();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
