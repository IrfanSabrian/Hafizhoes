/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package TokoSepatu;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author irfan
 */
public class PilihBarangPembelian extends javax.swing.JDialog {
    private DefaultTableModel model;
    private Connection koneksi;
    private Statement perintah;
    private ResultSet rs;
    private boolean dataDitemukan;
    TambahPembelian dataPembelian;
    
    /**
     * Creates new form KelolaDataJurusan
     */
    public PilihBarangPembelian(java.awt.Frame parent, boolean modal, TambahPembelian dataPembelian) {
        
        super(parent, modal);
        initComponents();
        this.dataPembelian = dataPembelian;
        setLocationRelativeTo(null);
        model = (DefaultTableModel) jTable1.getModel();
        dataDitemukan = false;
        
        // Mengatur DocumentFilter untuk jTextField4
        AbstractDocument doc4 = (AbstractDocument) jTextField4.getDocument();
        doc4.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                // Hanya izinkan input berupa angka
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                // Hanya izinkan penggantian dengan input berupa angka
                if (text.matches("\\d+")) {
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
    
    private void pencarianData() {
        String searchText = jTextField1.getText().toLowerCase();

        // Hapus semua baris dari tabel
        model.setRowCount(0);

        try {
        String query = "SELECT * FROM barang WHERE id_sepatu LIKE '%" + searchText +"%'";
        rs = perintah.executeQuery(query);
    
        while (rs.next()) {
            model.addRow(new Object[]{
                        rs.getString("id_sepatu"),
                        rs.getString("merk_sepatu"),
                        rs.getString("jenis_sepatu"),
                        rs.getString("uk_sepatu"),
                        rs.getString("stok")
                });
    }
    } catch (SQLException e) {
        System.err.println("Query Error");
    }
    }
    
    private void tampilData() {
        String searchText = jTextField1.getText().toLowerCase();

        // Hapus semua baris dari tabel
        model.setRowCount(0);

        try {
        String query = "SELECT * FROM barang ";
        rs = perintah.executeQuery(query);
    
        while (rs.next()) {
            model.addRow(new Object[]{
                        rs.getString("id_sepatu"),
                        rs.getString("merk_sepatu"),
                        rs.getString("jenis_sepatu"),
                        rs.getString("uk_sepatu"),
                        rs.getString("stok")
                });
    }
    } catch (SQLException e) {
        System.err.println("Query Error");
    }
    }
    
    private boolean cariData(String data) {
        if (!jTextField1.getText().equals("")) {
            try {
                rs = perintah.executeQuery("SELECT * FROM barang "+
                        "WHERE id_sepatu = '" + data + "'");
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
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pilih Barang");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setText("ID Sepatu");

        jLabel2.setText("Merek");

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
                "Kode Barang", "Merek", "Jenis", "Ukuran", "Stok"
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

        jLabel3.setText("Jenis");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jLabel4.setText("Ukuran");

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("OK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Tekan Enter untuk  Barang Baru");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(9, 9, 9))
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
        pencarianData();
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jTextField2.setEnabled(false);
        jTextField3.setEnabled(false);
        jTextField4.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        pencarianData();
        jButton1.setEnabled(true);
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
            if (dataDitemukan == true) {
                jTextField1.setEnabled(false);
                jButton2.setEnabled(true);
                try {
                    jTextField1.setText(rs.getString("id_sepatu"));
                    jTextField2.setText(rs.getString("merk_sepatu"));
                    jTextField3.setText(rs.getString("jenis_sepatu"));
                    jTextField4.setText(rs.getString("uk_sepatu"));
                } catch (SQLException ex) {
                    Logger.getLogger(PilihBarangPembelian.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else { // data tidak ditemukan
                jButton2.setEnabled(true);
                jTextField1.setEnabled(false);
                jTextField2.setEnabled(true);
                jTextField3.setEnabled(true);
                jTextField4.setEnabled(true);
                jTextField2.requestFocus();
            }
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() >= 0) {
            jTextField1.setText(model.getValueAt(jTable1.getSelectedRow(), 0).toString());
            jTextField2.setText(model.getValueAt(jTable1.getSelectedRow(), 1).toString());
            jTextField3.setText(model.getValueAt(jTable1.getSelectedRow(), 2).toString());
            jTextField4.setText(model.getValueAt(jTable1.getSelectedRow(), 3).toString());
            jTextField1.setEnabled(false);
            jTextField2.setEnabled(false);
            jTextField3.setEnabled(false);
            jTextField4.setEnabled(false);
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        if (jTextField2.getText().length() >= 50)
        {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        tampilData();
        jTextField1.requestFocus();
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(false);
        jTextField3.setEnabled(false);
        jTextField4.setEnabled(false);
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        // Clear jTextField4 content
        Document doc4 = jTextField4.getDocument();
        doc4.putProperty("filterByUser", false);
        try {
            doc4.remove(0, doc4.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(PilihBarangPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        // TODO add your handling code here:
        if (jTextField4.getText().length() >= 2)
        {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String kode, merek, jenis, ukuran;
        String inputValue = jTextField1.getText();
        boolean found = false;
        for (String id : dataPembelian.getIdKeranjang()) {
            if (inputValue.equals(id)) {
                found = true;
                break;
            }
        }
        if (found) {
            // Kode yang dijalankan jika cocok
            JOptionPane.showMessageDialog(
                this, 
                "ID Sepatu Telah ada dalam keranjang! Gunakan Kode lain!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            
        } else if (jTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this, 
                "Silahkan lengkapi data Kode Barang!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jTextField1.requestFocus();
        } else if (jTextField2.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this, 
                "Silahkan lengkapi data Merek!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jTextField2.requestFocus();
        } else if (jTextField3.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this, 
                "Silahkan lengkapi data Jenis!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jTextField3.requestFocus();
        } else if (jTextField4.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this, 
                "Silahkan lengkapi data Ukuran!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );
            jTextField4.requestFocus();
        } else {
            kode = jTextField1.getText();
            merek = jTextField2.getText();
            jenis = jTextField3.getText();
            ukuran = jTextField4.getText();
            dataPembelian.setDataBarang(kode, merek, jenis, ukuran);
            dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
