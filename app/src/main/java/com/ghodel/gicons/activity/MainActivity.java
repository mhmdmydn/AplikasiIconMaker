package com.ghodel.gicons.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.ghodel.gicons.R;
import com.ghodel.gicons.helper.LoadIconAsset;
import com.ghodel.gicons.util.MainUtils;
import androidx.recyclerview.widget.RecyclerView;
import com.ghodel.gicons.model.IconModel;
import java.util.List;
import androidx.recyclerview.widget.GridLayoutManager;
import com.ghodel.gicons.adapter.IconAdapter;
import java.io.IOException;
import android.view.animation.LayoutAnimationController;
import android.view.animation.AnimationUtils;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import android.view.MenuInflater;

public class MainActivity extends BaseActivity {
    
    private Toolbar toolbar;
    private RecyclerView rvIcon;
    private IconAdapter iconAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initLogic();
        initListeners();
   } 
   
    @Override
    public void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        rvIcon =(RecyclerView)findViewById(R.id.rv_icon);
        
    }

    @Override
    public void initLogic() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        RecyclerView.LayoutManager rvGridLayout = new GridLayoutManager(MainActivity.this, 4);
        rvIcon.setLayoutManager(rvGridLayout);
        rvIcon.setHasFixedSize(true);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_anim);
        rvIcon.setLayoutAnimation(animation); 
        
        new LoadIconAsset(MainActivity.this, "materialicon", new LoadIconAsset.TaskListener(){

                @Override
                public void onResponse(List<IconModel> result) {
                    if(result.size() != 0){
                        getSupportActionBar().setSubtitle("Total Icons : " + result.size());
                        iconAdapter = new IconAdapter(MainActivity.this, result);
                        rvIcon.setAdapter(iconAdapter);
                        iconAdapter.notifyDataSetChanged();
                    }
                }
            }).execute();
            
            
            
            
    }

    @Override
    public void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View _v) {
                    onBackPressed();
                }
         });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText != null){
                      iconAdapter.getFilter().filter(newText);
                    }
                    return false;
                }
			});
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_settings:
 
            break;
            default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
