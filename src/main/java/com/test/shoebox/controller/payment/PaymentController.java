package com.test.shoebox.controller.payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	

	@GetMapping("/orderlist")
	public String orderlist(Model model) {
		
		return "payment/orderlist";
	}
	
	@GetMapping("/payment_completed")
	public String payment_completed(Model model) {
		
		return "payment/payment_completed";
	}
	
	@GetMapping("/nonuser_order_terms")
	public String nonuser_order_terms(Model model) {
		
		return "payment/nonuser_order_terms";
	}
}
