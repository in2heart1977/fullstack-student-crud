import React, { useState } from 'react';
import StudentList from './components/StudentList';
import StudentForm from './components/StudentForm';
import { GraduationCap } from 'lucide-react';

function App() {
    // State สำหรับควบคุมการแสดงผล Modal
    const [isModalOpen, setIsModalOpen] = useState(false);
    
    // State สำหรับเก็บข้อมูลนักศึกษาที่ต้องการแก้ไข (ถ้าเป็น null แปลว่าโหมด "เพิ่ม")
    const [selectedStudent, setSelectedStudent] = useState(null);
    
    // Key สำหรับบังคับให้ StudentList ทำการ Re-fetch ข้อมูล
    const [refreshKey, setRefreshKey] = useState(0);

    // ฟังก์ชันเปิด Modal สำหรับเพิ่มใหม่
    const handleAddNew = () => {
        setSelectedStudent(null);
        setIsModalOpen(true);
    };

    // ฟังก์ชันเปิด Modal สำหรับแก้ไข
    const handleEdit = (student) => {
        setSelectedStudent(student);
        setIsModalOpen(true);
    };

    // ฟังก์ชันเมื่อบันทึกสำเร็จ
    const handleSaveSuccess = () => {
        setRefreshKey(prev => prev + 1); // เปลี่ยน Key เพื่อให้ StudentList โหลดข้อมูลใหม่
        setIsModalOpen(false);
    };

    return (
        <div className="min-h-screen bg-gray-50 font-sans">
            {/* Navigation Bar */}
            <nav className="bg-white shadow-sm border-b border-gray-200 px-6 py-4">
                <div className="max-w-6xl mx-auto flex items-center gap-3">
                    <div className="bg-blue-600 p-2 rounded-lg text-white">
                        <GraduationCap size={28} />
                    </div>
                    <div>
                        <h1 className="text-xl font-bold text-gray-800 leading-tight">Verb2Bee</h1>
                        <p className="text-xs text-gray-500">Student Management System</p>
                    </div>
                </div>
            </nav>

            {/* Main Content Area */}
            <main className="py-8">
                {/* 
                    ส่ง handleEdit ไปให้ StudentList เพื่อให้กดปุ่ม Edit ในตารางได้
                    ส่ง refreshKey เพื่อให้ตาราง Update เมื่อมีการ Save ข้อมูล
                */}
                <StudentList 
                    key={refreshKey} 
                    onEdit={handleEdit} 
                    onAddClick={handleAddNew} 
                />
            </main>

            {/* Modal Form Section */}
            {isModalOpen && (
                <StudentForm 
                    student={selectedStudent} 
                    onClose={() => setIsModalOpen(false)} 
                    onSave={handleSaveSuccess} 
                />
            )}

            {/* Footer */}
            <footer className="text-center py-8 text-gray-400 text-sm">
                &copy; {new Date().getFullYear()} Student CRUD Project Portfolio. Built with React & Spring Boot.
            </footer>
        </div>
    );
}

export default App;
