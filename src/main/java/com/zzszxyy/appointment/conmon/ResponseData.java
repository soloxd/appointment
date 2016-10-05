package com.zzszxyy.appointment.conmon;

public class ResponseData {
    
    private String retCode;
    private String retInfo;
    private Object data;
    
    public ResponseData() {
        
    }
    
    public ResponseData(String retCode) {
        this.retCode = retCode;
    }
    
    public ResponseData(String retCode, String retInfo) {
        this.retCode = retCode;
        this.retInfo = retInfo;
    }

    public ResponseData(String retCode, Object data) {
        this.retCode = retCode;
        this.data = data;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
