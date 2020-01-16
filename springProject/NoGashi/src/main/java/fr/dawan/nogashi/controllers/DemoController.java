package fr.dawan.nogashi.controllers;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")
public class DemoController {

	@PostMapping(path = "admin/fakeTimeOffset")
	public void getFakeTimeOffset(@RequestBody long timeOffset, HttpSession session, Locale locale, Model model) {
		
	}
	
}
