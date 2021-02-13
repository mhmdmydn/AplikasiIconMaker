package com.ghodel.gicons;

import com.ghodel.gicons.util.CrashHandler;
import android.app.Application;

public class App extends Application {
    
    private static App singleton = null;
    
    public App newInstance(){
        if(singleton == null){
            singleton = new App();
        }
        return singleton;
    }
    
	@Override
	public void onCreate() {
        singleton = this;
        CrashHandler.init(singleton);
		super.onCreate();
	}

    
}
