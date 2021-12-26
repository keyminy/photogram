package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	
	private final ImageRepository imageRepository;
	//yml에 적은 D:/sts-4.11.1.RELEASE/workspace/photogram/upload/를 가져오기(★끝에 /붙는거 중요!!)
	@Value("${file.path}")
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto,PrincipalDetails principalDetails) {
		UUID uuid  = UUID.randomUUID();
		//MultipartFile의 메소드 쓰는중..실제 file이름이 들어간다 ex)1.jpg
		String imageFileName = uuid+ "_" + imageUploadDto.getFile().getOriginalFilename();
		System.out.println("이미지 파일 이름 : " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		//통신이 일어나거나,I/O가 일어날때는 예외가 발생,컴파일 시 못잡고 런타임시에만 잡을 수 있다
		try {
			Files.write(imageFilePath,imageUploadDto.getFile().getBytes()); //write(Path, byte[], OpenOption[](생략가능))
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//image테이블에 저장 
		//imageUploadDto내용을 Image객체로 변환 후 save
		Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
		//imageFileName : b2b8c9e9-f40e-4dbd-9aa1-56b901c84f2b_캡처.JPG
		//근데 앞에 D:로 시작하는 경로는?? yml에 적혀있는걸로 쓰자 -> 나중에 webConfig설정하드라
		Image imageEntity = imageRepository.save(image); //insert수행됨
		System.out.println("imageEntity : " + imageEntity);
	}
}
