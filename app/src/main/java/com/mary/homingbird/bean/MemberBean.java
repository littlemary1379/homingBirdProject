package com.mary.homingbird.bean;


import lombok.Data;

@Data
public class MemberBean {

    //싱글톤 패턴 제작
    private static MemberBean instance = new MemberBean();

    private MemberBean() {}

    public static MemberBean getInstance(){
        return  instance;
    }

    public String email;
    public String code;
    public String username;

}
