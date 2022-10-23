package ru.nsu.fit.tretyakov;

import java.util.*;

import static java.lang.Math.max;

/**
 * Binary Tree is the class of interface Collection.
 * In it user can create tree-structure using node-classes
 * and iterate over the tree using BFS.
 *
 * @param <T> is the type of the stored objects.
 * @see Tree.Node
 */
public class Tree<T> implements Collection<T> {
    private static Integer modCount;
    private Node<T> root;
    private Integer size;

    /**
     * Parametrized constructor of the Tree. Used to create root Node.
     *
     * @param data — value of the root Node.
     */
    public Tree(T data) {
        this.size = 1;
        modCount = 0;
        this.root = new Node<>(data, null);
    }

    /**
     * Default constructor of the Tree. Used to create empty Tree.
     */
    public Tree() {
        this.size = 0;
        modCount = 0;
        this.root = null;
    }

    /**
     * Recursive function that added new node to the special node.
     *
     * @param node is the node, where we'd like to add another node.
     * @param data is the data of the new node, which will be added to the special node.
     * @return the new node, that was added to the Tree.
     */
    public Node<T> addNode(Node<T> node, T data) {

        if (node.leftSon == null || node.rightSon == null) {

            size++;
            var sonDepth = node.depth + 1;

            if (node.leftSon == null) node.leftSon = new Node<T>(data, sonDepth, node);
            else node.rightSon = new Node<T>(data, sonDepth, node);

            node.weight++;
            return node;

        } else if (node.leftSon.weight <= node.rightSon.weight)
            addNode(node.leftSon, data);
        else addNode(node.rightSon, data);

        node.weight = max(node.leftSon.weight, node.rightSon.weight) + 1;
        return node;
    }

    /**
     * Function of getting size of the Tree.
     *
     * @return size of the Tree (number of nodes).
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Function checks is the Tree empty or not.
     *
     * @return boolean value: Tree is empty.
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Function that checks presence of current element in the Tree.
     *
     * @param o element whose presence in this collection is to be tested
     * @return boolean value: is the Tree contains this element or not.
     * @throws NullPointerException if checking element is null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) throws NullPointerException {
        if (o == null) throw new NullPointerException("Elements is null");

        for (T t : this) {
            if (t.equals((T) o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function that creates BFS iterator from the Tree.
     *
     * @return BFS iterator-class of the Tree.
     */
    @Override
    public TreeIteratorBFS<T> iterator() {
        return new TreeIteratorBFS<T>(this.root);
    }

    /**
     * Function that creates DFS iterator from the Tree.
     *
     * @return DFS iterator-class of the Tree.
     */
    public TreeIteratorDFS<T> iteratorDFS() {
        return new TreeIteratorDFS<T>(this.root);
    }

    /**
     * Function that creates array from the Tree.
     *
     * @return Object-type array of values of the tree-nodes.
     */
    @Override
    public Object[] toArray() {

        Object[] resultArray = new Object[size];

        var counter = 0;
        for (var elem : this) {
            resultArray[counter++] = elem;
        }

        return resultArray;
    }

    /**
     * Function that concatenates required array and the tree-created array.
     *
     * @param a    the array into which the elements of this collection are to be
     *             stored, if it is big enough; otherwise, a new array of the same
     *             runtime type is allocated for this purpose.
     * @param <T1> is the generic-type of the required object.
     * @return modified array, that contains elements of the old array and the tree-created array.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] a) {

        var oldLength = a.length;
        a = Arrays.copyOf(a, oldLength + this.size);

        var counter = 0;
        for (var elem : this) {
            a[oldLength + counter++] = (T1) elem;
        }

        return a;
    }

    /**
     * Function of adding new node to the Tree.
     *
     * @param t element whose presence in this collection is to be ensured.
     * @return always true.
     */
    @Override
    public boolean add(T t) {
        modCount++;
        root = addNode(root, t);
        return true;
    }

    /**
     * Function of removing element from the Tree.
     * Deleting a specific node entails deleting the entire subtree associated with it.
     *
     * @param o element to be removed from this collection, if present
     * @return true, if required object contains in the Tree.
     * Otherwise, return false.
     * @throws NullPointerException if required object is null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) throws NullPointerException {

        if (o == null) throw new NullPointerException("Object is null");
        if (!contains(o)) return false;

        if (root.data == (T) o) {
            root = null;
            size = 0;
            return true;
        }

        modCount++;
        TreeIteratorBFS<T> itr = this.iterator();

        while (itr.hasNext()) {
            if (itr.peek().equals((T) o)) {
                itr.remove();
            } else itr.next();
        }
        size--;
        return true;
    }

    /**
     * Function that checks is there are all elements are contained in the Tree.
     *
     * @param c collection to be checked for containment in this collection
     * @return true, if all elements of a required collection are contained in the Tree.
     * Otherwise, return false.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    /**
     * Function of adding all elements of the collection in the Tree.
     *
     * @param c collection containing elements to be added to this collection
     * @return true always.
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T o : c) {
            add(o);
        }
        return true;
    }

    /**
     * Function of removing all elements of a collection.
     *
     * @param c collection containing elements to be removed from this collection
     * @return true, if function deleted all elements of a collection.
     * Otherwise, return false.
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    /**
     * Function that removes all elements that are not contained in required collection.
     *
     * @param c collection containing elements to be retained in this collection
     * @return true, if all elements has been retained.
     * Otherwise, return false.
     * @throws NullPointerException if collection c contains null-element.
     */
    @Override
    public boolean retainAll(Collection<?> c) throws NullPointerException {

        if (c.contains(null)) {
            throw new NullPointerException("Collection contains null pointer");
        }

        boolean allHasBeenRetained = true;
        TreeIteratorBFS<T> itr = this.iterator();

        while (itr.hasNext()) {
            var data = itr.peek();
            if (!c.contains(data)) {
                itr.remove();
                this.size--;
            } else {
                allHasBeenRetained = false;
                itr.next();
            }
        }

        return allHasBeenRetained;
    }

    /**
     * Function that clears all nodes in the Tree.
     */
    @Override
    public void clear() {

        TreeIteratorBFS<T> itr = this.iterator();

        while (itr.hasNext()) {
            if (itr.peek() != root.data) {
                itr.remove();
            } else itr.next();
        }

        remove(root.data);
        size = 0;
    }

    /**
     * This class is the iterator of the Tree.
     * Iterators can iterate over the collections using BFS and check existing of next elements.
     * They also can remove elements from the collection.
     *
     * @param <T> is the type of the stored objects.
     */
    public static class TreeIteratorBFS<T> implements Iterator<T> {
        private Queue<Node<T>> queueOfNodes;
        private Integer currentModification;

        private TreeIteratorBFS(Node<T> root) throws NullPointerException {
            if (root == null) throw new NullPointerException("Root node is null");
            this.currentModification = modCount;
            this.queueOfNodes = new ArrayDeque<>();
            queueOfNodes = createQueue(root);
        }

        private Queue<Node<T>> createQueue(Node<T> node) {
            Queue<Node<T>> curQueue = new ArrayDeque<>();
            curQueue.add(node);
            return curQueue;
        }

        /**
         * Function that checks is the next element is in the queue.
         *
         * @return true, if the queue of nodes is empty.
         * Otherwise, return false.
         */
        @Override
        public boolean hasNext() {
            return !queueOfNodes.isEmpty();
        }

        /**
         * Function that returns next element of the queue.
         *
         * @return next element of the queue.
         * @throws NullPointerException            if there's no next element in the queue.
         * @throws ConcurrentModificationException if collection has been modified outside the iterator.
         */
        @Override
        public T next() throws NullPointerException, ConcurrentModificationException {
            if (!hasNext()) throw new NullPointerException("Queue is empty");
            if (currentModification < modCount) {
                throw new ConcurrentModificationException("Tree has been modified outside the iterator");
            }
            return nextNode().data;
        }

        private T peek() throws NullPointerException {
            if (!hasNext()) throw new NullPointerException("Queue is empty");
            return queueOfNodes.element().data;
        }

        /**
         * Function that removes current element-node in the queue.
         * Attention! Deleting a specific node entails deleting the entire subtree associated with it.
         *
         * @throws NullPointerException if there's no next element in the queue.
         */
        @Override
        public void remove() throws NullPointerException {

            if (!hasNext()) {
                throw new NullPointerException("Queue is empty");
            }

            var curNode = nextNode();

            if (curNode.father != null) {
                if (curNode.father.leftSon == curNode) {
                    curNode.father.leftSon = null;
                } else curNode.father.rightSon = null;
            }

            curNode = null;
        }

        private Node<T> nextNode() throws NullPointerException {

            if (hasNext()) {
                Node<T> ls = queueOfNodes.element().leftSon;
                Node<T> rs = queueOfNodes.element().rightSon;

                if (ls != null) queueOfNodes.add(ls);
                if (rs != null) queueOfNodes.add(rs);

                return queueOfNodes.poll();
            } else {
                throw new NullPointerException("Queue is empty");
            }
        }
    }

    /**
     * DFS-Iterator class which implements Iterator interface. Created on deque-structure.
     * DFS-Iterator allows user iterate over the Tree, delete nodes, check next node.
     *
     * @param <T> is the required type of the element in the Collection.
     */
    public static class TreeIteratorDFS<T> implements Iterator<T> {
        private Deque<Node<T>> dequeOfNodes;
        private Integer currentModification;

        private TreeIteratorDFS(Node<T> root) {
            dequeOfNodes = new ArrayDeque<>();
            dequeOfNodes.add(root);
            currentModification = modCount;
        }

        /**
         * Function that checks is the next element is in the queue.
         *
         * @return true, if the queue of nodes is empty.
         * Otherwise, return false.
         */
        @Override
        public boolean hasNext() {
            return !dequeOfNodes.isEmpty();
        }

        /**
         * Function that returns next element of the queue.
         *
         * @return next element of the queue.
         * @throws NullPointerException            if there's no next element in the queue.
         * @throws ConcurrentModificationException if collection has been modified outside the iterator.
         */
        @Override
        public T next() throws NullPointerException, ConcurrentModificationException {
            if (currentModification < modCount) {
                throw new ConcurrentModificationException("Tree has been modified outside the iterator");
            }
            return nextNode().data;
        }

        /**
         * Function that removes current element-node in the queue.
         * Attention! Deleting a specific node entails deleting the entire subtree associated with it.
         *
         * @throws NullPointerException if there's no next element in the queue.
         */
        @Override
        public void remove() throws NullPointerException {
            if (!hasNext()) {
                throw new NullPointerException("Deque is empty");
            } else {
                var curNode = nextNode();
                if (curNode.father != null) {
                    if (curNode.father.leftSon == curNode) {
                        curNode.father.leftSon = null;
                    } else curNode.father.rightSon = null;
                }
                curNode = null;
            }
        }

        private Node<T> nextNode() throws NullPointerException {
            if (hasNext()) {
                Node<T> elem = dequeOfNodes.pollFirst();
                Node<T> ls = elem.leftSon;
                Node<T> rs = elem.rightSon;

                if (ls != null) dequeOfNodes.addFirst(ls);
                if (rs != null) dequeOfNodes.addLast(rs);

                return elem;

            } else {
                throw new NullPointerException("Stack is empty");
            }
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
