# Hafizhoes

Hafizhoes adalah aplikasi desktop Java Swing untuk pengelolaan toko sepatu. Aplikasi ini dibuat dengan form NetBeans dan menggunakan database MySQL sebagai penyimpanan data.

Aplikasi menyediakan halaman login, menu utama, master data barang, pemasok, member, transaksi pembelian, transaksi penjualan, dan kasir.

## Fitur Utama

- Login pengguna dari tabel `pengguna`.
- Dashboard menu utama aplikasi Hafizhoes.
- Pengelolaan data barang sepatu.
- Pengelolaan data pemasok.
- Pengelolaan data member.
- Pencatatan transaksi pembelian barang.
- Pencatatan transaksi penjualan barang.
- Fitur kasir untuk memilih barang, memilih member, menghitung total, dan menyimpan transaksi.
- Detail transaksi pembelian dan penjualan.
- Update stok barang berdasarkan transaksi.

## Teknologi

- Java
- Java Swing
- NetBeans GUI Builder
- MySQL
- MySQL Connector/J

## Struktur Repository

```text
Hafizhoes/
|-- Main.java
|-- Menu.java
|-- Barang.java
|-- Pemasok.java
|-- Member.java
|-- Pembelian.java
|-- Penjualan.java
|-- Kasir.java
|-- TambahPembelian.java
|-- DetPembelian.java
|-- DetPenjualan.java
|-- DetItem.java
|-- PilihBarangKasir.java
|-- PilihBarangPembelian.java
|-- PilihMember.java
|-- *.form
`-- asset gambar
```

Keterangan:

- `Main.java` adalah halaman login dan entry point aplikasi.
- `Menu.java` adalah halaman menu utama setelah login.
- `Barang.java` mengelola data sepatu.
- `Pemasok.java` berisi form pengelolaan data pemasok.
- `Member.java` mengelola data member.
- `Pembelian.java` menampilkan daftar transaksi pembelian.
- `TambahPembelian.java` menyimpan transaksi pembelian baru.
- `Penjualan.java` menampilkan daftar transaksi penjualan.
- `Kasir.java` menangani transaksi kasir/penjualan.
- `DetPembelian.java` dan `DetPenjualan.java` menampilkan detail transaksi.
- File `.form` adalah file layout dari NetBeans GUI Builder.
- File gambar digunakan sebagai icon dan tampilan aplikasi.

## Database

Aplikasi terkoneksi ke MySQL lokal dengan konfigurasi berikut:

```text
Database : toko_sepatu
Host     : localhost
User     : root
Password : kosong
```

Konfigurasi ini terlihat di beberapa file Java melalui URL:

```text
jdbc:mysql://localhost/toko_sepatu
```

Tabel yang digunakan oleh aplikasi:

- `pengguna`
- `barang`
- `pemasok`
- `member`
- `pembelian`
- `det_pembelian`
- `penjualan`
- `det_penjualan`

Catatan: file SQL untuk membuat database belum tersedia di repository ini. Sebelum menjalankan aplikasi, buat database `toko_sepatu` dan tabel-tabel di atas sesuai field yang dipakai di source code.

## Kolom Data yang Dipakai

Beberapa kolom yang terlihat dari kode:

- `pengguna`: `id_pengguna`, `nama_pengguna`, `password`
- `barang`: `id_sepatu`, `merk_sepatu`, `jenis_sepatu`, `uk_sepatu`, `stok`, `harga_jual`
- `pemasok`: `id_pemasok`, `nama_pemasok`
- `member`: `id_member`, `nama_member`
- `pembelian`: `no_nota`, `id_pengguna`, `tgl_pembelian`, `id_pemasok`, `harga_total`
- `det_pembelian`: `id_sepatu`, `no_nota`, `jlh_barang`, `harga_satuan`, `harga_subtotal`
- `penjualan`: `no_nota`, `id_pengguna`, `tgl_penjualan`, `id_member`, `harga_total`
- `det_penjualan`: `id_sepatu`, `no_nota`, `jlh_barang`, `harga_satuan`, `harga_subtotal`

## Cara Menjalankan dengan NetBeans

1. Clone repository:

   ```bash
   git clone https://github.com/IrfanSabrian/Hafizhoes.git
   cd Hafizhoes
   ```

2. Buka NetBeans.

3. Buat project Java baru.

4. Buat package:

   ```text
   TokoSepatu
   ```

5. Masukkan semua file `.java`, `.form`, dan asset gambar ke dalam package `TokoSepatu`.

   Source code di repository ini menggunakan deklarasi:

   ```java
   package TokoSepatu;
   ```

   Asset juga dipanggil dari path resource seperti:

   ```java
   /TokoSepatu/nama-file.png
   ```

6. Tambahkan library MySQL Connector/J ke project.

7. Pastikan MySQL berjalan dan database `toko_sepatu` sudah dibuat.

8. Jalankan class:

   ```text
   TokoSepatu.Main
   ```

## Cara Menjalankan Manual

Jika ingin menjalankan lewat terminal, pastikan JDK dan MySQL Connector/J sudah tersedia.

Contoh compile:

```bash
javac -cp ".;mysql-connector-j-8.x.x.jar" -d build *.java
```

Jika compile manual menampilkan error nama class dan nama file tidak sesuai, pastikan setiap `public class` berada di file dengan nama yang sama. Pada source saat ini, form pemasok memakai class `Pengguna`.

Contoh run:

```bash
java -cp "build;mysql-connector-j-8.x.x.jar" TokoSepatu.Main
```

Catatan untuk Windows gunakan pemisah classpath `;`, sedangkan macOS/Linux gunakan `:`.

## Alur Penggunaan

1. Jalankan aplikasi.
2. Login menggunakan akun dari tabel `pengguna`.
3. Setelah berhasil login, aplikasi membuka menu utama.
4. Pilih menu sesuai kebutuhan:
   - `Pemasok`
   - `Pembelian`
   - `Barang`
   - `Member`
   - `Kasir`
   - `Penjualan`
5. Setiap transaksi pembelian atau penjualan akan mempengaruhi data transaksi dan stok barang.

## Catatan Penting

- Repository ini belum menyertakan file schema database `.sql`.
- Koneksi database masih hardcoded ke `localhost/toko_sepatu` dengan user `root` dan password kosong.
- Password pengguna masih dibandingkan langsung dari database, belum memakai hashing.
- Beberapa query masih disusun dengan gabungan string, sehingga perlu diperbaiki dengan `PreparedStatement` jika ingin dipakai untuk production.
- Project ini paling mudah dibuka menggunakan NetBeans karena layout UI disimpan dalam file `.form`.

## Lisensi

Lisensi belum ditentukan.
