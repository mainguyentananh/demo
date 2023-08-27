package com.example.demo.Service;


import com.example.demo.Dto.ChucVuDto;
import com.example.demo.Model.ChucVu;

import java.util.List;

public interface ChucVuService {
    List<ChucVu> danhSachChucVu();
    ChucVu chiTietChucVu(Long id);
    void xoaChucVu(ChucVu chucVu);
    ChucVu luuChucVu(ChucVu chucVu);
}
