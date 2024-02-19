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

    public void uploadFile(MultipartFile file) throws IOException, SAXException, ParserConfigurationException, ParseException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo está vazio.");
        }

        if (!file.getContentType().equals("application/xml")) {
            throw new IllegalArgumentException("O arquivo não é um XML.");
        }
        
        
        InputStream inputStream = file.getInputStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("/nfeProc/NFe/infNFe/");
        FileEntity fileEntity = new FileEntity(null, null, null, null, null, null, null, null, null, null);
        for (int temp = 0; temp < nodeList.getLength(); temp++){
        	Node node = nodeList.item(temp);
        	if(node.getNodeType() == Node.ELEMENT_NODE){
        		Element element = (Element) node;
        		fileEntity.setFileId(element.getAttribute("id"));
        		String dataEmissao = element.getElementsByTagName("ide/dhEmi").item(0).getTextContent();
        		String pattern = "yyyy-MM-dd'T'HH:mm:ssXXX";
        		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        		Date date = dateFormat.parse(dataEmissao);		
        		fileEntity.setDhEmi(date);
        		String nNF = element.getElementsByTagName("ide/nNF").item(0).getTextContent();
        		fileEntity.setnNF(nNF);
        		String cUF = element.getElementsByTagName("ide//cUF").item(0).getTextContent();
        		fileEntity.setcUF(cUF);
        		String emitCNPJ = element.getElementsByTagName("emit/CNPJ").item(0).getTextContent();
        		fileEntity.setCNPJ(emitCNPJ);
        		String emitXfant = element.getElementsByTagName("emit/xFant").item(0).getTextContent();
        		fileEntity.setxFant(emitXfant);
        		String destCnpj = element.getElementsByTagName("dest/CNPJ").item(0).getTextContent();
        		fileEntity.setDesCNPJ(destCnpj);
        		String destXnome = element.getElementsByTagName("dest/xNome").item(0).getTextContent();
        		fileEntity.setDesxNome(destXnome);
        		String vTotTrib = element.getElementsByTagName("vTotTrib").item(0).getTextContent();
        		fileEntity.setvTotTrib(Double.parseDouble(vTotTrib));
        		String vNF = element.getElementsByTagName("vNF").item(0).getTextContent();
        		fileEntity.setvNF(Double.parseDouble(vNF));	
        	}
        }
       
        FileEntity savedFileEntity = fileRepository.save(fileEntity);
        
        BinaryFileEntity binaryFileEntity = new BinaryFileEntity();
        binaryFileEntity.setConteudoBinario(file.getBytes());
        binaryFileEntity.setFileEntity(savedFileEntity);
        
        binaryFileRepository.save(binaryFileEntity);
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
