package com.my.image.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@Log
public class FileUtil {

	// 파일이 존재하는지 확인하는 메서드
	public static boolean exist(File file) throws Exception{
		return file.exists();
	}
	
	// 파일이 존재하는지 확인하는 메서드
	public static boolean exist(String fileName) throws Exception{
		return toFile(fileName).exists();
	}
	
	// 문자열을 파일 객체로 만들어 주는 메서드
	public static File toFile(String fileName) throws Exception{
		return new File(fileName);
	}
	
	// 파일 지우기
	public static boolean delete(File file) throws Exception {
		return file.delete();
	}
	
	// 파일의 정보를 문자열로 넣어주면 지워주는 메서드
	// 파일은 realPath 정보의 파일명을 넘겨야 한다.
	public static boolean remove(String fileName) throws Exception {
		// 1. 문자열을 파일 객체로 만든다. 2. 있는지 확인한다. 3. 삭제한다. 4. 결과 리턴
		File file = toFile(fileName);
		// 파일이 존재하지 않는 경우 예외 발생
		if(!exist(file)) throw new Exception("삭제하려는 파일이 존재하지 않습니다.");
		// 파일이 존재하는 경우 처리
		else if(!delete(file)) throw new Exception("삭제하려는 파일이 삭제되지 않았습니다.");
		else System.out.println("FileUtil.remove() - 파일이 삭제 되었습니다.");
		return true;
	}
	
	//서버의 상대주소를 넣으면 절대주소로 바꾸어 주는 메소드 만들기 리턴 : 스트링
	public static String getRealPath(String path,String fileName,HttpServletRequest request) {
		String filePath = path+"/"+fileName;
		return request.getServletContext().getRealPath(filePath);
	}
	// 파일의 절대주소를 받아서 중복되지 않는 File객체를 리턴
	// 중복된 파일에 대한 처리 - 중복이 되지 않는 File 객체를 리턴해 준다.
	public static File noDuplicate(String fileFullName) throws Exception {
		System.out.println("FileUtil.noDuplicate().fileFullName = "+fileFullName);
		
		File file = null;

		int dotPos = fileFullName.lastIndexOf(".");
		// image.jpg - fileName : image, ext : .jpg
		String fileName = fileFullName.substring(0, dotPos); // image
		String ext = fileFullName.substring(dotPos); // .jpg
		
		boolean isDuplicate = false;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();        
		String today = dateFormat.format(date);
		
		// 파일 정보확인
		System.out.println("FileUtil.noDuplicate().fileName = " + fileName + ", ext = " + ext);
				
		//while문안에 파일명과 확장자명이 필요하다.
		while(true) {
			if(isDuplicate == false) { //중복 안되었으면
				file = toFile(fileFullName);
			}else { 
				file = toFile(fileName + "_" + today + ext);
			}
			if(!exist(file)) {
				//파일이 경로에 존재하지 않는다 = 중복x
				break;
			}
			isDuplicate = true; //중복이 되면 else문으로가서 today 붙이기
		}
		
		return file;
	}
	// 파일 서버에 올리는 메서드 - FileUpload 라이브러리 사용

	public static String upload(final String PATH, MultipartFile multiFile, HttpServletRequest request) throws Exception {
		String fileFullName = "";
		log.info("[" + multiFile.getOriginalFilename() + "]");
		if(multiFile != null && !multiFile.getOriginalFilename().equals("")) {
			log.info(multiFile.toString());
			log.info(multiFile.getName());
			String fileName = multiFile.getOriginalFilename();
			log.info(fileName);
			// 서버의 파일명 중복을 배제한 File 객체
			File saveFile = noDuplicate(getRealPath(PATH, fileName, request));
			fileFullName = PATH + "/" + saveFile.getName();
			log.info("FileUtil.upload() : " + fileFullName);
			// 실제적인 업로드 되는 파일을 multiFile에 저장함
			multiFile.transferTo(saveFile);
		} else {
			//첨부파일이 없을때 noImage.jpg
			fileFullName = PATH + "/" + "homepage_noimage.jpg";
		}
		return fileFullName;
	}
}
