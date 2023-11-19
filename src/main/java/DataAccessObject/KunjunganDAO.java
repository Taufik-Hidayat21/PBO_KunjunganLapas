/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessObject;

/**
 *
 * @author LENOVO
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Kunjungan;

public class KunjunganDAO extends Kunjungan{
    ArrayList<String[]> arrayKunjungan = new ArrayList();
    public boolean create() {
    boolean isOperationSuccess = false;

    try {
        this.openConnection();
            String queryZero = "SELECT COUNT(*) FROM kunjungan";
            this.preparedStatement = this.connection.prepareStatement(queryZero);
//            String result;
            this.resultSet = this.preparedStatement.executeQuery();
        
            this.resultSet.next();
            int rowCount = this.resultSet.getInt(1);
            if (rowCount == 0){
                    String sql = "INSERT INTO kunjungan (id_kunjungan, tanggal, waktu, narapidana_id_napi, pengunjung_id_pengunjung) VALUES (?, ?, ?, ?, ?)";
                    this.preparedStatement = this.connection.prepareStatement(sql);
                    this.preparedStatement.setInt(1, 1);
                    this.preparedStatement.setString(2,  this.tanggal);
                    this.preparedStatement.setString(3, this.waktu);
                    this.preparedStatement.setInt(4, this.fkIdNapi);
//                    this.preparedStatement.setString(5, "");
                    this.preparedStatement.setInt(5, this.fkIdPengunjung);
                    
                    int result = this.preparedStatement.executeUpdate();
                    
                    isOperationSuccess = result > 0;
//                java.sql.Connection conn = (Connection) database.openConnection();
            }else {
                    String getMaxIdQuery = "SELECT id_kunjungan FROM kunjungan ORDER BY id_kunjungan DESC LIMIT 1";
                    this.preparedStatement = this.connection.prepareStatement(getMaxIdQuery);
                    this.resultSet  = this.preparedStatement.executeQuery();
                    int newId;
                    this.resultSet.next();
                    newId = this.resultSet.getInt("id_kunjungan") + 1;
                    String sql = "INSERT INTO kunjungan (id_kunjungan, tanggal, waktu, narapidana_id_napi, pengunjung_id_pengunjung) VALUES (?, ?, ?, ?, ?)";
                    this.preparedStatement = this.connection.prepareStatement(sql);
                    this.preparedStatement.setInt(1, newId);
                    this.preparedStatement.setString(2,  this.tanggal);
                    this.preparedStatement.setString(3, this.waktu);
                    this.preparedStatement.setInt(4, this.fkIdNapi);
//                    this.preparedStatement.setString(5, "");
                    this.preparedStatement.setInt(5, this.fkIdPengunjung);
                    
                    int result = this.preparedStatement.executeUpdate();
                    
                    isOperationSuccess = result > 0;
            }
        
            // Handle the case where there are no available staf_nip values
            // You can return false or take another action based on your requirements.
        
    } catch (SQLException ex) {
        this.displayErrors(ex);
    } finally {
        this.closeConnection();
    }

    return isOperationSuccess;
}

    public boolean find (String user) {
        boolean isoperationSuccess = false;
            try {
                this.openConnection();
                this.preparedStatement = this.connection.prepareStatement("SELECT * FROM pengunjung WHERE username = ?");
                this.preparedStatement.setString(1, user);
                this.resultSet = this.preparedStatement.executeQuery();
                if (this.resultSet.next()){
                    this.fkIdPengunjung = this.resultSet.getInt("id_pengunjung");
                    isoperationSuccess = true;
                }
            }catch (SQLException ex) {
                this.displayErrors (ex);
            }finally {
                this.closeConnection();
            }
            return isoperationSuccess;
    }
    public ArrayList<String[]> readKunjungan() throws SQLException {
        this.openConnection();
        String query = "SELECT * FROM kunjungan";
        this.statement = this.connection.createStatement();
        ResultSet rs = this.statement.executeQuery(query);
        while (rs.next()) {            
            String idKunjungan = rs.getString("id_kunjungan");
            String tanggal = rs.getString("tanggal");
            String waktu = rs.getString("waktu");
            String idNapi = rs.getString("narapidana_id_narapidana");
            String nipStaf = rs.getString("staf_nip");
            String idPengunjung = rs.getString("pengunjung_id_pengunjung");
            String[] data = {idKunjungan, tanggal, waktu, idNapi, nipStaf, idPengunjung};   
            arrayKunjungan.add(data);
        }return arrayKunjungan;
    }
    public ArrayList<String[]> readKunjungan1() throws SQLException {
        this.openConnection();
        String query = "SELECT * FROM kunjungan";
        this.statement = this.connection.createStatement();
        ResultSet rs = this.statement.executeQuery(query);
        while (rs.next()) {            
            String idKunjungan = rs.getString("id_kunjungan");
            String tanggal = rs.getString("tanggal");
            String waktu = rs.getString("waktu");
            String idPengunjung = rs.getString("pengunjung_id_pengunjung");
            String[] data = {idKunjungan, tanggal, waktu, idPengunjung};   
            arrayKunjungan.add(data);
        }return arrayKunjungan;
    }
}
