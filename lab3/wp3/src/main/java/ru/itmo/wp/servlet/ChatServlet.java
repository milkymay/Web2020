package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import org.apache.tomcat.util.buf.Utf8Decoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class ChatServlet extends HttpServlet {
    private final Chat chat = new Chat();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        response.setContentType("application/json; charset=windows-1251");
        if (uri.equals("/message/auth")) {
            String user = request.getParameter("user");
            if (user != null) {
                session.setAttribute("user", user);
            } else {
                user = "";
            }
            String json = new Gson().toJson(user);
            response.getWriter().print(json);
            response.getWriter().flush();
        } else synchronized (chat) {
            if (uri.equals("/message/findAll")) {
                String json = new Gson().toJson(chat.getChat());
                response.getWriter().print(json);
                response.getWriter().flush();
            } else if (uri.equals("/message/add")) {
                String user = (String) session.getAttribute("user");
                String text = request.getParameter("text");
                chat.addMessage(new Message(user, text));
            }
        }
    }

    private static class Message {
        private String user;
        private String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    private static class Chat {
        private ArrayList<Message> chat = new ArrayList<>();

        public void addMessage(Message msg) {
            chat.add(msg);
        }

        public ArrayList<Message> getChat() {
            return chat;
        }
    }
}

