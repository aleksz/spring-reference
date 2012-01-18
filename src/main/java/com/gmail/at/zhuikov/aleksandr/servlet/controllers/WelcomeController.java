package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	@RequestMapping("/")
	public String redirectToOrders() {
		return "redirect:/orders";
	}
}
