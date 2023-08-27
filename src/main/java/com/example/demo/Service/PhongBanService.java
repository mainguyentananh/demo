package com.example.demo.Service;

import com.example.demo.Dto.PhongBanDto;
import com.example.demo.Model.PhongBan;

import java.util.List;

public interface PhongBanService {
    List<PhongBanDto> danhSachPhongBan();
    PhongBan chiTietPhongBan(Long id);
    void xoaPhongBan(PhongBan phongBan);
    PhongBan luuPhongBan(PhongBan phongBan);
}
