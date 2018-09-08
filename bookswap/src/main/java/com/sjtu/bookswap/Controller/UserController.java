package com.sjtu.bookswap.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sjtu.bookswap.Common.AppSettings;
import com.sjtu.bookswap.Common.ResponseWrapper;
import com.sjtu.bookswap.Entity.OwnList;
import com.sjtu.bookswap.Entity.User;
import com.sjtu.bookswap.Entity.WishList;
import com.sjtu.bookswap.Repository.OwnListRepository;
import com.sjtu.bookswap.Repository.UserRepository;
import com.sjtu.bookswap.Repository.WishListRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WishListRepository wishListRepository;
    
    @Autowired
    private OwnListRepository ownListRepository;
    
    
    @GetMapping("/search/user/{userId}")
    public JSONObject search(@PathVariable int userId) {
    	
		List<WishList> wishList = wishListRepository.findByUserId(userId);
		List<OwnList> ownList = ownListRepository.findByUserId(userId);
		
		List<OwnList> wishResult = new ArrayList<OwnList>();
		
		Map<Integer, OwnList> ownMap = new TreeMap<Integer, OwnList>();
		
		for(WishList book:wishList) {
			List<OwnList> tmpList = ownListRepository.findByTitleLike("%"+book.getTitle()+"%");
			for(OwnList tmp:tmpList) {
				ownMap.put(tmp.getId(), tmp);
			}
		}
		
		for(Entry<Integer, OwnList> own:ownMap.entrySet()) {
			wishResult.add(own.getValue());
		}
		
		Set<Integer> userSet = new TreeSet<Integer>();
		
		for(OwnList own:wishResult) {
			userSet.add(own.getUserId());
		}
		
		List<WishList> tmpResult = wishListRepository.findByUserIdIn(userSet);
		
		Set<Integer> userMatch = new TreeSet<Integer>();
		
		for(WishList book:tmpResult) {
			String title = book.getTitle();
			for(OwnList own:ownList) {
				if(own.getTitle().contains(title)) {
					userMatch.add(book.getUserId());
				}
			}
		}
		
		
		List<OwnList> matchResult = new ArrayList<OwnList>();
		
		for(OwnList book:wishResult) {
			if(userMatch.contains(book.getUserId())) {
				matchResult.add(book);
			}
		}
		
		JSONObject finalResult = new JSONObject();
		finalResult.put("totalMatch", matchResult);
		finalResult.put("halfMatch", wishResult);
		
		return ResponseWrapper.getResult(true, finalResult);
    	
    }

    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject obj){
        String account = obj.getString("account");
        String password = obj.getString("password");
        String email = obj.getString("email");
        
        User user = new User(account, password);
        user.setEmail(email);
        
        List<User> userList = userRepository.findByAccount(account);
        
        JSONObject res = ResponseWrapper.getResult(false, AppSettings.ERROR_DUPLICATED_ACCOUNT);
        if(userList.isEmpty()){
        	res = ResponseWrapper.getResult(true, userRepository.save(user));
        }
        
        return res;
    }
    
    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject obj) {
        String account = obj.getString("account");
        String password = obj.getString("password");
        List<User> userList = userRepository.findByAccountAndPassword(account, password);
        
        JSONObject res = ResponseWrapper.getResult(false, AppSettings.ERROR_LOGIN);
        if(!userList.isEmpty()) {
        	res = ResponseWrapper.getResult(true, userList.get(0));
        }
        
        return res;
    }

    @RequestMapping("/user")
    public Iterable<User> getUserList(){
        return userRepository.findAll();
    }
    
    @RequestMapping("/user/{userId}")
    public JSONObject getUserList(@PathVariable int userId){
        JSONObject res = ResponseWrapper.getResult(false, AppSettings.ERROR_WRONG_USER_ID);
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isPresent()) {
        	res = ResponseWrapper.getResult(true, userOpt.get());
        }
        return res;
    }
}
