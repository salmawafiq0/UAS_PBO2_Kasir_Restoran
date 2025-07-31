
package com.mycompany.kasir_restoran;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DetailPesanan {
    private final SimpleStringProperty namaMenu;
    private final SimpleIntegerProperty jumlah;
    private final SimpleIntegerProperty totalHarga;

    public DetailPesanan(String namaMenu, int jumlah, int totalHarga) {
        this.namaMenu = new SimpleStringProperty(namaMenu);
        this.jumlah = new SimpleIntegerProperty(jumlah);
        this.totalHarga = new SimpleIntegerProperty(totalHarga);
    }

    public String getNamaMenu() {
        return namaMenu.get();
    }

    public int getJumlah() {
        return jumlah.get();
    }

    public int getTotalHarga() {
        return totalHarga.get();
    }

    public SimpleStringProperty namaMenuProperty() {
        return namaMenu;
    }

    public SimpleIntegerProperty jumlahProperty() {
        return jumlah;
    }

    public SimpleIntegerProperty totalHargaProperty() {
        return totalHarga;
    }
}
