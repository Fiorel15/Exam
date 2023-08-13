/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NomorSatu;

/**
 *
 * @author ASUS
 */
public class Mahasiswa {
    private String nama; 
    private String nim; 

    public String getNama() { 
        return nama; 
    } 

    public void setNama(String nama) { 
        this.nama = nama; 
    }
    
    public String nim(){
        return nim;
    } 
    
    public void setNim (String nim){
        this.nim = nim;
    }
    
    public void displayMahasiswa() {
        System.out.println("Nama: " + nama);
        System.out.println("NIM: " + nim);
    }
    
    public static void main(String[] args) {
        // Membuat objek Mahasiswa
        Mahasiswa mahasiswa = new Mahasiswa();
        
        // Mengatur atribut nama dan nim
        mahasiswa.setNama("Fiorel Al Zahra");
        mahasiswa.setNim("2602236672");
        
        // Menampilkan atribut nama dan nim menggunakan method displayMahasiswa
        mahasiswa.displayMahasiswa();
    }
}
