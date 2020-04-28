package com.example.abdelhameedahmed.monset;



import android.content.Context;
import android.widget.Toast;

import com.example.abdelhameedahmed.monset.cl.BasicFeatureExtraction;

import java.io.File;

/**
 * Created by abdelhameed ahmed on 6/12/2018.
 */

public class processing2 {
    Context context;
    prediction4multest aClass;


    public processing2(Context context){
        this.context=context;
        aClass=new prediction4multest();

        //System.loadLibrary("libxuggle.so");

    }
    public String process_f(String path){
        BasicFeatureExtraction bfe = new BasicFeatureExtraction();
        double[] mfcc = bfe.extractMfcc(new File(path));

        String s=aClass.getmaxresult(mfcc);
/*
        for (int i =0;i<mfcc.length;i++){

            Toast.makeText(this.context,mfcc[i]+"", Toast.LENGTH_SHORT).show();
        }
   */

        return s;
    }
}
