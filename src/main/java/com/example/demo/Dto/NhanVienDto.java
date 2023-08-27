package com.example.demo.Dto;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NhanVienDto {
    private String hoVaTen;
    private String ngaySinh;
    private String queQuan;
    private String sdt;
    private String email;
    private Long phongBanId;
    private Long chucVuId;
    private Double luong;
    private String trangThai;
}
