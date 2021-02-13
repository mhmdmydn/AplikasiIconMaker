package com.ghodel.gicons.util;

import android.content.Context;
import java.io.IOException;
import android.widget.Toast;
import com.ghodel.gicons.model.IconModel;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import android.content.res.AssetManager;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.CheckBox;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;

public class MainUtils {
    
    public static String TAG = MainUtils.class.getSimpleName();
    
    public static void showToast(Context con, String s){
        Toast.makeText(con, s , Toast.LENGTH_LONG).show();
    }
    
    public static List<IconModel> getFiles(Context con, String subFolderPath){
        List<IconModel> listIM = new ArrayList<>();
        try {
            String[] listDir = con.getAssets().list(subFolderPath);
            if( listDir.length > 0){
                for(String file : listDir){
                    String path = subFolderPath + "/" + file;
                    if(new File(path).isDirectory()){
                        listIM.addAll(getFiles(con, subFolderPath));
                    } else{
                        IconModel im = new IconModel();
                        im.setFile(file);
                        im.setFilePath(path);
                        im.setSVG(SVG.getFromAsset(con.getAssets(), im.getFilePath()));
                        listIM.add(im);
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (SVGParseException e2){
            Log.e(TAG, e2.toString());
        }
        return listIM;
    }
    
    public static void changeActivityFont(Activity con, String fontNameAsset){
        String fontName = fontNameAsset.trim();
        if(fontName.contains(".ttf")){
            fontName = fontName.replaceAll(".ttf", "");
        }
        overrideFonts(con, con.getWindow().getDecorView(), fontNameAsset);
    }

    private static void overrideFonts(final Activity con, final View v, final String fontName){
        try{
            Typeface activityTypeFace = Typeface.createFromAsset(con.getAssets(), "fonts/" + fontName + ".ttf");
            if((v instanceof ViewGroup)){
                ViewGroup activityFontGroup = (ViewGroup) v;
                for (int i = 0; i < activityFontGroup.getChildCount(); i++) {
                    View child = activityFontGroup.getChildAt(i);
                    overrideFonts(con, child, fontName);
                }
            }
            else {
                if ((v instanceof TextView)) {
                    ((TextView) v).setTypeface(activityTypeFace);
                }
                else {
                    if ((v instanceof EditText )) {
                        ((EditText) v).setTypeface(activityTypeFace);
                    }
                    else {
                        if ((v instanceof Switch )) {
                            ((Switch) v).setTypeface(activityTypeFace);
                        }
                        else {
                            if ((v instanceof CheckBox )) {
                                ((CheckBox) v).setTypeface(activityTypeFace);
                            }
                            else {
                                if ((v instanceof Button)) {
                                    ((Button) v).setTypeface(activityTypeFace);
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            showToast(con, e.toString());
        }
    } 
    
}
