import React, { useState, useEffect } from 'react';
import studentApi from '../api/studentApi';
import axios from 'axios';
import { X, Save, User, Mail, Calendar, GraduationCap, Hash, AlertCircle } from 'lucide-react';

const StudentForm = ({ student, onClose, onSave }) => {
    const [formData, setFormData] = useState({
        studentCode: '', firstName: '', lastName: '',
        email: '', birthDate: '', majorId: ''
    });
    const [majors, setMajors] = useState([]);
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        const fetchMajors = async () => {
            const res = await axios.get('http://localhost:8080/api/v1/majors');
            setMajors(res.data.data);
        };
        fetchMajors();
        if (student) setFormData(student);
    }, [student]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        try {
            if (student?.id) await studentApi.update(student.id, formData);
            else await studentApi.create(formData);
            onSave();
        } catch (err) {
            alert("Error saving student data");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-900/40 backdrop-blur-md animate-in fade-in duration-300">
            <div className="bg-white w-full max-w-lg rounded-[2.5rem] shadow-2xl overflow-hidden border border-white/20 transform transition-all scale-100 shadow-indigo-200/50">
                
                {/* Header: Gradient Style */}
                <div className="bg-gradient-to-br from-indigo-600 to-blue-700 p-8 text-white relative">
                    <button onClick={onClose} className="absolute top-6 right-6 p-2 bg-white/10 hover:bg-white/20 rounded-full transition-colors">
                        <X size={20} />
                    </button>
                    <div className="flex items-center gap-4">
                        <div className="p-3 bg-white/20 rounded-2xl backdrop-blur-sm">
                            <User size={32} strokeWidth={2.5} />
                        </div>
                        <div>
                            <h2 className="text-2xl font-black">{student ? 'Edit Profile' : 'New Enrollment'}</h2>
                            <p className="text-indigo-100 text-sm font-medium">กรอกข้อมูลให้ครบถ้วนเพื่อบันทึกเข้าระบบ</p>
                        </div>
                    </div>
                </div>

                {/* Form Body */}
                <form onSubmit={handleSubmit} className="p-8 space-y-6">
                    <div className="space-y-4">
                        {/* Student Code */}
                        <div className="relative">
                            <label className="text-xs font-bold text-slate-400 uppercase tracking-widest ml-1 mb-2 block">Student ID</label>
                            <div className="relative group">
                                <Hash className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 group-focus-within:text-indigo-500 transition-colors" size={18} />
                                <input 
                                    name="studentCode" value={formData.studentCode}
                                    onChange={(e) => setFormData({...formData, studentCode: e.target.value})}
                                    className="w-full pl-12 pr-4 py-3.5 bg-slate-50 border-2 border-transparent rounded-2xl focus:bg-white focus:border-indigo-500/20 focus:ring-4 focus:ring-indigo-500/5 transition-all outline-none font-semibold text-slate-700"
                                    placeholder="e.g. 640001"
                                />
                            </div>
                        </div>

                        {/* Name Grid */}
                        <div className="grid grid-cols-2 gap-4">
                            <div className="space-y-2">
                                <label className="text-xs font-bold text-slate-400 uppercase tracking-widest ml-1 block">First Name</label>
                                <input 
                                    name="firstName" value={formData.firstName}
                                    onChange={(e) => setFormData({...formData, firstName: e.target.value})}
                                    className="w-full px-4 py-3.5 bg-slate-50 border-2 border-transparent rounded-2xl focus:bg-white focus:border-indigo-500/20 focus:ring-4 focus:ring-indigo-500/5 transition-all outline-none font-medium"
                                />
                            </div>
                            <div className="space-y-2">
                                <label className="text-xs font-bold text-slate-400 uppercase tracking-widest ml-1 block">Last Name</label>
                                <input 
                                    name="lastName" value={formData.lastName}
                                    onChange={(e) => setFormData({...formData, lastName: e.target.value})}
                                    className="w-full px-4 py-3.5 bg-slate-50 border-2 border-transparent rounded-2xl focus:bg-white focus:border-indigo-500/20 focus:ring-4 focus:ring-indigo-500/5 transition-all outline-none font-medium"
                                />
                            </div>
                        </div>

                        {/* Email */}
                        <div className="space-y-2">
                            <label className="text-xs font-bold text-slate-400 uppercase tracking-widest ml-1 block">Email Address</label>
                            <div className="relative group">
                                <Mail className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 group-focus-within:text-indigo-500 transition-colors" size={18} />
                                <input 
                                    name="email" type="email" value={formData.email}
                                    onChange={(e) => setFormData({...formData, email: e.target.value})}
                                    className="w-full pl-12 pr-4 py-3.5 bg-slate-50 border-2 border-transparent rounded-2xl focus:bg-white focus:border-indigo-500/20 focus:ring-4 focus:ring-indigo-500/5 transition-all outline-none font-medium"
                                    placeholder="student@university.com"
                                />
                            </div>
                        </div>

                        {/* Major Select */}
                        <div className="space-y-2">
                            <label className="text-xs font-bold text-slate-400 uppercase tracking-widest ml-1 block">Academic Major</label>
                            <div className="relative group">
                                <GraduationCap className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 group-focus-within:text-indigo-500 transition-colors" size={18} />
                                <select 
                                    name="majorId" value={formData.majorId}
                                    onChange={(e) => setFormData({...formData, majorId: e.target.value})}
                                    className="w-full pl-12 pr-4 py-3.5 bg-slate-50 border-2 border-transparent rounded-2xl focus:bg-white focus:border-indigo-500/20 focus:ring-4 focus:ring-indigo-500/5 transition-all outline-none font-bold text-slate-600 appearance-none cursor-pointer"
                                >
                                    <option value="">Select a Faculty</option>
                                    {majors.map(m => <option key={m.id} value={m.id}>{m.majorName}</option>)}
                                </select>
                            </div>
                        </div>
                    </div>

                    {/* Action Buttons */}
                    <div className="flex gap-4 pt-4">
                        <button 
                            type="button" onClick={onClose}
                            className="flex-1 py-4 text-slate-500 font-bold hover:bg-slate-100 rounded-2xl transition-all"
                        >
                            Discard
                        </button>
                        <button 
                            type="submit" disabled={isSubmitting}
                            className="flex-2 px-8 py-4 bg-indigo-600 text-white font-black rounded-2xl hover:bg-indigo-700 hover:shadow-xl hover:shadow-indigo-200 transition-all flex items-center justify-center gap-2 disabled:opacity-50"
                        >
                            <Save size={20} strokeWidth={3} />
                            {isSubmitting ? 'SAVING...' : 'SAVE PROFILE'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default StudentForm;
