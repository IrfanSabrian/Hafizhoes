-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 12 Feb 2024 pada 08.15
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `toko_sepatu`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `id_sepatu` char(5) NOT NULL,
  `merk_sepatu` varchar(50) DEFAULT NULL,
  `jenis_sepatu` varchar(50) DEFAULT NULL,
  `uk_sepatu` varchar(20) DEFAULT NULL,
  `stok` int(5) DEFAULT NULL,
  `harga_jual` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`id_sepatu`, `merk_sepatu`, `jenis_sepatu`, `uk_sepatu`, `stok`, `harga_jual`) VALUES
('AD001', 'Adidas', 'Sport', '39', 25, 600000.00),
('AD002', 'Adidas', 'Sport', '42', 45, 600000.00),
('AD003', 'Adidas', 'Sport', '39', 20, 480000.00),
('AD004', 'Adidas', 'Sport', '42', 25, 538800.00),
('AS011', 'Asics', 'Sport', '44', 15, 514800.00),
('AS012', 'Asics', 'Sport', '40', 10, 574800.00),
('CK009', 'Clarks', 'Formal', '39', 30, 634800.00),
('CK010', 'Clarks', 'Formal', '40', 17, 634800.00),
('CK011', 'Clarks', 'Formal', '41', 17, 694800.00),
('CV005', 'Converse', 'Sneakers', '37', 34, 274800.00),
('CV006', 'Converse', 'Sneakers', '38', 10, 480000.00),
('CV007', 'Converse', 'Sneakers', '40', 40, 720000.00),
('DM014', 'Dr. Martens', 'Formal', '40', 22, 706800.00),
('DM015', 'Dr. Martens', 'Formal', '39', 21, 706800.00),
('DM016', 'Dr. Martens', 'Formal', '40', 36, 754800.00),
('FL013', 'Fila', 'Sneakers', '37', 34, 322800.00),
('FL014', 'Fila', 'Sneakers', '38', 39, 358800.00),
('NB008', 'New Balance', 'Sneakers', '44', 27, 346800.00),
('NB009', 'New Balance', 'Sneakers', '43', 17, 346800.00),
('NB010', 'New Balance', 'Sneakers', '44', 5, 382800.00),
('NS001', 'Nike', 'Sneakers', '39', 11, 358800.00),
('NS002', 'Nike', 'Sneakers', '40', 38, 358800.00),
('NS003', 'Nike', 'Sneakers', '41', 4, 394800.00),
('PS003', 'Puma', 'Casual', '38', 21, 238800.00),
('PS004', 'Puma', 'Casual', '39', 14, 238800.00),
('PS005', 'Puma', 'Casual', '40', 20, 660000.00),
('RB004', 'Reebok', 'Formal', '41', 32, 598800.00),
('RB005', 'Reebok', 'Formal', '42', 20, 598800.00),
('RB006', 'Reebok', 'Formal', '43', 3, 658800.00),
('SK007', 'Skechers', 'Sport', '43', 34, 418800.00),
('SK008', 'Skechers', 'Sport', '44', 4, 418800.00),
('SW011', 'Sandalwood', 'Casual', '36', 35, 214800.00),
('SW012', 'Sandalwood', 'Casual', '37', 5, 214800.00),
('UA013', 'Under Armour', 'Sport', '42', 38, 538800.00),
('UA014', 'Under Armour', 'Sport', '43', 16, 538800.00),
('VN006', 'Vans', 'Casual', '40', 7, 298800.00),
('VN007', 'Vans', 'Casual', '41', 56, 334800.00),
('VN015', 'Vans', 'Sneakers', '43', 29, 262800.00),
('VN016', 'Vans', 'Sneakers', '40', 20, 600000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `det_pembelian`
--

CREATE TABLE `det_pembelian` (
  `no_pembelian` int(11) NOT NULL,
  `no_nota` varchar(20) DEFAULT NULL,
  `id_sepatu` char(5) NOT NULL,
  `jlh_barang` int(11) DEFAULT NULL,
  `harga_satuan` decimal(15,2) DEFAULT NULL,
  `harga_subtotal` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `det_pembelian`
--

INSERT INTO `det_pembelian` (`no_pembelian`, `no_nota`, `id_sepatu`, `jlh_barang`, `harga_satuan`, `harga_subtotal`) VALUES
(1, 'PB001', 'AD003', 17, 399000.00, 6783000.00),
(2, 'PB001', 'NS001', 11, 299000.00, 3289000.00),
(3, 'PB001', 'VN006', 7, 249000.00, 1743000.00),
(4, 'PB002', 'AD002', 31, 399000.00, 12369000.00),
(5, 'PB002', 'AD004', 28, 449000.00, 12572000.00),
(6, 'PB002', 'PS004', 14, 199000.00, 2786000.00),
(7, 'PB003', 'AS011', 15, 429000.00, 6435000.00),
(8, 'PB003', 'NS003', 4, 329000.00, 1316000.00),
(9, 'PB003', 'PS003', 21, 199000.00, 4179000.00),
(10, 'PB003', 'UA014', 16, 449000.00, 7184000.00),
(11, 'PB003', 'VN007', 26, 249000.00, 6474000.00),
(12, 'PB004', 'AS012', 1, 479000.00, 479000.00),
(13, 'PB004', 'RB004', 32, 499000.00, 15968000.00),
(14, 'PB005', 'CK010', 17, 529000.00, 8993000.00),
(15, 'PB005', 'CV005', 34, 229000.00, 7786000.00),
(16, 'PB005', 'SK008', 4, 349000.00, 1396000.00),
(17, 'PB006', 'CK011', 17, 579000.00, 9843000.00),
(18, 'PB006', 'RB005', 20, 499000.00, 9980000.00),
(19, 'PB007', 'CV006', 3, 229000.00, 687000.00),
(20, 'PB007', 'SK007', 34, 349000.00, 11866000.00),
(21, 'PB007', 'VN007', 30, 279000.00, 8370000.00),
(22, 'PB008', 'DM015', 21, 589000.00, 12369000.00),
(23, 'PB008', 'NB008', 27, 289000.00, 7803000.00),
(24, 'PB008', 'SW012', 5, 179000.00, 895000.00),
(25, 'PB009', 'CK009', 35, 529000.00, 18515000.00),
(26, 'PB009', 'DM016', 36, 629000.00, 22644000.00),
(27, 'PB009', 'RB006', 3, 549000.00, 1647000.00),
(28, 'PB010', 'AS010', 12, 429000.00, 5148000.00),
(29, 'PB010', 'FL013', 17, 269000.00, 4573000.00),
(30, 'PB011', 'FL014', 39, 299000.00, 11661000.00),
(31, 'PB011', 'SW011', 35, 179000.00, 6265000.00),
(32, 'PB012', 'FL013', 17, 269000.00, 4573000.00),
(33, 'PB012', 'NB009', 17, 289000.00, 4913000.00),
(34, 'PB013', 'NB010', 5, 319000.00, 1595000.00),
(35, 'PB013', 'UA013', 38, 449000.00, 17062000.00),
(36, 'PB014', 'DM014', 22, 589000.00, 12958000.00),
(37, 'PB014', 'NS002', 38, 299000.00, 11362000.00),
(38, 'PB015', 'VN015', 29, 219000.00, 6351000.00),
(92, 'PB016', 'AD002', 30, 400000.00, 12000000.00),
(93, 'PB016', 'CV007', 40, 600000.00, 24000000.00),
(94, 'PB016', 'VN016', 20, 500000.00, 10000000.00),
(95, 'PB016', 'PS005', 20, 550000.00, 11000000.00),
(96, 'PB017', 'AS012', 9, 479000.00, 4311000.00),
(97, 'PB017', 'CV006', 7, 400000.00, 2800000.00),
(98, 'PB018', 'AD003', 3, 400000.00, 1200000.00),
(99, 'PB018', 'AD001', 15, 500000.00, 7500000.00),
(116, 'PB019', 'AD001', 5, 400000.00, 2000000.00),
(117, 'PB019', 'AD005', 20, 500000.00, 10000000.00),
(118, 'PB020', 'AD001', 10, 500000.00, 5000000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `det_penjualan`
--

CREATE TABLE `det_penjualan` (
  `no_penjualan` int(11) NOT NULL,
  `no_nota` varchar(20) DEFAULT NULL,
  `id_sepatu` char(5) NOT NULL,
  `jlh_barang` int(11) DEFAULT NULL,
  `harga_satuan` decimal(15,2) DEFAULT NULL,
  `harga_subtotal` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `det_penjualan`
--

INSERT INTO `det_penjualan` (`no_penjualan`, `no_nota`, `id_sepatu`, `jlh_barang`, `harga_satuan`, `harga_subtotal`) VALUES
(1, 'JL001', 'DM016', 3, 729000.00, 2187000.00),
(2, 'JL001', 'NS001', 4, 399000.00, 1596000.00),
(3, 'JL002', 'AD002', 3, 499000.00, 1497000.00),
(4, 'JL002', 'CV006', 1, 329000.00, 329000.00),
(5, 'JL002', 'CV007', 4, 379000.00, 1516000.00),
(6, 'JL002', 'AD002', 1, 499000.00, 1497000.00),
(7, 'JL003', 'FL014', 3, 399000.00, 1197000.00),
(8, 'JL003', 'PS003', 2, 299000.00, 598000.00),
(9, 'JL004', 'RB004', 2, 599000.00, 1198000.00),
(10, 'JL005', 'SK008', 3, 499000.00, 1497000.00),
(11, 'JL006', 'VN016', 5, 319000.00, 1595000.00),
(12, 'JL007', 'SK007', 2, 449000.00, 898000.00),
(13, 'JL007', 'VN007', 3, 349000.00, 1047000.00),
(14, 'JL008', 'SW012', 4, 279000.00, 1116000.00),
(15, 'JL008', 'UA014', 4, 549000.00, 2196000.00),
(16, 'JL008', 'NB008', 5, 389000.00, 1945000.00),
(17, 'JL009', 'RB006', 2, 649000.00, 1298000.00),
(18, 'JL010', 'CK010', 4, 629000.00, 2516000.00),
(19, 'JL010', 'PS004', 3, 299000.00, 897000.00),
(20, 'JL011', 'PS005', 4, 329000.00, 1316000.00),
(21, 'JL011', 'SW011', 1, 279000.00, 279000.00),
(22, 'JL012', 'FL013', 2, 369000.00, 738000.00),
(23, 'JL012', 'NS002', 5, 399000.00, 1995000.00),
(24, 'JL012', 'NS003', 2, 429000.00, 858000.00),
(25, 'JL013', 'NB009', 1, 389000.00, 389000.00),
(26, 'JL013', 'NB010', 4, 419000.00, 1676000.00),
(27, 'JL013', 'UA013', 4, 549000.00, 2196000.00),
(28, 'JL014', 'AD004', 2, 549000.00, 1098000.00),
(29, 'JL014', 'DM014', 4, 689000.00, 2756000.00),
(30, 'JL014', 'DM015', 2, 689000.00, 1378000.00),
(31, 'JL014', 'FL013', 3, 369000.00, 1107000.00),
(32, 'JL015', 'AD002', 1, 480000.00, 480000.00),
(33, 'JL016', 'AD003', 2, 480000.00, 960000.00),
(34, 'JL016', 'AS010', 2, 514800.00, 1029600.00),
(36, 'JL017', 'AD002', 6, 600000.00, 3600000.00),
(37, 'JL017', 'AD004', 3, 538800.00, 1616400.00),
(38, 'JL018', 'AD001', 5, 480000.00, 2400000.00),
(39, 'JL018', 'AD002', 10, 600000.00, 6000000.00),
(40, 'JL019', 'CK009', 5, 634800.00, 3174000.00),
(41, 'JL019', 'AD001', 5, 480000.00, 2400000.00),
(42, 'JL020', 'AD001', 5, 600000.00, 3000000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `member`
--

CREATE TABLE `member` (
  `id_member` int(5) NOT NULL,
  `nama_member` varchar(50) DEFAULT NULL,
  `no_telp` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `member`
--

INSERT INTO `member` (`id_member`, `nama_member`, `no_telp`) VALUES
(0, 'Umum', '-'),
(1, 'Gita Permata', '081234567890'),
(2, 'Agus Santoso', '081234567891'),
(3, 'Kartika Purnama', '081234567898'),
(4, 'Hadi Sutanto', '081776655443'),
(5, 'Oscar Susanto', '081998877654'),
(6, 'Lia Agustina', '082345678905'),
(7, 'Budi Prasetyo', '085678912345'),
(8, 'Eka Putri', '081998877665'),
(9, 'Joko Susilo', '085667788990'),
(10, 'Dewi Lestari', '087654321098'),
(11, 'Mulyadi Wibowo', '081223344556'),
(12, 'Citra Wijaya', '081112233445'),
(13, 'Nia Aulia', '087654321001'),
(14, 'Fandi Nugroho', '082345678901'),
(15, 'Ira Maulida', '081122334455'),
(16, 'Irfan', '089508669956'),
(17, 'Dika', '08123456123');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pemasok`
--

CREATE TABLE `pemasok` (
  `id_pemasok` int(5) NOT NULL,
  `nama_pemasok` varchar(30) DEFAULT NULL,
  `no_telp` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pemasok`
--

INSERT INTO `pemasok` (`id_pemasok`, `nama_pemasok`, `no_telp`) VALUES
(1, 'SupplierA', '12345'),
(2, 'SupplierB', '67890'),
(3, 'SupplierC', '54321'),
(4, 'SupplierD', '98765'),
(5, 'SupplierE', '34567'),
(6, 'SupplierF', '87654'),
(7, 'SupplierG', '23456'),
(8, 'SupplierH', '78901'),
(9, 'SupplierI', '10987'),
(10, 'SupplierJ', '56789'),
(11, 'SupplierK', '45678'),
(12, 'SupplierL', '32109'),
(13, 'SupplierM', '21098'),
(14, 'SupplierN', '76543'),
(15, 'SupplierO', '98765'),
(16, 'Hafizh', '14022');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembelian`
--

CREATE TABLE `pembelian` (
  `no_nota` varchar(20) NOT NULL,
  `id_pengguna` int(5) DEFAULT NULL,
  `tgl_pembelian` date NOT NULL,
  `id_pemasok` int(5) DEFAULT NULL,
  `harga_total` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pembelian`
--

INSERT INTO `pembelian` (`no_nota`, `id_pengguna`, `tgl_pembelian`, `id_pemasok`, `harga_total`) VALUES
('PB001', 5, '2024-01-01', 1, 13131000.00),
('PB002', 7, '2024-01-01', 2, 27727000.00),
('PB003', 2, '2024-01-01', 3, 31569000.00),
('PB004', 8, '2024-01-02', 4, 16447000.00),
('PB005', 3, '2024-01-02', 5, 18175000.00),
('PB006', 4, '2024-01-02', 6, 19823000.00),
('PB007', 9, '2024-01-02', 7, 20923000.00),
('PB008', 6, '2024-01-03', 8, 21067000.00),
('PB009', 10, '2024-01-03', 9, 42806000.00),
('PB010', 11, '2024-01-03', 10, 9721000.00),
('PB011', 14, '2024-01-04', 11, 17926000.00),
('PB012', 1, '2024-01-04', 12, 5451000.00),
('PB013', 15, '2024-01-05', 13, 18657000.00),
('PB014', 12, '2024-01-05', 14, 24320000.00),
('PB015', 13, '2024-01-05', 15, 6351000.00),
('PB016', 1, '2024-02-11', 15, 57000000.00),
('PB017', 1, '2024-02-11', 4, 7111000.00),
('PB018', 5, '2024-02-12', 2, 8700000.00),
('PB019', 7, '2024-02-12', 16, 12000000.00),
('PB020', 12, '2024-02-12', 15, 5000000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pengguna`
--

CREATE TABLE `pengguna` (
  `id_pengguna` int(5) NOT NULL,
  `nama_pengguna` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pengguna`
--

INSERT INTO `pengguna` (`id_pengguna`, `nama_pengguna`, `password`) VALUES
(1, 'AdminAgus', 'admin123'),
(2, 'AdminBudi', 'admin456'),
(3, 'AdminCitra', 'admin789'),
(4, 'AdminDewi', 'admin321'),
(5, 'AdminEka', 'admin654'),
(6, 'AdminFandi', 'admin987'),
(7, 'AdminGita', 'admin1234'),
(8, 'AdminHadi', 'admin567'),
(9, 'AdminIra', 'admin890'),
(10, 'AdminJoko', 'admin12345'),
(11, 'AdminKartika', 'admin678'),
(12, 'AdminLia', 'admin910'),
(13, 'AdminMulyadi', 'admin234'),
(14, 'AdminNia', 'admin5678'),
(15, 'AdminOscar', 'admin901');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `no_nota` varchar(20) NOT NULL,
  `id_pengguna` int(5) DEFAULT NULL,
  `tgl_penjualan` date NOT NULL,
  `id_member` int(5) DEFAULT NULL,
  `harga_total` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`no_nota`, `id_pengguna`, `tgl_penjualan`, `id_member`, `harga_total`) VALUES
('JL001', 12, '2024-01-10', 14, 3783000.00),
('JL002', 2, '2024-01-10', 9, 3671000.00),
('JL003', 4, '2024-01-11', 13, 1795000.00),
('JL004', 6, '2024-01-11', 3, 1198000.00),
('JL005', 5, '2024-01-11', 8, 1497000.00),
('JL006', 8, '2024-01-11', 2, 1595000.00),
('JL007', 9, '2024-01-11', 12, 1945000.00),
('JL008', 11, '2024-01-12', 10, 5257000.00),
('JL009', 12, '2024-01-12', 1, 1298000.00),
('JL010', 15, '2024-01-12', 6, 3413000.00),
('JL011', 13, '2024-01-12', 4, 1595000.00),
('JL012', 1, '2024-01-12', 7, 3591000.00),
('JL013', 14, '2024-01-13', 11, 4261000.00),
('JL014', 3, '2024-01-14', 5, 6339000.00),
('JL015', 5, '2024-02-11', 0, 480000.00),
('JL016', 5, '2024-02-11', 16, 1790640.00),
('JL017', 5, '2024-02-12', 0, 5216400.00),
('JL018', 7, '2024-02-12', 0, 8400000.00),
('JL019', 7, '2024-02-12', 17, 5016600.00),
('JL020', 1, '2024-02-12', 0, 3000000.00);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_sepatu`);

--
-- Indeks untuk tabel `det_pembelian`
--
ALTER TABLE `det_pembelian`
  ADD PRIMARY KEY (`no_pembelian`),
  ADD KEY `no_nota` (`no_nota`),
  ADD KEY `idx_id_sepatu` (`id_sepatu`);

--
-- Indeks untuk tabel `det_penjualan`
--
ALTER TABLE `det_penjualan`
  ADD PRIMARY KEY (`no_penjualan`),
  ADD KEY `no_nota` (`no_nota`),
  ADD KEY `fk_det_penjualan_id_sepatu` (`id_sepatu`);

--
-- Indeks untuk tabel `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`id_member`);

--
-- Indeks untuk tabel `pemasok`
--
ALTER TABLE `pemasok`
  ADD PRIMARY KEY (`id_pemasok`);

--
-- Indeks untuk tabel `pembelian`
--
ALTER TABLE `pembelian`
  ADD PRIMARY KEY (`no_nota`),
  ADD KEY `id_pengguna` (`id_pengguna`),
  ADD KEY `id_pemasok` (`id_pemasok`);

--
-- Indeks untuk tabel `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`id_pengguna`);

--
-- Indeks untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`no_nota`),
  ADD KEY `id_pengguna` (`id_pengguna`),
  ADD KEY `id_member` (`id_member`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `det_pembelian`
--
ALTER TABLE `det_pembelian`
  MODIFY `no_pembelian` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;

--
-- AUTO_INCREMENT untuk tabel `det_penjualan`
--
ALTER TABLE `det_penjualan`
  MODIFY `no_penjualan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `fk_barang_det_pembelian` FOREIGN KEY (`id_sepatu`) REFERENCES `det_pembelian` (`id_sepatu`);

--
-- Ketidakleluasaan untuk tabel `det_pembelian`
--
ALTER TABLE `det_pembelian`
  ADD CONSTRAINT `fk_det_pembelian_pembelian` FOREIGN KEY (`no_nota`) REFERENCES `pembelian` (`no_nota`);

--
-- Ketidakleluasaan untuk tabel `pembelian`
--
ALTER TABLE `pembelian`
  ADD CONSTRAINT `pembelian_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`),
  ADD CONSTRAINT `pembelian_ibfk_2` FOREIGN KEY (`id_pemasok`) REFERENCES `pemasok` (`id_pemasok`);

--
-- Ketidakleluasaan untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD CONSTRAINT `fk_penjualan_det_penjualan` FOREIGN KEY (`no_nota`) REFERENCES `det_penjualan` (`no_nota`),
  ADD CONSTRAINT `penjualan_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`),
  ADD CONSTRAINT `penjualan_ibfk_2` FOREIGN KEY (`id_member`) REFERENCES `member` (`id_member`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
