package ru.nsu.fit.tretyakov;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Scanner reading
        Scanner in = new Scanner(System.in);

        System.out.println("Enter capacity of the array: ");
        int capacity = in.nextInt();
        int[] arr = new int[capacity];

        System.out.println("Enter elements of the array: ");
        for (int i = 0; i < capacity; i++) {
            arr[i] = in.nextInt();
        }

        HeapSort hs = new HeapSort(arr);
        hs.heapSort();

        System.out.println("The sorted array is:\n");
        for (int element : arr) {
            System.out.printf("%d ", element);
        }
    }
}
