package com.lab4.saigonwaterbus.DAO;

import java.util.List;

import com.lab4.saigonwaterbus.Model.TaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface TaiXeDAO extends JpaRepository<TaiXe, Long> {
	@Query(value="SELECT * FROM tai_xe  WHERE status = 1", nativeQuery = true)
	List<TaiXe> findAllByStatusTrue();
    @Query(value = "SELECT * FROM tai_xe  WHERE ho_ten LIKE %?1% OR sodt LIKE ?1", nativeQuery = true)
    List<TaiXe> findByHoTenOrSoDT(String keyword);
    @Query (value = "select id,ho_ten from tai_xe", nativeQuery =true)
	List<TaiXe> getAllTaiXe();
}