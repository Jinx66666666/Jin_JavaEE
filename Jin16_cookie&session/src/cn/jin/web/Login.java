package cn.jin.web;

import cn.jin.dao.UserDao;
import cn.jin.domain.User;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Description: ${description}
 * @author: HuangJing
 * @date: 2020/02/25  18:56:39
 * @Version: 1.0
 **/
@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        String checkCode = request.getParameter("checkCode");
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserDao userDao = new UserDao();
        User login = userDao.login(user);
        //3.先获取生成的验证码
        HttpSession session = request.getSession();
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        //删除session中存储的验证码
//        session.removeAttribute("checkCode_session");
        if (checkCode_session != null && checkCode.equalsIgnoreCase(checkCode_session)) {
//            if (user.getUsername().equals(login.getUsername()) && user.getPassword().equals(login.getPassword())) {
            if(login != null){
                session.setAttribute("user",login.getUsername());
                response.sendRedirect(request.getContextPath()+"/success.jsp");
            }else {
                //登录失败
                //存储提示信息到request
                request.setAttribute("login_error","用户名或密码错误");
                //转发到登录页面
                request.getRequestDispatcher("/user.jsp").forward(request,response);
            }
        }else {
            request.setAttribute("error","验证码错误");
            request.getRequestDispatcher("/user.jsp").forward(request,response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
