package net.fabio.uploader.dto;

import java.util.Date;

public class FileDto {

	private Long id;
	private String fileId;
    
	private String nNF;
    private Date dhEmi;
    private String cUF;
    private String CNPJ;
    private String xFant;
	private String desCNPJ;
    private String desxNome;
    private Double vTotTrib;
    private Double vNF;
    
    public FileDto (String fileId, String nNF, Date dhEmi, String cUF,String CNPJ,String xFant,String desCNPJ,String desxNome,Double vTotTrib, Double vNF) {
    	this.fileId = fileId;
    	this.nNF = nNF;
    	this.dhEmi = dhEmi;
    	this.cUF = cUF;
    	this .CNPJ = CNPJ;
    	this.xFant = xFant;
    	this.desCNPJ = desCNPJ;
    	this.desxNome = desxNome;
    	this. vTotTrib = vTotTrib;
    	this.vNF = vNF;	
    }
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getnNF() {
		return nNF;
	}
	public void setnNF(String nNF) {
		this.nNF = nNF;
	}
	public Date getDhEmi() {
		return dhEmi;
	}
	public void setDhEmi(Date dhEmi) {
		this.dhEmi = dhEmi;
	}
	public String getcUF() {
		return cUF;
	}
	public void setcUF(String cUF) {
		this.cUF = cUF;
	}
	public String getCNPJ() {
		return CNPJ;
	}
	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}
	public String getxFant() {
		return xFant;
	}
	public void setxFant(String xFant) {
		this.xFant = xFant;
	}
	public String getDesCNPJ() {
		return desCNPJ;
	}
	public void setDesCNPJ(String desCNPJ) {
		this.desCNPJ = desCNPJ;
	}
	public String getDesxNome() {
		return desxNome;
	}
	public void setDesxNome(String desxNome) {
		this.desxNome = desxNome;
	}
	public Double getvTotTrib() {
		return vTotTrib;
	}
	public void setvTotTrib(Double vTotTrib) {
		this.vTotTrib = vTotTrib;
	}
	public Double getvNF() {
		return vNF;
	}
	public void setvNF(Double vNF) {
		this.vNF = vNF;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}

