package ru.nsu.fit.tretyakov;

import java.util.Arrays;

/**
 * </b>Stack<Type></b> — класс-реализация стека, который работает с любымми объектами.
 * @param <Type> — Тип объекта.
 */
public class Stack<Type>{
    /** <b>cap</b> — ёмкость стэка */
    private int cap;
    /** <b>len</b> — текущий размер стэка */
    private int len = 0;
    /** <b>stack</b> — массив объектов Type стэка */
    private Type[] stack;

    /**
     * <b>Stack</b> — конструктор класса <b>Stack.</b>
     * @param cap — входной параметр длины стэка. Создает массив <i>stack</i> размера <i>cap</i>.
     * @see Stack#cap
     */
    public Stack(int cap) {
        this.cap = cap;
        len = 0;
        stack = (Type[]) (new Object[cap]);
    }

    /**
     * <b>resize</b> — функция увеличивает размер массива <i>stack</i>,
     * если текущий размер <i>len </i>совпадает с ёмкостью <i>cap</i>.
     */
    private void resize() {
        if (len == cap) {
            cap *= 2;
            stack = Arrays.copyOf(stack, cap);
        }
    }

    /**
     * <b>push</b> — функция добавления объекта <i>elem</i> в массив <i>stack.</i>
     * @param elem — элемент, добавляемый в массив.
     */
    public void push(Type elem) {
        resize();
        stack[len++] = elem;
    }

    /**
     * <b>pop</b> — функция удаления последнего объекта из массива <i>stack</i>
     * @return — возвращает последний элемент массива, если текущий размер массива <i>len</i> > 0. В противном случае возвращает <b>null.</b>
     */
    public Type pop(){
        if (len > 0) return stack[--len];
        return null;
    }

    /**
     * <b>pushStack</b> — функция вставки массива объектов <i>arr</i> в стэк <i>stack.</i>
     * @param arr — входной массив объектов.
     */
    public void pushStack(Type[] arr) {
        for (Type type : arr) {
            push(type);
        }
    }

    /**
     * <b>popStack</b> — функция удаления нескольких элементов стэка <i>stack</i>.
     * @param count — количество элементов, которое нужно удалить.
     * @return — функция возвращает массив удаленных элементов стэка.
     */
    public Type[] popStack(int count) {

        Type[] buf = (Type[]) (new Object[count]);

        for (int i = 0; i < count; i++) {
            buf[count - i - 1] = pop();
        }

        return buf;
    }

    /**
     * <b>count</b> — функция позволяет узнать текущий размер стэка <i>stack</i>.
     * @return — возвращает текущий размер стэка.
     * @see Stack#len
     */
    public int count() {
        return len;
    }
}
