package Nhom5_ASMGD1.Service.impl;

import Nhom5_ASMGD1.Model.Chuyen;

import java.time.LocalDate;
import java.util.List;

public interface  ITicketService {
    List<Chuyen> findChuyen(LocalDate ngayKH, String benDi, String benDen);
}
