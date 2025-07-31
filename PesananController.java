package com.mycompany.kasir_restoran;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PesananController {

    private TableView<DetailPesanan> tablePesanan;
    private ObservableList<DetailPesanan> data = FXCollections.observableArrayList();

    public Scene getScene(Stage stage) {
        Label title = new Label("📋 Daftar Pesanan");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        tablePesanan = new TableView<>();

        // Kolom Meja
        TableColumn<DetailPesanan, Integer> colMeja = new TableColumn<>("Meja");
        colMeja.setCellValueFactory(cell -> cell.getValue().mejaProperty().asObject());

        // Kolom Status dengan ComboBox (edit langsung)
        TableColumn<DetailPesanan, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cell -> cell.getValue().statusProperty());
        colStatus.setCellFactory(ComboBoxTableCell.forTableColumn("Belum Dibayar", "Sudah Dibayar"));
        colStatus.setOnEditCommit(event -> {
            DetailPesanan pesanan = event.getRowValue();
            pesanan.statusProperty().set(event.getNewValue());
            updateStatusPembayaran(pesanan.mejaProperty().get(), event.getNewValue());
        });

        // Kolom Jenis
        TableColumn<DetailPesanan, String> colJenis = new TableColumn<>("Jenis");
        colJenis.setCellValueFactory(cell -> cell.getValue().jenisProperty());

        // Kolom Nama Menu
        TableColumn<DetailPesanan, String> colNama = new TableColumn<>("Nama Menu");
        colNama.setCellValueFactory(cell -> cell.getValue().namaMenuProperty());

        // Kolom Harga
        TableColumn<DetailPesanan, Integer> colHarga = new TableColumn<>("Harga");
        colHarga.setCellValueFactory(cell -> cell.getValue().hargaProperty().asObject());

        // Kolom Jumlah
        TableColumn<DetailPesanan, Integer> colJumlah = new TableColumn<>("Jumlah");
        colJumlah.setCellValueFactory(cell -> cell.getValue().jumlahProperty().asObject());

        // Kolom Total
        TableColumn<DetailPesanan, Integer> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(cell -> cell.getValue().totalHargaProperty().asObject());

        tablePesanan.getColumns().addAll(colMeja, colStatus, colJenis, colNama, colHarga, colJumlah, colTotal);
        tablePesanan.setEditable(true); // penting agar bisa edit

        loadData();

        // Tombol Kembali
        Button btnKembali = new Button("⬅ Kembali");
        btnKembali.setStyle("-fx-background-color: #ff6666; -fx-text-fill: white; -fx-font-weight: bold;");
        btnKembali.setOnAction(e -> stage.setScene(new MenuController().getScene(stage)));

        // Tombol Refresh
        Button btnRefresh = new Button("🔄 Refresh");
        btnRefresh.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRefresh.setOnAction(e -> loadData());

        HBox buttonBar = new HBox(10, btnKembali, btnRefresh);

        VBox root = new VBox(15, title, tablePesanan, buttonBar);
        root.setStyle("-fx-padding: 20px; -fx-background-color: #f8f9fa;");

        return new Scene(root, 950, 550);
    }

    private void loadData() {
        data.clear();
        String sql = 
    "SELECT p.id AS pesanan_id, " +
    "p.meja, p.status, " +
    "m.jenis, m.nama, m.harga, dp.jumlah, " +
    "(m.harga * dp.jumlah) AS total " +
    "FROM detail_pesanan dp " +
    "JOIN menu m ON m.id = dp.menu_id " +
    "JOIN pesanan p ON p.id = dp.pesanan_id " +
    "ORDER BY p.id DESC " +
    "LIMIT 20";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                data.add(new DetailPesanan(
                        rs.getInt("pesanan_id"), // simpan ID untuk update status
                        rs.getInt("meja"),
                        rs.getString("status"),
                        rs.getString("jenis"),
                        rs.getString("nama"),
                        rs.getInt("harga"),
                        rs.getInt("jumlah"),
                        rs.getInt("total")
                ));
            }
            tablePesanan.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fungsi update status pembayaran
    private void updateStatusPembayaran(int pesananId, String statusBaru) {
        String sql = "UPDATE pesanan SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statusBaru);
            ps.setInt(2, pesananId);
            ps.executeUpdate();

            System.out.println("Status pesanan " + pesananId + " berhasil diubah ke: " + statusBaru);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner class untuk menampung data
    public static class DetailPesanan {
        private final int pesananId;
        private final SimpleIntegerProperty meja;
        private final SimpleStringProperty status;
        private final SimpleStringProperty jenis;
        private final SimpleStringProperty namaMenu;
        private final SimpleIntegerProperty harga;
        private final SimpleIntegerProperty jumlah;
        private final SimpleIntegerProperty totalHarga;

        public DetailPesanan(int pesananId, int meja, String status, String jenis, String nama, int harga, int jumlah, int total) {
            this.pesananId = pesananId;
            this.meja = new SimpleIntegerProperty(meja);
            this.status = new SimpleStringProperty(status);
            this.jenis = new SimpleStringProperty(jenis);
            this.namaMenu = new SimpleStringProperty(nama);
            this.harga = new SimpleIntegerProperty(harga);
            this.jumlah = new SimpleIntegerProperty(jumlah);
            this.totalHarga = new SimpleIntegerProperty(total);
        }

        public int getPesananId() { return pesananId; }
        public SimpleIntegerProperty mejaProperty() { return meja; }
        public SimpleStringProperty statusProperty() { return status; }
        public SimpleStringProperty jenisProperty() { return jenis; }
        public SimpleStringProperty namaMenuProperty() { return namaMenu; }
        public SimpleIntegerProperty hargaProperty() { return harga; }
        public SimpleIntegerProperty jumlahProperty() { return jumlah; }
        public SimpleIntegerProperty totalHargaProperty() { return totalHarga; }
    }
}
