/**
 * Created By: Pujan
 * Date: 2020/1/21 (yyyy/mm/dd)
 */
package com.ishanitech.ipalikawebapp.serviceImpl;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishanitech.ipalikawebapp.dto.LoginDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.RestClientService;
import com.ishanitech.ipalikawebapp.utilities.UserDetailsUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestClientServiceImpl implements RestClientService {
	
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
    public Response<UserDTO> login(LoginDTO requestObject) {
        Response<UserDTO> response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(requestObject, headers);
        ResponseEntity<Response> result = null; 
        try {
        	result = (ResponseEntity<Response>) restClient.postForEntity(baseUrl + loginUrl, entity, Response.class);
        	response = objectMapper.convertValue(result.getBody(), new TypeReference<Response<UserDTO>>() {
			});
        	response.getData().setToken(result.getHeaders().getFirst("Authorization"));
        } catch(HttpStatusCodeException sce) {
        	throw new BadCredentialsException("Bad Credentials");
        } catch(RestClientException rce) {
        	log.info("INSIDE LOGIN CALL: " + rce.getMessage());
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
        	log.error("ERROR CODE: " + sce.getStatusCode());
        	try {
                response = objectMapper.readValue(sce.getResponseBodyAsString(), responseType);
//        		Response<?> errorResponse = objectMapper.readValue(sce.getResponseBodyAsString(),
//        				objectMapper.getTypeFactory()
//        				.constructParametricType(Response.class,
//        						objectMapper.getTypeFactory().constructParametricType(List.class, parameterClasses))
//        				);
//        		CustomExceptionThrowerUtil.throwException(response.getStatus(), response.getMessage());
            } catch (IOException e) {
                log.error("ERROR: " + e.getMessage());
            }
        } catch(RestClientException rce) {
        	log.error("ERROR MSG: " + rce.getMessage());
        } catch(IOException ioEx) {
        	log.error("ERROR: " + ioEx.getMessage());
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
