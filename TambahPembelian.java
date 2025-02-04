/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package TokoSepatu;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.NumberFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

/**
 *
 * @author irfan
 */
public class TambahPembelian extends javax.swing.JDialog {
    private boolean modeSimpan;
    private DefaultTableModel model;
    private Connection koneksi;
    private Statement perintah;
    private ResultSet rs;
    private boolean dataDitemukan;
    private String userLogin;
    PilihBarangPembelian dialogPilihBarang;
    public String kodeBarang, merekBarang, jenisBarang, ukuranBarang;
    private String[] idKeranjangArray = new String [0];
    /**
     * Creates new form KelolaDataJurusan
     */
    public TambahPembelian(Pembelian parent, boolean modal, String text) {
        
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.userLogin = text;
        jLabel10.setText("User : " + userLogin);
        model = (DefaultTableModel) jTable1.getModel();
        dataDitemukan = false;
        
        // Mengatur DocumentFilter untuk jTextField3
        AbstractDocument doc3 = (AbstractDocument) jTextField3.getDocument();
        doc3.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                // Hanya izinkan input berupa angka
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                // Hanya izinkan penggantian dengan input berupa angka
                if (text != null && text.matches("\\d+")) {
            super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        // Mengatur DocumentFilter untuk jTextField4
        AbstractDocument doc4 = (AbstractDocument) jTextField4.getDocument();
        doc4.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                // Hanya izinkan input berupa angka
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                // Hanya izinkan penggantian dengan input berupa angka
                if (text != null && text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        
        try {
            // Step 1 : Registrasi atau Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Step 2 : Cipatakan dan bangun koneksi ke database "polnep"
            String url = "jdbc:mysql://localhost/toko_sepatu";
            String user = "root";
            String password = "";
            koneksi = DriverManager.getConnection(url, user, password);
            // Step 3 : Ciptakan Statement
            perintah = koneksi.createStatement();
    } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver tidak ditemukan");
    } catch (SQLException e) {
            System.out.println("Gagal koneksi atau menciptakan objek statement");
    }    
    }
        
    private void bersihData() {
        jTextField2.setText("");
        // Clear jTextField3 content
        Document doc3 = jTextField3.getDocument();
        doc3.putProperty("filterByUser", false);
        try {
            doc3.remove(0, doc3.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(TambahPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Clear jTextField4 content
        Document doc4 = jTextField4.getDocument();
        doc4.putProperty("filterByUser", false);
        try {
            doc4.remove(0, doc4.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(TambahPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setDataBarang(String kode, String merek, String jenis, String ukuran){
        jTextField2.setText(kode);
        this.kodeBarang = kode;
        this.merekBarang = merek;
        this.jenisBarang = jenis;
        this.ukuranBarang = ukuran;
    }
    
    private void tampilComboBox() {
        try {
            // Simpan item yang sudah terpilih (jika ada)
            Object selectedSupplier = jComboBox1.getSelectedItem();

            // Buat pernyataan baru jika belum ada
            if (perintah == null) {
                System.err.println("Statement tidak diinisialisasi. Membuat statement baru.");
                perintah = koneksi.createStatement();
            }

            // Query untuk mendapatkan nama pemasok
            String query = "SELECT DISTINCT nama_pemasok FROM pemasok";
            rs = perintah.executeQuery(query);

            // Hapus semua item dari combobox
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();


            // Tambahkan tanggal-tanggal ke combobox
            while (rs.next()) {
                model.addElement(rs.getString("nama_pemasok"));
            }

            // Set model combobox dengan model yang baru
            jComboBox1.setModel(model);

            // Set item terpilih kembali (jika ada)
            jComboBox1.setSelectedItem(selectedSupplier);
        } catch (SQLException e) {
            System.err.println("Tampil ComboBox: " + e.getMessage());
        }
    }
    
    private boolean cariData(String data) {
        if (!jTextField1.getText().equals("")) {
            try {
                rs = perintah.executeQuery("SELECT * FROM det_pembelian "+
                        "WHERE no_nota = '" + data + "'");
                if(rs.next()) { // data ditemukan
                    return true;
                } else { // data tidak ditemukan
                    return false;
                }
            } catch (SQLException e) {
                System.err.println("Query Error");
            }
        }
        return false;
    }
    
    private void isiIdKeranjangDariTabel() {
        int rowCount = jTable1.getRowCount();
        idKeranjangArray = new String[rowCount];  // Menginisialisasi array dengan ukuran yang sesuai
        for (int i = 0; i < rowCount; i++) {
            idKeranjangArray[i] = jTable1.getValueAt(i, 0).toString();
        }
    }
    
    public String[] getIdKeranjang() {
        return idKeranjangArray;
    }
    
    private void cekTabel() {
        if (model.getRowCount() == 0) {
            jButton5.setEnabled(false); 
        }   
    }
      
    private void hargaTotal() {
        int rowCount = jTable1.getRowCount();

        // Inisialisasi total harga
        int totalHarga = 0;

        for (int i = 0; i < rowCount; i++) {
            String nilaiKolom7 = jTable1.getValueAt(i, 6).toString();
            nilaiKolom7 = nilaiKolom7.replace("Rp", "").replace(",00", "");
            nilaiKolom7 = nilaiKolom7.replaceAll("[^\\d]", "");
            int nilaiKolom7Int = 0;
            if (!nilaiKolom7.isEmpty()) {
                nilaiKolom7Int = Integer.parseInt(nilaiKolom7);
            }
            totalHarga += nilaiKolom7Int;
        }
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String totalHargaFormatted = rupiahFormat.format(totalHarga);
        jLabel8.setText("Harga Total : " + totalHargaFormatted);
    }
    
    public void insertPembelian() {
        try {
            String noNota = jTextField1.getText();
            String namaPemasok = (String) jComboBox1.getSelectedItem();
            String namaPengguna = jLabel10.getText().replace("User : ", "");
            String tanggal = jLabel11.getText().replace("Tanggal : ", "");
            String hargaTotal = jLabel8.getText().replace("Harga Total : Rp","").replace(
                    ".","").replaceAll(",00", "");

            // Membuat PreparedStatement
            String query = "SELECT pemasok.id_pemasok, pengguna.id_pengguna " +
                    "FROM pemasok, pengguna " +
                    "WHERE pemasok.nama_pemasok = ? AND pengguna.nama_pengguna = ?";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, namaPemasok);
            ps.setString(2, namaPengguna);

            // Eksekusi query
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String idPemasok = rs.getString("id_pemasok");
                String idPengguna = rs.getString("id_pengguna");

                // Membuat PreparedStatement untuk query INSERT
                String insertQuery = "INSERT INTO pembelian (no_nota, id_pengguna, tgl_pembelian, id_pemasok, harga_total) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertPs = koneksi.prepareStatement(insertQuery);
                insertPs.setString(1, noNota);
                insertPs.setInt(2, Integer.parseInt(idPengguna));
                insertPs.setString(3, tanggal);
                insertPs.setInt(4, Integer.parseInt(idPemasok));
                insertPs.setDouble(5, Double.parseDouble(hargaTotal));

                // Eksekusi query INSERT
                insertPs.executeUpdate();
            } else {
                System.out.println("Data tidak ditemukan");
            }
        } catch (SQLException ex) {
            System.out.println("Error Insert data Pembelian " + ex.getMessage());
            JOptionPane.showMessageDialog(
                    this,
                    "Penyimpanan data Pembelian gagal dilakukan",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException ex) {
            System.out.println("Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(
                    this,
                    "Format harga total tidak valid",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    
    public void insertDetPembelian() {
        int rowCount = jTable1.getRowCount();
        String noNota = jTextField1.getText();
        String[] kolom1 = new String[rowCount];
        String[] kolom5 = new String[rowCount];
        int[] kolom6 = new int[rowCount]; // Kolom ke-6 sebagai integer
        int[] kolom7 = new int[rowCount]; // Kolom ke-7 sebagai integer
        for (int i = 0; i < rowCount; i++) {
            kolom1[i] = jTable1.getValueAt(i, 0).toString();
            kolom5[i] = jTable1.getValueAt(i, 4).toString();
            
            String nilaiKolom6 = jTable1.getValueAt(i, 5).toString().replace("Rp",
                    "").replaceAll(",00", "");
            String nilaiKolom7 = jTable1.getValueAt(i, 6).toString().replace("Rp",
                    "").replaceAll(",00", "");
            nilaiKolom6 = nilaiKolom6.replace(".", "");
            nilaiKolom7 = nilaiKolom7.replace(".", "");
            kolom6[i] = Integer.parseInt(nilaiKolom6);
            kolom7[i] = Integer.parseInt(nilaiKolom7);
        }
        for (int i = 0; i < rowCount; i++) {
            try {
                perintah.execute("INSERT INTO det_pembelian " +
                        "(id_sepatu, no_nota, jlh_barang, harga_satuan, harga_subtotal)" +
                        "VALUES ('" + kolom1[i] + "', '" +
                        noNota + "', " +
                        kolom5[i] + ", " + kolom6[i] + ", " +
                        kolom7[i] + ")"
                );
            } catch (SQLException e) {
                System.err.println("Error Insert Detail Pembelian "+ e.getMessage());
                JOptionPane.showMessageDialog(
                        this,
                        "Penyimpanan data Detail Pembelian gagal dilakukan",
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE
            );
            }
        }
    }
    
    public void insertBarang() {
    try {
        int rowCount = jTable1.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String id_sepatu = jTable1.getValueAt(i, 0).toString();
            String merk_sepatu = jTable1.getValueAt(i, 1).toString();
            String jenis_sepatu = jTable1.getValueAt(i, 2).toString();
            String uk_sepatu = jTable1.getValueAt(i, 3).toString();
            int stok = Integer.parseInt(jTable1.getValueAt(i, 4).toString());
            double hargaSatuan = Double.parseDouble(jTable1.getValueAt(i, 5).toString().replace(
                    "Rp", "").replace(".", "").replaceAll(",00", ""));
            double untung = hargaSatuan * 0.20;
            double hargaJual = untung + hargaSatuan;

            String query = "SELECT id_sepatu FROM barang WHERE id_sepatu = ?";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, id_sepatu);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String updateQuery = "UPDATE barang " +
                        "SET stok = (SELECT SUM(jlh_barang) FROM det_pembelian WHERE id_sepatu = ?), " +
                        "harga_jual = ? " +
                        "WHERE id_sepatu = ?";
                PreparedStatement updatePs = koneksi.prepareStatement(updateQuery);
                updatePs.setString(1, id_sepatu);
                updatePs.setDouble(2, hargaJual);
                updatePs.setString(3, id_sepatu);
                updatePs.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO barang " +
                        "(id_sepatu, merk_sepatu, jenis_sepatu, uk_sepatu, stok, harga_jual) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertPs = koneksi.prepareStatement(insertQuery);
                insertPs.setString(1, id_sepatu);
                insertPs.setString(2, merk_sepatu);
                insertPs.setString(3, jenis_sepatu);
                insertPs.setString(4, uk_sepatu);
                insertPs.setInt(5, stok);
                insertPs.setDouble(6, hargaJual);
                insertPs.executeUpdate();
            }
        }
    } catch (SQLException e) {
        System.err.println("Error Insert Barang " + e.getMessage());
        JOptionPane.showMessageDialog(
                this,
                "Penyimpanan data Barang gagal dilakukan",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tambah Pembelian");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setText("No Nota");

        jLabel3.setText("ID Sepatu");

        jButton5.setText("Simpan");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton2.setText("Hapus");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Batal");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sepatu", "Merek", "Jenis", "Ukuran", "Jumlah", "Harga Satuan", "Harga SubTotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Tekan Enter untuk melanjutkan");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TokoSepatu/hafizhoes.jpg"))); // NOI18N

        jLabel5.setText("Pemasok");

        jTextField2.setEditable(false);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jLabel6.setText("Harga Satuan");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Harga SubTotal : Rp0,00");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Harga Total : Rp0,00");

        jButton4.setText("Pilih Barang");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jLabel9.setText("Jumlah");

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("User : ");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Tanggal");

        jButton1.setText("Masukkan Keranjang");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jButton4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton3)))
                                .addGap(389, 389, 389))
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(120, 120, 120)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(5, 5, 5)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        // Mendapatkan tanggal hari ini
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = dateFormat.format(today);
        
        jLabel11.setText("Tanggal : " + tanggal);
        tampilComboBox();
        bersihData();
        jComboBox1.setSelectedIndex(-1);
        jTextField1.setText("");
        jButton1.setEnabled(false);
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(false);
        jTextField3.setEnabled(false);
        jComboBox1.setEnabled(true);
        jTextField4.setEnabled(false);
        jComboBox1.requestFocus();
        jButton4.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        bersihData();
        jTextField2.setEnabled(true);
        jTextField3.setEnabled(true);
        jTextField4.setEnabled(true);
        jTextField2.requestFocus();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        if (jTextField1.getText().length() >= 5)
        {
            evt.consume();
        }

    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        // Pengecekan apakah tombol enter di keyboard telah ditekan
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dataDitemukan = cariData(jTextField1.getText());
            if (jTextField1.getText().isEmpty() || (jComboBox1.getSelectedIndex() == -1)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Pilih Pemasok dan Masukkan No nota terlebih dahulu!",
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE
                );
                requestFocus();
                bersihData();
            } else {    
                if (dataDitemukan == true) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No nota " + jTextField1.getText() + " sudah ada! " +
                            "Gunakan No nota lain!",
                            "Informasi",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    jComboBox1.requestFocus();
                    jTextField1.setText("");
                    bersihData();
                } else {
                    jComboBox1.setEnabled(false);
                    jTextField1.setEnabled(false);
                    jTextField2.setEnabled(true);
                    jTextField3.setEnabled(true);
                    jTextField4.setEnabled(true);
                    jButton1.setEnabled(true);
                    jButton4.setEnabled(true);
            }
            }
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if ((jTable1.getSelectedRow() == -1) && (dataDitemukan == false)) {
            JOptionPane.showMessageDialog(
                this, //parent, biasanya this
                "Silahkan pilih baris data yang akan dihapus!", //pesannya
                "Informasi", //judul pesan
                JOptionPane.INFORMATION_MESSAGE //tipe pesan
            );
        } else {
            int pilihan;
            pilihan = JOptionPane.showConfirmDialog(
                this,
                "Apakah anda yakin akan menghapus data?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
            );
            if (pilihan == 0) {
                model.removeRow(jTable1.getSelectedRow());
                bersihData();
                hargaTotal();
                jTextField1.setEnabled(false);
                jButton1.setEnabled(true);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton4.setEnabled(true);
                jTextField2.setEnabled(true);
                jTextField3.setEnabled(true);
                jTextField4.setEnabled(true);
                jLabel7.setText("Harga SubTotal : Rp0,00");
            }
        }
        cekTabel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() >= 0) {
            jTextField2.setText(model.getValueAt(jTable1.getSelectedRow(), 0).toString());
            jTextField4.setText(model.getValueAt(jTable1.getSelectedRow(), 4).toString());
            String hargaSatuan = model.getValueAt(jTable1.getSelectedRow(), 5).toString().replace(
                    "Rp", "").replace(",00", "");
            jTextField3.setText(hargaSatuan);
            jTextField1.setEnabled(false);
            jTextField2.setEnabled(false);
            jTextField3.setEnabled(false);
            jTextField4.setEnabled(false);
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
            jButton3.setEnabled(true);
            jButton4.setEnabled(false);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        if (jTextField2.getText().length() >= 5)
        {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        isiIdKeranjangDariTabel();
        dialogPilihBarang = new PilihBarangPembelian(null, true, this);
        dialogPilihBarang.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        cekTabel();
        insertPembelian();
        insertDetPembelian();
        insertBarang();
        JOptionPane.showMessageDialog(
                this, // parent, biasanya this
                "Data berhasil disimpan", // pesannya
                "Informasi", // judul pesan
                JOptionPane.INFORMATION_MESSAGE // tipe pesan
        );
        
        model.setRowCount(0);
        bersihData();
        jButton1.setEnabled(false);
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(false);
        jTextField3.setEnabled(false);
        jComboBox1.setEnabled(true);
        jTextField4.setEnabled(false);
        jComboBox1.requestFocus();
        jButton5.setEnabled(false);
        jButton4.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(-1);
        jLabel8.setText ("Harga Total : Rp0,00");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        try {
            long nilaiTextField3 = Long.parseLong(jTextField3.getText());
            if (jTextField4.getText().equals("")) {
                // Hitung hasil perkalian
                long hasilPerkalian = nilaiTextField3;
                // Format hasil perkalian sebagai mata uang Rupiah
                NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String hasilPerkalianFormatted = rupiahFormat.format(hasilPerkalian);
                jLabel7.setText("Harga SubTotal : " + hasilPerkalianFormatted);
            } else {
                long nilaiTextField4 = Long.parseLong(jTextField4.getText());
                // Hitung hasil perkalian
                long hasilPerkalian = nilaiTextField3 * nilaiTextField4;
                // Format hasil perkalian sebagai mata uang Rupiah
                NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String hasilPerkalianFormatted = rupiahFormat.format(hasilPerkalian);
                jLabel7.setText("Harga SubTotal : " + hasilPerkalianFormatted);
            }
            } catch (NumberFormatException e) {
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        try {
            long nilaiTextField4 = Integer.parseInt(jTextField4.getText()); 
            if (jTextField3.getText().equals("")) {
                // Hitung hasil perkalian
                long hasilPerkalian = nilaiTextField4;
                // Format hasil perkalian sebagai mata uang Rupiah
                NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String hasilPerkalianFormatted = rupiahFormat.format(hasilPerkalian);
                jLabel7.setText("Harga SubTotal : " + hasilPerkalianFormatted);
            } else {
                long nilaiTextField3 = Integer.parseInt(jTextField3.getText());
                // Hitung hasil perkalian
                long hasilPerkalian = nilaiTextField3 * nilaiTextField4;
                // Format hasil perkalian sebagai mata uang Rupiah
                NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String hasilPerkalianFormatted = rupiahFormat.format(hasilPerkalian);
                jLabel7.setText("Harga SubTotal : " + hasilPerkalianFormatted);
            }
        } catch (NumberFormatException e) {
        }
    }//GEN-LAST:event_jTextField4KeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (model.getRowCount() > 0) {
                int pilihan = JOptionPane.showConfirmDialog(
                        null, 
                        "Data anda belum tersimpan! Anda yakin ingin keluar?", 
                        "Konfirmasi", 
                        JOptionPane.YES_NO_OPTION
                );
                if (pilihan == JOptionPane.YES_OPTION) {
                    dispose();
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            } else {
            dispose();
            Pembelian pembelianDialog = new Pembelian(new javax.swing.JFrame(), true, "NamaPengguna");
            pembelianDialog.tampilData(model, perintah, rs);
            pembelianDialog.setVisible(true);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        modeSimpan = true;
        if (jTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "No Nota tidak boleh kosong!",
                "Informasi", // judul pesan
                JOptionPane.INFORMATION_MESSAGE
            );
        } else if (jTextField2.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "ID Sepatu tidak boleh kosong!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jButton4.requestFocus();
        } else if (jTextField3.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Harga Satuan tidak boleh kosong!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jTextField3.requestFocus();
        } else if (jTextField4.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Jumlah tidak boleh kosong!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jTextField4.requestFocus();
        } else {
            int nilaiTextField3 = Integer.parseInt(jTextField3.getText());
            int nilaiTextField4 = Integer.parseInt(jTextField4.getText());
            int hasilPerkalian = nilaiTextField3 * nilaiTextField4;
            if (modeSimpan == true) { // simpan data baru
                // Format harga satuan dan hasil perkalian sebagai mata uang Rupiah
                NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String hargaSatuanFormatted = rupiahFormat.format(nilaiTextField3);
                String hasilPerkalianFormatted = rupiahFormat.format(hasilPerkalian);

                model.addRow(
                    new Object[]{
                        jTextField2.getText(),
                        merekBarang,
                        jenisBarang,
                        ukuranBarang,
                        jTextField4.getText(),
                        hargaSatuanFormatted,
                        hasilPerkalianFormatted
                    }
                );
            }
            bersihData();
            hargaTotal();
            jComboBox1.setEnabled(false);
            jTextField1.setEnabled(false);
            jTextField2.setEnabled(true);
            jTextField3.setEnabled(true);
            jTextField4.setEnabled(true);
            jTextField2.requestFocus();
            jButton4.setEnabled(true);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jLabel7.setText("Harga SubTotal : Rp0,00");
            jButton5.setEnabled(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TambahPembelian dialog = new TambahPembelian(null, true, "NamaPengguna");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
