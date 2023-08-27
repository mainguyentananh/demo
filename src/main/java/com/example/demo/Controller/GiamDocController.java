package com.example.demo.Controller;

import com.example.demo.Response.ResponseEntityV2;
import com.example.demo.Service.GiamDocService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@Log4j2
@RequestMapping("/api/giam-doc")
public class GiamDocController {

    @Autowired
    private GiamDocService giamDocService;

    @PostMapping("/upload")
    public ResponseEntityV2<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Vui Long Chon File");
        }
        String[] extension = file.getOriginalFilename().split("\\.");
        if (!extension[1].equalsIgnoreCase("xlsx")) {
            return new ResponseEntityV2<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "File Sai Dinh Dang");
        }
        try {
            String projectPath = System.getProperty("user.dir") + "\\temp";
            File uploadDir = new File(projectPath);
            if (uploadDir.exists()) {
                uploadDir.delete();
            }
            uploadDir.mkdirs();
            File tempFile = new File(uploadDir, Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(tempFile);
            return new ResponseEntityV2<>("Luu file thanh cong");
        } catch (IOException e) {
            return new ResponseEntityV2<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.name(),"Lỗi tải File");
        }
    }


    @GetMapping("/trigger")
    @Scheduled(cron = "${config.job}")
    public ResponseEntityV2<?> trigger() throws IOException {
        String message = giamDocService.capNhatLuong();
        log.error(message);
        return new ResponseEntityV2<>(message);
    }

}
