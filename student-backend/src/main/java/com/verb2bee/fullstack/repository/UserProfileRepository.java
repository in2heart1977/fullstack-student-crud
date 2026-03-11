package com.verb2bee.fullstack.repository;

import com.verb2bee.fullstack.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // ค้นหาโปรไฟล์ผ่านเบอร์โทรศัพท์ (ถ้าต้องการ)
    Optional<UserProfile> findByPhoneNumber(String phoneNumber);

    // ตรวจสอบว่าเบอร์โทรศัพท์นี้ถูกใช้งานไปแล้วหรือไม่
    boolean existsByPhoneNumber(String phoneNumber);
}
