package com.example.demo.Controller;

import com.example.demo.Dto.NhanVienDto;
import com.example.demo.Model.ChucVu;
import com.example.demo.Model.NhanVien;
import com.example.demo.Model.PhongBan;
import com.example.demo.Repository.ChucVuRepository;
import com.example.demo.Repository.PhongBanRepository;
import com.example.demo.Request.TimKiemRequest;
import com.example.demo.Response.ResponseEntityV2;
import com.example.demo.Service.NhanVienService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api")
@Validated
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private PhongBanRepository phongBanRepository;

    @Autowired
    private ChucVuRepository chucVuRepository;

    @GetMapping("/nhan-vien")
    public ResponseEntityV2<?> danhSachNhanVien() {
        return new ResponseEntityV2<>(HttpStatus.OK.value(), HttpStatus.OK.name(), nhanVienService.danhSachNhanVien());
    }

    @PostMapping("/nhan-vien")
    public ResponseEntityV2<?> themNhanVien(@RequestBody NhanVienDto nhanVienDto) throws ParseException {
        NhanVien nhanVien = new NhanVien();
        if (nhanVienService.validationNhanVien(nhanVienDto)) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
        }
        PhongBan phongBan = phongBanRepository.findById(nhanVienDto.getPhongBanId()).orElse(null);
        if (ObjectUtils.isEmpty(phongBan)) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
        }
        ChucVu chucVu = chucVuRepository.findById(nhanVienDto.getChucVuId()).orElse(null);
        if (ObjectUtils.isEmpty(chucVu)) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
        }
        BeanUtils.copyProperties(nhanVienDto, nhanVien);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        nhanVien.setNgaySinh(dateFormat.parse(nhanVienDto.getNgaySinh()));
        nhanVien.setMaNV(nhanVienService.randomMaNV());
        nhanVien.setPhongBan(phongBan);
        nhanVien.setChucVu(chucVu);
        return new ResponseEntityV2<>(nhanVienService.luuNhanVien(nhanVien));

    }


    @DeleteMapping("/nhan-vien/{id}")
    public ResponseEntityV2<?> xoaNhanVien(@PathVariable(name = "id") Long id) {
        NhanVien nhanVien = nhanVienService.chiTietNhanVien(id);
        if (!ObjectUtils.isEmpty(nhanVien)) {
            nhanVienService.xoaNhanVien(nhanVien);
            return new ResponseEntityV2<>(nhanVien);
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }

    @GetMapping("/nhan-vien/{id}")
    public ResponseEntityV2<?> chiTietNhanVien(@PathVariable(name = "id") Long id) {
        NhanVien nhanVien = nhanVienService.chiTietNhanVien(id);
        if (!ObjectUtils.isEmpty(nhanVien)) {
            return new ResponseEntityV2<>(nhanVien);
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }

    @PutMapping("/nhan-vien/{id}")
    public ResponseEntityV2<?> chinhSuaNhanVien(@PathVariable(name = "id") Long id, @RequestBody NhanVienDto nhanVienDto) throws ParseException {
        NhanVien nhanVien = nhanVienService.chiTietNhanVien(id);
        if (ObjectUtils.isEmpty(nhanVien)) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
        }
        if (nhanVienService.validationNhanVien(nhanVienDto)) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
        }
        PhongBan phongBan = phongBanRepository.findById(nhanVienDto.getPhongBanId()).orElse(null);
        if (ObjectUtils.isEmpty(phongBan)) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
        }
        ChucVu chucVu = chucVuRepository.findById(nhanVienDto.getChucVuId()).orElse(null);
        if (ObjectUtils.isEmpty(chucVu)) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
        }
        BeanUtils.copyProperties(nhanVienDto, nhanVien);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        nhanVien.setNgaySinh(dateFormat.parse(nhanVienDto.getNgaySinh()));
        nhanVien.setPhongBan(phongBan);
        nhanVien.setChucVu(chucVu);
        return new ResponseEntityV2<>(nhanVienService.luuNhanVien(nhanVien));
    }

    @PostMapping("/nhan-vien/tim-kiem")
    public ResponseEntityV2<?> timKiemNhanVien(@RequestBody TimKiemRequest timKiemRequest) {
        return new ResponseEntityV2<>(HttpStatus.OK.value(), HttpStatus.OK.name(), nhanVienService.timKiemNhanVien(timKiemRequest));
    }
}
