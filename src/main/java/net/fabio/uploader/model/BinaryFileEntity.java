package net.fabio.uploader.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class BinaryFileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private byte[] conteudoBinario;

	@OneToOne
	@JoinColumn(name = "file_id", referencedColumnName = "id")
	private FileEntity fileEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getConteudoBinario() {
		return conteudoBinario;
	}

	public void setConteudoBinario(byte[] conteudoBinario) {
		this.conteudoBinario = conteudoBinario;
	}

	public FileEntity getFileEntity() {
		return fileEntity;
	}

	public void setFileEntity(FileEntity fileEntity) {
		this.fileEntity = fileEntity;
	}

}
