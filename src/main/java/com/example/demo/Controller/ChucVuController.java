package com.example.demo.Controller;

import com.example.demo.Model.ChucVu;
import com.example.demo.Model.PhongBan;
import com.example.demo.Response.ResponseEntityV2;
import com.example.demo.Service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChucVuController {
    @Autowired
    private ChucVuService chucVuService;

    @GetMapping("/chuc-vu")
    public ResponseEntityV2<?> danhSachChucVu() {
        return new ResponseEntityV2<>(HttpStatus.OK.value(), HttpStatus.OK.name(), chucVuService.danhSachChucVu());
    }

    @PostMapping("/chuc-vu")
    public ResponseEntityV2<?> themChucVu(@RequestBody ChucVu chucVu) {
        if (!ObjectUtils.isEmpty(chucVu) && !ObjectUtils.isEmpty(chucVu.getTenChucVu())
                && !ObjectUtils.isEmpty(chucVu.getThamQuyen())) {
            return new ResponseEntityV2<>(chucVuService.luuChucVu(chucVu));
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }

    @DeleteMapping("/chuc-vu/{id}")
    public ResponseEntityV2<?> xoaChucVu(@PathVariable(name = "id") Long id) {
        ChucVu chucVu = chucVuService.chiTietChucVu(id);
        if (!ObjectUtils.isEmpty(chucVu)) {
            chucVuService.xoaChucVu(chucVu);
            return new ResponseEntityV2<>(chucVu);
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }

    @GetMapping("/chuc-vu/{id}")
    public ResponseEntityV2<?> chiTietChucVu(@PathVariable(name = "id") Long id) {
        ChucVu chucVu = chucVuService.chiTietChucVu(id);
        if (!ObjectUtils.isEmpty(chucVu)) {
            return new ResponseEntityV2<>(chucVu);
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }

    @PutMapping("/chuc-vu/{id}")
    public ResponseEntityV2<?> chinhSuaChucVu(@PathVariable(name = "id") Long id, @RequestBody ChucVu chinhSuaChucVu) {
        if (!ObjectUtils.isEmpty(chinhSuaChucVu) && !ObjectUtils.isEmpty(chinhSuaChucVu.getTenChucVu())
                && !ObjectUtils.isEmpty(chinhSuaChucVu.getThamQuyen())) {
            ChucVu chucVu = chucVuService.chiTietChucVu(id);
            if (!ObjectUtils.isEmpty(chucVu)) {
                chucVu.setTenChucVu(chinhSuaChucVu.getTenChucVu());
                chucVu.setThamQuyen(chinhSuaChucVu.getThamQuyen());
                return new ResponseEntityV2<>(chucVuService.luuChucVu(chucVu));
            }
        }
        return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), null);
    }
}
