package com.Bikkadit.blog.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Bikkadit.blog.Services.FileService;
import com.Bikkadit.blog.payloads.FileResponse;

@RestController
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to upload Image in post
	 * @param image
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileupload(@RequestParam("image") MultipartFile image) {
		String fileName = null;
		try {
			fileName = this.fileService.UploadImage(path, image);
		} catch (IOException e) {

			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null, "image not uploaded on server due to some error!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(new FileResponse(fileName, "image is Successfully uploaded!"), HttpStatus.OK);

	}
}
