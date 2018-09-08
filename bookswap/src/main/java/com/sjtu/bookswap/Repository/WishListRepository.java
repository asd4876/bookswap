package com.sjtu.bookswap.Repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sjtu.bookswap.Entity.WishList;

public interface WishListRepository extends CrudRepository<WishList, Integer>{
	public List<WishList> findByUserId(int userId);
	public List<WishList> findByTitleLike(String title);
	public List<WishList> findByUserIdIn(Collection<Integer> userId);

}
