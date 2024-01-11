package com.q3;

import java.util.ArrayList;

public class MaxSumIncreaseSequence {
	public static ArrayList<Integer> maxSumIncreasingSubsequence(int[] arr) {
        int n = arr.length;
        int[] lis = new int[n];
        int[] sums = new int[n];
        int maxSumIndex= 0;

        // initialize lis and sums arrays with values of arr
        for (int i = 0; i < n; i++) {
            lis[i] = arr[i];
            sums[i] = arr[i];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    if (lis[j] + arr[i] > lis[i]) {
                        lis[i] = lis[j] + arr[i];
                        sums[i] = sums[j] + arr[i];
                    }
                }
            }
            if (sums[i] > sums[maxSumIndex]) {
                maxSumIndex = i;
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        int maxSum = sums[maxSumIndex];
        for (int i = maxSumIndex; i >= 0; i--) {
            if (sums[i] == maxSum && (result.isEmpty() || arr[i] < result.get(result.size() - 1))) {
                result.add(arr[i]);
                maxSum -= arr[i];
            }
        }

        ArrayList<Integer> reversedResult = new ArrayList<>();
        for (int i = result.size() - 1; i >= 0; i--) {
            reversedResult.add(result.get(i));
        }

        return reversedResult;
    }

    public static void main(String[] args) {
        int[] arr = {0,8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11};
        ArrayList<Integer> resultSubsequence = maxSumIncreasingSubsequence(arr);
        
        System.out.println("Maximum Sum Increasing Subsequence: " + resultSubsequence);
    }


}
