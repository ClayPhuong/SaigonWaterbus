package com.lab4.saigonwaterbus.Service.Impl;

import com.lab4.saigonwaterbus.Model.Chuyen;

import java.time.LocalDate;
import java.util.List;

public interface  ITicketService {
    List<Chuyen> findChuyen(LocalDate ngayKH, String benDi, String benDen);
}
