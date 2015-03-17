package com.example.app;

/**
 * Created by OBNinja on 11/9/14.
 */
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity{
    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    ArrayAdapter<String> adapter;
    BreadAdapter drawerAdapter;
    ArrayList<BreadSlices> drawerMenu;
    Blur blurry;
    FrameLayout frameContent;
    ImageView blurLayer;
    Activity Myself;

    // All Fragments
    FragmentManager fragmentManager;
    Fragment dashFrag;
    Fragment contactsFrag;

    // Dashboard Things
    ListView conversationListView;
    ArrayList<Sms> UnsortedSmses;
    ArrayList<Conversation> listOfConvos;
    ConversationAdapter convoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dLayout.setScrimColor(Color.argb(60,0,0,0));
        dList = (ListView) findViewById(R.id.left_drawer);
        blurLayer = (ImageView) findViewById(R.id.blur_layer);
        frameContent = (FrameLayout) findViewById(R.id.content_frame);
        blurry = new Blur();
        Myself = this;

        UnsortedSmses = new ArrayList<Sms>();
        listOfConvos = new ArrayList<Conversation>();
        convoAdapter = new ConversationAdapter(listOfConvos,this);

        fragmentManager = this.getFragmentManager();
        // Fragment Initializations
        dashFrag = new ConversationFragment();

        dLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDrawerOpened(View view) {
                blurLayer.setVisibility(View.VISIBLE);

                // Animating in
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(blurLayer, "alpha", .0f, 1f);
                fadeIn.setDuration(700);
                final AnimatorSet mAnimationSet = new AnimatorSet();

                frameContent.setDrawingCacheEnabled(true);
                frameContent.destroyDrawingCache();
                Bitmap currentSnapshot = frameContent.getDrawingCache();
                BitmapDrawable bibi = new BitmapDrawable(blurry.fastblur(getApplicationContext(), getResizedBitmap(currentSnapshot,(float)0.25,(float)0.25), 8));
                blurLayer.setBackground(bibi);

                mAnimationSet.play(fadeIn);
                mAnimationSet.start();
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerClosed(View view) {
                blurLayer.setAlpha((float) 0.0);
                blurLayer.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        drawerMenu = new ArrayList<BreadSlices>();
        drawerMenu.add(new BreadSlices(R.drawable.dashboard_icon,"Dashboard"));
        drawerMenu.add(new BreadSlices(R.drawable.contactsicon,"Contacts"));
        drawerMenu.add(new BreadSlices(R.drawable.inboxicon,"Inbox"));
        drawerMenu.add(new BreadSlices(R.drawable.outboxicon,"Sent"));
        drawerMenu.add(new BreadSlices(R.drawable.settingsicon,"Settings"));
        drawerMenu.add(new BreadSlices(R.drawable.abouticon,"About"));
        drawerAdapter = new BreadAdapter(this,drawerMenu);

        dList.setAdapter(drawerAdapter);

        dList.setOnItemClickListener(new OnItemClickListener(){

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
            dLayout.closeDrawers();
            switch (position){
                case 0: {
                    // Opening dashboard fragment
                    if(!dashFrag.isAdded()){
                            clearSelected();
                            drawerMenu.get(position).setSelected(true);
                            drawerAdapter.notifyDataSetChanged();
                            ((ConversationFragment) dashFrag).setParentActivity(Myself);
                            ((ConversationFragment) dashFrag).refreshFragmentProcesses();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, dashFrag).commit();
                    }
                    break;
                }
                case 1:{
                    break;
                }
                case 3:{
                    break;
                }
            }

            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
    }

    private void clearSelected(){
        for (BreadSlices bread : drawerMenu){
            bread.setSelected(false);
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image, float scaleHeight, float scaleWidth) {
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        return resizedBitmap;
    }
}
