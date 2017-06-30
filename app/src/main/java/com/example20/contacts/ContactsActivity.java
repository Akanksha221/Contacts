package com.example20.contacts;

/**
 * Created by AKANKSHA on 29-06-2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.data;

public class ContactsActivity extends Activity implements ItemClickListener {
    /** Called when the activity is first created. */
    ArrayList<ContactListItem> arrayList= new ArrayList<ContactListItem>();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_layout);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if (ContextCompat.checkSelfPermission(ContactsActivity.this,
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(ContactsActivity.this,
                        Manifest.permission.READ_CONTACTS)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(ContactsActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }else {
                getContacts();

            }
        }
        else {
            getContacts();
        }


    }

    private void getContacts() {


        // Run query
//        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] { ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER };
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
                + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";



        Cursor cursor = managedQuery(uri, projection, selection, selectionArgs,
                sortOrder);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            do {

                String displayName = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                String displayNumber = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                DBadapter dBadapter=new DBadapter(ContactsActivity.this);
                dBadapter.add(displayName,displayNumber);

            } while (cursor.moveToNext());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    getContacts();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onItemClick(int status) {
        arrayList.clear();
     DBadapter dBadapter= new DBadapter(ContactsActivity.this);
        Cursor cursor= dBadapter.getAll();
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            do{
                ContactListItem contactListItem= new ContactListItem();
                contactListItem.setContactName(cursor.getString(cursor.getColumnIndex(Comment.NAME)));
                contactListItem.setContactNumber(cursor.getString(cursor.getColumnIndex(Comment.NUMBER)));
                    arrayList.add(contactListItem);
            }while (cursor.moveToNext());
        }
        MyAdapter myadapter=new MyAdapter(arrayList,ContactsActivity.this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myadapter);



    }
}
