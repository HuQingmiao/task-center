package com.github.walker.taskcen.service;

import com.github.walker.mybatis.paginator.PageBounds;
import com.github.walker.common.BasicService;

import com.github.walker.taskcen.dao.EventControlDao;
import com.github.walker.taskcen.vo.EventControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 事件设置Service
 * <p/>
 * Created by HuQingmiao on 2015-5-13.
 */
@Service
@Transactional
public class EventCtrService extends BasicService {

    @Autowired
    private EventControlDao eventControlDao;


    public void addEventCtr(Long appId)  {
        EventControl eventCtr = new EventControl();
        eventCtr.setAppId(appId);
        eventCtr.setEnable(BasicService.ENABLE_TRUE);//默认为启用

        eventControlDao.save(eventCtr);
    }

    public void updateEventCtr(EventControl eventCtr)  {
        eventControlDao.update(eventCtr);
    }

    public EventControl findEventCtrByAppId(Long appId) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("appId",appId);

        List<EventControl> list = eventControlDao.find(paramMap,new PageBounds());
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

}