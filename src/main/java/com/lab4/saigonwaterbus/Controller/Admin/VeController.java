package com.lab4.saigonwaterbus.Controller.Admin;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.lab4.saigonwaterbus.DAO.VeDAO;
import com.lab4.saigonwaterbus.Model.Ve;

@Controller
public class VeController {
	@Autowired
	VeDAO VeDao;

	@PostMapping("/add-ve")
	public String addDriver(Ve ve) throws IOException {
		//TODO: process POST request
		String annouce = "Create ve success!";
		VeDao.save(ve); 
		return "redirect:/tool/ve?annouce="+annouce;
	}

	@PostMapping("/update-ve")
	public String updateDriver(Model model, @ModelAttribute Ve ve) {
		//TODO: process POST request
		System.out.println("ve id: " + ve.getId());
		if (VeDao.existsById(ve.getId())) {
			String annouce = "Update ve success!";
			VeDao.save(ve); 
			return "redirect:/tool/ve?annouce="+annouce;
		} else {
			throw new RuntimeException("Không tồn tại vé có id: " + ve.getId());
		}
	}
	
	@PostMapping("/delete-ve")
	public String deleteDriver(Model model, @ModelAttribute Ve ve) {
		//TODO: process POST request
		System.out.println("ve id delete: " + ve.getId());
		if (VeDao.existsById(ve.getId())) {
			String annouce = "Delete ve success!";
			ve.setStatus(false);
			VeDao.save(ve); 
			return "redirect:/tool/ve?annouce="+annouce;
		} else {
			throw new RuntimeException("Không tồn tại vé có id: " + ve.getId());
		}
	}
	
}




