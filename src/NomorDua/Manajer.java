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
public class Manajer extends Pegawai{
    private int tunjangan;
    
    public int getTunjangan(){
        return tunjangan;
    }
    
    public void setTunjangan(int tunjangan){
        this.tunjangan = tunjangan;
    }
    
    public Manajer(String nama, int gaji, int tunjangan) {
        super(nama, gaji);
        this.tunjangan = tunjangan;
    }
    
    public static void main(String[] args) {
        // Membuat objek Manajer
        Manajer manajer = new Manajer("John", 5000000, 1000000);

        // Menampilkan atribut nama, gaji, dan tunjangan dari class manajer
        System.out.println("Nama: " + manajer.getNama());
        System.out.println("Gaji: " + manajer.getGaji());
        System.out.println("Tunjangan: " + manajer.getTunjangan());
    }
}
