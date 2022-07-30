package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{

	@Query(value="SELECT i.* FROM image i INNER JOIN (SELECT imageId,COUNT(imageId) AS likeCnt FROM likes GROUP BY imageId) l ON i.id = l.imageId ORDER BY l.likeCnt DESC"
			,nativeQuery = true)
	public List<Image> mPopular();
	
	@Query(value="SELECT * \r\n"
			+ "FROM image\r\n"
			+ "WHERE userId \r\n"
			+ "IN (SELECT toUserId\r\n"
			+ "FROM subscribe\r\n"
			+ "WHERE fromuserId=:principalId) "
			+ "ORDER BY id DESC",nativeQuery = true)
	Page<Image> mStory(int principalId,Pageable pageable);
	//이제 List가 아닌,Page로 리턴 받아야함
}
