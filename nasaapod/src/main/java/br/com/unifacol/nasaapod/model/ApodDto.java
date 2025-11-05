package br.com.unifacol.nasaapod.model;

import lombok.Data;

@Data
public class ApodDto {
    private String date;
    private String title;
    private String explanation;
    private String url;
    private String hdurl;
    private String media_type;
}
