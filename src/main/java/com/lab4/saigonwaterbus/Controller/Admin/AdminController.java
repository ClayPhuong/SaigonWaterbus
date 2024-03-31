package com.lab4.saigonwaterbus.Controller.Admin;

import com.lab4.saigonwaterbus.DAO.*;
import com.lab4.saigonwaterbus.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AdminController {
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

    @RequestMapping("/saigonwaterbus/admin")
    public String trangChuAdmin() {
        return "admin/banner";
    }

    @RequestMapping("/saigonwaterbus/admin/routeship")
    public String routeship(Model model, @RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            model.addAttribute("listTuyen", daoTuyen.findAll());
        } else {
            model.addAttribute("listTuyen", daoTuyen.searchByTenTuyen(keyword));
        }
        model.addAttribute("tuyen", new Tuyen());
        model.addAttribute("listTau", daoTau.findByTauStatus());
        return "admin/tuyenTau";
    }

    @RequestMapping("/saigonwaterbus/admin/ship")
    public String ship(Model model, @RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            model.addAttribute("listTau", daoTau.findByTauStatus());
        } else {
            model.addAttribute("listTau", daoTau.searchByKeyword(keyword));
        }
        return "admin/tau";
    }

    @RequestMapping("/saigonwaterbus/admin/driver")
    public String driver(Model model,
                         @RequestParam(required = false) String keyword,
                         @ModelAttribute("taiXe") TaiXe taiXe) {
        System.out.println("keyword: " + keyword);
        List<TaiXe> listTaiXe;
        if (keyword == null || keyword.isEmpty()) {
            listTaiXe = taiXeDao.findAllByStatusTrue();
        } else {
            listTaiXe = taiXeDao.findByHoTenOrSoDT(keyword);
        }
        model.addAttribute("listTaiXe", listTaiXe);
        return "admin/taiXe";
    }

    @RequestMapping("/saigonwaterbus/admin/trip")
    public String trip(Model model,
                         @RequestParam(required = false) String keyword,
                       @Validated @ModelAttribute("chuyen") Chuyen chuyen,
                       @ModelAttribute("idChuyen") String idChuyen) {
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
            System.out.println("list ve chuyen: " + listVeChuyen.size());
        }
        return "admin/chuyenTau";
    }

    @RequestMapping("/saigonwaterbus/admin/ticket")
    public String ticket(Model model,
                       @RequestParam(required = false) String keyword, @Validated @ModelAttribute("Ve") Ve ve) {
        System.out.println("keyword: " + keyword);
        List<Ve> listVe;
        if (keyword == null || keyword.isEmpty()) {
            listVe = VeDao.findAll();
        } else {
            listVe = VeDao.findBytenGheOrid(keyword);
        }
        model.addAttribute("listVe", listVe);
        return "admin/veTau";
    }

    @RequestMapping("/saigonwaterbus/admin/bill")
    public String bill(Model model,
                         @RequestParam(required = false) String keyword, @ModelAttribute("hoaDon") HoaDon hoaDon) {
        List<HoaDon> listHoaDon;
        if (keyword == null || keyword.isEmpty()) {
            listHoaDon = HoaDonDao.findAll();
        } else {
            listHoaDon = HoaDonDao.findBytenGheOridAccount(keyword);
        }
        model.addAttribute("listHoaDon", listHoaDon);
        return "admin/hoaDon";
    }

    @RequestMapping("/saigonwaterbus/admin/thongKe")
    public String thongKe(Model model, @RequestParam(required = false)String date) {
        if (date != null) {
            List<Object[]> list1 = HoaDonDao.findHoaDonByDateChose(date);
            model.addAttribute("listThongKe", list1);
        } else {
            List<Object[]> list1 = HoaDonDao.findHoaDonByDate();
            model.addAttribute("listThongKe", list1);
        }
        model.addAttribute("totalDate", HoaDonDao.findTotalPrice());
        return "admin/thongKe";
    }
}
