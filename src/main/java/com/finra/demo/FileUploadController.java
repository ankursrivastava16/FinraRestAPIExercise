package com.finra.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	private static String UPLOADED_FOLDER = "C://Ankur//";

	@PostMapping("/fileUpload")
	public ResponseEntity<String> FileUpload(@RequestHeader(value = "fileversion") String fileversion,
			@RequestHeader(value = "filetype") String filetype, @RequestBody MultipartFile file) {

		// Save the uploaded file to UPLOADED_FOLDER 
		/***
		 * Meta data of the file fileversion and filetype
		 * file is picked up from C:\temp and attached as a postman request.
		 * 
		 */

		try {

			saveUploadedFilesToLocalFileSystemFolder(fileversion, filetype, Arrays.asList(file));

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Successfully uploaded - " + file.getOriginalFilename(), new HttpHeaders(),
				HttpStatus.OK);

	}

	// save file
	private void saveUploadedFilesToLocalFileSystemFolder(String fileversion, String filetype,
			List<MultipartFile> files) throws IOException {

		for (MultipartFile file : files) {

			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

		}

		/***
		 * Persisting the Meta data of file to the same file in the local file system.
		 * 
		 */

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			fw = new FileWriter("C://Ankur//FileUpload.txt",true);
			bw = new BufferedWriter(fw);
			bw.newLine();
			bw.write("****Writing meta data to the same file****");
			bw.newLine();
			bw.write(fileversion);
			bw.newLine();
			bw.write(filetype);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}
}