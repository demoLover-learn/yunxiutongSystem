package org.example.util;


import org.example.context.BaseContext;
import org.example.entity.ServiceOrder;
import org.example.mapper.AdminOrderManageMapper;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTools {

    @Autowired
    private  AdminOrderManageMapper orderMapper;


}
