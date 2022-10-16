package ru.nsu.fit.tretyakov;

import java.util.*;

import static java.lang.Math.max;

public class Tree<T> implements Collection<T> {

    private int size;
    private Node<T> root;

    public Tree(int size, T data) {
        this.size = size;
        this.root = new Node<>(data,null);
    }

    private boolean findData(Node<T> curNode, T data) {

        if (curNode == null) return false;
        if (curNode.data == data) return true;

        Node<T> ls = curNode.leftSon;
        Node<T> rs = curNode.rightSon;

        return findData(ls, data) || findData(rs, data);
    }

    private int createArrayFromTree(Object[] resultArray, int sizeOfArray, Node<T> node) {
        if (node != null){
            resultArray[sizeOfArray++] = node.data;
            sizeOfArray = createArrayFromTree(resultArray, sizeOfArray, node.leftSon);
            sizeOfArray = createArrayFromTree(resultArray, sizeOfArray, node.rightSon);
        }
        return sizeOfArray;
    }

    private Node<T> addRecursive(Node<T> node, T data) {

        if (node.leftSon == null || node.rightSon == null) {

            size++;
            var sonDepth = node.depth + 1;

            if (node.leftSon == null) node.leftSon = new Node<T>(data,sonDepth, node);
            else node.rightSon = new Node<T>(data, sonDepth, node);

            node.weight++;
            return node;

        } else if (node.leftSon.weight <= node.rightSon.weight)
            addRecursive(node.leftSon, data);
        else addRecursive(node.rightSon, data);

        node.weight = max(node.leftSon.weight, node.rightSon.weight) + 1;
        return node;
    }

    private Node<T> clearTraverse(Node<T> node) {
        if (node.leftSon != null) node.leftSon = clearTraverse(node.leftSon);
        if (node.rightSon != null) node.rightSon = clearTraverse(node.rightSon);
        node = null;
        return node;
    }

    private Node<T> findDeepestLeaf(Node<T> node, int maxDepth) {

        Node<T> ls = node.leftSon;
        Node<T> rs = node.rightSon;

        // if node is leaf

        if (ls == null && rs == null) {
            if (node.depth >= maxDepth) {
                maxDepth = node.depth;
                return node;
            }
        }

        // return deepest leaf

        if (rs != null && ls != null) {

            Node<T> deepestRight, deepestLeft;

            deepestRight = findDeepestLeaf(rs, maxDepth);
            deepestLeft = findDeepestLeaf(ls, maxDepth);

            if (deepestRight.depth >= deepestLeft.depth)
                return deepestRight;
            else return deepestLeft;

        } else return findDeepestLeaf(Objects.requireNonNullElse(rs, ls), maxDepth);
    }

    private Node<T> removeRecursive(Node<T> curNode, T data) {

        Node<T> ls = curNode.leftSon;
        Node<T> rs = curNode.rightSon;
        Node<T> prevNode = curNode;

        if (curNode.data == data) {
            if (ls == null && rs == null) curNode = null;
            else {
                curNode = findDeepestLeaf(root, 0);

                if (curNode.father.leftSon != null){
                    if (curNode.father.leftSon.data == curNode.data){
                        curNode.father.leftSon = null;
                    }
                } else if (curNode.father.rightSon != null){
                    if (curNode.father.rightSon.data == curNode.data){
                        curNode.father.rightSon = null;
                    }
                }

                curNode.father = prevNode.father;
                curNode.depth = prevNode.depth + 1;

                if (ls != curNode) curNode.leftSon = ls;
                else curNode.leftSon = null;
                if (rs != curNode) curNode.rightSon = rs;
                else curNode.rightSon = null;

                if (curNode.rightSon != null && curNode.leftSon != null)
                    curNode.weight = max(curNode.leftSon.weight,curNode.rightSon.weight) + 1;
                else if (curNode.leftSon != null)
                    curNode.weight = curNode.leftSon.weight + 1;
                else if (curNode.rightSon != null)
                    curNode.weight = curNode.rightSon.weight + 1;
                else curNode.weight = 0;
            }

        } else{
            if (curNode.leftSon != null) curNode.leftSon = removeRecursive(curNode.leftSon,data);
            if (curNode.rightSon != null) curNode.rightSon = removeRecursive(curNode.rightSon,data);
        }

        return curNode;
    }

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
    public boolean contains(Object o) throws NullPointerException{
        if (o == null) throw new NullPointerException("Elements is equals null");
        return findData(root, (T) o);
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeIterator<T>(this.root) {};
    }

    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[size];
        createArrayFromTree(resultArray, 0, root);
        return resultArray;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] resultArray = (T1[]) new Object[size];
        createArrayFromTree(resultArray, 0, root);
        return resultArray;
    }

    @Override
    public boolean add(T t) {
        root = addRecursive(root, t);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) throws NullPointerException {
        if (!contains(o)) return false;
        root = removeRecursive(root,(T)o);
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T o: c) add(o);
        return true;
    }

    // there's exception when element of collection isn't in tree
    @Override
    public boolean removeAll(Collection<?> c) {
        for(Object o: c) remove(o);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c) throws NullPointerException{

        Tree<T> newTree = null;
        boolean allHasBeenRetained = true;
        boolean hasRoot = false;

        for(Object o: c){
            if (!contains(o)) allHasBeenRetained = false;
            else{
                if (o == null){
                    throw new NullPointerException("Cannot retain null-element in collection");
                }
                if (!hasRoot){
                    newTree = new Tree<>(1, (T)o);
                    hasRoot = true;
                }
                else newTree.add((T)o);
            }
        }

        if (newTree != null){
            root = newTree.root;
            size = newTree.size();

        }
        return allHasBeenRetained;
    }

    @Override
    public void clear() {
        root = clearTraverse(root);
        size = 0;
    }

    private static class TreeIterator<T> implements Iterator<T>{
        private List<Node<T>> treeNodes;
        private Integer cursor;

        private TreeIterator(Node<T> root) {
            this.treeNodes = new ArrayList<>();
            cursor = 0;
            createArrayList(treeNodes,root);
        }

        private void createArrayList(List<Node<T>> list, Node<T> node){
            if (node == null) return;
            list.add(node);
            createArrayList(list,node.leftSon);
            createArrayList(list,node.rightSon);
        }

        @Override
        public boolean hasNext() {
            return cursor != treeNodes.size();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new ConcurrentModificationException();
            return treeNodes.get(cursor++).data;
        }
    }

    private static class Node<T> {
        protected Integer weight, depth;
        protected Node<T> leftSon, rightSon, father;
        private T data;

        public Node(T data, Node<T> father) {
            this.data = data;
            depth = weight = 0;
            leftSon = rightSon = null;
            this.father = father;
        }

        public Node(T data, Integer depth, Node<T> father) {
            this.data = data;
            this.depth = depth;
            weight = 0;
            leftSon = rightSon = null;
            this.father = father;
        }
    }
}
