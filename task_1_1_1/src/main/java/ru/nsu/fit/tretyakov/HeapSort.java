package ru.nsu.fit.tretyakov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс <b>HeapSort</b> реализует прирамидальную сортировку.
 * Принимает входным параметром изначальный массив и возвращает отсортированный.
 */
public class HeapSort {
    /** Вспомогательный список — куча. Необходим для осуществления пирамидальной сортировки.
     * @see HeapSort#heapSort()
     */
    private List<Integer> heap = new ArrayList<>();
    /**
     * Возвращаемый отсортированный массив.
     */
    private int[] array;

    /**
     * Конструктор класса. Принимает входым параметром неотсортированный массив.
     * @param array — входной массив.
     */
    public HeapSort(int[] array){
        this.array = array;
    }

    /**
     * <b>swap</b> — Вспомогательный метод. Позволяет поменять местами два элемента кучи.
     * @see HeapSort#heap
     * @param fst — индекс первого элемента в куче.
     * @param snd — индекс второго элемента в куче.
     */
    private void swap(int fst, int snd){
        int buf = heap.get(fst);
        heap.set(fst,heap.get(snd));
        heap.set(snd, buf);
    }

    /**
     * <b>siftDown</b> — Рекурсивный метод просеивания вниз элемента в куче.
     * Алгоритм: У данного элемента индекс <i>node</i>. Выбирается минимальный элемегт с индексом <i>node * 2 + 1</i>
     * или <i>node * 2 + 2</i> таким образом, чтобы он был меньше данного элемента. Далее данный элемент и минимальный
     * меняются местами, затем функция вызывается рекурсивно, пока индекс минимального элемента меньше размера кучи.
     * @param node — индекс элемента
     */
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

    /**
     * <b>siftUp</b> — Рекурсивный метод просеивания вверх элемента в куче.
     * Алгоритм обратный к методу <b>siftDown</b>.
     * {@link HeapSort#siftDown(int)}
     * @param node — индекс элемента
     */
    private void siftUp(int node){
        if (node >=0 && heap.get(node) < heap.get((node - 1) / 2)) {
            swap(node,(node-1)/2);
            siftUp((node - 1) / 2);
        }
    }

    /**
     * <b>extractMin</b> — Метод, возвращающий минимальный элемент в куче.
     * Алгоритм: Первый и последний элементы кучи меняются местами, размер кучи уменьшиается.
     * Затем вызывается метод <b>siftDown</b>.
     * @see HeapSort#siftDown(int)
     * @return res — минимальный элемент в куче на данный момент
     */
    private int extractMin(){
        int res = heap.get(0);
        swap(0,heap.size() - 1);
        heap.remove(heap.size() - 1);
        siftDown(0);
        return res;
    }

    /**
     * <b>heapSort</b> — Главный метод класса HeapSort. Изначально добавляет элементы в кучу,
     * затем, пока куча непустая, возвращает наименьший элемент на данном этапе.
     */
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

    /**
     * <b>print</b> — Публичный метод. Выводит в консоль отсортированный массив.
     */
    public void print(){
        System.out.println(Arrays.toString(array));
    }
}
