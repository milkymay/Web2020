package ru.itmo.wp.servlet;

import com.google.gson.Gson;
//import jdk.internal.net.http.common.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class ChatServlet extends HttpServlet {
    private Chat chat = new Chat();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        switch (uri) {
            case "/message/auth": {
                String user = request.getParameter("user");
                if (user != null) {
                    session.setAttribute("user", user);
                } else {
                    user = "";
                }
                String json = new Gson().toJson(user);
                response.getWriter().print(json);
                response.getWriter().flush();
                break;
            }
            case "/message/findAll": {
                String json = new Gson().toJson(chat.getChat());
                response.getWriter().print(json);
                response.getWriter().flush();
                break;
            }
            case "/message/add": {
                String user = (String) session.getAttribute("user");
                String text = request.getParameter("text");
                chat.addMessage(new Message(user, text));
                break;
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


    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}

