package com.ghodel.gicons.activity;

import android.os.Bundle;
import com.ghodel.gicons.R;
import androidx.appcompat.widget.Toolbar;
import android.widget.LinearLayout;
import com.ghodel.gicons.util.CheckerboardDrawable;
import android.graphics.Color;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVG;
import android.content.res.AssetManager;
import java.io.IOException;
import com.caverock.androidsvg.SVGParseException;

import android.widget.SeekBar;
import com.gcssloop.widget.RCRelativeLayout;
import android.content.res.Resources;
import android.view.View;
import com.ghodel.gicons.util.MainUtils;
import androidx.cardview.widget.CardView;
import com.google.android.material.navigation.NavigationView;
import com.itsaky.colorpicker.ColorPickerDialog;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import java.io.File;
import java.util.Random;
import java.io.FileOutputStream;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.content.DialogInterface;
import android.widget.RelativeLayout;
import android.graphics.drawable.Drawable;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.net.Uri;

public class CreateIconActivity extends BaseActivity {
    
    public static String TAG = CreateIconActivity.class.getSimpleName();
    private Toolbar toolbar;
    private LinearLayout rootView, bgAndShadow, layoutRound;
    private SeekBar seekTopLeftRadius, seekTopRightRadius, seekBottomLeftRadius, seekBottomRightRadius;
    private String filePath, fileName;
    private int initialColor = 0xffffff00;
    private SVGImageView svgImg;
    private RCRelativeLayout RCRound;
    private CardView cardEditor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_icon);
        initViews();
        initLogic();
        initListeners();
        
    }
    
    
    @Override
    public void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        rootView = (LinearLayout)findViewById(R.id.root);
        bgAndShadow =(LinearLayout)findViewById(R.id.bg_and_shadow);
        svgImg =(SVGImageView)findViewById(R.id.icon_view);
        cardEditor = (CardView)findViewById(R.id.card_editor);
        layoutRound =(LinearLayout)findViewById(R.id.layout_round);
        RCRound =(RCRelativeLayout)findViewById(R.id.rc_layout);
        seekTopLeftRadius = (SeekBar)findViewById(R.id.seekbar_radius_top_left);
        seekTopRightRadius = (SeekBar)findViewById(R.id.seekbar_radius_top_right);
        seekBottomLeftRadius = (SeekBar)findViewById(R.id.seekbar_radius_bottom_left);
        seekBottomRightRadius = (SeekBar)findViewById(R.id.seekbar_radius_bottom_right);
    }

    @Override
    public void initLogic() {
        
        if(getIntent().getStringExtra("filePath") != null && getIntent().getStringExtra("fileName") != null){
            filePath = getIntent().getStringExtra("filePath");
            fileName = getIntent().getStringExtra("fileName");
        }
        
        try {
            svgImg.setSVG(SVG.getFromAsset(getAssets(), filePath));
            
        } catch (IOException e) {
            
        } catch (SVGParseException e) {
            
        }
        
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(fileName);
        
        
        CheckerboardDrawable drawable = new CheckerboardDrawable.Builder()
            .colorOdd(Color.LTGRAY)
            .colorEven(Color.GRAY)
            .size(20)
            .build();
        rootView.setBackgroundDrawable(drawable);
        
        
    }

    @Override
    public void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View _v) {
                    showPermission();
                }
            });
            
        svgImg.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                    colorPicker();
                }
            });
        
        seekTopLeftRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                @Override
                public void onProgressChanged(SeekBar p1,final int p2, boolean p3) {
                    
                    runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                RCRound.setTopLeftRadius(dp2px(p2));
                                
                            }
                        });
                }
                @Override
                public void onStartTrackingTouch(SeekBar p1) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar p1) {
                }
            });
            
        seekTopRightRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                @Override
                public void onProgressChanged(SeekBar p1,final int p2, boolean p3) {

                    runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                RCRound.setTopRightRadius(dp2px(p2));

                            }
                        });
                }
                @Override
                public void onStartTrackingTouch(SeekBar p1) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar p1) {
                }
            });
            
        seekBottomLeftRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                @Override
                public void onProgressChanged(SeekBar p1,final int p2, boolean p3) {

                    runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                RCRound.setBottomLeftRadius(dp2px(p2));

                            }
                        });
                }
                @Override
                public void onStartTrackingTouch(SeekBar p1) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar p1) {
                }
            });
            
        seekBottomRightRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                @Override
                public void onProgressChanged(SeekBar p1,final int p2, boolean p3) {

                    runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                RCRound.setBottomRightRadius(dp2px(p2));

                            }
                        });
                }
                @Override
                public void onStartTrackingTouch(SeekBar p1) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar p1) {
                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    
    private int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    private void colorPicker(){
        ColorPickerDialog view = new ColorPickerDialog(this, "#f44336");
        view.setColorPickerCallback(new ColorPickerDialog.ColorPickerCallback(){
                @Override
                public void onColorPicked(int color, String hexColorCode)
                {
                    layoutRound.setBackgroundColor(color);
                }
            });
            
            view.show();
    }
    
    private Bitmap screenShot(View view) {
        Bitmap bitmap;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return bitmap = view.getDrawingCache();
    }
    
    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) 
            bgDrawable.draw(canvas);
        else 
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
    
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
        boolean willNotCacheDrawing = view.willNotCacheDrawing();
        view.setDrawingCacheEnabled(true);
        view.setWillNotCacheDrawing(false);
        Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (null == drawingCache || drawingCache.isRecycled()) {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                         View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            drawingCache = view.getDrawingCache();
            if (null == drawingCache || drawingCache.isRecycled()) {
                bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
            } else {
                bitmap = Bitmap.createBitmap(drawingCache);
            }
        } else {
            bitmap = Bitmap.createBitmap(drawingCache);
        }
        view.setWillNotCacheDrawing(willNotCacheDrawing);
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        return bitmap;
    }
    
    public static Bitmap cropToSquare(Bitmap srcBmp) {
        Bitmap dstBmp = null;
        if (srcBmp.getWidth() >= srcBmp.getHeight()) {

            dstBmp = Bitmap.createBitmap(
                srcBmp,
                srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                0,
                srcBmp.getHeight(),
                srcBmp.getHeight()
            );

        } else {
            dstBmp = Bitmap.createBitmap(
                srcBmp,
                0,
                srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                srcBmp.getWidth(),
                srcBmp.getWidth()
            );
        }

        return dstBmp;
    }
    

    private static void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Gicons");    
        myDir.mkdirs();
        Random generator = new Random();
        int n = 100;
        n = generator.nextInt(n);
        String fname = "ic_launcher"+ n +".png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete (); 
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 0, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void startSaveFile() {
        Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("image/png");
        intent.putExtra("android.intent.extra.TITLE", "ic_launcher.png");
        try {
            startActivityForResult(intent, 43);
        } catch (ActivityNotFoundException e) {

        }
    }
    
    
    private void showPermission(){
        if (ActivityCompat.checkSelfPermission(CreateIconActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            new AlertDialog.Builder(CreateIconActivity.this)
                .setTitle("Permission Required")
                .setMessage("App need access external storage for store apk backup file.")
                .setCancelable(false)
                .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(CreateIconActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                    }
                })
                .setNegativeButton("CLOSE", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                })
                .show();
        } else {
            Log.d("PLAYGROUND", "Permission is granted");
            startSaveFile();
        }

    }
    

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PLAYGROUND", "Permission has been granted");
                startSaveFile();
            } else {
                Log.d("PLAYGROUND", "Permission has been denied or request cancelled");
                MainUtils.showToast(CreateIconActivity.this, "failed to save icon!!!");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 43 && resultCode == -1 && data != null) {
            saveBitmapToUri(data.getData(), screenShot(this.RCRound));
        }
    }
    
    private void saveBitmapToUri(Uri uri, Bitmap bitmap) {
        try {
            ParcelFileDescriptor openFileDescriptor = getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(openFileDescriptor.getFileDescriptor());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            openFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
