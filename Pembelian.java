/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package TokoSepatu;

import java.awt.Frame;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
/**
 *
 * @author irfan
 */
public class Pembelian extends javax.swing.JDialog {
    private DefaultTableModel model;
    private Connection koneksi;
    private Statement perintah;
    String noNota, hargaTotal, namaPengguna;
    DetPembelian detPembelian;
    private String userLogin;
    private static ResultSet rs;


    /**
     * Creates new form 
     */
    public Pembelian(Frame parent, boolean modal, String text) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.userLogin = text;
        model = (DefaultTableModel) jTable1.getModel();
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
    
    private void tampilDataTanggal() {
    try {
        // Simpan item yang sudah terpilih (jika ada)
        Object selectedDate = jComboBox1.getSelectedItem();

        // Buat pernyataan baru jika belum ada
        if (perintah == null) {
            System.err.println("Statement tidak diinisialisasi. Membuat statement baru.");
            perintah = koneksi.createStatement();
        }

        // Query untuk mendapatkan tanggal-tanggal unik
        String query = "SELECT DISTINCT tgl_pembelian FROM pembelian";
        rs = perintah.executeQuery(query);

        // Hapus semua item dari combobox
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        // Tambahkan opsi "Semua" ke combobox
        model.addElement("Semua");

        // Tambahkan tanggal-tanggal ke combobox
        while (rs.next()) {
            model.addElement(rs.getString("tgl_pembelian"));
        }

        // Set model combobox dengan model yang baru
        jComboBox1.setModel(model);

        // Set item terpilih kembali (jika ada)
        jComboBox1.setSelectedItem(selectedDate);
    } catch (SQLException e) {
        System.err.println("Query Error: " + e.getMessage());
        }
    }
    
    private void filterData() {
        // Hapus semua baris dari tabel
        model.setRowCount(0);

        try {
            // Ambil tanggal yang dipilih dari combobox
            String selectedDate = (String) jComboBox1.getSelectedItem();

            // Jika "Semua" dipilih, tampilkan semua data
            if ("Semua".equals(selectedDate)) {
                Pembelian.tampilData(model, perintah, rs);
                return;
            }

            // Query SQL dengan menggunakan tanggal yang dipilih
            String query = "SELECT pembelian.no_nota, " + 
                    "pengguna.nama_pengguna, " +
                    "pembelian.tgl_pembelian, " +
                    "pemasok.nama_pemasok, " +
                    "pembelian.harga_total " +
                    "FROM pembelian " +
                    "JOIN pengguna ON pembelian.id_pengguna = pengguna.id_pengguna " +
                    "JOIN pemasok ON pembelian.id_pemasok = pemasok.id_pemasok " +
                    "WHERE pembelian.tgl_pembelian = '" + selectedDate + "'";
            rs = perintah.executeQuery(query);

            // Loop untuk menambahkan baris ke dalam model
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            while (rs.next()) {
                // Mengubah nilai harga_jual menjadi format mata uang
                double harga_total = rs.getDouble("harga_total");
                String formattedHargaTotal = formatRupiah.format(harga_total);

                model.addRow(new Object[] {
                        rs.getString("no_nota"),
                        rs.getString("nama_pengguna"),
                        rs.getString("tgl_pembelian"),
                        rs.getString("nama_pemasok"),
                        formattedHargaTotal // Menggunakan harga yang sudah diformat
                });
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
    }
    
    public static void tampilData(DefaultTableModel model, Statement perintah, ResultSet rs) {
        // Hapus semua baris dari tabel
        model.setRowCount(0);

        try {
        String query = "SELECT pembelian.no_nota, " + 
                "pengguna.nama_pengguna, " +
                "pembelian.tgl_pembelian, " +
                "pemasok.nama_pemasok, " +
                "pembelian.harga_total " +
                "FROM pembelian " +
                "JOIN pengguna ON pembelian.id_pengguna = pengguna.id_pengguna " +
                "JOIN pemasok ON pembelian.id_pemasok = pemasok.id_pemasok ";
        rs = perintah.executeQuery(query);
        
        // Loop untuk menambahkan baris ke dalam model
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        while (rs.next()) {
            // Mengubah nilai harga_jual menjadi format mata uang
            double harga_total = rs.getDouble("harga_total");
            String formattedHargaTotal = formatRupiah.format(harga_total);
            // Tambahkan baris ke dalam model
            model.addRow(new Object[] {
                rs.getString("no_nota"),
                rs.getString("nama_pengguna"),
                rs.getString("tgl_pembelian"),
                rs.getString("nama_pemasok"),
                formattedHargaTotal // Menggunakan harga yang sudah diformat
            });
        }
    } catch (SQLException e) {
        System.err.println("Query Error");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pembelian");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TokoSepatu/HAFIZHOES.jpg"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pilih Tanggal Pembelian");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Nota", "User", "Tgl Pembelian", "Pemasok", "Harga Total"
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

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton3.setText("Detail");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Tambah Pembelian");
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
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        tampilDataTanggal();
        Pembelian.tampilData(model, perintah, rs);
        jComboBox1.setSelectedIndex(0);
        jButton3.setEnabled(false);
        jButton1.setEnabled(true);
    }//GEN-LAST:event_formWindowOpened

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() >= 0) {
            noNota = (model.getValueAt(jTable1.getSelectedRow(), 0).toString());
            hargaTotal = (model.getValueAt(jTable1.getSelectedRow(), 4).toString());
            namaPengguna = (model.getValueAt(jTable1.getSelectedRow(), 1).toString());
            jButton3.setEnabled(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        filterData();
        jButton3.setEnabled(false);
        jButton1.setEnabled(true);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        // Mendapatkan nilai noNota dari objek Pembelian
        if (jTable1.getSelectedRow() >= 0) {
            noNota = (model.getValueAt(jTable1.getSelectedRow(), 0).toString());
            hargaTotal = (model.getValueAt(jTable1.getSelectedRow(), 4).toString());
            namaPengguna = (model.getValueAt(jTable1.getSelectedRow(), 1).toString());
            // Membuat objek DetPembelian dengan memberikan noNota sebagai parameter
            detPembelian = new DetPembelian(new javax.swing.JFrame(), true, noNota, hargaTotal, namaPengguna);
            detPembelian.tampilData(); // Memanggil tampilData pada DetPembelian
            detPembelian.setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
        new TambahPembelian(this,true,userLogin).setVisible(true);
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
            java.util.logging.Logger.getLogger(Pembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Pembelian dialog = new Pembelian(new javax.swing.JFrame(), true, "NamaPengguna");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
