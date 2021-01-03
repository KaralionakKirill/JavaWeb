package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.Command;
import com.epam.JavaWeb.constant.PagePath;
import com.epam.JavaWeb.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private UserService service;

    public LoginCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String loginValue = request.getParameter("uname");
        String passwordValue = request.getParameter("password");

        page = PagePath.LOGIN;

        return page;
    }
}
