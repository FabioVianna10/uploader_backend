package services;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.util.StringUtils;
import net.fabio.uploader.model.BinaryFileEntity;
import net.fabio.uploader.model.FileEntity;
import net.fabio.uploader.repositories.BinaryFileRepository;
import net.fabio.uploader.repositories.FileRepository;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BinaryFileRepository binaryFileRepository;

    public void uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo está vazio.");
        }

        if (!file.getContentType().equals("application/xml")) {
            throw new IllegalArgumentException("O arquivo não é um XML.");
        }
        
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setUploadDate(new Date(0));
        
        FileEntity savedFileEntity = fileRepository.save(fileEntity);
        
        BinaryFileEntity binaryFileEntity = new BinaryFileEntity();
        binaryFileEntity.setConteudoBinario(file.getBytes());
        binaryFileEntity.setFileEntity(savedFileEntity);
        
        binaryFileRepository.save(binaryFileEntity);
    }

    public List<FileDataDTO> getAllFiles() {
        List<FileEntity> fileEntities = fileRepository.findAll();

        List<Long> binaryFileIds = binaryFileRepository.findAll()
                .stream()
                .map(binaryFileEntity -> binaryFileEntity.getFileEntity().getId())
                .collect(Collectors.toList());

        List<FileEntity> filesWithoutBinary = fileEntities.stream()
                .filter(fileEntity -> !binaryFileIds.contains(fileEntity.getId()))
                .collect(Collectors.toList());

        List<FileDataDTO> fileDataDTOs = filesWithoutBinary.stream()
                .map(this::mapToFileDataDTO)
                .collect(Collectors.toList());

        return fileDataDTOs;
    }

    private FileDataDTO mapToFileDataDTO(FileEntity fileEntity) {
        FileDataDTO fileDataDTO = new FileDataDTO();
        fileDataDTO.setId(fileEntity.getId());
      
        return fileDataDTO;
    }

    public byte[] downloadFile(Long fileId) {
      
        java.util.Optional<BinaryFileEntity> optionalBinaryFileEntity = binaryFileRepository.findById(fileId);

        if (optionalBinaryFileEntity.isPresent()) {
            BinaryFileEntity binaryFileEntity = optionalBinaryFileEntity.get();
            return binaryFileEntity.getConteudoBinario();
        } else {
            
            throw new NotFoundException("Arquivo não encontrado com o ID fornecido: " + fileId);
        }
    }
}
