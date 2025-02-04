/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package TokoSepatu;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.NumberFormat;
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
public class Kasir extends javax.swing.JDialog {
    private boolean modeSimpan;
    private DefaultTableModel model;
    private Connection koneksi;
    private Statement perintah;
    private ResultSet rs;
    private boolean dataDitemukan;
    private String userLogin;
    public String idBarang, namaBarang, jenisBarang, ukuranBarang;
    private String[] idKeranjangArray = new String [0];
    public String idmember, namamember, notelp;
    public String idbarang, merekbarang, jenisbarang, ukuranbarang, stok, hargajual;
    private String idMember;
    private DetItem detItem;
    private Kasir dataBarang;
    /**
     * Creates new form KelolaDataJurusan
     */
    public Kasir(Menu parent, boolean modal, String text) {
        
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.userLogin = text;
        jLabel10.setText("User : " + userLogin);
        model = (DefaultTableModel) jTable1.getModel();
        dataDitemukan = false;
        
        // Mengatur DocumentFilter untuk jTextField5
        AbstractDocument doc5 = (AbstractDocument) jTextField4.getDocument();
        doc5.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                // Hanya izinkan input berupa angka dalam rentang 1-stok
                if (string.matches("\\d+")) {
                    String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                    String newText = currentText.substring(0, offset) + string + currentText.substring(offset, currentText.length());
                    int newValue = Integer.parseInt(newText);
                    if (newText.length() <= String.valueOf(stok).length() && newValue >= 1 && newValue <= Integer.parseInt(stok)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                // Hanya izinkan penggantian dengan input berupa angka dalam rentang 1-stok
                if (text != null && text.matches("\\d+")) {
                    String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                    String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length, currentText.length());
                    int newValue = Integer.parseInt(newText);
                    if (newText.length() <= String.valueOf(stok).length() && newValue >= 1 && newValue <= Integer.parseInt(stok)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            }
        });


        // Mengatur DocumentFilter untuk jTextField6
        AbstractDocument doc6 = (AbstractDocument) jTextField5.getDocument();
        doc6.setDocumentFilter(new DocumentFilter() {
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
    
    private void resetData() {
        model.setRowCount(0);
        jCheckBox1.setSelected(false);
        jTextField1.setText("Umum");
        jTextField2.setText("");
        jCheckBox1.setEnabled(true);
        jButton1.setEnabled(true);
        jButton6.setEnabled(false);
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(false);
        jTextField5.setEnabled(false);
        jButton4.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        jButton7.setEnabled(false);
        jLabel7.setText("Harga SubTotal : Rp0,00");
        jLabel5.setText("Potongan : 0% = Rp0,00");
        jLabel8.setText("Harga Total : Rp0,00");
        jLabel14.setText("Kembalian : Rp0,00");
        jLabel15.setText("Harga Total : Rp0,00");
    }
    
    private void bersihData() {
        
        // Clear jTextField3 content
        Document doc4 = jTextField4.getDocument();
        doc4.putProperty("filterByUser", false);
        try {
            doc4.remove(0, doc4.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(Kasir.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Clear jTextField4 content
        Document doc5 = jTextField5.getDocument();
        doc5.putProperty("filterByUser", false);
        try {
            doc5.remove(0, doc5.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(Kasir.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
    }
    
    public void setDataMember(String idMember, String namaMember, String noTelp){
        jTextField2.setText(idMember);
        this.idmember = idMember;
        this.namamember = namaMember;
        this.notelp = noTelp;
    }
    
    public void setDataBarang(String idBarang, String merekBarang, String jenisBarang,
        String ukuranBarang, String stok, String hargaJual){
        jButton1.setEnabled(true);
        jCheckBox1.setEnabled(false);
        jTextField3.setText(idBarang);
        jTextField4.setText(hargaJual);
        this.idbarang = idBarang;
        this.merekbarang = merekBarang;
        this.jenisbarang = jenisBarang;
        this.ukuranbarang = ukuranBarang;
        this.stok = stok;
        this.hargajual = hargaJual;
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
            jTextField5.setEnabled(false);
        }   
    }
      
    private void hargaSubTotal() {
        int rowCount = jTable1.getRowCount();

        // Inisialisasi total harga
        int totalHarga = 0;

        for (int i = 0; i < rowCount; i++) {
            String nilaiKolom5 = jTable1.getValueAt(i, 4).toString();
            nilaiKolom5 = nilaiKolom5.replace("Rp", "").replace(",00", "");
            nilaiKolom5 = nilaiKolom5.replaceAll("[^\\d]", "");
            int nilaiKolom7Int = 0;
            if (!nilaiKolom5.isEmpty()) {
                nilaiKolom7Int = Integer.parseInt(nilaiKolom5);
            }
            totalHarga += nilaiKolom7Int;
        }
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String totalHargaFormatted = rupiahFormat.format(totalHarga);
        jLabel7.setText("Harga SubTotal : " + totalHargaFormatted);
    }
    
    private void potongan() {
        if (jCheckBox1.isSelected()) {
            String subtotalStr = jLabel7.getText().replace(
                "Harga SubTotal : Rp", "").replace(
                ",00", "").replace(".", "");
            double subtotal = Double.parseDouble(subtotalStr);
            double doublepotongan = subtotal * 0.1;
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String potonganFormatted = rupiahFormat.format(doublepotongan);

            jLabel5.setText("Potongan : 10% = " + potonganFormatted);
        } else {
            jLabel5.setText("Potongan : 0% = Rp0,00");
        }
    }
    
    private void hargaTotal() {
        if (jCheckBox1.isSelected()) {
            String subtotalStr = jLabel7.getText().replace(
            "Harga SubTotal : Rp", "").replace(
                    ",00", "").replace(".", "");
            String potonganStr = jLabel5.getText().replace(
            "Potongan : 10% = Rp", "").replace(
                    ",00", "").replace(".", "");
            double subtotal = Double.parseDouble(subtotalStr);
            double potongan = Double.parseDouble(potonganStr);
            double total = subtotal - potongan;
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String potonganFormatted = rupiahFormat.format(total);
            jLabel8.setText("Harga Total : " + potonganFormatted);
            jLabel15.setText("Harga Total : " + potonganFormatted);
        } else {
            String subtotal = jLabel7.getText().replace(
            "Harga SubTotal : ", "");
            jLabel8.setText("Harga Total : " + subtotal);
            jLabel15.setText("Harga Total : " + subtotal);
        }
    }
    
    public void insertPenjualan() {
        try {
            String noNota = jLabel2.getText().replace("No Nota : ", "");
            String namaPengguna = jLabel10.getText().replace("User : ", "");
            String tanggal = jLabel11.getText().replace("Tanggal : ", "");

            String idMemberKasir = ""; // Default value

            if (jTextField1.getText().equals("")) {
                idMemberKasir = jTextField2.getText();
            } else {
                idMemberKasir = "0";
            }
            String hargaTotal = jLabel8.getText().replace("Harga Total : Rp","").replace(
                    ".","").replaceAll(",00", "");

            // Membuat PreparedStatement
            String query = "SELECT id_pengguna " +
                    "FROM pengguna " +
                    "WHERE nama_pengguna = ?";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, namaPengguna);

            // Eksekusi query
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String idPengguna = rs.getString("id_pengguna");

                // Membuat PreparedStatement untuk query INSERT
                String insertQuery = "INSERT INTO penjualan (no_nota, id_pengguna, tgl_penjualan, id_member, harga_total) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertPs = koneksi.prepareStatement(insertQuery);
                insertPs.setString(1, noNota);
                insertPs.setInt(2, Integer.parseInt(idPengguna));
                insertPs.setString(3, tanggal);
                insertPs.setInt(4, Integer.parseInt(idMemberKasir));
                insertPs.setDouble(5, Double.parseDouble(hargaTotal));

                // Eksekusi query INSERT
                insertPs.executeUpdate();
            } else {
                System.out.println("Data tidak ditemukan");
                return;
            }
        } catch (SQLException ex) {
            System.out.println("Error Insert data Penjualan " + ex.getMessage());
            JOptionPane.showMessageDialog(
                    this,
                    "Penyimpanan data Penjualan gagal dilakukan",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException ex) {
            System.out.println("Error: Format harga total tidak valid");
            JOptionPane.showMessageDialog(
                    this,
                    "Format harga total tidak valid",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    public void insertDetPenjualan() {
        int rowCount = jTable1.getRowCount();
        String nomorNota = jLabel2.getText().replace("No Nota : ","");
        String noNota = nomorNota.replace("No Nota : ","");
        String[] kolom1 = new String[rowCount];
        String[] kolom3 = new String[rowCount];
        int[] kolom4 = new int[rowCount]; // Kolom ke-4 sebagai integer
        int[] kolom5 = new int[rowCount]; // Kolom ke-5 sebagai integer
        for (int i = 0; i < rowCount; i++) {
            kolom1[i] = jTable1.getValueAt(i, 0).toString();
            kolom3[i] = jTable1.getValueAt(i, 2).toString();
            
            String nilaiKolom4 = jTable1.getValueAt(i, 3).toString().replace("Rp",
                    "").replaceAll(",00", "");
            String nilaiKolom5 = jTable1.getValueAt(i, 4).toString().replace("Rp",
                    "").replaceAll(",00", "");
            nilaiKolom4 = nilaiKolom4.replace(".", "");
            nilaiKolom5 = nilaiKolom5.replace(".", "");
            kolom4[i] = Integer.parseInt(nilaiKolom4);
            kolom5[i] = Integer.parseInt(nilaiKolom5);
        }
        for (int i = 0; i < rowCount; i++) {
            try {
                perintah.execute("INSERT INTO det_penjualan " +
                        "(id_sepatu, no_nota, jlh_barang, harga_satuan, harga_subtotal)" +
                        "VALUES ('" + kolom1[i] + "', '" +
                        nomorNota + "', " +
                        kolom3[i] + ", " + kolom4[i] + ", " +
                        kolom5[i] + ")"
                );
            } catch (SQLException e) {
                System.err.println("Error Insert Detail Penjualan "+ e.getMessage());
                JOptionPane.showMessageDialog(
                        this,
                        "Penyimpanan data Detail Penjualan gagal dilakukan",
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
            int stok = Integer.parseInt(jTable1.getValueAt(i, 2).toString());

            String query = "SELECT id_sepatu FROM barang WHERE id_sepatu = ?";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, id_sepatu);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String updateQuery = "UPDATE barang " +
                        "SET stok = stok - " + stok + " " +
                        "WHERE id_sepatu = ?";
                PreparedStatement updatePs = koneksi.prepareStatement(updateQuery);
                updatePs.setString(1, id_sepatu);
                updatePs.executeUpdate();
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

    private void setNota() {
        // Koneksi ke database
        try {
            // Query untuk mendapatkan data terbaru dari tabel penjualan
            String query = "SELECT MAX(no_nota) AS latest_nota FROM penjualan";
            ResultSet resultSet = perintah.executeQuery(query);

            // Mendapatkan nilai noNota dari hasil query
            String noNota = "";
            if (resultSet.next()) {
                noNota = resultSet.getString("latest_nota");
                if (noNota != null) {
                    // Mengambil nomor nota terbaru dan menambahkan 1
                    String[] parts = noNota.split("(?<=\\D)(?=\\d)");
                    int incrementedNo = Integer.parseInt(parts[1]) + 1;
                    noNota = parts[0] + String.format("%03d", incrementedNo);
                }
            }
            jLabel2.setText("No Nota : " + noNota);

        } catch (SQLException e) {
            e.printStackTrace();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kasir");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("No Nota : ");

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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sepatu", "Merek Sepatu", "Jumlah", "Harga per  Item", "Total per Item"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TokoSepatu/hafizhoes.jpg"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Harga SubTotal : Rp0,00");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Harga Total : Rp0,00");

        jButton4.setText("Pilih");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jLabel9.setText("Jumlah");

        jTextField3.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("User : ");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tanggal : ");

        jButton1.setText("Tambahkan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setText("Umum");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Pelanggan");

        jCheckBox1.setText("Member");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton6.setText("Detail Item");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("ID Member");

        jTextField2.setEditable(false);

        jButton7.setText("Pilih");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Potongan : 0% = Rp0,00");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Tunai");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Kembalian : Rp0,00");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Harga Total : Rp0,00");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton8.setText("Bersih");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(66, 66, 66)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton8))))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jButton5)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton4)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(39, 39, 39)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(158, 158, 158)))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jButton4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton2)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton8))
                        .addGap(40, 40, 40))))
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
        setNota();
        bersihData();
        jButton1.setEnabled(false);
        jTextField5.setEnabled(false);
        jButton6.setEnabled(false);
        jTextField2.setEnabled(false);
        jButton4.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        jButton7.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        bersihData();
        jTextField3.setEnabled(true);
        jTextField4.setEnabled(true);
        jTextField4.setEnabled(true);
        jTextField3.requestFocus();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(true);
        jButton6.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

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
                hargaSubTotal();
                potongan();
                hargaTotal();
                jTextField1.setEnabled(false);
                jButton1.setEnabled(true);
                jTextField5.setEnabled(true);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton4.setEnabled(true);
                jTextField2.setEnabled(true);
                jTextField3.setEnabled(true);
                jTextField4.setEnabled(true);
                jTextField4.setEnabled(true);
                jTextField5.setEnabled(true);
            }
        }
        cekTabel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() >= 0) {
            jTextField3.setText(model.getValueAt(jTable1.getSelectedRow(), 0).toString());
            jTextField4.setText(model.getValueAt(jTable1.getSelectedRow(), 3).toString().replace("Rp", 
                    "").replace(",00", "").replace(".", ""));
            jTextField4.setText(model.getValueAt(jTable1.getSelectedRow(), 2).toString());
            jTextField3.setEnabled(false);
            jTextField4.setEnabled(false);
            jTextField4.setEnabled(false);
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
            jButton3.setEnabled(true);
            jButton4.setEnabled(false);
            jButton6.setEnabled(true);
            jTextField3.requestFocus();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        isiIdKeranjangDariTabel();
        PilihBarangKasir dialogPilihBarang = new PilihBarangKasir(null, true,this);
        dialogPilihBarang.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        insertDetPenjualan();
        insertPenjualan();
        insertBarang();
        cekTabel();
        JOptionPane.showMessageDialog(
                this, // parent, biasanya this
                "Data berhasil disimpan", // pesannya
                "Informasi", // judul pesan
                JOptionPane.INFORMATION_MESSAGE // tipe pesan
        );
        setNota();
        resetData();
        bersihData();
    }//GEN-LAST:event_jButton5ActionPerformed

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
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        modeSimpan = true;
        if (jTextField1.getText().equals("")) {
            idmember = jTextField2.getText();
        } else {
            idmember = "0";
        }
        if ("".equals(idmember)) {
            JOptionPane.showMessageDialog(
                this,
                "ID Member tidak boleh kosong!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else if (jTextField3.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "ID Sepatu tidak boleh kosong!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jButton4.requestFocus();
        } else if (jTextField4.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Harga Satuan tidak boleh kosong!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jTextField4.requestFocus();
        } else if (jTextField4.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Jumlah tidak boleh kosong!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jTextField4.requestFocus();
        } else {
            int hargaSatuan = Integer.parseInt(hargajual);
            int nilaiTextField5 = Integer.parseInt(jTextField4.getText());
            int hasilPerkalian = hargaSatuan * nilaiTextField5;
            if (modeSimpan == true) { // simpan data baru
                // Format harga satuan dan hasil perkalian sebagai mata uang Rupiah
                NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String hargaSatuanFormatted = rupiahFormat.format(hargaSatuan);
                String hasilPerkalianFormatted = rupiahFormat.format(hasilPerkalian);

                model.addRow(
                    new Object[]{
                        idbarang,
                        merekbarang,
                        jTextField4.getText(),
                        hargaSatuanFormatted,
                        hasilPerkalianFormatted
                    }
                );
            }
            hargaSubTotal();
            potongan();
            hargaTotal();
            bersihData();
            jTextField5.setEnabled(true);
            jButton7.setEnabled(false);
            jButton4.setEnabled(true);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jButton5.setEnabled(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        isiIdKeranjangDariTabel();
        PilihMember dialogPilihMember = new PilihMember(null, true,this);
        dialogPilihMember.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox1.isSelected()) {
            jTextField1.setText("");
            jTextField1.setEnabled(false);
            jTextField2.setEnabled(true);
            jButton7.setEnabled(true);
        } else {
            jTextField1.setText("Umum");
            jTextField2.setText("");
            jTextField1.setEnabled(true);
            jTextField2.setEnabled(false);
            jButton7.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        setNota();
        bersihData();
        resetData();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        // TODO add your handling code here:   
        // Pengecekan apakah tombol enter di keyboard telah ditekan
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {                                     
        try {
            long tunai = Long.parseLong(jTextField5.getText());
            String totalStr = jLabel8.getText().replace("Harga Total : Rp", 
                    "").replace(",00", "").replace(".", "");
            long total = Long.parseLong(totalStr);
            long kembalian = tunai - total;
            if (kembalian < 0) {
                // Jika uang tunai kurang dari total, beri tahu pengguna
                jLabel14.setText("Uang Tunai Kurang!");
                jLabel15.setText("Uang Tunai Kurang!");
            } else {
                // Format hasil perkalian sebagai mata uang Rupiah
                NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String hasilPerkalianFormatted = rupiahFormat.format(kembalian);
                jLabel14.setText("Kembalian : " + hasilPerkalianFormatted);
                jLabel15.setText("Kembalian : " + hasilPerkalianFormatted);
                }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String idSepatu = model.getValueAt(jTable1.getSelectedRow(), 0).toString();
        // Membuat objek DetPembelian dengan memberikan noNota sebagai parameter
        detItem = new DetItem(new javax.swing.JFrame(), true,
                dataBarang, idSepatu);
        detItem.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

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
                Kasir dialog = new Kasir(null, true, "NamaPengguna");
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
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
