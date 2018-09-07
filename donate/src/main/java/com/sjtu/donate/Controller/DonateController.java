package com.sjtu.donate.Controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.sjtu.donate.Common.AppSettings;
import com.sjtu.donate.Common.ResponseWrapper;
import com.sjtu.donate.Common.Utils;
import com.sjtu.donate.Entity.Donate;
import com.sjtu.donate.Entity.User;
import com.sjtu.donate.Repository.DonateRepository;
import com.sjtu.donate.Repository.UserRepository;

@RestController
public class DonateController {

	@Autowired
	private DonateRepository donateRepository;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/donate/{userId}")
	public JSONObject donate(HttpServletRequest request, @PathVariable int userId) {

		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		MultipartFile file = params.getFile("file");
		String title = params.getParameter("title");
		String description = params.getParameter("description");
		String writtenBy = params.getParameter("writtenBy");
		Donate donate = new Donate(userId, title, description);
		donate.setWrittenBy(writtenBy);

		if (file!=null&&!file.isEmpty()) {
			String filePath = AppSettings.IMAGE_SAVE_PATH;
			String fileName = userId + "_" + System.currentTimeMillis();
			System.out.println(file.getOriginalFilename());
			fileName += "." + file.getOriginalFilename().split("\\.")[1];
			try {
				Utils.saveFiles(file.getBytes(), filePath, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			donate.setFileName(fileName);
			donate.setFilePath(filePath);
		}

		donateRepository.save(donate);
		
		Optional<User> userOpt = userRepository.findById(userId);
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			int curBalance = user.getBalance();
			user.setBalance(curBalance+AppSettings.DONATE_REWARD);
			userRepository.save(user);
		}
		
		JSONObject res = ResponseWrapper.getResult(true, "");

		return res;
	}

	@GetMapping("/donate")
	public Iterable<Donate> getUserList() {
		return donateRepository.findAll();
	}
	
	@GetMapping("/donate/user/{userId}")
	public Iterable<Donate> getUserList(@PathVariable int userId) {
		return donateRepository.findByUserIdOrderByCreateTimeDesc(userId);
	}

}
