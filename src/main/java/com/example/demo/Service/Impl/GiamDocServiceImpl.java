package com.example.demo.Service.Impl;

import com.example.demo.Repository.NhanVienRepository;
import com.example.demo.Service.GiamDocService;
import com.example.demo.Service.NhanVienService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

@Service
public class GiamDocServiceImpl implements GiamDocService {

    @Autowired
    private NhanVienService nhanVienService;

    @Override
    public String capNhatLuong() throws IOException {
        LocalDate localDate = LocalDate.now();
        String[] date = localDate.toString().split("-");
        String fileName = String.format("BangLuongThang_%s_%s.xlsx", date[1], date[0]);
        String projectPath = System.getProperty("user.dir") + "\\temp";
        File tempFile = new File(projectPath, fileName);
        if (tempFile.exists()) {
            FileInputStream inputStream = new FileInputStream(tempFile);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            HashMap<String, Double> listResult = new HashMap<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (ObjectUtils.isEmpty(row)) {
                    continue;
                }
                String cellMaNV = row.getCell(0).getStringCellValue();
                double cellLuong = row.getCell(1).getNumericCellValue();
                listResult.put(cellMaNV, cellLuong);
            }
            nhanVienService.updateLuong(listResult);
            return "thanh cong";
        }
        return "file không tồn tại";
    }
}
