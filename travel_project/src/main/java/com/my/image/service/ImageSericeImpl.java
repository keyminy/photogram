package com.my.image.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.my.image.mapper.ImageMapper;
import com.my.image.vo.ImageVO;

import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Service
@Qualifier("isi")
@Log4j
public class ImageSericeImpl implements ImageService {

	@Autowired
	private ImageMapper mapper;
	
	@Override
	public List<ImageVO> list(PageObject pageObject) throws Exception {
		//전체 데이터 갯수 구한 후, PageObject에 셋팅해야 함.
		pageObject.setTotalRow(mapper.getTotalRow(pageObject));
		log.info("mapper 후 : " + pageObject);
		//데이터를 가져와서 리턴한다.
		return mapper.list(pageObject);
	}

	@Override
	public ImageVO view(ImageVO vo) throws Exception {
		return mapper.view(vo);
	}

	@Override
	public int write(ImageVO vo) throws Exception {
		return mapper.write(vo);
	}

	@Override
	public int changeImage(ImageVO vo) throws Exception {
		return mapper.changeImage(vo);
	}

	@Override
	public int update(ImageVO vo) throws Exception {
		return mapper.update(vo);
	}

	@Override
	public int delete(ImageVO vo) throws Exception {
		return mapper.delete(vo);
	}
	@Override
	public int dolike(ImageVO vo) throws Exception {
		return mapper.dolike(vo);
	}

	@Override
	public int likeCancle(ImageVO vo) throws Exception {
		return mapper.likeCancle(vo);
	}

}
