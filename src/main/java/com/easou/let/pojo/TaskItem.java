package com.easou.let.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-12-5
 * Time: 下午6:26
 * To change this template use File | Settings | File Templates.
 *
 * @param <T> the type parameter
 */
public class TaskItem<T extends Object> {
    private T item;

    /**
     * Instantiates a new Task item.
     *
     * @param item the item
     */
    public TaskItem(T item){
        this.item = item;
    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public T getItem() {
        return item;
    }

    /**
     * Sets item.
     *
     * @param item the item
     */
    public void setItem(T item) {
        this.item = item;
    }
}
