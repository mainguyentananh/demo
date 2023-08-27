package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "nhan_vien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ma_nv")
    private String maNV;

    @Column(name = "ho_va_ten")
    private String hoVaTen;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "que_quan")
    private String queQuan;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "phong_ban_id")
    private PhongBan phongBan;

    @ManyToOne
    @JoinColumn(name = "chuc_vu_id")
    private ChucVu chucVu;

    @Column(name = "luong")
    private Double luong;

    @Column(name = "trang_thai")
    private String trangThai;
}
