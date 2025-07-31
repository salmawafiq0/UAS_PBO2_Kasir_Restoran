
package com.mycompany.kasir_restoran;

public class Minuman extends Menu {
    public Minuman(String nama, int harga) {
        super(nama, harga);
    }

    @Override
    public int hitungHarga(int jumlah) {
        return harga * jumlah;
    }
}
