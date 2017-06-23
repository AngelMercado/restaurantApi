package com.primeted.springRest.storage;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private Date now = null;
	private String currentTime = null;
	private final Path rootLocation = Paths.get("upload-dir");
	private String finalName ="";
	public String store(MultipartFile file){
		
		now = new Date();
		currentTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(now);
		try{
			
			finalName = "image"+currentTime+file.getOriginalFilename();
			Files.copy(file.getInputStream(), this.rootLocation.resolve(finalName));
			return finalName;
		}catch(Exception e){
			log.info(" {} ",e);
			throw new RuntimeException("fails to copy file");
		}
		
	}
	
	public Resource loadFile(String filename){
		try{
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()){
				return resource;
			}else{
				throw new RuntimeException("fails to read file");
			}
		}catch(Exception e){
			throw new RuntimeException("fails to load files");
		}
	}
	
	public void deleteAll(){
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	public void init(){
		try{
			Files.createDirectory(rootLocation);
		}catch(IOException e){
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
