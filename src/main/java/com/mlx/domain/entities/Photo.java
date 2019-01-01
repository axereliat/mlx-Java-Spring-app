package com.mlx.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    private Integer id;

    private String url;

    private Ad ad;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_id")
    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
