package com.Bikkadit.blog.Services.ServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Bikkadit.blog.Services.FileService;
@Service
public class FileServiceimpl  implements FileService {

	

	@Override
	public String UploadImage(String path, MultipartFile file) throws IOException  {
		
		//filename
		
		String filename = file.getOriginalFilename();
		//abc.png
		
		//generate random name for file
		String rendomId = UUID.randomUUID().toString();
		String fileName1 = rendomId.concat(filename.substring(filename.lastIndexOf(".")));
		
		
		//Full path
		String filepath=path+File.separator+fileName1;
		
	
		
		
		//Create image folder if not created
		
		File f=new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		//Upload File
		
		Files.copy(file.getInputStream(),Paths.get(filepath));
		
		return fileName1 ;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullpath=path+File.separator+fileName;
		InputStream is = new FileInputStream(fullpath);
		return is;
	}

	
}
