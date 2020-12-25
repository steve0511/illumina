package com.steve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author stevexu
 * @since 2020/10/1
 */
public class Test {
	static void combinationUtil(int arr[], int data[], int start,
		int end, int index, int r)
	{
		// Current combination is ready to be printed, print it
		if (index == r)
		{
			int sum = 0;
			for (int j=0; j<r; j++){
				sum += data[j];
			}
			System.out.print(sum);
			return;
		}

		// replace index with all possible elements. The condition
		// "end-i+1 >= r-index" makes sure that including one element
		// at index will make a combination with remaining elements
		// at remaining positions
		for (int i=start; i<=end && end-i+1 >= r-index; i++)
		{
			data[index] = arr[i];
			combinationUtil(arr, data, i+1, end, index+1, r);
		}
	}

	// The main function that prints all combinations of size r
	// in arr[] of size n. This function mainly uses combinationUtil()
	static void printCombination(int arr[], int n, int r)
	{
		// A temporary array to store all combination one by one
		int data[]=new int[r];

		// Print all combination using temprary array 'data[]'
		combinationUtil(arr, data, 0, n-1, 0, r);
	}

	/*Driver function to check for above function*/
	public static void main (String[] args) {
		int arr[] = {20, 10, 5};
		int r = 3;
		int n = arr.length;
		printCombination(arr, n, r);
	}
}
