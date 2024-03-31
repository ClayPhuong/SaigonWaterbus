package com.lab4.saigonwaterbus.DAO;

import java.util.List;

import com.lab4.saigonwaterbus.Model.Tuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TuyenDAO extends JpaRepository<Tuyen, String>{
    @Query("SELECT t FROM Tuyen t WHERE  t.tenTuyen LIKE %:keyword%")
    List<Tuyen> searchByTenTuyen(String keyword);
}
