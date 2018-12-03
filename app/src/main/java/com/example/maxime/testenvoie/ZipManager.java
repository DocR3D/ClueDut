package com.example.maxime.testenvoie;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class ZipManager {

    public void zip(String[] _files, String zipFileName) {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[1024];

            for (int i = 0; i < _files.length; i++) {
                Log.v("Compress", "Adding: " + _files[i]);
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, 1024);

                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void unzip(String _zipFile, String _targetLocation) {

        try {
            int tailleGlabole = 0;
            int taille = 0;
            FileInputStream fin = new FileInputStream(_zipFile);
            File fileRar = new File(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {

                //create dir if required while unzipping
                if (ze.isDirectory()) {
                } else {
                    FileOutputStream fout = new FileOutputStream(_targetLocation + ze.getName());
                    File file = new File(_targetLocation + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                        taille = (int) ((file.length() * 100) / fileRar.length());
                    }
                    tailleGlabole += taille;
                    SalonActivityClient.progressBar.setProgress(tailleGlabole);
                    zin.closeEntry();
                    fout.close();
                }
            }
            SalonActivityClient.progressBar.setProgress(100);
            SalonActivityClient.ready.setEnabled(true);
            System.out.println("Fin");
            zin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}