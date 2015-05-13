package com.example.david.animasisangatsederhana;

import android.app.Fragment;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.david.animasisangatsederhana.R;

/**
 * Created by david on 5/12/15.
 */
public class MainFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.first_scene, container, false);

        final Scene scene = Scene.getSceneForLayout(container, R.layout.another_scene, getActivity());
        final Transition transition = TransitionInflater.from(getActivity()).inflateTransition(R.transition.fade_transition);
        Button goButton = (Button)rootView.findViewById(R.id.goButton);

        goButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //executeTransition(scene);
                executeTransitionWithEffect(scene,transition);
            }
        });

        return rootView;
    }

    private void executeTransitionWithEffect(Scene scene, Transition transition)
    {
        TransitionManager.go(scene,transition);
    }

    private void executeTransition(Scene scene)
    {
        TransitionManager.go(scene);
    }

}
