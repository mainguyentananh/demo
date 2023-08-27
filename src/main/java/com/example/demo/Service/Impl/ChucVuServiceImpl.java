package com.example.demo.Service.Impl;

import com.example.demo.Dto.ChucVuDto;
import com.example.demo.Model.ChucVu;
import com.example.demo.Repository.ChucVuRepository;
import com.example.demo.Service.ChucVuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChucVuServiceImpl implements ChucVuService {
    @Autowired
    private ChucVuRepository chucVuRepository;

    @Override
    public List<ChucVu> danhSachChucVu() {
        List<ChucVu> lstChucVu = chucVuRepository.findAll();
        List<ChucVuDto> lstChucVuDto = new ArrayList<>();
        for (ChucVu chucVu : lstChucVu) {
            ChucVuDto chucVuDto = new ChucVuDto();
            BeanUtils.copyProperties(chucVu, chucVuDto);
            lstChucVuDto.add(chucVuDto);
        }
        return lstChucVu;
    }

    @Override
    public ChucVu chiTietChucVu(Long id) {
        return chucVuRepository.findById(id).orElse(null);
    }

    @Override
    public void xoaChucVu(ChucVu chucVu) {
        chucVuRepository.delete(chucVu);
    }

    @Override
    public ChucVu luuChucVu(ChucVu chucVu) {
        return chucVuRepository.save(chucVu);
    }
}
