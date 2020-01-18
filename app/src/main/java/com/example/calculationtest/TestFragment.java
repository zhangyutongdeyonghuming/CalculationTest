package com.example.calculationtest;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculationtest.databinding.FragmentTestBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyScoreViewModel model = ViewModelProviders.of(requireActivity()
                ,new SavedStateViewModelFactory(requireActivity().getApplication(),this)).get(MyScoreViewModel.class);
        FragmentTestBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_test,container,false);
        binding.setData(model);
        binding.setLifecycleOwner(requireActivity());
        StringBuilder result = new StringBuilder();
        //定义点击事件
        View.OnClickListener listener = (view) -> {
            switch (view.getId()){
                case R.id.button0:
                    result.append(0);
                    break;
                case R.id.button1:
                    result.append(1);
                    break;
                case R.id.button2:
                    result.append(2);
                    break;
                case R.id.button3:
                    result.append(3);
                    break;
                case R.id.button4:
                    result.append(4);
                    break;
                case R.id.button5:
                    result.append(5);
                    break;
                case R.id.button6:
                    result.append(6);
                    break;
                case R.id.button7:
                    result.append(7);
                    break;
                case R.id.button8:
                    result.append(8);
                    break;
                case R.id.button9:
                    result.append(9);
                    break;
                    default:
                        break;
            }
            if (result.length() == 0 ){
                binding.textView8.setText(R.string.tip_input);
            }else{
                binding.textView8.setText(result.toString());
            }
        };
        //将所有数字按钮加上点击事件
        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);

        //清除按钮点击事件
        binding.buttonClear.setOnClickListener((view) -> {
            result.setLength(0);
            binding.textView8.setText(R.string.tip_input);
        });
        //提交按钮点击事件
        binding.buttonSubmit.setOnClickListener((view) -> {
            //空提交
            if (result.length() == 0 ){
                return;
            }
            //如果不是空提交，答对加一分并重刷题，答错跳转到错误页面
            if (Integer.valueOf(result.toString()) == model.getResult().getValue()){
                model.afterRight();
                result.setLength(0);
                binding.textView8.setText(R.string.right_tip);
            }else{
                NavController controller = Navigation.findNavController(view);

                if (model.winFlag){
                    //如果赢了一把 则跳成功界面
                    controller.navigate(R.id.action_testFragment_to_winFragment);
                    //重置当前输入的数字
                    result.setLength(0);
                    model.winFlag = false;
                    model.save();
                }else{
                    //如果一盘没对则失败
                    controller.navigate(R.id.action_testFragment_to_loseFragment);
                }
            }
        });
        return binding.getRoot();
    }

}
