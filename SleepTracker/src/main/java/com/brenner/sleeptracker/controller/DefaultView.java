/**
 * 
 */
package com.brenner.sleeptracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dbrenner
 * 
 */
@Controller
public class DefaultView {

	@RequestMapping("/")
	  public String handleRequest (Model model) {
	      return "forward:/index.html";
	  }

}
