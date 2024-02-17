
import net.fabio.uploader.model.*;
import net.fabio.uploader.repositories.*;


import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


		@RestController
		@RequestMapping("/api/files")
		
		public class FileController {
		
		    @Autowired
		    private FileService fileService;
		
		    @PostMapping("/upload")
		    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		        try {
		            fileService.uploadFile(file);
		            return ResponseEntity.ok().body("Arquivo enviado com sucesso.");
		        } catch (IOException e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao processar o arquivo.");
		        }
		    }
		
		
		@GetMapping("/getAll")
		public ResponseEntity<List<FileDataDTO>> getAllFiles() {
		    List<FileDataDTO> files = fileService.getAllFiles();
		    return ResponseEntity.ok().body(files);
		}
		@GetMapping("/download/{fileId}")
	    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
	        byte[] content = fileService.downloadFile(fileId);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", "file.xml");
	        return new ResponseEntity<>(content, headers, HttpStatus.OK);
	    }
	}
		