package com.mycompany.kasir_restoran;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class MenuController {

    private TextField tfNama, tfHarga, tfJumlah, tfMeja;
    private ComboBox<String> cbJenis;
    private Label labelStatus;

    public Scene getScene(Stage stage) {
        Label title = new Label("🍽 Kasir Restoran");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        tfMeja = new TextField();
        tfMeja.setPromptText("No Meja");

        cbJenis = new ComboBox<>();
        cbJenis.getItems().addAll("Makanan", "Minuman");
        cbJenis.setPromptText("Jenis");

        tfNama = new TextField();
        tfNama.setPromptText("Nama Menu");

        tfHarga = new TextField();
        tfHarga.setPromptText("Harga");

        tfJumlah = new TextField();
        tfJumlah.setPromptText("Jumlah");

        Button btnTambah = new Button("➕ Tambah");
        btnTambah.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnTambah.setOnAction(e -> handleTambah());

        Button btnLihat = new Button("📋 Lihat Pesanan");
        btnLihat.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLihat.setOnAction(e -> stage.setScene(new PesananController().getScene(stage)));

        labelStatus = new Label();
        labelStatus.setStyle("-fx-text-fill: #8e44ad; -fx-font-weight: bold;");

        HBox inputRow = new HBox(10, tfMeja, cbJenis, tfNama, tfHarga, tfJumlah);
        inputRow.setStyle("-fx-padding: 10px; -fx-background-color: #ecf0f1; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        inputRow.setPrefHeight(50);

        HBox buttonRow = new HBox(10, btnTambah, btnLihat);
        buttonRow.setStyle("-fx-padding: 10px;");

        VBox root = new VBox(15, title, inputRow, buttonRow, labelStatus);
        root.setStyle("-fx-padding: 20px; -fx-background-color: #f9f9f9;");
        root.setPrefWidth(850);

        return new Scene(root, 900, 300);
    }

    private void handleTambah() {
        try {
            String jenis = cbJenis.getValue();
            String nama = tfNama.getText();
            int harga = Integer.parseInt(tfHarga.getText());
            int jumlah = Integer.parseInt(tfJumlah.getText());
            int meja = Integer.parseInt(tfMeja.getText());

            if (jenis == null || nama.isEmpty()) {
                labelStatus.setText("⚠️ Harap isi semua field!");
                return;
            }

            Menu item = jenis.equals("Makanan") ? new Makanan(nama, harga) : new Minuman(nama, harga);
            int total = item.hitungHarga(jumlah);

            Connection conn = DatabaseConnection.getConnection();

            // Insert ke pesanan
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO pesanan (meja, status) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, meja);
            ps.setString(2, "Belum Dibayar");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int pesananId = rs.getInt(1);

            // Insert menu
            PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO menu (nama, harga, jenis) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, nama);
            ps2.setInt(2, harga);
            ps2.setString(3, jenis);
            ps2.executeUpdate();
            ResultSet rs2 = ps2.getGeneratedKeys();
            rs2.next();
            int menuId = rs2.getInt(1);

            // Insert detail pesanan
            PreparedStatement ps3 = conn.prepareStatement(
                    "INSERT INTO detail_pesanan (pesanan_id, menu_id, jumlah) VALUES (?, ?, ?)");
            ps3.setInt(1, pesananId);
            ps3.setInt(2, menuId);
            ps3.setInt(3, jumlah);
            ps3.executeUpdate();

            labelStatus.setText("✅ Pesanan berhasil ditambahkan! Total: Rp " + total);

            // Bersihkan input
            tfNama.clear();
            tfHarga.clear();
            tfJumlah.clear();
            tfMeja.clear();
            cbJenis.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
            labelStatus.setText("❌ Terjadi kesalahan saat menyimpan pesanan.");
        }
    }
}
