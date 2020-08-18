package com.bdqn.financing.servlet;


import com.bdqn.financing.utils.EmptyUtils;
import com.bdqn.financing.utils.PrintUtil;
import com.bdqn.financing.utils.ReturnResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author wang_lei
 * 公共的Servlet抽象类
 * Created by bdqn on 2016/4/21.
 */
public abstract class AbstractServlet extends HttpServlet {


    /**
     * 获取我们自己写的servlet类对象
     * @return
     */
    public abstract Class getServletClass();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取action参数值
        String actionIndicator = req.getParameter("action");
        Method method = null;
        Object result = null;
        try {
            // 判断action参数值是否为空
            if (EmptyUtils.isEmpty(actionIndicator)) {
                result = execute(req, resp);
            } else {
                // 通过反射对象的api  getDeclareMethod方法 获取类的相应
                // 第一个参数方法名
                method = getServletClass().getDeclaredMethod(actionIndicator, HttpServletRequest.class, HttpServletResponse.class);
                result = method.invoke(this, req, resp);
            }
            // 根据获取到的方法的执行结果 跳转到相应页面
            toView(req, resp, result);
        } catch (NoSuchMethodException e) {
            String viewName = "404.jsp";
            req.getRequestDispatcher(viewName).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            if (!EmptyUtils.isEmpty(result)) {
                if (result instanceof String) {
                    String viewName = "500.jsp";
                    req.getRequestDispatcher(viewName).forward(req, resp);
                } else {
                    ReturnResult returnResult = new ReturnResult();
                    returnResult.returnFail("系统错误");
                    PrintUtil.write(returnResult, resp);
                }
            } else {
                String viewName = "500.jsp";
                req.getRequestDispatcher(viewName).forward(req, resp);
            }

        }
    }

    protected void toView(HttpServletRequest req, HttpServletResponse resp, Object result) throws IOException, ServletException {
        if (!EmptyUtils.isEmpty(result)) {
            if (result instanceof String) {
                String viewName = result.toString() + ".jsp";
                req.getRequestDispatcher(viewName).forward(req, resp);
            } else {
                PrintUtil.write(result, resp);
            }
        }
    }

    public Object execute(HttpServletRequest req, HttpServletResponse resp) {
        return "pre/index";
    }
}
