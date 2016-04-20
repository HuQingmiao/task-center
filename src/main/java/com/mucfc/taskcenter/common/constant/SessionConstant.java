package com.mucfc.taskcenter.common.constant;

/**
 * 集中管理Session范围的属性，所有公开的Session属性名必须在此注册以便查看和引用。
 * 
 * 说明：虽然常量接口不被推荐使用，但在这种情况下有其可取之处。
 * 
 * @author HuQingmiao
 *  
 */
public interface SessionConstant {

    //存放操作员信息的 Session Key
    //public static String OPERATOR = "OPERATOR";

    //分页查询专用, 当前页码
    public static String CURR_PAGENUM = "CURR_PAGENUM";

    //分页查询专用, 当前页记录条数
    public static String CURR_ROWCNT = "CURR_ROWCNT";


}

