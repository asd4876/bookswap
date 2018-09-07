package com.sjtu.donate.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sjtu.donate.Entity.Donate;

public interface DonateRepository extends CrudRepository<Donate, Integer>{
	List<Donate> findByUserIdOrderByCreateTimeDesc(int userId);
}
