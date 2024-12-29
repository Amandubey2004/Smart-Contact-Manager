package com.smart.controller;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.helper.Message;
import com.smart.models.Contact;
import com.smart.models.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
//	method for adding common data to response
	
	@ModelAttribute
	public void addCommondata(Model model,Principal principal) {
		String userName=principal.getName();
		System.out.println("USERNAME"+userName);
		User user=userRepository.getUserByUserName(userName);
		System.out.println("USER "+user);
		
		model.addAttribute("user", user);
		
	}
	
//	dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal)
	{
		model.addAttribute("title", "User Dashboard");
		
		return "normal/user_dashboard";
	}
	
//	open add form handler
	
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model)
	{
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	
//	processing add contact form
	
	@PostMapping("/process-contact")
	public String processContact
	(@ModelAttribute Contact contact, Principal principal,HttpSession session) {
	
		
		
	try {
			
		String name=principal.getName();
		User user=this.userRepository.getUserByUserName(name);
		
//		processing and uploading file
//		if(file.isEmpty())
//		{
//			System.out.println("file is empty");
//		}else {
//			contact.setImage(file.getOriginalFilename());
//			
//			File saveFile=new ClassPathResource("static/img").getFile();
//			
//			Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
//			
//			
//			Files.copy(file.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
//			
//			System.out.println("Image uploaded");
//			
//		}
		
		contact.setUser(user);	
		user.getContacts().add(contact);
		this.userRepository.save(user);
		System.out.println("DATA" +contact);
		
		System.out.println("contact added to database");
		
		session.setAttribute("message", new Message("Your contact is added !! Add more", "success"));
		
		
		
	}catch (Exception e) {
		e.printStackTrace();
		session.setAttribute("message", new Message("Something went wrong try again", "danger"));
		
		
	}	
		
		return "normal/add_contact_form";
	}
	
//	show contact handler
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page,Model m,Principal principal) {
		m.addAttribute("title","Show User Contacts" );
		
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
//		currentpage-page
//		Contact Perpage-5
		
		Pageable pageable = PageRequest.of(page, 5);
		
		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(),pageable);
		
		m.addAttribute("contacts",contacts);
	    m.addAttribute("currentPage",page);
	    
	    m.addAttribute("totalPages",contacts.getTotalPages());
		
		return "normal/show_contacts";
	}
	
//	delete contact handler
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid,Model model,HttpSession session)
	{
		Contact contact= this.contactRepository.findById(cid).get();
		
		contact.setUser(null);

		this.contactRepository.delete(contact);
		
		session.setAttribute("message",new Message("contact deleted successfully", "success"));
		
		return "redirect:/user/show-contacts/0";
	}
	
//	update form handler
//	@PostMapping("/update-contact/{cid}")
//	public String updateForm(Model m)
//	{
//		m.addAttribute("title", "Update contact");
//		
//		m.addAttribute("contact", contact);
//	
//		return "normal/update_form";
//	}
	
	
	
	
//	your profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model) 
	{
		model.addAttribute("title","Profile Page" );
		return "normal/profile";
	}

}

