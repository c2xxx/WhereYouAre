package com.chen.whereyouare.other;

/**
 * Created by ChenHui on 2016/11/14.
 */

public class AnyEvent {
    public interface TYPE {
        int RECEIVE_POSITION = 1;
    }

    private int eventType;
    private Object obj;

    public AnyEvent(int eventType, Object obj) {
        this.eventType = eventType;
        this.obj = obj;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
