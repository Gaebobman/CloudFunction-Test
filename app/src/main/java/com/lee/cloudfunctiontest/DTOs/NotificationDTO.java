package com.lee.cloudfunctiontest.DTOs;

public class NotificationDTO {
    private String time;
    private String content;

    public NotificationDTO(String date, String  notice_content){
        this.time = date;
        this.content = notice_content;
    }

    public String getTime() {
        return time;
    }

    public String getNotice_content() {
        return content;
    }




}
