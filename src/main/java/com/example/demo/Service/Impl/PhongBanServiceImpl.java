package com.example.demo.Service.Impl;

import com.example.demo.Dto.PhongBanDto;
import com.example.demo.Model.PhongBan;
import com.example.demo.Repository.PhongBanRepository;
import com.example.demo.Service.PhongBanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhongBanServiceImpl implements PhongBanService {

    @Autowired
    private PhongBanRepository phongBanRepository;

    @Override
    public List<PhongBanDto> danhSachPhongBan() {
        List<PhongBan> lstPhongBan = phongBanRepository.findAll();
        List<PhongBanDto> lstPhongBanDto = new ArrayList<>();
        for (PhongBan phongBan : lstPhongBan) {
            PhongBanDto phongBanDto = new PhongBanDto();
            BeanUtils.copyProperties(phongBan, phongBanDto);
            lstPhongBanDto.add(phongBanDto);
        }
        return lstPhongBanDto;
    }

    @Override
    public PhongBan chiTietPhongBan(Long id) {
        return phongBanRepository.findById(id).orElse(null);
    }

    @Override
    public void xoaPhongBan(PhongBan phongBan) {
        phongBanRepository.delete(phongBan);
    }

    @Override
    public PhongBan luuPhongBan(PhongBan phongBan) {
        return phongBanRepository.save(phongBan);
    }
}
