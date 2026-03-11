package com.verb2bee.fullstack.service;

import com.verb2bee.fullstack.dto.request.UserRequest;
import com.verb2bee.fullstack.dto.response.UserResponse;
import com.verb2bee.fullstack.entity.User;
import com.verb2bee.fullstack.entity.UserProfile;
import com.verb2bee.fullstack.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection (แทนการใช้ @Autowired)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        // 1. ตรวจสอบข้อมูลซ้ำ (Business Logic)
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Mapping Request DTO -> User Entity (One-to-One Owner)
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // 3. Mapping Request DTO -> UserProfile Entity (One-to-One Child)
        UserProfile profile = new UserProfile();
        profile.setAddress(request.getAddress());
        profile.setPhoneNumber(request.getPhoneNumber());

        // 4. เชื่อมความสัมพันธ์ (Link them together)
        user.setProfile(profile);
        profile.setUser(user);

        // 5. บันทึกข้อมูล (CascadeType.ALL ใน User Entity จะช่วยบันทึก Profile ให้เอง)
        User savedUser = userRepository.save(user);

        // 6. Mapping Entity -> Response DTO
        return mapToResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();

        for (User user : users) {
            responses.add(mapToResponse(user));
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // Helper Method สำหรับ Mapping ข้อมูลกลับไปเป็น DTO
    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        // ดึงข้อมูลจาก One-to-One Profile มาใส่ใน Response
        if (user.getProfile() != null) {
            response.setAddress(user.getProfile().getAddress());
            response.setPhoneNumber(user.getProfile().getPhoneNumber());
        }

        return response;
    }
}
