import axios from 'axios';

// 1. สร้าง Instance ของ Axios เพื่อกำหนดค่าพื้นฐาน
const api = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    headers: {
        'Content-Type': 'application/json'
    }
});

const studentApi = {
    // ดึงข้อมูลนักศึกษาแบบแบ่งหน้า (Pagination) ⭐️ NEW
    // page: ลำดับหน้า (เริ่มที่ 0), size: จำนวนรายการต่อหน้า
    getPage: async (page = 0, size = 5) => {
        const response = await api.get(`/students/page?page=${page}&size=${size}`);
        return response.data; // จะคืนค่า { success: true, data: { content: [], totalPages: x, ... } }
    },

    // ดึงข้อมูลนักศึกษาทั้งหมด (แบบเดิม - ถ้ายังอยากใช้)
    getAll: async () => {
        const response = await api.get('/students');
        return response.data;
    },

    // ดึงข้อมูลนักศึกษารายคน
    getById: async (id) => {
        const response = await api.get(`/students/${id}`);
        return response.data;
    },

    // เพิ่มนักศึกษาใหม่
    create: async (studentData) => {
        const response = await api.post('/students', studentData);
        return response.data;
    },

    // อัปเดตข้อมูลนักศึกษา
    update: async (id, studentData) => {
        const response = await api.put(`/students/${id}`, studentData);
        return response.data;
    },

    // ลบนักศึกษา
    delete: async (id) => {
        const response = await api.delete(`/students/${id}`);
        return response.data;
    },

    // ค้นหานักศึกษา (ปรับให้รองรับ keyword)
    search: async (keyword) => {
        const response = await api.get(`/students/search?keyword=${keyword}`);
        return response.data;
    }
};

export default studentApi;
