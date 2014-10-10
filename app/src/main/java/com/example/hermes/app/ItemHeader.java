package com.example.hermes.app;

/**
 * Created by Jinhyun on 6/5/14.
 */
public class ItemHeader {
    private String title;
    private String description;
    private String source;
    private String companyName;

    ///=================TODO: CHANGED BY KAREN==================================//
    private String image;

    public ItemHeader(InformationExtractor information) {
        this.title = information.getTitle();
        this.source = information.getSource();
        this.companyName = information.getCompanyName();

        //=================TODO: CHANGED BY KAREN==================================//
        this.image = information.getImage();
        if (this.companyName.equals("CNN")) {
            this.description = information.getDescription(18, "(CNN)");
        }
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSource() {
        return source;
    }

    public String getCompanyName() {
        return companyName;
    }

    //=================TODO: CHANGED BY KAREN==================================//
    public String getImage() {return image; }
}
