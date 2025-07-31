
package com.mycompany.kasir_restoran;

public class Makanan extends Menu {
    public Makanan(String nama, int harga) {
        super(nama, harga);
    }

    @Override
    public int hitungHarga(int jumlah) {
        return harga * jumlah;
    }
}