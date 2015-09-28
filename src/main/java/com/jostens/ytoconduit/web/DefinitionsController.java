package com.jostens.ytoconduit.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jostens.config.YTOConfig;
import com.jostens.ytoconduit.model.DesignPublishResult;
import com.jostens.ytoconduit.model.ImageUploadResult;
import com.jostens.ytoconduit.model.View;
import com.jostens.ytoconduit.model.YTODesignDefinition;
import com.jostens.ytoconduit.model.YTOImageDefinition;
import com.jostens.ytoconduit.service.DefinitionsService;

@Controller
@RequestMapping("/conduitservices")
public class DefinitionsController {
	private static Logger LOG = LoggerFactory.getLogger(DefinitionsController.class);
	
	@Autowired
	YTOConfig ytoConfig;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private DefinitionsService definitionsService;
	
	
    @RequestMapping("/home")
    public String home(
    		@RequestParam(value="name", required=false, defaultValue="World") String name,
    		@RequestParam(value="designId", required=false, defaultValue="9") String designId,
    		Model model) {
        model.addAttribute("name", name);
        YTODesignDefinition designDefinition = definitionsService.getDesignDefinition(Long.valueOf(designId));
        model.addAttribute("designDefinition", designDefinition);
        return "greeting";
    }
    
    @RequestMapping("/error")
    public String errorMethod(Model model) {
        return "error";
    }
	
	@RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }    
	
	@JsonView(View.DefinitionSummary.class)
    @ResponseBody
	@RequestMapping(value = "/getdesigndefinition.json")
	public YTODesignDefinition getDesignDefinition(Model model, @RequestParam(required = true) String designId) {
		return definitionsService.getDesignDefinition(Long.valueOf(designId));
	}
	
    @ResponseBody
	@RequestMapping(value = "/getdesigndefinitionoffline.json")
	public FileSystemResource getDesignDefinitionOffline(Model model, @RequestParam(required = true) String designId) {
    	try {
    		FileSystemResource fsr = new FileSystemResource("c:/dev/workspaces/sts.general/YTOConduit/src/main/resources/public/designDefinition.json");
    		LOG.info("content length :: {} ", fsr.contentLength());
    		return fsr;
    	}catch(IOException error) {
    		LOG.info("Error reading design definition offline {}", error);
    	}
    	return null;	
	}
    
    @ResponseBody
    @RequestMapping(value = "/publishdesigndefinition.json", method = RequestMethod.POST)
    public DesignPublishResult publishDesignDefinition(Model model, @RequestBody String designDefinition) {
    	LOG.info("publish the design");
    	DesignPublishResult result = new DesignPublishResult();
    	result.setStatus(Boolean.TRUE);
    	result.setMessage("The design definition was published. The previous version was backed up.");
    	
    	//1. rename current defintion file
//    	File currentDefinition = new File("c:/dev/workspaces/sts.general/YTOConduit/src/main/resources/public/designDefinition.json");
//    	boolean renameWorked = currentDefinition.renameTo(new File("c:/dev/workspaces/sts.general/YTOConduit/src/main/resources/public/designDefinition_version02.json"));
//    	if(!renameWorked) {
//    		result.setStatus(Boolean.FALSE);
//    		result.setMessage("Could not publish design definition.");
//    		result.setErrorMessage("Could not rename original version of design definition.");
//    		return result;
//    	}

    	File newDefinition = new File("c:/dev/workspaces/sts.general/YTOConduit/src/main/resources/public/designDefinition.json");
    	try {
    		FileUtils.writeStringToFile(newDefinition, designDefinition);    	
    	} catch(IOException error) {
    		result.setStatus(Boolean.FALSE);
    		result.setMessage("Could not publish design definition.");
    		result.setErrorMessage(error.getMessage());
    		return result;
    	}
    	
    	return result;
    }
    
    
    
    
    @RequestMapping(value = "/getdesignimage")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getDesignImage(@RequestParam(required=true) String path) {
    	try {    		
    		FileInputStream in = new FileInputStream(path);
    		ByteArrayResource bar = new ByteArrayResource(IOUtils.toByteArray(in));
    		LOG.info("content length :: {} ", bar.contentLength());
    		return ResponseEntity.ok().contentLength(bar.contentLength()).contentType(MediaType.IMAGE_JPEG).body(bar);
    	}catch(IOException error) {
    		LOG.info("Error getting design image. Return default image. {}", error);
    		try {
	    		FileInputStream in2 = new FileInputStream("c:/dev/workspaces/sts.general/YTOConduit/library/Lighthouse.jpg");
	    		ByteArrayResource bar2 = new ByteArrayResource(IOUtils.toByteArray(in2));
	    		LOG.info("content length :: {} ", bar2.contentLength());
	    		return ResponseEntity.ok().contentLength(bar2.contentLength()).contentType(MediaType.IMAGE_JPEG).body(bar2);
	    	}catch(IOException error2){
    			//We can't even load the default image. This plan backfired.
    			return null;
    		}
    	}
    }
    /*
    @RequestMapping(value = "/getdesignimage2")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getDesignImage2(String uri) {
    	try {
    		FileSystemResource fsr = new FileSystemResource("c:/dev/workspaces/sts.general/YTOConduit/library/Lighthouse.jpg");
    		LOG.info("content length :: {} ", fsr.contentLength());
    		return ResponseEntity.ok().contentLength(fsr.contentLength()).contentType(MediaType.IMAGE_JPEG).body(fsr);
    	}catch(IOException error) {
    		LOG.info("Error reading design definition offline {}", error);
    	}
    	return null;
    }
 	*/
	
	@ResponseBody
	@RequestMapping(value = "/getcategorydefinition.json")
	public FileSystemResource getCategoryDefinition(Model model, @RequestParam(required = true) String designId) {
		
		FileSystemResource fsr = new FileSystemResource("c:/dev/workspaces/sts.general/YTOConduit/src/main/resources/public/categories.json");
		return fsr;
	
	}
	
	@JsonView(View.ImageList.class)
	@ResponseBody
	@RequestMapping("/getimagelist")
	public List<YTOImageDefinition> getYTOImageList(
				Model model, 
				@RequestParam(required = true) String designId, 
				@RequestParam(required = true) String categoryId) {
		return definitionsService.getImageList(Long.valueOf(designId), Long.valueOf(categoryId));
	}
    
    
    @ResponseBody
	@RequestMapping(value = "/getclipartimagedefinition.json")
	public Object getImageDefinition(Model model, @RequestParam(required = true) String designId) {

		String jsonImageDefinition = "Get your clipart image definition here.";
		LOG.info("What is context class :: {}", context.getClass());
		LOG.info("Read from applicaiton context :: {}, {}", context.containsBean("YTOConfig"), context.containsBean("YTOConfig234"));
		ytoConfig.getConfigurationListeners();
		
		return jsonImageDefinition;
	}
    
    @JsonView(View.ImageUploadSummary.class)
    @ResponseBody
	@RequestMapping(value = "/intermediateupload.json")
	public ImageUploadResult intermediateUploadMethod(Model model,
        @RequestParam(value="file", required=true) MultipartFile file,
        @RequestParam(value="designId", required=false, defaultValue="9") String designId,
        @RequestParam(value="imageName", required=false, defaultValue="image001.jpg") String imageName,
        @RequestParam(value="categoryId", required=false, defaultValue="2332") String categoryId
        ){
    
    	ImageUploadResult uploadResult = new ImageUploadResult();
    	uploadResult.setMessage("noresult");
    	
    	//Create path to new upload location.
    	LOG.info("Upload details {}, {}, {}", designId, categoryId, imageName);
    	StringBuilder path = new StringBuilder();
    	path.append("library\\");
    	path.append(designId);
    	path.append("\\");
    	path.append(categoryId);    	

    	File storage = new File(path.toString());
    	
    	//Create directories. Bail if we can't.
    	if(!storage.exists()) {
			try {
				storage.mkdirs();				
			}catch(SecurityException error) {
				uploadResult.setStatus(Boolean.FALSE);
				uploadResult.setMessage("Image upload failed. Could not create directory to store file.");
				uploadResult.setErrorMessage(error.getMessage());
				return uploadResult;
			}
    	}
    	
    	//Once the directory is created, add the image name to the path.
		path.append("\\");
    	path.append(imageName);
    	
    	//Create uri that will be added to upload result so we can reference the image later. Bail if we can't create it.
    	URI uri = null;
    	try {
    		uri =  new URIBuilder().setHost("localhost")
    				.setScheme("http")
    				.setPort(9000)
    				.setPath("/conduitservices/getdesignimage")
    				.setParameter("path", path.toString())
    				.build();
    	} catch(URISyntaxException error) {
    		uploadResult.setStatus(Boolean.FALSE);
    		uploadResult.setMessage("Image was not uploaded. Could not build locator");
    		uploadResult.setErrorMessage(error.getMessage());
    		return uploadResult;
    	}
    	
    	if(file.isEmpty()) {
    		uploadResult.setStatus(Boolean.FALSE);
	        uploadResult.setMessage("Upload failed. File was empty");
	        return uploadResult;
    	}
    	
    	//Write the file to disk. Populate result.
    	try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path.toString())));
            stream.write(bytes);
            stream.close();
            uploadResult.setStatus(Boolean.TRUE);
            uploadResult.setMessage("Upload complete.");
            uploadResult.setUrl(uri.toString());
            uploadResult.setCdnUrl(uri.toString());
        } catch (IOException e) {
        	 uploadResult.setStatus(Boolean.FALSE);
             uploadResult.setMessage("Upload failed.");
             uploadResult.setErrorMessage(e.getMessage());
        }     
    	
		return uploadResult;
	}

}
