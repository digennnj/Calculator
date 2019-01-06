package edu.miamioh.digennnj.calculator;

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<T> implements StackInterface<T>{

    private T[] stack;
    private int size = 0;

    //Check on discussion boards how to properly handle this
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        stack = (T[]) new Object[10];
    }

    @Override
    public void push(T newEntry) {
        if(stack.length == size) {
            stack = Arrays.copyOf(stack, size*2);
        }
        stack[size] = newEntry;
        size++;
    }

    @Override
    public T pop() {
        if(isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack[--size];
        }
    }

    @Override
    public T peek() {
        if(isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack[size - 1];
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for(T obj : stack) {
            obj = null;
            size = 0;
        }
    }

}

