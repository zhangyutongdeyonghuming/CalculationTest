package com.example.calculationtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MyScoreViewModel model;
    private NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this,new SavedStateViewModelFactory(getApplication(),this)).get(MyScoreViewModel.class);
        controller = Navigation.findNavController(this,R.id.fragment);
        //增加返回小箭头，传入controller逻辑
        NavigationUI.setupActionBarWithNavController(this,controller);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //点击返回 判断是否在测试中，
        if (controller.getCurrentDestination().getId() == R.id.testFragment){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.is_quit));
            //如果确定则退出
            builder.setPositiveButton(getString(R.string.ok),(dialog,which) -> {
                //当前分数值为0
                model.getCurrentScore().setValue(0);
                controller.navigateUp();
            });
            //如果取消则不进行操作
            builder.setNegativeButton(getString(R.string.cancel),(dialog,which) -> {

            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }else if(controller.getCurrentDestination().getId() == R.id.homeFragment){
            //如果要转到homeFragment直接放行
            finish();
        }else{
            controller.navigate(R.id.homeFragment);
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}
