package com.easou.let.filters;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-7
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
public class AspLogFilter extends DefaultLogFilter {
    private static final String ASP_VALID_LOG_TAG = "show_ver=";

    /**
     * 验证asp日志是否合格
     * 合格返回true
     * 不合格返回false
     * @param line
     * @return
     */
    @Override
    public boolean validate(String line) {
        if(!StringUtils.isNotEmpty(line)){
            return false;
        }

        if(line.contains(ASP_VALID_LOG_TAG)){
            return true;
        }
        return false;
    }
}
