package com.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJSONResult {
    private Integer code; // 状态码 200 成功 非200异常
    private String message;//后端返回用于封装提示信息
    private Object data;//封装具体的数据返回给前端

    public ResponseJSONResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseJSONResult(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }
}

