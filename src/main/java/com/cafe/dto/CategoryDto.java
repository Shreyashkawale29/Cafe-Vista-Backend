package com.cafe.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Data
public class CategoryDto {

        private Long id;
        private String name;
        private String description;
        private MultipartFile img;

        private byte[] returnedImg;

    public CategoryDto(Long id, String name, String description, MultipartFile img, byte[] returnedImg) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
        this.returnedImg = returnedImg;
    }

    public CategoryDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    public byte[] getReturnedImg() {
        return returnedImg;
    }


    public void setReturnedImg(byte[] returnedImg) {
        this.returnedImg = returnedImg;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", img=" + img +
                ", returnedImg=" + Arrays.toString(returnedImg) +
                '}';
    }
}
