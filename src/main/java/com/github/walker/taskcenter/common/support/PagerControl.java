package com.github.walker.taskcenter.common.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 分页查询控制器
 *
 * @author HuQingmiao
 */
public class PagerControl {

    private static final int DEFAULT_MAX_ROWCNT = 15;//默认每页的记录条数

    private static final int DEFAULT_MAX_LINKCNT = 10;//默认导航栏的页码链接数

    private int maxRowcnt = DEFAULT_MAX_ROWCNT; // 每页的记录条数

    private int maxLinkcnt = DEFAULT_MAX_LINKCNT; //导航栏的页码链接数

    private int offset = 0;


    /**
     * 构造子, 分页控件默认导航栏的链接数是DEFAULT_NAVI_PAGE_LINKS
     *
     * @param rowcntPerPage 每页记录条数
     * @param request       HttpServletRequest对象
     */
    public PagerControl(HttpServletRequest request,
                        int rowcntPerPage) {

        this.maxRowcnt = rowcntPerPage;
        request.setAttribute("NAVI_PAGE_LINKS", String.valueOf(maxLinkcnt));

        //计算和设置查询范围
        this.indexQueryScope(request);
    }

    /**
     * 构造子
     *
     * @param request       HttpServletRequest对象
     * @param rowcntPerPage 每页记录条数
     * @param naviPageLinks 导航栏的页码索引数
     */
    public PagerControl(HttpServletRequest request,
                        int rowcntPerPage, int naviPageLinks) {

        this.maxRowcnt = rowcntPerPage;
        this.maxLinkcnt = naviPageLinks;
        request.setAttribute("NAVI_PAGE_LINKS", String.valueOf(naviPageLinks));

        //计算和设置查询范围
        this.indexQueryScope(request);
    }

    /**
     * 设置分页查询本次取到的记录条数, 以及符合条件的总记录数.
     *
     * @param request
     * @param currItems  本次取到的记录条数
     * @param totalItems 符合条件的总记录条数
     */
    public void setItemsCount(HttpServletRequest request, int currItems,
                              int totalItems) {

        int pageCount = (int) Math.ceil((double) totalItems / this.maxRowcnt);

        //总记录条数
        request.setAttribute("TOTAL_ITEMS", String.valueOf(totalItems));

        //总页数
        request.setAttribute("TOTAL_PAGES", String.valueOf(pageCount));

        //当前页记录条数
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.CURR_ROWCNT, String.valueOf(currItems));
    }

    /**
     * 将当前页码保存到Request中, 以便回到原来那一页。
     * <p/>
     * 如果修改/删除结果列表中的某条数据后，并希望回到原来的那页, 那么这个方法将被用到。
     *
     * @param request HttpServletRequest对象
     * @throws Exception
     */
    public static void saveOffset(HttpServletRequest request)
            throws Exception {
        saveOffset(request, 0);
    }

    /**
     * 将当前页码保存到Request中, 以便回到原来那一页。
     * <p/>
     * 如果修改/删除结果列表中的某条数据后，并希望回到原来的那页, 那么这个方法将被用到。
     *
     * @param request HttpServletRequest对象
     * @param diff    当前页的记录条数的变化值. 如当在界面列表中删除某记录后, 则当前页记录数减少.
     * @throws Exception
     */
    public static void saveOffset(HttpServletRequest request, int diff)
            throws Exception {

        HttpSession session = request.getSession();

        //当前页码
        int currPage = Integer.parseInt((String) session
                .getAttribute(SessionConstant.CURR_PAGENUM));

        //当前页记录条数
        int currItems = Integer.parseInt((String) session
                .getAttribute(SessionConstant.CURR_ROWCNT));

        currItems += diff;

        //如果当前页记录数为0, 则显示上一页
        if (currPage > 1 && currItems == 0) {
            currPage--;
        }

        if (currPage <= 0) {
            currPage = 1;
        }

        //重置页码和当前页记录数
        request.setAttribute("CURR_PAGENUM", String.valueOf(currPage));
        session.setAttribute(SessionConstant.CURR_PAGENUM, String.valueOf(currPage));
        session.setAttribute(SessionConstant.CURR_ROWCNT, String.valueOf(currItems));
    }

    /**
     * 取得分页查询的当前页码
     *
     * @param request
     * @return 当前页码
     */
    public static String getCurrPageNum(HttpServletRequest request) {

        HttpSession session = request.getSession();
        return (String) session.getAttribute(SessionConstant.CURR_PAGENUM);
    }

    //根据请求参数中的页码, 计算和定位查询范围
    private void indexQueryScope(HttpServletRequest request) {

        String currPageNum = request.getParameter("CURR_PAGENUM");

        if (currPageNum == null) {
            currPageNum = (String) request.getAttribute("CURR_PAGENUM");
        }

        if (currPageNum == null || "".equals(currPageNum.trim())) {//进入查询界面, 默认从1开始
            currPageNum = "1";
        }

        request.setAttribute("CURR_PAGENUM", currPageNum);
        this.offset = (Integer.parseInt(currPageNum) - 1) * this.getMaxRowcnt();

        //把当前页码存一个副本到session
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.CURR_PAGENUM, currPageNum);
    }

    public int getOffset() {
        return this.offset;
    }

    public int getMaxRowcnt() {
        return maxRowcnt;
    }
}

