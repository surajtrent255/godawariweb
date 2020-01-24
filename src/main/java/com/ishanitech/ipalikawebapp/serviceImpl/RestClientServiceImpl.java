/**
 * Created By: Pujan
 * Date: 2020/1/21 (yyyy/mm/dd)
 */
package com.ishanitech.ipalikawebapp.serviceImpl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishanitech.ipalikawebapp.dto.*;
import com.ishanitech.ipalikawebapp.service.RestClientService;
import com.ishanitech.ipalikawebapp.utilities.UserDetailsUtil;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RestClientServiceImpl implements RestClientService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(RestClientServiceImpl.class);
    private RestTemplate restClient;
    private String partialRestUrl;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Value("${webservice.login-url}")
    private String loginUrl;

    
    private String baseUrl;
    
    //@Autowired is optional. Even when @Autowired is not specified
    //Spring boot automatically gets the bean for constructor injection
    @Autowired
    public RestClientServiceImpl(RestTemplate restTemplate,
    		@Value("${webservice.base-url}") String baseUrl,
    		@Value("${webservice.partialRestUrl}") String partialRestUrl) {
        this.restClient = restTemplate;
        this.partialRestUrl = partialRestUrl;
        this.baseUrl = baseUrl;
    }



    @Override
    public Response<?> login(Object requestObject, JavaType responseType) {
        Response<UserDTO> response = null;
        HttpHeaders headers = new HttpHeaders();
        LoginDTO loginData = (LoginDTO) requestObject;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(loginData, headers);
        ResponseEntity<String> result = null; 
        try {
        	result = restClient.exchange(baseUrl + loginUrl, HttpMethod.POST, entity, String.class);
        	response = objectMapper.readValue(result.getBody(), responseType);
        	
        } catch(HttpStatusCodeException sce) {
        	throw new BadCredentialsException("Bad Credentials");
        } catch(RestClientException rce) {
        	LOGGER.info("INSIDE LOGIN CALL: " + rce.getMessage());
        } catch(IOException ioEx) {
        	log.error("INSIDE LOGIN IOEXCEPTION: " + ioEx.getMessage());
        }
        return response;
    }

    /**
     *
     * @param url
     * @param responseType
     * @return
     */


    @Override
    public Response<?> getData(String url, JavaType responseType) {
        Response<?> response = null;
        URI completeUrl = URI.create(baseUrl + url);
        HttpHeaders headers = new HttpHeaders();
        UserDTO loggedInUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(UserDetailsUtil.isLoggedIn(authentication)) {
            loggedInUser = (UserDTO) authentication.getPrincipal();
//            headers.add("Authorization", "Bearer " + loggedInUser.getToken());
        }
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters" , headers);
        ResponseEntity<String> responseEntity = null;
        try {
        	responseEntity = restClient.exchange(completeUrl, HttpMethod.GET, entity, String.class);
        	response = objectMapper.readValue(responseEntity.getBody(), responseType);
        } catch(HttpStatusCodeException sce) {
        	LOGGER.error("ERROR CODE: " + sce.getStatusCode());
        	try {
                response = objectMapper.readValue(sce.getResponseBodyAsString(), responseType);
//        		Response<?> errorResponse = objectMapper.readValue(sce.getResponseBodyAsString(),
//        				objectMapper.getTypeFactory()
//        				.constructParametricType(Response.class,
//        						objectMapper.getTypeFactory().constructParametricType(List.class, parameterClasses))
//        				);
//        		CustomExceptionThrowerUtil.throwException(response.getStatus(), response.getMessage());
            } catch (IOException e) {
                LOGGER.error("ERROR: " + e.getMessage());
            }
        } catch(RestClientException rce) {
        	LOGGER.error("ERROR MSG: " + rce.getMessage());
        } catch(IOException ioEx) {
        	LOGGER.error("ERROR: " + ioEx.getMessage());
        }
        
        return response;
    }
    
    /*

    @Override
    public   Response<?> postData(String url, Object body, JavaType responseType){
        String completeUrl = baseUrl + partialRestUrl + url;
        Response<?> response = null;
        String stringRequest = "";
        HttpHeaders headers = new HttpHeaders();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO loggedInUser = null;
        if(UserDetailsUtil.isLoggedIn(authentication)) {
            loggedInUser = (UserDTO) authentication.getPrincipal();
            headers.add("Authorization", "Bearer " + loggedInUser.getToken());
        }
         
        try{
            stringRequest = objectMapper.writeValueAsString(body);
        }catch (JsonProcessingException jsonProcessingException) {
//            LOGGER.info(jsonProcessingException.getMessage());
        } 

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(stringRequest,headers);
        ResponseEntity<String> result = null;
        try {
        	 result = restClient.exchange(completeUrl, HttpMethod.POST, entity, String.class);
        	 response = objectMapper.readValue(result.getBody(), responseType);
        } catch(HttpStatusCodeException sce) {
//        	LOGGER.info("INSIDE POSTDATA HTTPSTATUSCODE EXCEPTION");
        	LOGGER.error("STATUS CODE: " + sce.getStatusCode());
        	 try {
                 response = objectMapper.readValue(sce.getResponseBodyAsString(), responseType);
             } catch (IOException e) {
                
             }
        } catch(RestClientException rce) {
//        	LOGGER.info("INSIDE RESTCLIENT ERROR OF POSTDATA");
        	LOGGER.error("ERROR MSG: " + rce.getMessage());
        } catch(IOException ioEx) {
//        	LOGGER.info("INSIDE IOEXCEPTION OF POST DATA");
        	LOGGER.error("ERROR MSG: " + ioEx.getMessage());
        }
        
        return response;
    }

    @Override
    public Response<?> putData(String url, Object body, JavaType responseType) {
        String completeUrl = baseUrl + partialRestUrl + url;
        Response<?> response = null;
        HttpHeaders headers = new HttpHeaders();
        UserDTO loggedInUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(UserDetailsUtil.isLoggedIn(authentication)) {
            loggedInUser = (UserDTO) authentication.getPrincipal();
            headers.add("Authorization", "Bearer " + loggedInUser.getToken());
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = null;
        try{
        	result = restClient.exchange(completeUrl, HttpMethod.PUT, entity, String.class);
        	response = objectMapper.readValue(result.getBody(), responseType);
        } catch(HttpStatusCodeException sce) {
//        	LOGGER.info("INSIDE HTTPSTATUSCODE EXCEPTION OF PUT DATA");
        	LOGGER.error("STATUS CODE: " + sce.getStatusCode());
        	try {
                response = objectMapper.readValue(sce.getResponseBodyAsString(), responseType);
            } catch (IOException e) {
//                LOGGER.info("INSIDE IOEXCEPTION OF HTTPSTATUSCODEEXCEPTION OF POSTDATA");
                LOGGER.error("ERROR MSG: " + e.getMessage());
            }
        } catch(RestClientException rce) {
//        	LOGGER.info("INSIDE RESTCLIENT ERROR OF RESTCLIENTEXCEPTION");
        	LOGGER.error("ERROR MSG: " + rce.getMessage());
        } catch(IOException ioEx) {
//        	LOGGER.info("INSIDE IOEXCEPTION OF PUTDATA");
        	LOGGER.error("ERROR MSG: " + ioEx.getMessage());
        }
        
        
        return response;
    }

    @Override
    public Response<?> postMultipartFiles(String url, MultipartFile[] files, String[] fileNames, JavaType responseType) {
        String commpleteUrl = baseUrl + partialRestUrl + url;
        Response<String> response = null;
        //Create http header and set content type.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(UserDetailsUtil.isLoggedIn(auth)) {
            headers.add("Authorization", "Bearer " + ((UserDTO) auth.getPrincipal()).getToken());
        } else {
            try {
                throw new Exception("Not authorized use.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        //Create request body and add payload to request body.
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        List<String> filesName = new ArrayList<>();
        String temporaryFileName = "";
        try {
            for(MultipartFile multipartFile: files) {
                temporaryFileName = "/tmp/" + multipartFile.getOriginalFilename();
                filesName.add(temporaryFileName);
                File file = new File(temporaryFileName);
                multipartFile.transferTo(file);
                body.add("file", new FileSystemResource(file));
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        body.add("fileName", fileNames);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = null;
        //Call rest api and get response from rest.
        try {
        	responseEntity = restClient.exchange(commpleteUrl, HttpMethod.POST, entity, String.class);
        	response = objectMapper.readValue(responseEntity.getBody(), responseType);
        } catch(HttpStatusCodeException sce) {
//        	LOGGER.info("INSIDE HTTPSTATUS CODE EXCEPTION OF POST MULTIPART FILE");
        	LOGGER.error("STATUS CODE: " +sce.getStatusCode());
        	try {
                response = objectMapper.readValue(sce.getResponseBodyAsString(), responseType);
            }catch(IOException ex) {

            }
        } catch(RestClientException rce) {
//        	LOGGER.info("INSIDE REST CLIENT ERROR OF POST MULTIPART FILE EXCEPTION");
        	LOGGER.error("EROR MSG: " + rce.getMessage());
        } catch(IOException ioEx) {
//        	LOGGER.info("INSIDE IOEXCEPTION OF POST MULTIPART FILE EXCEPTION");
        	LOGGER.error("EROR MSG: " + ioEx.getMessage());
        }
        
        //delete files placed temporary folder
        filesName.forEach(fileName -> {
            File file = new File(fileName);
            file.delete();
        });
        
        return response;
	}
	@Override
	public Response<?> deleteData(String url, Object body, JavaType responseType) {
		String completeUrl = baseUrl + partialRestUrl + url;
        Response<?> response = null;
        HttpHeaders headers = new HttpHeaders();
        UserDTO loggedInUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(UserDetailsUtil.isLoggedIn(authentication)) {
            loggedInUser = (UserDTO) authentication.getPrincipal();
            headers.add("Authorization", "Bearer " + loggedInUser.getToken());
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = null;
        try {
        	result = restClient.exchange(completeUrl, HttpMethod.DELETE, entity, String.class);
        	response = objectMapper.readValue(result.getBody(), responseType);
        } catch(HttpStatusCodeException sce) {
//        	LOGGER.info("INSIDE DELETE DATA HTTPSTATUSCODEEXCEPTION");
        	LOGGER.error("STATUS CODE: " + sce.getStatusCode());
        	 try {
                 response = objectMapper.readValue(sce.getResponseBodyAsString(), responseType);
             } catch (IOException e) {
//                LOGGER.info("DELETEDATA IOEXCEPTION" + e.getMessage());
             }
        } catch(RestClientException rce) {
//        	LOGGER.info("INSIDE RESTCLIENT EXCEPTION OF DELETE DATA");
        	LOGGER.error("ERROR MESSAGE: " + rce.getMessage());
        } catch(IOException ioEx) {
//        	LOGGER.info("IOEXCEPTION OF DELETE DATA: " +ioEx.getMessage());
        }
        
        return response;
	}

*/
   

}
