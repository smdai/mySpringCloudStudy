package com.bztc.dto;

import java.io.Serializable;

/**
 * @author daism
 * @create 2023-07-11 09:27
 * @description groovy脚本结果
 */
public final class GroovyResult implements Serializable {
    private static final long serialVersionUID = -9030251107663977565L;
    /**
     * 脚本运行是否正常 true异常，false正常
     */
    private boolean flag;
    /**
     * 结果
     */
    private Object obj;
    /**
     * 异常
     */
    private Throwable exception;

    private GroovyResult(boolean flag, Object obj) {
        this.flag = flag;
        this.obj = obj;
    }

    private GroovyResult(Throwable e, Object obj) {
        this.flag = true;
        this.exception = e;
        this.obj = obj;
    }

    public static GroovyResult newGroovyResult(Throwable e, Object obj) {
        return new GroovyResult(e, obj);
    }

    public static GroovyResult newGroovyResult(boolean flag, Object obj) {
        return new GroovyResult(flag, obj);
    }

    public boolean isException() {
        return false;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
