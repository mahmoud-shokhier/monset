/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.abdelhameedahmed.monset.cl;

/**
 * Definition of the FeaturesExtractor interface
 * 
 * @author Amaury Crickx
 * @param <T> the kind of features to extract
 */
public interface FeaturesExtractor<T> {

    /**
     * Extracts features from given voice sample
     * @param voiceSample the voice sample to analyze
     * @return An objet of type T representing the features
     */
    public T extractFeatures(double[] voiceSample);

}