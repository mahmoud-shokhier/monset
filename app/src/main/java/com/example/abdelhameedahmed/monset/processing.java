package com.example.abdelhameedahmed.monset;

/**
 * Created by abdelhameed ahmed on 6/6/2018.
 */
import android.content.Context;
import android.text.StaticLayout;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import org.openimaj.audio.features.MFCC;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.video.xuggle.*;



import static android.widget.Toast.LENGTH_LONG;


public class processing {
    Context context;

    public processing(Context context){
        this.context=context;
        //System.loadLibrary("libxuggle.so");

    }
    public String functionallity(String path){
        // Open a URL to the sine wave sweep. If you have downloaded
        // this file you should use a new File(<filename>) here.


        final XuggleAudio xa = new XuggleAudio(path);

        // Create the Fourier transform processor chained to the audio decoder
        final MFCC mfcc = new MFCC(xa);

        // Create a visualisation to show our FFT and open the window now
        //final BarVisualisation bv = new BarVisualisation( 400, 200 );
        //bv.showWindow( "MFCCs" );

        // Loop through the sample chunks from the audio capture thread
        // sending each one through the feature extractor and displaying
        // the results in the visualisation.
        int v=0;
        ArrayList<ArrayList<Double>>features = new ArrayList<>();
        while( mfcc.nextSampleChunk() != null )
        {

            ArrayList<Double> f = new ArrayList<>();
            final double[][] mfccs = mfcc.getLastCalculatedFeatureWithoutFirst();
            //	bv.setData( mfccs[0]);
            int i = 0;
            for (i=1;i<mfccs[0].length;i++){
                //System.out.println(mfccs[0][i]);
                f.add(mfccs[0][i]);
            }
            features.add(f);

        }
        //System.out.println(features.size());

        ArrayList<Double> featuresAVG = new ArrayList<>();
        for( int y = 0; y<features.get(1).size();y++){
            double sum=0;
            for (int x=0; x<features.size();x++){
                sum+=features.get(x).get(y);
            }
            sum=sum/features.size();
            featuresAVG.add(sum);
        }



//        for (int i=0;i<featuresAVG.size();i++){
//            System.out.print(featuresAVG.get(i)+",");
//        }

        double [][] multi = new double[][]{

                {-6.293339367675419,-8.729532124067248,0.31619772854125605,-1.9535306762931564,-1.6937726263143014,2.1196510576315224,0.21903982929553142,-3.744228931408375,-3.1052478153193372,0.1345601609322591,-0.08467802402787869,-0.3350076629876708,1},
                {-14.516331061908879,-8.994898171656846,1.195432121253091,-1.2084946902021272,-3.5517341398603492,-0.4360406284326987,0.06894975845726749,-2.7050184427091115,-4.871324445486728,-1.0926771798536536,1.2441740028244856,-0.07425146503123056,1},
                {0.5286051519132912,-3.1918847460688182,0.8149536987913173,-0.27515944996305525,-2.231529794356551,1.523790781999672,0.5117110928837939,0.055922469195426185,-2.1093275744900555,0.9033951723551574,-0.5714290643451397,-0.5746905236602523,1},
                {7.543612926912677,-3.945915973262485,-0.47937859173341774,2.212101662789546,1.0751914631937847,-0.060632795973647145,1.4892682873044776,1.124399318364003,-0.6518363948868661,-2.632141393016043,-0.6145969322962744,0.5286423847910123,0},
                {6.477972704880127,-2.975628973572211,0.835923496374575,0.9601913122413523,0.14076865364179122,1.1452908900047551,2.125855701797299,-0.6399211960802562,-0.9115216956199748,-0.982901482755145,-1.0072707195688222,-0.38968677295984,0}

        };


       // System.out.println("result");
        //System.out.println("result");
        double []res = new double[5];
        for (int ycounter=0 ; ycounter<multi.length ;ycounter++){
            double sum = 0.0;
            for (int xcounter=0;xcounter<multi[ycounter].length-1 ; xcounter++){
                sum+= Math.pow((featuresAVG.get(xcounter)-multi[ycounter][xcounter]),2);

            }
            sum=Math.sqrt(sum);
            // System.out.println(sum);
            res[ycounter]=sum;
        }

        // Arrays.sort(res);
        //15,23,7,5,2
        double minValue = res[0];
        int indexOfres=0;
        for (int i = 1; i < res.length; i++) {
            if (res[i] < minValue) {
                minValue = res[i];
                indexOfres=i;
            }
        }

       // for (int r = 0;r<res.length;r++){
         //   System.out.println(res[r]);}



        if(multi[indexOfres][12]==1.0){
            return "babycry";
        }
        else
            return "car";


    }
    public String  get_name(){
        return "excuted";
    }


}
