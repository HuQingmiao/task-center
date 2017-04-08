package com.github.walker.taskcenter.service;

import com.github.walker.mybatis.paginator.PageBounds;
import com.github.walker.common.BasicService;
import com.github.walker.taskcenter.dao.ScheduleControlDao;
import com.github.walker.taskcenter.vo.ScheduleControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 时间设置Service
 * <p/>
 * Created by HuQingmiao on 2015-5-13.
 */
@Service
@Transactional
public class ScheduleCtrService extends BasicService {

    @Autowired
    private ScheduleControlDao scheduleControlDao;


    public void addScheduleCtr(Long appId, String cronExpression) {
        ScheduleControl scheduleCtr = new ScheduleControl();
        scheduleCtr.setAppId(appId);
        scheduleCtr.setCronExpression(cronExpression);
        scheduleCtr.setEnable(BasicService.ENABLE_TRUE);//默认为启用

        scheduleControlDao.save(scheduleCtr);
    }

    public void updateScheduleCtr(ScheduleControl scheduleCtr) {
        scheduleControlDao.update(scheduleCtr);
    }

    public ScheduleControl findSchduleCtrByAppId(Long appId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("appId", appId);

        List<ScheduleControl> list = scheduleControlDao.find(paramMap, new PageBounds());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}