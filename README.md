# 🍽️ Aplikasi Kasir Restoran (JavaFX + MySQL)

## 📌 Deskripsi Singkat
Aplikasi ini adalah sistem desktop sederhana berbasis **Java** dengan tampilan **JavaFX** (tanpa FXML) dan database **MySQL** yang dirancang untuk mengelola pemesanan dan pembayaran makanan serta minuman di restoran.  
Aplikasi dibangun menggunakan konsep **Object-Oriented Programming (OOP)** dan koneksi database melalui **JDBC**.

---

## ✨ Fitur Utama
- 📝 Menambahkan pesanan baru berdasarkan nomor meja.
- 🍔 Menambahkan menu makanan/minuman baru.
- 💰 Menghitung total harga pesanan secara otomatis.
- 📋 Menampilkan daftar pesanan terbaru.
- 🔄 Mengubah status pembayaran pesanan.
- 🎨 Tampilan antarmuka sederhana dan mudah digunakan.

---

## 📂 Struktur Program
| **Kelas**             | **Fungsi** |
|----------------------|------------|
| **MainApp**          | Menjalankan aplikasi utama JavaFX. |
| **Menu** *(Abstract)* | Kelas abstrak untuk makanan/minuman, memiliki atribut dasar dan metode `hitungHarga()`. |
| **Makanan**          | Turunan dari `Menu` khusus untuk jenis makanan. |
| **Minuman**          | Turunan dari `Menu` khusus untuk jenis minuman. |
| **DatabaseConnection** | Mengelola koneksi database MySQL menggunakan JDBC. |
| **MenuController**   | Mengelola input pesanan, menyimpan data ke database, dan navigasi ke daftar pesanan. |
| **PesananController** | Menampilkan data pesanan terbaru, mengubah status pembayaran, dan kembali ke menu utama. |

---

## 🛠️ Teknologi yang Digunakan
- **Bahasa Pemrograman**: Java
- **Framework UI**: JavaFX (tanpa FXML)
- **Database**: MySQL (via Laragon)
- **IDE**: NetBeans
- **Library Eksternal**: `mysql-connector-j-8.0.33.jar`

---

🧩 Penerapan OOP
Encapsulation

Atribut disimpan sebagai private dan diakses melalui getter/setter.

Abstraction

Kelas Menu dibuat abstrak untuk mendefinisikan metode hitungHarga().

Inheritance

Kelas Makanan dan Minuman adalah turunan dari kelas Menu.

Polymorphism

Metode hitungHarga() diimplementasikan sesuai kebutuhan oleh kelas turunan.

📸 Hasil Run Aplikasi

Untuk melihat hasil run aplikasi secara lengkap, beriku merupakan link drivenya:

https://drive.google.com/drive/folders/1gmwP1T1Gw3j9KjdKIxvaEtZ5amQze1Fz?usp=drive_link

