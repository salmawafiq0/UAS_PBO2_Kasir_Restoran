
package com.mycompany.kasir_restoran;

public abstract class Menu {
    protected String nama;
    protected int harga;

    public Menu(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public abstract int hitungHarga(int jumlah);

    public String getNama() { return nama; }
    public int getHarga() { return harga; }
}

