package com.sjtu.bookswap.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sjtu.bookswap.Entity.OwnList;

public interface OwnListRepository extends CrudRepository<OwnList, Integer>{
	public List<OwnList> findByUserId(int userId);
	public List<OwnList> findAllByOrderByCreateTimeDesc();
	
	public List<OwnList> findByTitleLike(String title);
}
