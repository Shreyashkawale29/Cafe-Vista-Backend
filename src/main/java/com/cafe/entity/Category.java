package com.cafe.entity;

import com.cafe.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;

@Entity
@Data
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String name;
    private String description;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] img;

    public CategoryDto getcategoryDto(){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        categoryDto.setReturnedImg(img);  // Corrected setter
        return categoryDto;
    }



    public Category() {

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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Category(Long id, String name, String description, byte[] img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", img=" + Arrays.toString(img) +
                '}';
    }


}
