package com.example.demo.Service.Impl;

import com.example.demo.Dto.NhanVienDto;
import com.example.demo.Model.ChucVu;
import com.example.demo.Model.NhanVien;
import com.example.demo.Model.PhongBan;
import com.example.demo.Repository.NhanVienRepository;
import com.example.demo.Request.TimKiemRequest;
import com.example.demo.Service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<NhanVien> danhSachNhanVien() {
        return nhanVienRepository.findAll();
    }

    @Override
    public NhanVien luuNhanVien(NhanVien nhanVien) {
        return nhanVienRepository.save(nhanVien);
    }


    @Override
    public void xoaNhanVien(NhanVien nhanVien) {
        nhanVienRepository.delete(nhanVien);
    }

    @Override
    public NhanVien chiTietNhanVien(Long id) {
        return nhanVienRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean validationNhanVien(NhanVienDto nhanVienDto) {
        if (ObjectUtils.isEmpty(nhanVienDto)) {
            return true;
        }
        if (ObjectUtils.isEmpty(nhanVienDto.getChucVuId()) || ObjectUtils.isEmpty(nhanVienDto.getPhongBanId())
                || ObjectUtils.isEmpty(nhanVienDto.getEmail()) || ObjectUtils.isEmpty(nhanVienDto.getHoVaTen())
                || ObjectUtils.isEmpty(nhanVienDto.getQueQuan()) || ObjectUtils.isEmpty(nhanVienDto.getTrangThai())
                || ObjectUtils.isEmpty(nhanVienDto.getNgaySinh()) || ObjectUtils.isEmpty(nhanVienDto.getLuong()) || ObjectUtils.isEmpty(nhanVienDto.getSdt())) {
            return true;
        }
        String regexSdt = "\\d{10,11}";
        String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
        String regexNgaySinh = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        if (!nhanVienDto.getEmail().trim().matches(regexEmail) || nhanVienDto.getLuong() < 0 || nhanVienDto.getEmail().length() > 255
                || nhanVienDto.getTrangThai().length() > 255 || nhanVienDto.getQueQuan().length() > 255
                || nhanVienDto.getHoVaTen().length() > 255 || !nhanVienDto.getNgaySinh().trim().matches(regexNgaySinh) || !nhanVienDto.getSdt().trim().matches(regexSdt)) {
            return true;
        }
        return false;
    }

    @Override
    public String randomMaNV() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }
        if (nhanVienRepository.existsByMaNV(stringBuilder.toString())) {
            return randomMaNV();
        }
        return stringBuilder.toString();
    }

    @Override
    public List<NhanVien> timKiemNhanVien(TimKiemRequest timKiemRequest) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        StringJoiner stringJoiner = new StringJoiner(" ", " ", " ");
        stringJoiner.add("select * from nhan_vien nv");
        stringJoiner.add("inner join phong_ban pb on nv.phong_ban_id = pb.id");
        stringJoiner.add("inner join chuc_vu cv on nv.chuc_vu_id = cv.id");
        if (!ObjectUtils.isEmpty(timKiemRequest)) {
            if (!ObjectUtils.isEmpty(timKiemRequest.getTuKhoa()) && !ObjectUtils.isEmpty(timKiemRequest.getDanhMuc())) {
                mapSqlParameterSource.addValue("danhMuc", timKiemRequest.getDanhMuc().trim());
                mapSqlParameterSource.addValue("tuKhoa", String.format("%s%s%s", "%", timKiemRequest.getTuKhoa().trim(), "%"));
                stringJoiner.add("where pb.ten_phong_ban like :tuKhoa or cv.ten_chuc_vu like :tuKhoa or");
                stringJoiner.add("nv.ho_va_ten like :tuKhoa or nv.que_quan like :tuKhoa");
                stringJoiner.add("and pb.ten_phong_ban like :danhMuc or cv.ten_chuc_vu like :danhMuc");
                stringJoiner.add("or nv.trang_thai like :danhMuc");
            }
            if (!ObjectUtils.isEmpty(timKiemRequest.getTuKhoa()) && ObjectUtils.isEmpty(timKiemRequest.getDanhMuc())) {
                mapSqlParameterSource.addValue("tuKhoa", String.format("%s%s%s", "%", timKiemRequest.getTuKhoa().trim(), "%"));
                stringJoiner.add("where pb.ten_phong_ban like :tuKhoa or cv.ten_chuc_vu like :tuKhoa or");
                stringJoiner.add("nv.ho_va_ten like :tuKhoa or nv.que_quan like :tuKhoa");
            }
            if (!ObjectUtils.isEmpty(timKiemRequest.getDanhMuc()) && ObjectUtils.isEmpty(timKiemRequest.getTuKhoa())) {
                mapSqlParameterSource.addValue("danhMuc", timKiemRequest.getDanhMuc().trim());
                stringJoiner.add("where pb.ten_phong_ban like :danhMuc or cv.ten_chuc_vu like :danhMuc");
                stringJoiner.add("or nv.trang_thai like :danhMuc");
            }
        }
        List<NhanVien> result = jdbcTemplate.query(stringJoiner.toString(), mapSqlParameterSource, (rs, rowNum) -> {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setId(rs.getLong("nv.id"));
            nhanVien.setMaNV(rs.getString("ma_nv"));
            nhanVien.setNgaySinh(rs.getDate("ngay_sinh"));
            nhanVien.setEmail(rs.getString("email"));
            nhanVien.setSdt(rs.getString("sdt"));
            nhanVien.setQueQuan(rs.getString("que_quan"));
            nhanVien.setTrangThai(rs.getString("trang_thai"));
            nhanVien.setLuong(rs.getDouble("luong"));
            nhanVien.setHoVaTen(rs.getString("ho_va_ten"));
            ChucVu chucVu = new ChucVu();
            chucVu.setId(rs.getLong("cv.id"));
            chucVu.setTenChucVu(rs.getString("ten_chuc_vu"));
            chucVu.setThamQuyen(rs.getString("tham_quyen"));
            nhanVien.setChucVu(chucVu);
            PhongBan phongBan = new PhongBan();
            phongBan.setId(rs.getLong("pb.id"));
            phongBan.setTenPhongBan(rs.getString("ten_phong_ban"));
            phongBan.setChucNang(rs.getString("chuc_nang"));
            nhanVien.setPhongBan(phongBan);
            return nhanVien;
        });
        return result;
    }

    @Override
    public void updateLuong(HashMap<String, Double> hashMap) {
        Set<String> danhSachNhanVien = hashMap.keySet();
        List<NhanVien> listNhanVien = nhanVienRepository.findAllByMaNVIn(danhSachNhanVien);
        for (NhanVien nhanVien : listNhanVien) {
            nhanVien.setLuong(hashMap.get(nhanVien.getMaNV()));
        }
        nhanVienRepository.saveAll(listNhanVien);
    }
}
