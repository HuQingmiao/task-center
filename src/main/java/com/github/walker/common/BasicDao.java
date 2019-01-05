package com.github.walker.common;


import com.github.walker.mybatis.paginator.PageBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * mybatis DAO基类
 * <p/>
 * Created by HuQingmiao on 2015-4-29.
 */
public interface BasicDao {

    int save(BasicVo basicVo);

    int saveBatch(List list);


    int update(BasicVo basicVo);

    int updateIgnoreNull(BasicVo basicVo);

    int updateBatch(List list);


    int delete(BasicVo basicVo);

    int deleteBatch(List list);

    int deleteByPK(Long id);

    int deleteAll();


    long count();

    BasicVo findByPK(Long id);

    ArrayList find(Map<String, Object> paramMap, PageBounds pageBounds);
}