package com.spring.boot.security.springbootsecurity.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
* @Description:    自定义403权限受限返回数据
* @Author:         tan_yi
* @CreateDate:     2021/11/30 11:07
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/30 11:07
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        // 设置网络状态
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 设置媒体类型
        httpServletResponse.setContentType("application/json;charset=utf-8");

        // 获取返回writer对象
        PrintWriter writer = httpServletResponse.getWriter();

        // 返回json数据
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("status","error");
        objectNode.put("massage","权限不足,请联系管理员");
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
        ObjectNode jsonNodes = objectMapper.readValue(jsonString, ObjectNode.class);

        writer.print(jsonNodes);
        writer.flush();
        writer.close();


    }
}
