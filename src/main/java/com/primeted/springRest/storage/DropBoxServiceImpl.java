package com.primeted.springRest.storage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.tomcat.jni.File;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.dom4j.io.OutputFormat;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ThumbnailFormat;
import com.dropbox.core.v2.files.ThumbnailSize;
import com.dropbox.core.v2.files.UploadErrorException;

@Service
public class DropBoxServiceImpl implements DropBoxService{

	DbxRequestConfig config=null;
	DbxClientV2 client=null;
	private static String ACCESS_TOKEN="3dp7i7TQzHAAAAAAAAAADtD8e8qKiLij-kqlW5SlMeidzMNOs3SlvjRaKJXv-LPC";
	private Date now = null;
	private String currentTime = null;
	private String finalName ="";
	public DropBoxServiceImpl() {
		
		this.init();
	}
	@Override
	public String store(MultipartFile file) throws UploadErrorException, DbxException {
		now = new Date();
		currentTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(now);
		finalName="/"+"image"+currentTime+file.getOriginalFilename();
		try (InputStream in = file.getInputStream()) {
            FileMetadata metadata = client.files().uploadBuilder(finalName).uploadAndFinish(in);
                      
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        return finalName;
	}

	@Override
	public FileMetadata loadFile(String filename) throws IOException 	{
		File file= new File();
		FileOutputStream fileoutputStream = new FileOutputStream(filename);
		FileMetadata fr=null;
		try {
			fr=client.files().downloadBuilder("/"+filename).download(fileoutputStream);
			
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			fileoutputStream.close();
			
		
		}
		return fr;
	}

	@Override
	public void init() {
		config = new DbxRequestConfig("dropbox/restaurants_app", "en_US");
        client = new DbxClientV2(config, ACCESS_TOKEN);		
      
	}
	
	@Override
	public InputStream loadFilev2(String fileName) {
		// set our optional request parameters
	    DbxDownloader<FileMetadata> downloader=null;
	    FileMetadata metadata = null;
	    FileOutputStream out = null;
		try {
			out = new FileOutputStream("filename");
			metadata  = downloader.getResult();
		    InputStream in = downloader.getInputStream();
	        IOUtils.copy(in, out);
	        return in;
		
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			downloader.close();
	        try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				downloader.close();
			}
		}
		return null;
	
    }
	public void readAndWrite(InputStream metaData, OutputStream os) {
		try {
			IOUtils.copy(metaData,os);
			metaData.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
