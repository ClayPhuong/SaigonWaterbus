package com.lab4.saigonwaterbus.Controller.Admin;

import java.time.LocalDate;
import java.util.List;

import com.lab4.saigonwaterbus.DAO.*;
import com.lab4.saigonwaterbus.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminControllerView {
	@Autowired
	TaiXeDAO taiXeDao;
	@Autowired
	ChuyenDAO chuyenDao;
	@Autowired
	TuyenDAO tuyenDao;
	@Autowired
	HoaDonDAO HoaDonDao;
	@Autowired
	VeDAO VeDao;
	@Autowired
	private TauDAO daoTau;
	@Autowired
	private TuyenDAO daoTuyen;

	@GetMapping("/indexAdmin")
	public String index(Model model) {
		model.addAttribute("viewad", "/admin/banner.html");
		return "admin/home";
	}

	@GetMapping("/tool/{toolName}")
	public String yourHandlerMethod(@PathVariable String toolName, Model model,
									@RequestParam(required = false) String keyword,
									@RequestParam(name = "p", required = false, defaultValue = "0") Integer p,
									@ModelAttribute("taiXe") TaiXe taiXe, @Validated @ModelAttribute("chuyen") Chuyen chuyen,
									@Validated @ModelAttribute("Ve") Ve ve, @ModelAttribute("hoaDon") HoaDon hoaDon,
									@ModelAttribute("idChuyen") String idChuyen) {
		if ("tools".equals(toolName)) {
			model.addAttribute("viewad", "/admin/tools.jsp");
		} else if ("add".equals(toolName)) {
			model.addAttribute("viewad", "/admin/addticket.jsp");
		} else if ("update".equals(toolName)) {
			model.addAttribute("viewad", "/admin/update.jsp");
		} else if ("routeship".equals(toolName)) {
			if (keyword == null || keyword.isEmpty()) {
				model.addAttribute("listTuyen", daoTuyen.findAll());
			} else {
				model.addAttribute("listTuyen", daoTuyen.searchByTenTuyen(keyword));
			}
			model.addAttribute("tuyen", new Tuyen());
			model.addAttribute("listTau", daoTau.findByTauStatus());
			model.addAttribute("viewad", "/admin/ShipRoutes/routeship.html");
		} else if ("toolship".equals(toolName)) {
			if (keyword == null || keyword.isEmpty()) {
				model.addAttribute("listTau", daoTau.findByTauStatus());
			} else {
				model.addAttribute("listTau", daoTau.searchByKeyword(keyword));
			}
			model.addAttribute("tau", new Tau());
			model.addAttribute("viewad", "/admin/Ship/toolship.html");
		} else if ("driver".equals(toolName)) {
			System.out.println("keyword: " + keyword);
			List<TaiXe> listTaiXe;
			if (keyword == null || keyword.isEmpty()) {
				listTaiXe = taiXeDao.findAllByStatusTrue();
			} else {
				listTaiXe = taiXeDao.findByHoTenOrSoDT(keyword);
			}
			model.addAttribute("listTaiXe", listTaiXe);
			model.addAttribute("viewad", "/admin/driver/driver.html");
		} else if ("trip".equals(toolName)) {
			if (keyword == null || keyword.isEmpty()) {
				LocalDate dateNow = LocalDate.now();
				model.addAttribute("listChuyen", chuyenDao.findChuyenByDate(dateNow));
			} else {
				LocalDate date = LocalDate.parse(keyword);
				model.addAttribute("listChuyen", chuyenDao.findChuyenByDate(date));
			}

			model.addAttribute("listTuyen", tuyenDao.findAll());
			model.addAttribute("listTaiXe", taiXeDao.findAll());
			if (idChuyen != null && !idChuyen.isEmpty()) {
				Long ic_chuyen = Long.parseLong(idChuyen);
				List<Ve> listVeChuyen = VeDao.findByChuyenId(ic_chuyen);
				model.addAttribute("listVeChuyen", listVeChuyen);
				System.out.println("list ve chuyn: " + listVeChuyen.size());
			}
			model.addAttribute("viewad", "/admin/trip/trip.html");
		} else if ("ve".equals(toolName)) {
			System.out.println("keyword: " + keyword);
			List<Ve> listVe;
			if (keyword == null || keyword.isEmpty()) {
				listVe = VeDao.findAll();
			} else {
				listVe = VeDao.findBytenGheOrid(keyword);
			}
			model.addAttribute("listVe", listVe);
			model.addAttribute("viewad", "/admin/diem/ve.html");
		}

		else if ("hoadon".equals(toolName)) {
			List<HoaDon> listHoaDon;
			if (keyword == null || keyword.isEmpty()) {
				listHoaDon = HoaDonDao.findAll();
			} else {
				listHoaDon = HoaDonDao.findBytenGheOridAccount(keyword);
			}
			model.addAttribute("listHoaDon", listHoaDon);
			model.addAttribute("viewad", "/admin/hoadonadmin.html");
		}

		else {
			return "admin/home";
		}
		return "admin/home";
	}

}