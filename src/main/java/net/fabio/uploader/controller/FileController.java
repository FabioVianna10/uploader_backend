package net.fabio.uploader.controller;

import net.fabio.uploader.dto.FileDto;
import net.fabio.uploader.model.*;
import net.fabio.uploader.repositories.*;
import services.FileService;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@RestController
@RequestMapping("/api/files")

public class FileController {
		
    @Autowired
    private FileService fileService;
		
    @PostMapping("upload")
    public ResponseEntity<List<FileDto>> uploadFile(@RequestParam("file") ArrayList<MultipartFile> files) {
        List<FileDto> updatedFiles = fileService.uploadFile(files);
        return ResponseEntity.ok().body(updatedFiles);
    }
	
	
	@GetMapping("/getAll")
	public ResponseEntity<List<FileDto>> getAllFiles() {
	    List<FileDto> files = fileService.getAllFiles();
	    return ResponseEntity.ok().body(files);
	}
//		@GetMapping("/download/{fileId}")
//	    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
//	        byte[] content = fileService.downloadFile(fileId);
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//	        headers.setContentDispositionFormData("attachment", "file.xml");
//	        return new ResponseEntity<>(content, headers, HttpStatus.OK);
//	    }
	}
		