package com.example.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

import static android.content.ContentValues.TAG;

public class MyScoreViewModel extends AndroidViewModel {

    //最高分
    private static final String KEY_HIGHEST_SCORE = "key_highest_score";
    //左边数
    private static final String KEY_FIRST_NUM = "key_first_num";
    //右边数
    private static final String KEY_SECOND_NUM = "key_second_num";
    //运算符
    private static final String KEY_OPERATOR = "key_operator";
    //结果
    private static final String KEY_RESULT = "key_result";
    //当前得分
    private static final String KEY_CURRENT_SCORE = "key_current_score";
    //shpName
    private static final String KEY_SHP_NAME = "key_shp_name";

    private SavedStateHandle handle;

    boolean winFlag;

    public MyScoreViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGHEST_SCORE)){
            SharedPreferences shp = getApplication().getSharedPreferences(KEY_SHP_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGHEST_SCORE,shp.getInt(KEY_HIGHEST_SCORE,0));
            handle.set(KEY_FIRST_NUM,0);
            handle.set(KEY_SECOND_NUM,0);
            handle.set(KEY_CURRENT_SCORE,0);
            handle.set(KEY_RESULT,0);
            handle.set(KEY_OPERATOR,"");
        }
        this.handle = handle;
    }


    public void generator(){
        int level = 100;
        Random random = new Random();
        int x,y;
        x = random.nextInt(level)+1;
        y = random.nextInt(level)+1;
        getFirstNum().setValue(x);
        getSecondNum().setValue(y);
        //加减法各占一半
        if (x < y){
            getOperator().setValue("+");
            getResult().setValue(x + y);
        } else {
            getOperator().setValue("-");
            getResult().setValue(x - y);
        }
    }

    /**
     * 保存数据
     */
    public void save(){
        SharedPreferences shp = getApplication().getSharedPreferences(KEY_SHP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_HIGHEST_SCORE,getHighestScore().getValue());
        editor.apply();
    }

    public void afterRight(){
        //答对题之后当前分加一
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getHighestScore().getValue() < getCurrentScore().getValue()){
            getHighestScore().setValue(getCurrentScore().getValue());
            winFlag = true;
        }
        //重置题
        generator();
    }


    /**
     * 获取最高分
     * @return
     */
    public MutableLiveData<Integer> getHighestScore() {
        return handle.getLiveData(KEY_HIGHEST_SCORE);
    }

    /**
     * 获取第一个数
     * @return
     */
    public MutableLiveData<Integer> getFirstNum() {
        return handle.getLiveData(KEY_FIRST_NUM);
    }

    /**
     * 获取第二个数
     * @return
     */
    public MutableLiveData<Integer> getSecondNum() {
        return handle.getLiveData(KEY_SECOND_NUM);
    }

    /**
     * 获取当前分数
     * @return
     */
    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    /**
     * 获取运算符
     * @return
     */
    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }

    /**
     * 获取结果
     * @return
     */
    public MutableLiveData<Integer> getResult() {
        return handle.getLiveData(KEY_RESULT);
    }
}
