package com.haiyang.common;
import lombok.Data;

@Data
public class Result {
    private Integer code;  //请求操作是否成功状态码 20000成功
    private String message;   //请求操作提示信息。
    private Object resultdata;  //如果请求进行是查询操作，存储就查询返回数据对象。List集合,实体类。

    //成功：  重载
    public static Result success(int code,String message,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setResultdata(data);
        return r;
    }

    public static Result success(Object data){
        return success(20000,"操作成功",data);
    }

    //失败:
    public static Result fail(int code,String message,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setResultdata(data);
        return r;
    }
    public static Result fail(String message){
        return fail(400,message,null);
    }
}
