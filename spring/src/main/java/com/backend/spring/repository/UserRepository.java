package com.backend.spring.repository;

import com.backend.spring.models.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    List<UserEntity> findAll();

    UserEntity save(UserEntity userEntity);

//    void deleteByUserName(String username);

//    @Modifying
//    @Query("UPDATE UserEntity e SET e.status = :status WHERE e.employeeId = :employeeId")
//    void updateStatusById(@Param("status") String status, @Param("employeeId") String employeeId);
}
