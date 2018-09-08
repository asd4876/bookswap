package com.sjtu.bookswap.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sjtu.bookswap.Common.AppSettings;
import com.sjtu.bookswap.Common.ResponseWrapper;
import com.sjtu.bookswap.Entity.User;
import com.sjtu.bookswap.Entity.WishList;
import com.sjtu.bookswap.Repository.UserRepository;
import com.sjtu.bookswap.Repository.WishListRepository;

@RestController
public class WishListController {

	@Autowired
	private WishListRepository wishListRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/wish/user/{userId}")
	public Iterable<WishList> getWishList(@PathVariable int userId) {
        return wishListRepository.findByUserId(userId);
	}
	
	@DeleteMapping("/wish/{wishId}")
	public JSONObject deleteWishList(@PathVariable int wishId) {
		wishListRepository.deleteById(wishId);
		return ResponseWrapper.getResult(true, null);
	}
	
	@GetMapping("/wish/{wishId}")
	public JSONObject getWishListById(@PathVariable int wishId) {
		Optional<WishList> wishOpt = wishListRepository.findById(wishId);
		if(!wishOpt.isPresent()) {
			return ResponseWrapper.getResult(false, AppSettings.ERROR_INVALID_ID);
		}
		return ResponseWrapper.getResult(true, wishOpt.get());
	}
	
	@PostMapping("/wish/user/{userId}")
	public JSONObject addWishList(@PathVariable int userId, @RequestBody JSONObject obj) {
		String title = obj.getString("title");
		String description = obj.getString("description");
		String author = obj.getString("author");
		String publisher = obj.getString("publisher");
		
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(!userOpt.isPresent()) {
			return ResponseWrapper.getResult(false, AppSettings.ERROR_WRONG_USER_ID);
		}

		User user = userOpt.get();
		
		WishList book = new WishList(title, userId, user.getAccount());
		book.setAuthor(author);
		book.setDescription(description);
		book.setPublisher(publisher);
		
		wishListRepository.save(book);
		
		return ResponseWrapper.getResult(true, null);
	}
}
