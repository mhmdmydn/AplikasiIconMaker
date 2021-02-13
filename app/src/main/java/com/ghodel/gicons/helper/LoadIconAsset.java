package com.ghodel.gicons.helper;
import android.os.AsyncTask;
import com.ghodel.gicons.model.IconModel;
import java.util.List;
import android.content.res.AssetManager;
import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import com.ghodel.gicons.util.MainUtils;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.kaopiz.kprogresshud.KProgressHUD;
import android.util.Log;

public class LoadIconAsset extends AsyncTask<Void, Void, List<IconModel> > {
    
    private Context con;
    private TaskListener taskListener;
    private KProgressHUD hud;
    private String assetPath;
    private List<IconModel> im;
    public static String TAG = LoadIconAsset.class.getSimpleName();
    
    public LoadIconAsset(Context con, String assetPath, TaskListener taskListener){
        
        this.con = con;
        this.assetPath = assetPath;
        this.taskListener = taskListener;
    }
    
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        hud = new KProgressHUD(con);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();
        
    }

    @Override
    protected List<IconModel> doInBackground(Void... params){
        im = new ArrayList<>();
        im = MainUtils.getFiles(con, assetPath);
        return im;
    }

    @Override
    protected void onPostExecute(List<IconModel> result) {
        super.onPostExecute(result);
        Log.d(TAG, result.toString());
        if(hud != null && hud.isShowing()){
            hud.dismiss();
            taskListener.onResponse(result);
            }
    }
    
    public interface TaskListener{
        void onResponse(List<IconModel> result);
    }
}
