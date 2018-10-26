package com.example.resourcetrack1;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class CallForHelp extends AppCompatActivity implements LocationListener{

    Button getLocationBtn,confirmLocationBtn;
    TextView locationText,t1;
    boolean flag = true;
    LocationManager locationManager;

    private MobileServiceClient mClient;

   // private EditText mTextNewToDo;

    private NeedyPeopleItemAdapter mAdapter;

    private MobileServiceTable<NeedyPeopleItem> mToDoTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_for_help);
        getLocationBtn = (Button)findViewById(R.id.button5);
        confirmLocationBtn = (Button)findViewById(R.id.button4);
        locationText = (TextView)findViewById(R.id.locationText);
        t1 = (TextView)findViewById(R.id.textView4);
       // locationText.setText("1");
        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://resourcetrack1.azurewebsites.net",
                    this);//.withFilter(new ProvideHelp.ProgressFilter());

            // Extend timeout from default of 10s to 20s
            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });

            // Get the Mobile Service Table instance to use
           // locationText.setText("2");
            mToDoTable = mClient.getTable(NeedyPeopleItem.class);
            //locationText.setText("3");
            // Offline Sync
            //mToDoTable = mClient.getSyncTable("ToDoItem", ToDoItem.class);

            //Init local storage
            initLocalStore().get();
            //locationText.setText("4");

            //mTextNewToDo = (EditText) findViewById(R.id.textNewToDo);

            // Create an adapter to bind the items with the view
            mAdapter = new NeedyPeopleItemAdapter(this, R.layout.row_list_to_do);
          //  ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
          //  if(listViewToDo==null)
            {
         //       System.out.println("**************");
           //     locationText.setText("***************");
            }
           // listViewToDo.setAdapter(mAdapter);

            // Load the items from the Mobile Service
         //   refreshItemsFromTable();

        } catch (MalformedURLException e) {
            flag=false;
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e){
            flag=false;
            createAndShowDialog(e, "Error");
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
               // addItem(v);
            }
        });
        confirmLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 addItem();
                 if(flag)
                    t1.setText("Help is on the way..!! Please be patient..");
                 else
                     t1.setText("Facing some technical issue.. Please try again after some time..");
            }
        });
    }
    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("text", ColumnDataType.String);
                    tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("needypeopleitem", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    flag=false;
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<NeedyPeopleItem> results = refreshItemsFromMobileServiceTable();

                    //Offline Sync
                    //final List<ToDoItem> results = refreshItemsFromMobileServiceTableSyncTable();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();

                            for (NeedyPeopleItem item : results) {
                                mAdapter.add(item);
                            }
                        }
                    });
                } catch (final Exception e){
                    flag=false;
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
    }

    /**
     * Refresh the list with the items in the Mobile Service Table
     */

    private List<NeedyPeopleItem> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        return mToDoTable.where().field("complete").
                eq(val(false)).execute().get();
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            flag=false;
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {
                flag=false;
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(CallForHelp.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }


    public void addItem() {
        //getLocation();

        System.out.println("gotlocation");
        if (mClient == null) {
            System.out.println("mclient=null");
            return;
        }

        // Create a new item
        final NeedyPeopleItem item = new NeedyPeopleItem();

        item.setText(locationText.getText().toString());
        item.setComplete(false);
        //locationText.setText(item.getText()+"woooooooo");
        System.out.println("hello");
        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                  //  locationText.setText("hjghjg");
                    System.out.println("ggjujgku");
                    try{
                    mToDoTable.insert(item);
                        System.out.println("happy");}
                    catch(Exception e)
                    {
                        flag=false;
                        System.out.println("dukh");
                    }

           /*        final NeedyPeopleItem entity = addItemInTable(item);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!entity.isComplete()){
                                System.out.println("hii");
                                locationText.setText("jojo");
                                mAdapter.add(entity);
                            }
                        }
                    });*/
                } catch (final Exception e) {
                    flag=false;
                    System.out.println("nooooooooo");
                   // createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };

        runAsyncTask(task);

       // mTextNewToDo.setText("");
        //locationText.setText(locationText.getText()+"\n"+"done");
        System.out.println("donedonedone");
    }

    public NeedyPeopleItem addItemInTable(NeedyPeopleItem item){// throws ExecutionException, InterruptedException {
        NeedyPeopleItem entity = null;
        try {
            entity = mToDoTable.insert(item).get();
        } catch (ExecutionException e) {
            flag=false;
            locationText.setText("blah");
            e.printStackTrace();
           // createAndShowDialogFromTask(e, "Error");
        } catch (InterruptedException e) {
            flag=false;
            locationText.setText("huh");
            e.printStackTrace();
         //   createAndShowDialogFromTask(e, "Error");
        }
        if(entity==null)
        {
            locationText.setText("shit");
        }
        else
        {
            locationText.setText("yaaaaaas");
        }
        return entity;
    }

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }
    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }


    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }
}
