package com.lab4.saigonwaterbus.Service;

import com.lab4.saigonwaterbus.DAO.ChuyenDAO;
import com.lab4.saigonwaterbus.Model.Chuyen;
import com.lab4.saigonwaterbus.Service.Impl.ITicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final ChuyenDAO chuyenDAO;
    @Override
    public List<Chuyen> findChuyen(LocalDate ngayKH, String benDi, String benDen) {
        return chuyenDAO.findChuyen(ngayKH, benDi, benDen);
    }
}
