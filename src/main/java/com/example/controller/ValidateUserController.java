package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Member;
import com.example.domain.TmpMember;
import com.example.repository.PracticeRepository;

@Controller
@RequestMapping("")
public class ValidateUserController {
	
	@Autowired
	private PracticeRepository practiceRepository;
	
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	@CrossOrigin
	public String validate(@RequestParam("uuid") String uuid) {
		TmpMember isExist = practiceRepository.load(uuid);
		
		if(isExist != null) {
			TmpMember tmp = practiceRepository.load(uuid);
			String name = tmp.getName();
			String mail = tmp.getMail();
			String password = tmp.getPassword();
			
			Member member = new Member();
			member.setName(name);
			member.setMail(mail);
			member.setPassword(password);
			
			practiceRepository.register(member);
			practiceRepository.delete(uuid);
			
		}
		return "index";
	}
}
