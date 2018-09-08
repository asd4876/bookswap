package com.sjtu.bookswap.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sjtu.bookswap.Entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
    List<User> findByAccountAndPassword(String account, String password);
    List<User> findByAccount(String account);
}