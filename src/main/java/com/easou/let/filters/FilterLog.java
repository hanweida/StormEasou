package com.easou.let.filters;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-7
 * Time: 下午3:49
 * To change this template use File | Settings | File Templates.
 */
public interface FilterLog {

    /**
     * Init.
     */
    public void init();

    /**
     * Validate boolean.
     *
     * @param line the line
     * @return the boolean
     */
    public boolean validate(String line);
}
