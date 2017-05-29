package com.github.walker.taskcenter.control;

import com.github.walker.mybatis.paginator.PageList;
import com.github.walker.taskcenter.common.BasicController;
import com.github.walker.taskcenter.common.support.PagerControl;
import com.github.walker.taskcenter.vo.AppReg;
import com.github.walker.taskcenter.service.AppRegService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户登录 Controller
 * <p/>
 * Created by HuQingmiao
 */
@Controller
@RequestMapping("portal")
public class PortalController extends BasicController {

    @Autowired
    private AppRegService service;


    @RequestMapping(value = "prepare", method = RequestMethod.GET)
    public String prepare(@ModelAttribute("form") LoginForm form, Map<String, Object> map) {
        try {
            log.info(">>> portal prepare()");

            //LoginForm loginForm = new LoginForm();
            //map.put("form", form);

            return "login";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "login";
        }
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute("form") LoginForm form, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(">>> login()");

            String username = form.getUsername();
            String password = form.getPassword();
            //map.put("form", form);
            log.info("username: " + username);
            log.info("password: " + password);

            log.info(">>>cookies: ");
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                System.out.println(cookies[i].getName() + ": " + cookies[i].getValue());
            }
            //Cookie c = new Cookie("kkkk","pig");
            //c.setMaxAge(120000000);
            //response.addCookie(c);

            boolean pass = true;
            if (pass) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
            } else {
                map.put("error", "您输入的用户名/密码不正确!");
                return "login";
            }

            AppRegForm appRegForm = new AppRegForm();
            map.put("form", appRegForm);

            //分页查询, 1. 构造分页控制器
            PagerControl pager = new PagerControl(request, 10, 5);
            log.info(">>>>>>>>>>>>>>>>>> " + pager.getOffset());

            //分页查询, 2.调用分页查询方法
            List<AppReg> appRegList = service.findAppConfigs(null, null, null,
                    pager.getOffset(), pager.getMaxRowcnt());

            //分页查询, 3.设置本次取到的记录数, 以及符合条件的总记录数
            PageList<AppReg> pageList = (PageList<AppReg>) appRegList;// 获得结果集条总数
            pager.setItemsCount(request, appRegList.size(), pageList.getTotalCount());

            map.put("appList", appRegList);
            return "listApps";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "login";
        }
    }
}
