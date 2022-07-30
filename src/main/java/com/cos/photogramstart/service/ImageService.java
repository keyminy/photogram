package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(){
		return imageRepository.mPopular();
	}
	
	@Transactional(readOnly=true)
	public Page<Image> 이미지스토리(int principalId,Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId,pageable);
		
		//images에 좋아요 상태를 담아야 한다.
		images.forEach((image) -> {
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach(like -> {
				//like안의 userId와 principalId를 비교하면 됨
				if(like.getUser().getId() == principalId) {
					//해당 이미지에 좋아요 한 사람들을 찾아서,현재 로그인한 사람이 좋아요 한 것인지 비교
					image.setLikeState(true);
				}
			});
		});
		return images;
	}
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto,@AuthenticationPrincipal  PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		//1.jpg같은거, 실제 파일 이름이 들어감
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
		System.out.println("이미지 파일 이름 :  " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		//통신이나 I/O처리 시 예외가 발생할 수 있으므로 try~catch예외처리 필요
		//런타임 에러 처리
		try {
			Files.write(imageFilePath,imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//image테이블에 저장하기.
		Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
		imageRepository.save(image);
		
		//System.out.println(imageEntity);
	}
}
