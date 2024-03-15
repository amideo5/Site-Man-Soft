package com.backend.spring.repository;

import com.backend.spring.models.EmployeeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {

    EmployeeEntity save(EmployeeEntity employeeEntity);

    List<EmployeeEntity> findAll();

    void deleteById(Long employeeId);

    EmployeeEntity findByEmployeeId(String employeeId);

    Optional<EmployeeEntity> findById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE EmployeeEntity e SET e.status = :status WHERE e.employeeId = :employeeId")
    void updateStatusById(@Param("status") String status, @Param("employeeId") String employeeId);
}
