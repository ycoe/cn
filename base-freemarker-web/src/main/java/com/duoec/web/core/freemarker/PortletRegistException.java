package com.duoec.web.core.freemarker;

/**
 *
 * Created by ycoe on 16/4/28.
 */
public class PortletRegistException extends RuntimeException {
    public PortletRegistException(Exception e) {
        super(e);
    }

    public PortletRegistException(String msg) {
        super(msg);
    }
}
