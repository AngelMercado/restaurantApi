package com.primeted.springRest.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	public String store(MultipartFile file);
	public Resource loadFile(String filename);
	public void deleteAll();
	public void init();
	
}
