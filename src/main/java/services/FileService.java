package services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.util.StringUtils;
import net.fabio.uploader.dto.FileDto;
import net.fabio.uploader.mapper.Mapper;
import net.fabio.uploader.model.BinaryFileEntity;
import net.fabio.uploader.model.FileEntity;
import net.fabio.uploader.repositories.BinaryFileRepository;
import net.fabio.uploader.repositories.FileRepository;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.InputStream;




@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BinaryFileRepository binaryFileRepository;

    public List<FileDto> uploadFile(ArrayList<MultipartFile> files) {
    	files.forEach((file) -> {
    		try {
    			InputStream inputStream = file.getInputStream();
    			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    			Document doc = dBuilder.parse(inputStream);
    			doc.getDocumentElement().normalize();

    			// Obtendo o elemento raiz <nfeProc>
    			Node nfeProcNode = doc.getDocumentElement();

    			// Navegando para o elemento <NFe> dentro de <nfeProc>
    			Node nFeNode = getChildNodeByTagName(nfeProcNode, "NFe");

    			// Navegando para o elemento <infNFe> dentro de <NFe>
    			Node infNFeNode = getChildNodeByTagName(nFeNode, "infNFe");

    			FileEntity fileEntity = new FileEntity();
    			// Obtendo os elementos filhos de <infNFe> e configurando os valores em fileEntity
    			if (infNFeNode.getNodeType() == Node.ELEMENT_NODE) {
    			    Element infNFeElement = (Element) infNFeNode;
    			    fileEntity.setFileId(infNFeElement.getAttribute("Id"));
    			    fileEntity.setnNF(getChildNodeTextContent(infNFeElement, "ide/nNF"));
    			    fileEntity.setDhEmi(getDateFromString(getChildNodeTextContent(infNFeElement, "ide/dhEmi")));
    			    fileEntity.setcUF(getChildNodeTextContent(infNFeElement, "ide/cUF"));
    			    fileEntity.setxFant(getChildNodeTextContent(infNFeElement, "emit/xFant"));
    			    fileEntity.setCNPJ(getChildNodeTextContent(infNFeElement, "ëmit/CNPJ"));
    			    fileEntity.setDesCNPJ(getChildNodeTextContent(infNFeElement, "dest/CNPJ"));
    			    fileEntity.setDesxNome(getChildNodeTextContent(infNFeElement, "dest/xNome"));
    			    fileEntity.setvTotTrib(Double.parseDouble(getChildNodeTextContent(infNFeElement, "total/ICMSTot/vTotTrib")));
    			    fileEntity.setvNF(Double.parseDouble(getChildNodeTextContent(infNFeElement, "total/ICMSTot/vNF")));
    			}
               
                FileEntity savedFileEntity = fileRepository.save(fileEntity);
                
                BinaryFileEntity binaryFileEntity = new BinaryFileEntity();
                binaryFileEntity.setConteudoBinario(file.getBytes());
                binaryFileEntity.setFileEntity(savedFileEntity);
                
                binaryFileRepository.save(binaryFileEntity);
    		} catch (IOException | SAXException | ParserConfigurationException | ParseException e) {
    			return;
    		}
    	});
    	List<FileEntity> fileEntities = fileRepository.findAll();
        Mapper mapper = new Mapper();
        List<FileDto> updatedFiles = fileEntities.stream().map(fe -> mapper.fileEntityToDto(fe)).collect(Collectors.toList());
		return updatedFiles;	
    }
    
 // Função auxiliar para obter o nó filho de um nó pai com um determinado nome de tag
	private Node getChildNodeByTagName(Node parentNode, String tagName) {
	    NodeList nodeList = parentNode.getChildNodes();
	    String[] tags = tagName.split("/");
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node node = nodeList.item(i);
	        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(tags[0])) {
	        	if (tags.length == 1) {
		        	return node;
		        }
	        	NodeList childNodes = node.getChildNodes();
	        	for (int idxChild = 0; idxChild < childNodes.getLength(); idxChild++) {
	        		Node childNode = childNodes.item(idxChild);
	        		if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(tags[1])) {
	        			if (tags.length == 2) {
	        				return childNode;
	        			}
	        			NodeList thirdLevelChildNodes = childNode.getChildNodes();
	        			for (int idxThirdLevelChild = 0; idxThirdLevelChild < thirdLevelChildNodes.getLength(); idxThirdLevelChild++) {
	        				Node thirdLevelChildNode = thirdLevelChildNodes.item(idxThirdLevelChild);
	        				if (thirdLevelChildNode.getNodeType() == Node.ELEMENT_NODE && thirdLevelChildNode.getNodeName().equals(tags[2])) {
	        					return thirdLevelChildNode;
	        				}
	        			}
	        		}
	        	}
	            
	        }
	    }
	    return null;
	}

	// Função auxiliar para obter o conteúdo de texto de um nó filho de um nó pai com um determinado nome de tag
	private String getChildNodeTextContent(Element parentElement, String tagName) {
	    Node node = getChildNodeByTagName(parentElement, tagName);
	    return node != null ? node.getTextContent() : null;
	}
	
	// Função auxiliar para converter uma string de data no formato "yyyy-MM-dd'T'HH:mm:ssXXX" em um objeto Date
	private Date getDateFromString(String dateString) throws ParseException {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	    return dateFormat.parse(dateString);
	}

    public List<FileDto> getAllFiles() {
        List<FileEntity> fileEntities = fileRepository.findAll();
        Mapper mapper = new Mapper();
        List<FileDto> files = fileEntities.stream().map(fileEntity -> mapper.fileEntityToDto(fileEntity)).collect(Collectors.toList());
        return files;  
    }

   
//    public byte[] downloadFile(Long fileId) {
//      
//        java.util.Optional<BinaryFileEntity> optionalBinaryFileEntity = binaryFileRepository.findById(fileId);
//
//        if (optionalBinaryFileEntity.isPresent()) {
//            BinaryFileEntity binaryFileEntity = optionalBinaryFileEntity.get();
//            return binaryFileEntity.getConteudoBinario();
//        } else {
//            
//            throw new NotFoundException("Arquivo não encontrado com o ID fornecido: " + fileId);
//        }
//    }
}
