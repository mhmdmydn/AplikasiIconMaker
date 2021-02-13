package com.ghodel.gicons.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ghodel.gicons.util.MainUtils;

public abstract class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        MainUtils.changeActivityFont(this, "Roboto-Medium");
	}
	
	public abstract void initViews();
	public abstract void initLogic();
	public abstract void initListeners();
}
