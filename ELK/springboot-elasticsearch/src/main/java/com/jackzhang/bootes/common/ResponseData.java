package com.jackzhang.bootes.common;

/**
 * Created by Jack
 */
public class ResponseData<T> {
    private int isOk;//1：成功、0：失败
    private String msg;//成功或失败信息
    private T data;//具体数据信息

    public boolean isSuccess(){
        return 1==this.isOk;
    }
    public int getIsOk() {
        return isOk;
    }
    public void setIsOk(int isOk) {
        this.isOk = isOk;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public ResponseData<T> isOk(int isOk){
        this.isOk=isOk;
        return this;
    }
    public ResponseData<T> msg(String msg){
        this.msg=msg;
        return this;
    }
    public ResponseData<T> data(T data){
        this.data=data;
        return this;
    }
}