import React, { useEffect, useState } from 'react';
import studentApi from '../api/studentApi';
import { Trash2, Edit3, UserPlus, Search, Mail, IdCard, RefreshCw, ChevronLeft, ChevronRight, GraduationCap } from 'lucide-react';

const StudentList = ({ onEdit, onAddClick }) => {
    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState("");
    
    // State สำหรับ Pagination (อ้างอิงตาม Spring Data Page)
    const [page, setPage] = useState(0); 
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [pageSize] = useState(5); // กำหนดให้แสดงหน้าละ 5 รายการ

    // ฟังก์ชันดึงข้อมูลแบบแบ่งหน้า
    const fetchStudents = async () => {
        try {
            setLoading(true);
            const response = await studentApi.getPage(page, pageSize);
            
            if (response.success && response.data) {
                // สำหรับ Spring Page ข้อมูลจริงจะอยู่ใน .content
                setStudents(response.data.content || []);
                setTotalPages(response.data.totalPages || 0);
                setTotalElements(response.data.totalElements || 0);
            }
        } catch (err) {
            console.error("Fetch Error:", err);
            setStudents([]);
        } finally {
            setLoading(false);
        }
    };

    // โหลดข้อมูลใหม่ทุกครั้งที่เลขหน้าเปลี่ยน
    useEffect(() => {
        fetchStudents();
    }, [page]);

    const handleDelete = async (id) => {
        if (window.confirm("⚠️ คุณแน่ใจใช่ไหมที่จะลบข้อมูลนักศึกษานี้?")) {
            try {
                const res = await studentApi.delete(id);
                if (res.success) {
                    // หลังจากลบ ให้โหลดหน้าปัจจุบันใหม่ เพื่อให้ Pagination คำนวณใหม่
                    fetchStudents(); 
                }
            } catch (err) {
                alert("ลบข้อมูลไม่สำเร็จ");
            }
        }
    };

    // กรองข้อมูลเฉพาะหน้าปัจจุบัน (Client-side search สำหรับหน้าที่มีอยู่)
    const filteredStudents = students.filter(s => 
        s.firstName.toLowerCase().includes(searchTerm.toLowerCase()) || 
        s.studentCode.includes(searchTerm)
    );

    if (loading) return (
        <div className="flex flex-col justify-center items-center h-96">
            <RefreshCw className="animate-spin text-indigo-600 mb-4" size={48} />
            <p className="text-slate-500 font-black tracking-widest uppercase">Fetching Data...</p>
        </div>
    );

    return (
        <div className="max-w-6xl mx-auto px-4 py-10">
            {/* Header Section */}
            <div className="relative overflow-hidden bg-gradient-to-br from-indigo-600 via-blue-600 to-indigo-700 rounded-[2.5rem] p-10 mb-10 shadow-2xl shadow-indigo-200">
                <div className="relative z-10 flex flex-col md:flex-row justify-between items-center gap-8 text-white">
                    <div className="text-center md:text-left">
                        <h1 className="text-5xl font-black tracking-tighter mb-2 italic">STUDENTS.</h1>
                        <p className="opacity-90 font-medium">จัดการฐานข้อมูลนักศึกษา (ระบบแบ่งหน้าอัจฉริยะ)</p>
                    </div>
                    <button 
                        onClick={onAddClick}
                        className="bg-white text-indigo-600 font-black px-10 py-5 rounded-2xl flex items-center gap-3 hover:bg-slate-50 transition-all transform hover:-translate-y-1 active:scale-95 shadow-2xl"
                    >
                        <UserPlus size={24} strokeWidth={3} />
                        NEW ENROLLMENT
                    </button>
                </div>
                <div className="absolute top-[-30px] right-[-30px] w-64 h-64 bg-white/10 rounded-full blur-3xl"></div>
            </div>

            {/* Toolbar: Search */}
            <div className="mb-8 relative group">
                <Search className="absolute left-6 top-1/2 -translate-y-1/2 text-slate-400 group-focus-within:text-indigo-600 transition-colors" size={22} />
                <input 
                    type="text" 
                    placeholder="Search in this page..."
                    className="w-full pl-16 pr-6 py-5 bg-white border-none rounded-3xl shadow-sm focus:ring-4 focus:ring-indigo-500/10 transition-all outline-none text-slate-700 font-bold text-lg"
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </div>

            {/* Main Table */}
            <div className="bg-white rounded-[2.5rem] shadow-2xl shadow-slate-200/50 border border-slate-100 overflow-hidden">
                <div className="overflow-x-auto">
                    <table className="w-full text-left">
                        <thead>
                            <tr className="bg-slate-50/50">
                                <th className="px-10 py-8 text-xs font-black text-slate-400 uppercase tracking-[0.2em]">Full Details</th>
                                <th className="px-10 py-8 text-xs font-black text-slate-400 uppercase tracking-[0.2em]">Academic</th>
                                <th className="px-10 py-8 text-xs font-black text-slate-400 uppercase tracking-[0.2em] text-center">Actions</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-slate-100">
                            {filteredStudents.length > 0 ? (
                                filteredStudents.map((s) => (
                                    <tr key={s.id} className="hover:bg-slate-50/80 transition-all group">
                                        <td className="px-10 py-8">
                                            <div className="flex items-center gap-5">
                                                <div className="w-14 h-14 bg-indigo-100 rounded-2xl flex items-center justify-center text-indigo-600 font-black text-xl border border-white shadow-inner">
                                                    {s.firstName.charAt(0)}
                                                </div>
                                                <div>
                                                    <div className="font-black text-slate-800 text-xl leading-none mb-2">{s.firstName} {s.lastName}</div>
                                                    <div className="flex items-center gap-2 text-slate-400 text-sm font-medium"><Mail size={14}/> {s.email}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td className="px-10 py-8">
                                            <div className="flex items-center gap-2 text-slate-700 font-black text-lg mb-2"><IdCard size={18} className="text-indigo-400"/> {s.studentCode}</div>
                                            <span className="px-3 py-1.5 bg-indigo-50 text-indigo-600 text-[10px] font-black rounded-lg border border-indigo-100 uppercase">{s.majorName}</span>
                                        </td>
                                        <td className="px-10 py-8">
                                            <div className="flex justify-center gap-3">
                                                <button onClick={() => onEdit(s)} className="p-4 bg-white text-amber-500 rounded-2xl shadow-sm border border-slate-100 hover:bg-amber-50 hover:-translate-y-1 transition-all"><Edit3 size={20}/></button>
                                                <button onClick={() => handleDelete(s.id)} className="p-4 bg-white text-red-500 rounded-2xl shadow-sm border border-slate-100 hover:bg-red-50 hover:-translate-y-1 transition-all"><Trash2 size={20}/></button>
                                            </div>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="3" className="px-10 py-24 text-center opacity-30">
                                        <div className="flex flex-col items-center">
                                            <GraduationCap size={64} className="mb-4" />
                                            <p className="text-xl font-black tracking-widest uppercase">No Records Found</p>
                                        </div>
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>

                {/* --- Enhanced Pagination Controls --- */}
                <div className="px-10 py-8 bg-slate-50/50 border-t border-slate-100 flex flex-col md:flex-row justify-between items-center gap-6">
                    <div className="text-sm font-black text-slate-400 uppercase tracking-widest">
                        Total <span className="text-indigo-600">{totalElements}</span> Students
                    </div>
                    
                    <div className="flex items-center gap-4">
                        <span className="text-xs font-black text-slate-500 uppercase">
                            Page {page + 1} of {totalPages}
                        </span>
                        
                        <div className="flex items-center gap-2">
                            <button 
                                disabled={page === 0}
                                onClick={() => setPage(prev => prev - 1)}
                                className="p-3 bg-white border border-slate-200 rounded-2xl text-slate-600 hover:bg-indigo-50 disabled:opacity-20 transition-all shadow-sm"
                            >
                                <ChevronLeft size={24} />
                            </button>
                            
                            {/* หน้าปัจจุบัน Visual Indicator */}
                            <div className="flex gap-1.5 px-3">
                                {[...Array(totalPages)].map((_, i) => (
                                    <button 
                                        key={i}
                                        onClick={() => setPage(i)}
                                        className={`h-2 rounded-full transition-all duration-300 ${page === i ? 'bg-indigo-600 w-8' : 'bg-slate-300 w-2 hover:bg-slate-400'}`}
                                    />
                                ))}
                            </div>

                            <button 
                                disabled={page >= totalPages - 1}
                                onClick={() => setPage(prev => prev + 1)}
                                className="p-3 bg-white border border-slate-200 rounded-2xl text-slate-600 hover:bg-indigo-50 disabled:opacity-20 transition-all shadow-sm"
                            >
                                <ChevronRight size={24} />
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StudentList;
