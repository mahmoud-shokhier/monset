package com.example.abdelhameedahmed.monset.cl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sata
 */

public class DCT {

	/**
	 * number of mfcc coeffs
	 */
	int numCepstra;
	/**
	 * number of Mel Filters
	 */
	int M;

	/**
	 * @param len
	 *            length of array, i.e., number of features
	 * @param M
	 *            numbe of Mel Filters
	 * @return
	 */
	public DCT(int numCepstra, int M) {
		this.numCepstra = numCepstra;
		this.M = M;
	}

	public double[] performDCT(double y[]) {
		double cepc[] = new double[numCepstra];
		// perform DCT
		for (int n = 1; n <= numCepstra; n++) {
			for (int i = 1; i <= M; i++) {
				cepc[n - 1] += y[i - 1] * Math.cos(Math.PI * (n - 1) / M * (i - 0.5));
			}
		}
		return cepc;
	}
}