package com.jostens.ytoconduit.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    	FileSystemResource fsr = new FileSystemResource("c:/dev/workspaces/sts.general/YTOConduit/src/main/resources/public/designDefinition.json");
		return fsr;
	}
    
    @RequestMapping(value = "/getdesignimage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStream> getDesignImage(String uri) {
    	FileSystemResource fsr = new FileSystemResource("c:/dev/workspaces/sts.general/YTOConduit/library/Koala.jpg");
    	ResponseEntity.BodyBuilder responseBody = ResponseEntity.ok();
    	ResponseEntity<InputStream> response = null;
    	try {
    		responseBody.body(fsr.getInputStream());
    		responseBody.contentLength(fsr.contentLength());
    		response = ResponseEntity.ok().contentLength(fsr.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(fsr.getInputStream());
    	}catch(IOException error) {
    		LOG.info(error.getMessage());
    	}
    	
        return response;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getcategorydefinition.json")
	public FileSystemResource getCategoryDefinition(Model model, @RequestParam(required = true) String designId) {
		
		FileSystemResource fsr = new FileSystemResource("c:/dev/workspaces/sts.general/YTOConduit/src/main/resources/public/categories.json");
		return fsr;
		/*
		JsonFactory jsonFactory = new JsonFactory();
		
		try {
			File f = new File("C:/dev/workspaces/sts3.6.4/YTOConduit/src/main/resources/public/categories.json");
			return f;
			//f.isFile()
		}catch (Exception e){
			LOG.info("Error reading file.");
		}
		try {
			JsonParser jp = jsonFactory.createParser(new File("C:/dev/workspaces/sts3.6.4/YTOConduit/src/main/resources/public/categories.json"));
			LOG.info("Text length {}", String.valueOf(jp.getTextLength()));
			while (!jp.isClosed()) {
			    // read the next element
			    JsonToken token = jp.nextToken();
			    // if the call to nextToken returns null, the end of the file has been reached
			    if (token == null)
			        break;			 
			}
			return jp.getValueAsString();
		} catch (Exception e) {
			return "Difficult reading json file";
		}
		*/
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
    
    @ResponseBody
	@RequestMapping(value = "/intermediateupload.json")
	public String intermediateUploadMethod(Model model,
        @RequestParam(value="file", required=true) MultipartFile file,
        @RequestParam(value="designId", required=false, defaultValue="9") String designId,
        @RequestParam(value="imageName", required=false, defaultValue="image001.jpg") String imageName,
        @RequestParam(value="categoryId", required=false, defaultValue="2332") String categoryId,
        @RequestParam(value="categoryName", required=false, defaultValue="ActivityD") String categoryName
        ){
    
    	String uploadResult = "{\"result\":345}";
    	LOG.info("Upload details {}, {}, {}", imageName, categoryId, categoryName);
     if (!file.isEmpty()) {
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File("library\\" + imageName)));
            stream.write(bytes);
            stream.close();
            uploadResult = "{\"result\": \"Upload complete.\"}";
        } catch (Exception e) {
        	uploadResult = "{\"result\": \"Upload failed.\"}";
        }
    } else {
    	uploadResult = "{\"result\": \"Upload failed. File was empty.\"}";
    }
    	
		return uploadResult;
	}

}
