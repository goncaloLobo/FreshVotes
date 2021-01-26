package com.freshvotes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {

	// vai ouvir um pedido GET na root e retorna a view index
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String rootView() {
		return "index";
	}
}
