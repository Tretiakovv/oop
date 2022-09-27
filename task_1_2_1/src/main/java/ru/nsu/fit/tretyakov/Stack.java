package ru.nsu.fit.tretyakov;

import java.util.Arrays;

/**
 * <b>Stack</b> — class, which works with any type of object.
 * @param <Type> — Type of object.
 */
public class Stack<Type>{
    /** <b>cap</b> — capacity of stack */
    private int cap;
    /** <b>len</b> — current length of stack */
    private int len = 0;
    /** <b>stack</b> — array of objects Type of stack */
    private Type[] stack;

    /**
     * <b>Stack</b> — constructor of class <b>Stack.</b>
     * @param cap — input parameter of capacity of stack. Creates the array <i>stack</i> with <i>cap</i> size.
     * @see Stack#cap
     */
    public Stack(int cap) {
        this.cap = cap;
        len = 0;
        stack = (Type[]) (new Object[cap]);
    }

    /**
     * <b>resize</b> — this function increases the of the <i>stack</i>,
     * if the current length <i>len</i> of stack equals to its capacity <i>cap</i>.
     */
    private void resize() {
        if (len == cap) {
            cap *= 2;
            stack = Arrays.copyOf(stack, cap);
        }
    }

    /**
     * <b>push</b> — this function pushes object <i>elem</i> into array <i>stack.</i>
     * @param elem — element, which will be added to stack.
     */
    public void push(Type elem) {
        resize();
        stack[len++] = elem;
    }

    /**
     * <b>pop</b> — this function deletes the last element of the array <i>stack</i>
     * @return — return the last element of the array, if current length <i>len</i> of the array > 0. Otherwise returns <b>null.</b>
     */
    public Type pop(){
        if (len > 0) return stack[--len];
        return null;
    }

    /**
     * <b>pushStack</b> — this function pushes the array <i>arr</i> of objects into the stack.
     * @param arr — input array of objects.
     */
    public void pushStack(Type[] arr) {
        for (Type type : arr) {
            push(type);
        }
    }

    /**
     * <b>popStack</b> — this function deletes some elements from the array <i>stack</i>.
     * @param count — number of elements, which should be deleted.
     * @return — this functions returns the array of deleted elements from stack.
     */
    public Type[] popStack(int count) {

        Type[] buf = (Type[]) (new Object[count]);

        for (int i = 0; i < count; i++) {
            buf[count - i - 1] = pop();
        }

        return buf;
    }

    /**
     * <b>count</b> — this function shows current length of the <i>stack</i>.
     * @return — returns current length of the stack.
     * @see Stack#len
     */
    public int count() {
        return len;
    }
}
