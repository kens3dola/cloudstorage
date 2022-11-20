package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

@Data
public class File {
    private int fileId;
    private String filename;
    private String filesize;
    private int userid;
    private byte[] filedata;
}
