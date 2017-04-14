package com.easou.let.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-12-5
 * Time: 下午6:26
 * To change this template use File | Settings | File Templates.
 */
public class TaskItem<T extends Object> {
    private T item;
    public TaskItem(T item){
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
