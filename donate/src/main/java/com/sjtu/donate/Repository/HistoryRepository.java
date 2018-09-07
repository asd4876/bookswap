package com.sjtu.donate.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sjtu.donate.Entity.History;

public interface HistoryRepository extends CrudRepository<History, Integer>{
	List<History> findByUserIdOrderByCreateTimeDesc(int userId);
}
