package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	//web설정 파일
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		// /upload/~~ 경로만 쳐도 file:///C:/file_repo2/ 이 주소로 발동됨
		registry
			.addResourceHandler("/upload/**") //jsp페이지에서 /upload/ 패턴이 나올때 발동됨
			.addResourceLocations("file:///"+uploadFolder)
			.setCachePeriod(60*10*6) //60초 * 10 * 6 = 1시간 동안 이미지를 캐싱함
			.resourceChain(true)
			.addResolver(new PathResourceResolver());
	}
	
	
	
	
}
