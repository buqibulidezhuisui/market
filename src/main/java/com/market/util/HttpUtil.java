package com.market.util;

import javax.servlet.http.HttpServletRequest;


/**
 * The type Http util.
 *
 * @author Ruizhi
 * @date 2019.01.10
 */
public class HttpUtil {

    /**
     * Is ajax request boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }
}
