package com.example.demo.Service;

import com.example.demo.Dto.NhanVienDto;
import com.example.demo.Model.NhanVien;
import com.example.demo.Request.TimKiemRequest;

import java.util.HashMap;
import java.util.List;

public interface NhanVienService {
    List<NhanVien> danhSachNhanVien();
    NhanVien luuNhanVien(NhanVien nhanVien);
    void xoaNhanVien(NhanVien nhanVien);
    NhanVien chiTietNhanVien(Long id);
    Boolean validationNhanVien(NhanVienDto nhanVienDto);
    String randomMaNV();
    List<NhanVien> timKiemNhanVien(TimKiemRequest timKiemRequest);
    void updateLuong(HashMap<String,Double> hashMap);
}
