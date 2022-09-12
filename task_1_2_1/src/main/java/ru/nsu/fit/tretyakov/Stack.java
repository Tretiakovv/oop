package ru.nsu.fit.tretyakov;

import java.security.InvalidParameterException;
import java.util.Arrays;

/**
 * <b>Stack</b> — class, which works with any type of object.
 *
 * @param <T> — Type of object.
 */
public class Stack<T> {
    /**
     * <b>Cap</b> — capacity of stack.
     */
    private int cap;
    /**
     * <b>Len</b> — current length of stack.
     */
    private int len = 0;
    /**
     * <b>Stack</b> — array of objects Type of stack.
     */
    private T[] stack;

    /**
     * Constructor of the stack.
     *
     * @param arr is input array which is used to create stack.
     */

    @SuppressWarnings("unchecked")
    public Stack(T[] arr) {
        cap = arr.length * 2;
        len = 0;
        stack = (T[]) (new Object[cap]);

        for (T element : arr) {
            this.push(element);
        }
    }

    /**
     * <b>Stack</b> — constructor of class <b>Stack.</b>
     *
     * @param cap — input parameter of capacity of stack. Creates the array
     *            <i>stack</i> with <i>cap</i> size.
     * @throws ArrayIndexOutOfBoundsException when stack capacity is null.
     * @see Stack#cap
     */
    @SuppressWarnings("unchecked")
    public Stack(int cap) throws ArrayIndexOutOfBoundsException {
        if (cap <= 0) {
            throw new ArrayIndexOutOfBoundsException("Stack is empty.");
        }
        this.cap = cap;
        len = 0;
        stack = (T[]) (new Object[cap]);
    }

    /**
     * Getter of the stack.
     *
     * @return stack if it's needed.
     */
    public T[] getStack() {
        return stack;
    }

    /**
     * <b>TryResize</b> increases the of the <i>stack</i>,
     * if the current length <i>len</i> of stack equals to its capacity <i>cap</i>.
     */
    private void tryResize() {
        if (len == cap) {
            cap *= 2;
            stack = Arrays.copyOf(stack, cap);
        }
    }

    /**
     * <b>Push</b> object <i>elem</i> into array <i>stack.</i>
     *
     * @param elem will be added to stack.
     */
    public void push(T elem) {
        tryResize();
        stack[len++] = elem;
    }

    /**
     * <b>Pop</b> — this function deletes the last element of the array <i>stack</i>.
     *
     * @return the last element of the array,
     * if current length <i>len</i> of the array which is bigger than 0.
     * Otherwise returns <b>null.</b>
     * @throws ArrayIndexOutOfBoundsException if size of stack is null.
     */
    public T pop() throws ArrayIndexOutOfBoundsException {

        if (len == 0) {
            throw new ArrayIndexOutOfBoundsException("Stack is empty.");
        }
        return stack[--len];
    }

    /**
     * <b>PushStack</b> — this function pushes the array <i>arr</i> of objects into the stack.
     *
     * @param ingoingStack — input stack.
     */
    public void pushStack(Stack<T> ingoingStack) {
        if (ingoingStack.count() + len >= cap) {
            tryResize();
        }
        System.arraycopy(ingoingStack.stack, 0, stack, len, ingoingStack.count());
        len += ingoingStack.count();
    }

    /**
     * <b>PopStack</b> — this function deletes some elements from the array <i>stack</i>.
     *
     * @param count — number of elements, which should be deleted.
     * @return the stack of deleted elements from stack.
     * @throws ArrayIndexOutOfBoundsException if number of deleted elements is bigger than
     *                                        size of the stack.
     */
    public Stack<T> popStack(int count) throws ArrayIndexOutOfBoundsException {

        if (len - count < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        Stack<T> res = new Stack<>(count);
        T[] bufArray = Arrays.copyOfRange(stack, len - count, len);
        Arrays.fill(stack, len - count, len, null);

        len -= count;
        res.stack = bufArray;
        res.len = count;

        return res;
    }

    /**
     * <b>Count</b> — this function shows current length of the <i>stack</i>.
     *
     * @return — returns current length of the stack.
     * @see Stack#len
     */
    public int count() {
        return len;
    }
}
