package com.mc.demo.ergon.dtos;

public class ActivityStatusDto {
    private String id;
    private String description;
    private String color;

    public ActivityStatusDto() {}

    public ActivityStatusDto(String id, String description, String color) {
        this.id = id;
        this.description = description;
        this.color = color;
    }
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
