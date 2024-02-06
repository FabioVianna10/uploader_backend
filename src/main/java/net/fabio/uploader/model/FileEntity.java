package net.fabio.uploader.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nNF;
    private Date dhEmi;
    private String cUF;
    private String CNPJ;
    private String xFant;
    private String desCNPJ;
    private String desxNome;
    private Double vTotTrib;
    private Double vNF;
    
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
    
    
    
}
