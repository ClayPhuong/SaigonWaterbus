package Nhom5_ASMGD1.Service;

import Nhom5_ASMGD1.DAO.ChuyenDAO;
import Nhom5_ASMGD1.Model.Chuyen;
import Nhom5_ASMGD1.Service.impl.ITicketService;
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
