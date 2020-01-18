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

import com.example.calculationtest.databinding.FragmentWinBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class WinFragment extends Fragment {


    public WinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MyScoreViewModel model = ViewModelProviders.of(requireActivity(), new SavedStateViewModelFactory(getActivity().getApplication(), requireActivity())).get(MyScoreViewModel.class);
        FragmentWinBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_win,container,false);
        binding.setData(model);
        binding.setLifecycleOwner(requireActivity());
        binding.button11.setOnClickListener((view) -> {
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_winFragment_to_homeFragment);
        });
        return binding.getRoot();
    }

}
