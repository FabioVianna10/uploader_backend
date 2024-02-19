package net.fabio.uploader.mapper;

import net.fabio.uploader.dto.FileDto;
import net.fabio.uploader.model.FileEntity;

public class Mapper {
	
	public FileDto fileEntityToDto(FileEntity fileEntity) {
		return new FileDto(fileEntity.getFileId(),fileEntity.getnNF(), fileEntity.getDhEmi(), fileEntity.getcUF(), fileEntity.getCNPJ(), 
				fileEntity.getxFant(),fileEntity.getDesCNPJ(), fileEntity.getDesxNome(), fileEntity.getvTotTrib(), fileEntity.getvNF());
	}
	public FileEntity fileDtoToFileEntity(FileDto fileDto) {
		return new FileEntity(fileDto.getFileId(),fileDto.getnNF(), fileDto.getDhEmi(), fileDto.getcUF(), fileDto.getCNPJ(), 
				fileDto.getxFant(),fileDto.getDesCNPJ(), fileDto.getDesxNome(), fileDto.getvTotTrib(), fileDto.getvNF());
				
	}	
	
}
	
	








