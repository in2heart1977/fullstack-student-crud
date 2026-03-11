package com.verb2bee.fullstack.repository;

import com.verb2bee.fullstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ค้นหา User ด้วย Username (สำหรับการตรวจสอบข้อมูลซ้ำ)
    Optional<User> findByUsername(String username);

    // ค้นหา User ด้วย Email
    Optional<User> findByEmail(String email);

    // ตรวจสอบว่ามี Username นี้อยู่ในระบบแล้วหรือไม่
    boolean existsByUsername(String username);
}
