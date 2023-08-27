package com.example.demo.Controller;

import com.example.demo.Response.ResponseEntityV2;
import com.example.demo.Model.PhongBan;
import com.example.demo.Service.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PhongBanController {
    @Autowired
    private PhongBanService phongBanService;

    @GetMapping("/phong-ban")
    public ResponseEntityV2<?> danhSachPhongBan() {
        return new ResponseEntityV2<>(HttpStatus.OK.value(), HttpStatus.OK.name(), phongBanService.danhSachPhongBan());
    }

    @PostMapping("/phong-ban")
    public ResponseEntityV2<?> themPhongBan(@RequestBody PhongBan phongBan) {
        if (!ObjectUtils.isEmpty(phongBan) && !ObjectUtils.isEmpty(phongBan.getTenPhongBan())
                && !ObjectUtils.isEmpty(phongBan.getChucNang())) {
            return new ResponseEntityV2<>(phongBanService.luuPhongBan(phongBan));
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.name(),null);
    }

    @DeleteMapping("/phong-ban/{id}")
    public ResponseEntityV2<?> xoaPhongBan(@PathVariable(name = "id") Long id) {
        PhongBan phongBan = phongBanService.chiTietPhongBan(id);
        if (!ObjectUtils.isEmpty(phongBan)) {
            phongBanService.xoaPhongBan(phongBan);
            return new ResponseEntityV2<>(phongBan);
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }

    @GetMapping("/phong-ban/{id}")
    public ResponseEntityV2<?> chiTietPhongBan(@PathVariable(name = "id") Long id) {
        PhongBan phongBan = phongBanService.chiTietPhongBan(id);
        if (!ObjectUtils.isEmpty(phongBan)) {
            return new ResponseEntityV2<>(phongBan);
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }

    @PutMapping("/phong-ban/{id}")
    public ResponseEntityV2<?> chinhSuaPhongBan(@PathVariable(name = "id") Long id, @RequestBody PhongBan chinhSuaPhongBan) {
        if (!ObjectUtils.isEmpty(chinhSuaPhongBan) && !ObjectUtils.isEmpty(chinhSuaPhongBan.getTenPhongBan())
                && !ObjectUtils.isEmpty(chinhSuaPhongBan.getChucNang())) {
            PhongBan phongBan = phongBanService.chiTietPhongBan(id);
            if (!ObjectUtils.isEmpty(phongBan)) {
                phongBan.setTenPhongBan(chinhSuaPhongBan.getTenPhongBan());
                phongBan.setChucNang(chinhSuaPhongBan.getChucNang());
                return new ResponseEntityV2<>(phongBanService.luuPhongBan(phongBan));
            }
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }
}
