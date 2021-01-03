package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.Command;
import com.epam.JavaWeb.constant.PagePath;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.REGISTRATION;
    }
}
