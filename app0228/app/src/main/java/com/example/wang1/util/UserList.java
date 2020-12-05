package com.example.wang1.util;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    public int page;
    public int per_page;
    public int total;
    public int total_pages;

    public List<UserInfo> data;
    public Support support ;

    public class UserInfo {
        public String id;
        public String email;
        public String first_name;
        public String last_name;
        public String avatar ;

    }

    public  class Support{
        public String url;
        public String text;
    }


}
