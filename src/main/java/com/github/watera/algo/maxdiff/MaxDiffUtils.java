/*
 * Created by Yan Jian on 2017/4/29.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo.maxdiff;

import com.github.watera.algo.Range;

public class MaxDiffUtils {

    // O(n^2)
    public static Range simple(final int[] arr) {
        int startPoint = 0, endPoint = 0;
        final int length = arr.length;
        int maxDiff = -1;
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                int diff = arr[j] - arr[i];
                if (diff > maxDiff) {
                    maxDiff = diff;
                    startPoint = i;
                    endPoint = j;
                }
            }
        }
        return new Range(startPoint, endPoint);
    }

    // O(n)
    public static Range dp(final int[] arr) {
        int startPoint = 0, endPoint = 0;
        final int length = arr.length;
        int maxDiff = 0;
        int[] minPs = new int[length];
        minPs[0] = 0;
        for (int i = 1; i < length; i++) {
            final int lastMin = arr[minPs[i - 1]];
            if (lastMin < arr[i]) {
                minPs[i] = minPs[i-1];
            } else {
                minPs[i] = i;
            }
        }
        for (int i = 1; i<length;i++) {
            final int diff = arr[i] - arr[minPs[i-1]];
            if (diff > maxDiff) {
                maxDiff = diff;
                startPoint = minPs[i-1];
                endPoint = i;
            }
        }
        return new Range(startPoint, endPoint);
    }
}
