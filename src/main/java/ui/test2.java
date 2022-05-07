package ui;

import jrcet.frame.tools.Dencrypt.Ascii.Ascii;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

public class test2 {

    public static void main(String[] args) {
        String res1 = Ascii.char2Ascii("asdfqwxawe!23","");
        System.out.print(res1+"\n");
        String res2 = Ascii.ascii2Char(res1, " ");
        System.out.print(res2);
    }

}