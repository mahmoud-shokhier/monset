/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.abdelhameedahmed.monset.cl;

/**
 *
 * @author sata
 */

public class LpcFeaturesExtractor 
        extends WindowedFeaturesExtractor<double[]> {

    private final int poles;
    private final WindowFunction windowFunction;
    private final LinearPredictiveCoding lpc;

    public LpcFeaturesExtractor(float sampleRate, int poles) {
        super(sampleRate);
        this.poles = poles;
        this.windowFunction = new HammingWindowFunction(windowSize);
        this.lpc = new LinearPredictiveCoding(windowSize, poles);
    }

    @Override
    public double[] extractFeatures(double[] voiceSample) {

        double[] voiceFeatures = new double[poles];
        double[] audioWindow = new double[windowSize];

        int counter = 0;
        int halfWindowLength = windowSize / 2;

        for (int i = 0; (i + windowSize) <= voiceSample.length; i += halfWindowLength) {

            System.arraycopy(voiceSample, i, audioWindow, 0, windowSize);

            windowFunction.applyFunction(audioWindow);
            double[] lpcCoeffs = lpc.applyLinearPredictiveCoding(audioWindow)[0];

            for (int j = 0; j < poles; j++) {
                voiceFeatures[j] += lpcCoeffs[j];
            }
            counter++;
        }

        if (counter > 1) {
            for (int i = 0; i < poles; i++) {
                voiceFeatures[i] /= counter;
            }
        }
        return voiceFeatures;
    }
}