package com.primeted.springRest.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;

public interface DropBoxService {
	public String store(MultipartFile file) throws UploadErrorException, DbxException;
	public FileMetadata loadFile(String filename) throws IOException;
	public void init();
	InputStream loadFilev2(String fileName);
	
}
