package com.github.walker.taskcenter.service;

import com.github.walker.mybatis.paginator.PageBounds;
import com.github.walker.taskcenter.common.BasicService;
import com.github.walker.taskcenter.common.utils.DateTimeUtil;
import com.github.walker.taskcenter.dao.AppRegDao;

import com.github.walker.taskcenter.vo.AppReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 与应用的注册、删除、更新相关的Service
 * <p/>
 * Created by HuQingmiao on 2015-5-13.
 */
@Service
@Transactional
public class AppRegService extends BasicService {

    @Autowired
    private AppRegDao appRegDao;


    public void addAppConfig(AppReg appReg)  {
        appReg.setState(BasicService.APP_REG_STATE_IDLE);//默认为休闲，即未运行
        appReg.setCreateTime(DateTimeUtil.currentTime());
        appRegDao.save(appReg);

    }

    public void updateAppConfig(AppReg appConfig)  {
        appRegDao.update(appConfig);
    }

    public AppReg findByCodeOrByName(String appCode, String appName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appCode", appCode);
        map.put("appName", appName);
        return appRegDao.findByCodeOrByName(map);
    }

    public void deleteAll() {
        appRegDao.deleteAll();
    }


    public void delAppConfig(Long id) {
        appRegDao.deleteByPK(id);
    }

    public AppReg findAppById(Long id) {
        return (AppReg) appRegDao.findByPK(id);
    }


    public List<AppReg> findAppConfigs(String appCode, String appName, String hostName, int offset, int rowCnt)  {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        if (appCode != null) {
            paramMap.put("appCode", "%" + appCode + "%");
        }
        if (appName != null) {
            paramMap.put("appName", "%" + appName + "%");
        }
        if (hostName != null) {
            paramMap.put("hostName", "%" + hostName + "%");
        }

        //log.info(">>>>>>>pageNum: "+pageNum);
        //log.info(">>>>>>>rowcntPerPage: "+rowcntPerPage);
        PageBounds pageBounds = new PageBounds(offset, rowCnt);
        return appRegDao.find(paramMap, pageBounds);
    }

}