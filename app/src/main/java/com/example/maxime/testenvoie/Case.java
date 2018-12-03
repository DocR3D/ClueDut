package com.example.maxime.testenvoie;

import android.widget.ImageButton;

public class Case {

    private boolean clickable;
    private ImageButton image;

    public Case(ImageButton pImage) {
        clickable = false;
        image = pImage;
    }

    public boolean image(){
        if (this.image != null)
            return true;
        return false;
    }

    public ImageButton getImage(){
        return image;
    }

    public void setImage(ImageButton pImage){
        image = pImage;
    }

    public boolean getClickable(){
        return clickable;
    }

    public void caseClickable(){
        clickable = true;
    }

    public void caseNonClickable(){
        clickable = false;
    }
}
