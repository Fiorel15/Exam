/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NomorDua;

/**
 *
 * @author ASUS
 */
public class Pegawai {
    private String nama; 
    private int gaji; 

    public Pegawai(String nama, int gaji) { 
        this.nama = nama; 
        this.gaji = gaji; 
    } 

    public String getNama() { 
        return nama; 
    } 

    public void setNama(String nama) { 
        this.nama = nama; 
    } 

    public int getGaji() { 
        return gaji; 
    } 

    public void setGaji(int gaji) { 
        this.gaji = gaji; 
    } 
}
