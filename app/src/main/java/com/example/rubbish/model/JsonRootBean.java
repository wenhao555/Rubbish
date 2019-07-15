/**
  * Copyright 2019 bejson.com 
  */
package com.example.rubbish.model;
import java.util.List;

/**
 * Auto-generated: 2019-07-12 13:50:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private int code;
    private String msg;
    private List<Newslist> newslist;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setNewslist(List<Newslist> newslist) {
         this.newslist = newslist;
     }
     public List<Newslist> getNewslist() {
         return newslist;
     }

}