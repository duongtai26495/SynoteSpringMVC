package com.duongtai.syndiary.controllers;

import com.duongtai.syndiary.configs.Snippets;
import com.duongtai.syndiary.entities.*;
import com.duongtai.syndiary.services.impl.StorageServiceImpl;
import com.duongtai.syndiary.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;

import static com.duongtai.syndiary.configs.MyUserDetail.getUsernameLogin;

@CrossOrigin
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    StorageServiceImpl storageService;

    @Autowired
    UserServiceImpl userService;

    


    @PutMapping("change_password")
    public ResponseEntity<ResponseObject> updatePasswordByUsername(@RequestBody User user){
    	if(userService.updatePassword(user.getPassword())) {
    		return ResponseEntity.status(HttpStatus.OK).body(
        			new ResponseObject(Snippets.SUCCESS, Snippets.PASSWORD_UPDATED, null)
        			);
    	}
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
    			new ResponseObject(Snippets.FAILED, Snippets.HAVE_ERROR, null)
    			);
    }

    @PostMapping("upload_image/{username}")
    public ResponseEntity<ResponseObject> uploadImageWithUsername(@RequestParam("image") MultipartFile file, @PathVariable String username){
    	String filename = "";
		if(username.equalsIgnoreCase(getUsernameLogin())){
			filename = storageService.storeFile(file, username);
			if(filename != null) {
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseObject(Snippets.SUCCESS, Snippets.UPLOAD_PROFILE_IMAGE_SUCCESS, filename)
				);
			}
		}

    		return ResponseEntity.status(HttpStatus.OK).body(
	    			new ResponseObject(Snippets.FAILED, Snippets.STORE_FILE_FAILED, filename)
	    			);
    			
    }

    @PostMapping("upload_image")
    public ResponseEntity<ResponseObject> uploadImage(@RequestParam("image") MultipartFile file){
    	String filename = storageService.storeFile(file, "noname");
		if(filename != null) {
			return ResponseEntity.status(HttpStatus.OK).body(
	    			new ResponseObject(Snippets.SUCCESS, Snippets.UPLOAD_IMAGE_SUCCESS, filename)
	    			);
		}
		return ResponseEntity.status(HttpStatus.OK).body(
    			new ResponseObject(Snippets.FAILED, Snippets.STORE_FILE_FAILED, null)
    			);

    }

    @GetMapping("images/{username}")
    public ResponseEntity<byte[]> readUserImage (@PathVariable String username){
        return storageService.readProfileImage(username);
    }

    
    @GetMapping("update/{username}")
    public ModelAndView update_view(ModelMap model, @PathVariable String username) {
    	User user = userService.findByUsername(username);
    	
    	if(username.equalsIgnoreCase(getUsernameLogin())) {
    		if(user != null) {
    			model.addAttribute("title", String.format(Snippets.UPDATE_USER,user.getFull_name()));
        		model.addAttribute("user", user);
            	return new ModelAndView("user/update_user",model);
        	}
    	}
    	
    	return new ModelAndView("redirect:/");
    }

    @PostMapping("save_update")
    public ModelAndView save_update(@ModelAttribute User user, ModelMap model) {
    	System.out.println("User full name: "+user.getFull_name());
    	System.out.println("User email: "+user.getEmail());
    	System.out.println("User gender: "+user.getGender());
    	System.out.println("User image: "+user.getProfile_image());
    	System.out.println("User username: "+user.getUsername());
    	if(user != null) {
    		user.setId(userService.findByUsername(user.getUsername()).getId());
    		if(userService.editByUsername(user) != null) {
    			model.addAttribute("user", ConvertEntity.convertToDTO(userService.findByUsername(user.getUsername())));
    			model.addAttribute("result", "success");
    			return new ModelAndView("redirect:/user/update/"+user.getUsername(),model);
    		}
    		
    	}
    	model.addAttribute("result", "failed");
    	return new ModelAndView("redirect:/user/update/"+user.getUsername(),model);
    }



}
