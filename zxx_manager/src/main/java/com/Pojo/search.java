package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//用户搜索记录
@Data
@AllArgsConstructor
@ToString
public class search {
    private String search_id;// 搜索id
    private String user_id;// 用户id
    private String search_content;// 搜索内容

    public search() {
    }
}
