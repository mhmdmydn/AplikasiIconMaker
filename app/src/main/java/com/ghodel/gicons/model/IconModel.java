package com.ghodel.gicons.model;

import com.caverock.androidsvg.SVG;

public class IconModel {
    private String file;
    private String fileName;
    private SVG iconSVG;
    
    public void setFile(String file){
        this.file = file;
    }
    
    public String getFile(){
        return this.file;
    }
    
    public String getFileName() {
        int lastIndexOf = getFile().lastIndexOf(".");
        return lastIndexOf >= 0 ? getFile().substring(0, lastIndexOf) : this.file;
    }
    
    public void setFilePath(String fileName){
        this.fileName = fileName;
    }
    
    public String getFilePath(){
        return this.fileName;
    }
    
    public void setSVG(SVG iconSVG){
        this.iconSVG = iconSVG;
    }
    
    public SVG getSVG(){
        return this.iconSVG;
    }

    @Override
    public String toString() {
        return file + fileName + iconSVG;
    }
}
