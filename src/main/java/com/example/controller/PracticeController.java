package com.example.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Member;
import com.example.domain.TmpMember;
import com.example.form.PracticeForm;
import com.example.repository.PracticeRepository;

@Controller
@RequestMapping("")
public class PracticeController {

	@Autowired
	private PracticeRepository practiceRepository;
	
	@Autowired
	private MailSender mailSender;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/register")
	public String register(PracticeForm form) {
		Member isMember = practiceRepository.findByMail(form.getMail());
		if(isMember == null) {
			String vali = UUID.randomUUID().toString();
			TmpMember tmpMember = new TmpMember();
			tmpMember.setName(form.getName());
			tmpMember.setMail(form.getMail());
			tmpMember.setPassword(form.getPassword());
			tmpMember.setUuid(vali);
			practiceRepository.insert(tmpMember);
			
			String IPadnPort = "localhost:8080";
			String from = "rakus.yahoo@gmail.com";
			String title = "アカウント確認のお願い";
			String content = form.getName() + "さん" +"\n"+"\n"+"以下のリンクにアクセスしてアカウント認証をしてください"+"\n"+"http://"+IPadnPort+"/validate"+"?uuid="+vali;
			
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom(from);
			msg.setTo(form.getMail());
			msg.setSubject(title);
			msg.setText(content);
			mailSender.send(msg);
			
		}
		return "index";
	}
}
