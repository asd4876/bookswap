package com.sjtu.donate.Controller;

import com.alibaba.fastjson.JSONObject;
import com.sjtu.donate.Common.AppSettings;
import com.sjtu.donate.Common.ResponseWrapper;
import com.sjtu.donate.Entity.User;
import com.sjtu.donate.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    


    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject obj){
        String account = obj.getString("account");
        String password = obj.getString("password");
        String name = obj.getString("firstName")+" "+obj.getString("lastName");
        
        User user = new User(account, password);
        user.setName(name);
        
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
