package ru.nsu.fit.tretyakov;

import java.io.BufferedInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Tree<T> implements Collection<T> {

    private int size;
    private Node<T> root;
    private Node<T>[] nodes;

    public Tree(int size, T data) {
        this.size = size;
        this.root = new Node<>(data);
    }

    private boolean findData(Node<T> curNode, T data){

        if (curNode == null) return false;
        if (curNode.getData() == data) return true;

        Node<T> ls = curNode.leftSon;
        Node<T> rs = curNode.rightSon;

        return findData(ls, data) || findData(rs, data);
    }

    private void createArrayFromTree(Object[] resultArray, int sizeOfArray, Node<T> node){
        if (node == null) return;
        resultArray[sizeOfArray++] = (Object) node;
        createArrayFromTree(resultArray,sizeOfArray,node.leftSon);
        createArrayFromTree(resultArray,sizeOfArray,node.rightSon);
    }

    private void addRecursive(Node<T> node, T data){

        Node<T> ls = node.leftSon;
        Node<T> rs = node.rightSon;

        if (ls == null || rs == null){
            if (ls == null) ls = new Node<T>(data);
            else rs = new Node<T>(data);
            size++;
        }

        else if (ls.depth <= rs.depth) addRecursive(ls,data);
        else addRecursive(rs,data);

        return;
    }

    private void clearTraverse(Node<T> node){
        if (node.leftSon == null && node.rightSon == null){
            node = null;
            return;
        }
        if (node.leftSon != null) clearTraverse(node.leftSon);
        if (node.rightSon != null) clearTraverse(node.rightSon);
    }

//    private Node<T> remove(Node<T> curNode, Node<T> checkNode){
//
//        Node<T> ls = curNode.leftSon;
//        Node<T> rs = curNode.rightSon;
//
//        /*if (checkNode.equals(curNode)){
//            if (ls == null && rs == null) curNode = null;
//            else{
//
//            }
//
//        }*/
//
//    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        return findData(root,(T)o);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                return null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[size];
        createArrayFromTree(resultArray,0,root);
        return resultArray;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] resultArray = (T1[]) new Object[size];
        createArrayFromTree(resultArray,0,root);
        return resultArray;
    }

    @Override
    public boolean add(T t) {
        addRecursive(root,t);
        return true;
    }

    @Override
    public boolean remove(Object o) throws NullPointerException{
        if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        while(c.iterator().hasNext()){
            if (!contains(c.iterator().next()))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        clearTraverse(root);
    }

    private class Node<T>{
        private T data;
        protected Integer depth;
        protected boolean isChecked;
        protected Node<T> leftSon, rightSon;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node(T data) {
            this.data = data;
            isChecked = false;
            depth = 0;
            leftSon = rightSon = null;
        }
    }
}
