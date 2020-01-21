/**
 * 
 */
package com.ishanitech.ipalikawebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pujan K.C. <pujanov69@gmail.com>
 *
 * Created on Jan 21, 2020
 */
@Controller
@RequestMapping("/main")
public class MainController {
	@GetMapping()
	String getLoginView(Model model) {
		return "main";
	}
}
