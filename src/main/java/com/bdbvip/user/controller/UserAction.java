package com.bdbvip.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bdbvip.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {
	@Autowired
	UserService userService;
}
