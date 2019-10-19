package com.ydys.qmb.bean;

public class MessageEvent {
    private String message;

    private NameDetailWrapper nameDetailWrapper;

    private int lastClickIndex;

    private int isKeep;

    public NameDetailWrapper getNameDetailWrapper() {
        return nameDetailWrapper;
    }

    public void setNameDetailWrapper(NameDetailWrapper nameDetailWrapper) {
        this.nameDetailWrapper = nameDetailWrapper;
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLastClickIndex() {
        return lastClickIndex;
    }

    public void setLastClickIndex(int lastClickIndex) {
        this.lastClickIndex = lastClickIndex;
    }

    public int getIsKeep() {
        return isKeep;
    }

    public void setIsKeep(int isKeep) {
        this.isKeep = isKeep;
    }
}
