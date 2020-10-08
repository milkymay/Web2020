package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Random random = new Random();
        if (session.getAttribute("captcha") == null) {
            if (session.getAttribute("number") != null && request.getParameter("number") != null &&
                    request.getParameter("number").equals(session.getAttribute("number").toString())) {
                response.sendRedirect(request.getRequestURI());
                session.setAttribute("captcha", true);
            } else {
                int number = random.nextInt(899) + 100;
                session.setAttribute("number", number);
                Path path = Paths.get(getServletContext().getRealPath("/static/captcha.html"));
                byte[] bytes = Files.readAllBytes(path);
                String captcha_img = Base64.getEncoder().encodeToString(ImageUtils.toPng(Integer.toString(number)));
                response.getWriter().print(String.format(new String(bytes), captcha_img));
                response.getWriter().flush();
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}