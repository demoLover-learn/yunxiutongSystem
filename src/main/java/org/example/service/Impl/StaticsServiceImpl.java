package org.example.service.Impl;

import jakarta.annotation.Resource;

import org.example.mapper.StaticsMapper;
import org.example.service.StaticsService;
import org.example.vo.OrderCountVO;
import org.example.vo.OrderStatusCountVO;
import org.example.vo.WorkerRankVO;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaticsServiceImpl implements StaticsService {
    @Resource
    private StaticsMapper staticsMapper;

    /**
     * 订单趋势图
     * @return
     */
    @Override
    public Map<String,Object> showPage() {
        List<OrderCountVO> VO = staticsMapper.getMonthAndAmount();
        List<String> months = new ArrayList<>();
        List<Integer> counts=new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        VO.forEach(orderCountVO -> {
            months.add(orderCountVO.getMonth());
            counts.add(orderCountVO.getCounts());
            map.put("month",months);
            map.put("counts",counts);
        });
        return map;
    }
    /**
     * 订单状态图
     * @return
     */
    @Override
    public List<OrderStatusCountVO> getStatusPic() {
      return staticsMapper.getStatusAndCount();

    }

    /**
     * 工人排行
     * @return
     */
    @Override
    public List<WorkerRankVO> getWorkerRank() {
      return staticsMapper.getWorkerRank();
    }
}
