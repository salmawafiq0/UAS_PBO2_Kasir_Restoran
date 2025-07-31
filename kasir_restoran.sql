-- kasir_restoran.sql
-- Struktur database untuk aplikasi Kasir Restoran

-- Membuat database jika belum ada
CREATE DATABASE IF NOT EXISTS kasir_restoran;
USE kasir_restoran;

-- Tabel menu: Menyimpan semua jenis makanan dan minuman
CREATE TABLE IF NOT EXISTS menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    harga INT NOT NULL,
    jenis ENUM('Makanan', 'Minuman') NOT NULL
);

-- Tabel pesanan: Menyimpan data transaksi per meja
CREATE TABLE IF NOT EXISTS pesanan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    meja INT NOT NULL,
    status VARCHAR(50) DEFAULT 'Belum Dibayar'
);

-- Tabel detail_pesanan: Menyimpan detail menu yang dipesan
CREATE TABLE IF NOT EXISTS detail_pesanan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pesanan_id INT NOT NULL,
    menu_id INT NOT NULL,
    jumlah INT NOT NULL,
    FOREIGN KEY (pesanan_id) REFERENCES pesanan(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE
);
