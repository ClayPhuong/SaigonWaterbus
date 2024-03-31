package com.lab4.saigonwaterbus.Controller.User;

import com.lab4.saigonwaterbus.DAO.ChuyenDAO;
import com.lab4.saigonwaterbus.DAO.HoaDonDAO;
import com.lab4.saigonwaterbus.DAO.UserDao;
import com.lab4.saigonwaterbus.DAO.VeDAO;
import com.lab4.saigonwaterbus.Model.Chuyen;
import com.lab4.saigonwaterbus.Model.HoaDon;
import com.lab4.saigonwaterbus.Service.Impl.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {
    private final ITicketService iTicketService;
    @Autowired
    VeDAO veDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ChuyenDAO chuyenDao;
    @Autowired
    HoaDonDAO HoaDonDao;

    public UserController(ITicketService iTicketService) {
        this.iTicketService = iTicketService;
    }

    @RequestMapping("/saigonwaterbus")
    public String trangChu() {
       return "user/trangChuUser";
    }

    @RequestMapping("/saigonwaterbus/ga-tau-thuy")
    public String ga() {
        return "user/gaUser";
    }

    @RequestMapping("/saigonwaterbus/lich-khoi-hanh")
    public String lich() {
        return "user/lichKhoiHanh";
    }

    @RequestMapping("/saigonwaterbus/dat-ve")
    public String datVe(Model model, @RequestParam(value = "benDi", required = false) String benDi,
                        @RequestParam(value = "benDen", required = false) String benDen,
                        @RequestParam(value = "benDung", required = false) String benDung,
                        @RequestParam(value = "ngayKhoiHanh", required = false) String ngayKhoiHanh,
                        @RequestParam(value = "editidChuyen", required = false) String idChuyen) {
        LocalDate ngayKH;
        List<Chuyen> listChuyen;
        if (ngayKhoiHanh != null && !ngayKhoiHanh.isEmpty()) {
            ngayKH = LocalDate.parse(ngayKhoiHanh);
            System.out.println("ngày kh: " + ngayKH);
            listChuyen = iTicketService.findChuyen(ngayKH, benDi, benDen);
            System.out.println(listChuyen.size() + "size list");
            model.addAttribute("ngayKH", ngayKH);
        } else {
            listChuyen = null;
            System.out.println("ngày kh: " + ngayKhoiHanh);
        }
        model.addAttribute("listChuyen", listChuyen);
        model.addAttribute("benDi", benDi);
        model.addAttribute("benDen", benDen);
        return "user/timVeUser";
    }

    @RequestMapping("/saigonwaterbus/lien-he")
    public String lienHe() {
        return "user/lienHe";
    }

    @RequestMapping("/saigonwaterbus/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("user", userDao.findByEmail(authentication.getName()));
        }
        return "user/profile";
    }

    @RequestMapping("/saigonwaterbus/lich-su-dat-ve")
    public String lichSu(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<HoaDon> listVeDaDat = HoaDonDao.findByUser(userDao.findByEmail(authentication.getName()));
        Collections.sort(listVeDaDat, new Comparator<HoaDon>() {
            public int compare(HoaDon hoaDon1, HoaDon hoaDon2) {
                return hoaDon1.getNgayTao().compareTo(hoaDon2.getNgayTao());
            }
        });
        model.addAttribute("listVeDaDat", listVeDaDat);
        return "user/lichSuDatVe";
    }

}
