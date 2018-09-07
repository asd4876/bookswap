package com.sjtu.donate.Repository;

import com.sjtu.donate.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer>{
    List<User> findByAccountAndPassword(String account, String password);
    List<User> findByAccount(String account);
}
