package com.sjtu.bookswap.Controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.sjtu.bookswap.Common.AppSettings;
import com.sjtu.bookswap.Common.ResponseWrapper;
import com.sjtu.bookswap.Common.Utils;
import com.sjtu.bookswap.Entity.OwnList;
import com.sjtu.bookswap.Entity.User;
import com.sjtu.bookswap.Repository.OwnListRepository;
import com.sjtu.bookswap.Repository.UserRepository;


@RestController
public class OwnListController {

	@Autowired
	private OwnListRepository ownListRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/own/user/{userId}")
	public Iterable<OwnList> getOwnList(@PathVariable int userId){
		return ownListRepository.findByUserId(userId);
	}
	
	@GetMapping("/all")
	public Iterable<OwnList> getAll(){
		return ownListRepository.findAllByOrderByCreateTimeDesc();
	}
	
	@DeleteMapping("/own/{ownId}")
	public JSONObject deleteOwnList(@PathVariable int ownId) {
		ownListRepository.deleteById(ownId);
		return ResponseWrapper.getResult(true, null);
	}

	@GetMapping("/own/{ownId}")
	public JSONObject getWishListById(@PathVariable int ownId) {
		Optional<OwnList> ownOpt = ownListRepository.findById(ownId);
		if(!ownOpt.isPresent()) {
			return ResponseWrapper.getResult(false, AppSettings.ERROR_INVALID_ID);
		}
		return ResponseWrapper.getResult(true, ownOpt.get());
	}
	
	@PostMapping("/own/user/{userId}")
	public JSONObject addOwnList(HttpServletRequest request, @PathVariable int userId) {
		
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		
		MultipartFile file = params.getFile("file");
		String title = params.getParameter("title");
		String description = params.getParameter("description");
		String author = params.getParameter("author");
		String publisher = params.getParameter("publisher");
		
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(!userOpt.isPresent()) {
			return ResponseWrapper.getResult(false, AppSettings.ERROR_WRONG_USER_ID);
		}

		User user = userOpt.get();
		
		OwnList book = new OwnList(title, userId, user.getAccount());
		book.setAuthor(author);
		book.setDescription(description);
		book.setPublisher(publisher);
		
		if (file!=null&&!file.isEmpty()) {
			String filePath = AppSettings.IMAGE_SAVE_PATH;
			String fileName = userId + "_" + System.currentTimeMillis();
			System.out.println("[Receive file name: ]"+file.getOriginalFilename());
			String[] strList = file.getOriginalFilename().split("\\.");
			int len = strList.length;
			if(len > 1) {
				fileName += "." + strList[len-1];
			}
			
			try {
				Utils.saveFiles(file.getBytes(), filePath, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			book.setPhotoUrl(fileName);
		}
		
		ownListRepository.save(book);
		
		return ResponseWrapper.getResult(true, null);
	}
}
