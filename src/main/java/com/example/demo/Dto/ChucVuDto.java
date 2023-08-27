package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChucVuDto {
    private Long id;
    private String tenChucVu;
    private String thamQuyen;
}
