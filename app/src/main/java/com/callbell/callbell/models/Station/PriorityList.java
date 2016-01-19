package com.callbell.callbell.models.Station;

import com.callbell.callbell.models.response.MessageResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by austin on 1/18/16.
 */
public class PriorityList<E> implements Iterable<E>{

    public enum PriorityState {
        HIGH,
        LOW
    }

    List<E> priorityItems, otherItems;

    public PriorityList() {
        priorityItems = new ArrayList<>();
        otherItems = new ArrayList<>();
    }

    /**
     * Add an item
     * @param item item to add
     */
    public void add(E item) {
        otherItems.add(item);
    }

    /**
     * Add an item with a specific priority
     * @param item item to add
     * @param ps priority value
     */
    public void add(E item, PriorityState ps) {

        switch (ps) {
            case HIGH:
                priorityItems.add(item);
                break;
            case LOW:
                otherItems.add(0, item);
        }
    }

    /**
     * get Value
     * @param index Index of Value
     * @return
     */
    public E get(int index) {

        if (isPriorityIndex(index)) {
            return priorityItems.get(index);
        } else {
            return otherItems.get(index - priorityItems.size());
        }
    }

    public List<E> getPriorityItems() {
        return priorityItems;
    }

    public List<E> getOtherItems() {
        return otherItems;
    }

    /**
     * get Size of PriorityLsit
     * @return
     */
    public int size() {
        return priorityItems.size() + otherItems.size();
    }

    /**
     * Remove Item
     * @param index index to be removed
     * @return
     */
    public E remove(int index) {
        if (isPriorityIndex(index)) {
            return priorityItems.remove(index);
        } else {
            return otherItems.remove(index - priorityItems.size());
        }
    }

    public void set(int index, E item) {

        if (isPriorityIndex(index)) {
            priorityItems.set(index, item);
        } else {
            otherItems.set(index - priorityItems.size(), item);
        }
    }

    /**
     * Set Priority List Item PriorityState
     * @param index
     */
    public void setPriorityState(int index, PriorityState ps ) {

        // check null action
        boolean isPriorityIndex = isPriorityIndex(index);
        if (isPriorityIndex && ps.equals(PriorityState.HIGH)
                || !isPriorityIndex && ps.equals(PriorityState.LOW)) {
            return;
        }
        PriorityState state = isPriorityIndex ? PriorityState.LOW : PriorityState.HIGH;
        add(remove(index), state);
    }

    /**
     * Checks to see if we are a priority Index
     * @param index index
     * @return true if it is an index that is high priority.
     */
    private boolean isPriorityIndex(int index) {
        return index < priorityItems.size();
    }


    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size() - 1 && get(currentIndex) != null;
            }

            @Override
            public E next() {
                return get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        return it;
    }
}
