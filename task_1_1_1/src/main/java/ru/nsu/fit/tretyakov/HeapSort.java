package ru.nsu.fit.tretyakov;

import java.util.ArrayList;
import java.util.Arrays;

public class HeapSort {
    private ArrayList<Integer> heap = new ArrayList<>();
    private int[] array;

    public HeapSort(int[] array){
        this.array = array;
    }
    private void swap(int fst, int snd){
        int buf = heap.get(fst);
        heap.set(fst,heap.get(snd));
        heap.set(snd, buf);
    }
    private void siftDown(int node)
    {
        int minNode;

        if (node * 2 + 2 < heap.size() && heap.get(node * 2 + 1) > heap.get(node * 2 + 2)){
            if (node * 2 + 2 < heap.size())
            {
                minNode = node * 2 + 2;
            } else minNode = node * 2 + 1;
        } else minNode = node * 2 + 1;

        if (minNode < heap.size() && heap.get(node) > heap.get(minNode)){
            swap(node, minNode);
            siftDown(minNode);
        }
    }
    private void siftUp(int node){
        if (node >=0 && heap.get(node) < heap.get((node - 1) / 2)) {
            swap(node,(node-1)/2);
            siftUp((node - 1) / 2);
        }
    }
    private int extractMin(){
        int res = heap.get(0);
        swap(0,heap.size() - 1);
        heap.remove(heap.size() - 1);
        siftDown(0);
        return res;
    }

    public void heapSort(){
        int cap = 0;

        for (int j : array) {
            heap.add(j);
            cap++;
            if (cap != 1) siftUp(cap - 1);
        }

        while (cap != 0) {
            array[array.length - cap] = extractMin();
            cap--;
        }
    }

    public void print(){
        System.out.println(Arrays.toString(array));
    }
}
