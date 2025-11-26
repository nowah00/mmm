package com.ssg.mmm.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {

    public static void setSession(HttpServletRequest req, String key, Object value) {
        req.getSession().setAttribute(key, value);
    }

    public static Object getSession(HttpServletRequest req, String key) {
        return req.getSession().getAttribute(key);
    }

    public static void removeSession(HttpServletRequest req, String key) {
        req.getSession().removeAttribute(key);
    }
}
