package com.mucfc.taskcenter.common;


import com.github.walker.mybatis.paginator.PageBounds;

import java.util.List;
import java.util.Map;

/**
 * mybatis DAO基类
 * <p/>
 * Created by HuQingmiao on 2015-4-29.
 */
public interface BasicDao {

    public void save(BasicVo basicPo);

    public void saveBatch(List list);


    public int update(BasicVo basicPo);

    public int updateIgnoreNull(BasicVo basicPo);

    public void updateBatch(List list);


    public int delete(BasicVo basicPo);

    public void deleteBatch(List list);

    public int deleteByPK(Long id);

    public int deleteAll();


    public long count();

    public BasicVo findByPK(Long id);

    //public BasicVo findByUK(Map<String, Object> paramMap);

    public List find(Map<String, Object> paramMap, PageBounds pageBounds);
}
