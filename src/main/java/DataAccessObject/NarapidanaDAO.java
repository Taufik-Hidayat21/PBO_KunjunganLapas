/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessObject;
import java.sql.*;
import Databases.database;
import model.Narapidana;
import Interface.MenuAdmin;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NarapidanaDAO extends Narapidana {
    ArrayList<String[]> arrayNapi = new ArrayList();
    public boolean create() {
        boolean isOperationSuccess = false;
        
        try{
            this.openConnection();
            

                String queryZero = "SELECT COUNT(*) FROM narapidana";
            this.preparedStatement = this.connection.prepareStatement(queryZero);
//            String result;
            this.resultSet = this.preparedStatement.executeQuery();
        
            this.resultSet.next();
            int rowCount = this.resultSet.getInt(1);
            if (rowCount == 0){
                    String sql = "INSERT INTO narapidana (id_napi, nama_napi, umur, jenis_kelamin, tgl_masuk, tgl_keluar, masa_tahanan) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    this.preparedStatement = this.connection.prepareStatement(sql);
                    this.preparedStatement.setInt(1, 1);
                    this.preparedStatement.setString(2,  this.namaNapi);
                    this.preparedStatement.setInt(3, this.umur);
                    this.preparedStatement.setString(4, this.jenisKelamin);
                    this.preparedStatement.setString(5, this.tglMasuk);
                    this.preparedStatement.setString(6, this.tglKeluar);
                    this.preparedStatement.setString(7, this.masaTahanan);
                    int result = this.preparedStatement.executeUpdate();
                    isOperationSuccess = result > 0;
                    
                    String sqlSub1 = "INSERT INTO napi_lapas (id_napi, program_rehab) VALUES (?,?)";
                    this.preparedStatement = this.connection.prepareStatement(sqlSub1);
                    this.preparedStatement.setInt(1, 1);
                    this.preparedStatement.setString(2, this.programRehab);
                    int result2 = this.preparedStatement.executeUpdate();
                    isOperationSuccess = result2 > 0;
                    
                    String sqlSub2 = "INSERT INTO napi_program (id_napi, program) VALUES (?,?)";
                    this.preparedStatement = this.connection.prepareStatement(sqlSub2);
                    this.preparedStatement.setInt(1, 1);
                    this.preparedStatement.setString(2, "tidak ada");
                    int result3 = this.preparedStatement.executeUpdate();
                    isOperationSuccess = result3 > 0;
//                java.sql.Connection conn = (Connection) database.openConnection();
            }else {
                    String getMaxIdQuery = "SELECT id_napi FROM narapidana ORDER BY id_napi DESC LIMIT 1";
                    this.preparedStatement = this.connection.prepareStatement(getMaxIdQuery);
                    this.resultSet  = this.preparedStatement.executeQuery();
                    int newId;
                    this.resultSet.next();
                    newId = this.resultSet.getInt("id_napi") + 1;
                    String sql = "INSERT INTO narapidana (id_napi, nama_napi, umur, jenis_kelamin, tgl_masuk, tgl_keluar, masa_tahanan) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    this.preparedStatement = this.connection.prepareStatement(sql);
                    this.preparedStatement.setInt(1, newId);
                    this.preparedStatement.setString(2,  this.namaNapi);
                    this.preparedStatement.setInt(3, this.umur);
                    this.preparedStatement.setString(4, this.jenisKelamin);
                    this.preparedStatement.setString(5, this.tglMasuk);
                    this.preparedStatement.setString(6, this.tglKeluar);
                    this.preparedStatement.setString(7, this.masaTahanan);
                    int result = this.preparedStatement.executeUpdate();
                    isOperationSuccess = result > 0;
                    
                    String sqlSub = "INSERT INTO napi_lapas (id_napi, program_rehab) VALUES (?,?)";
                    this.preparedStatement = this.connection.prepareStatement(sqlSub);
                    this.preparedStatement.setInt(1, newId);
                    this.preparedStatement.setString(2, this.programRehab);
                    int result2 = this.preparedStatement.executeUpdate();
                    isOperationSuccess = result2 > 0;
                    
                    String sqlSub2 = "INSERT INTO napi_program (id_napi, program) VALUES (?,?)";
                    this.preparedStatement = this.connection.prepareStatement(sqlSub2);
                    this.preparedStatement.setInt(1, newId);
                    this.preparedStatement.setString(2, "");
                    int result3 = this.preparedStatement.executeUpdate();
                    isOperationSuccess = result3 > 0;
            }
            
        }catch (SQLException ex){
//            System.err.println("gagal koneksi"+ex.getMessage());
            this.displayErrors(ex);
        }finally{
            this.closeConnection();
        }
        return isOperationSuccess;
    }
    public boolean find (int id) {
        boolean isoperationSuccess = false;
            try {
                this.openConnection();
                this.preparedStatement = this.connection.prepareStatement("SELECT * FROM narapidana WHERE id_napi = ?");
                this.preparedStatement.setInt(1, id);
                this.resultSet = this.preparedStatement.executeQuery();
                if (this.resultSet.next()){
                    this.idNapi = id;
                    this.namaNapi=  this.resultSet.getString("nama_napi");
                    this.umur = this.resultSet.getInt("umur");
                    this.jenisKelamin = this.resultSet.getString("jenis_kelamin");
                    this.tglMasuk = this.resultSet.getString("tgl_masuk");
                    this.tglKeluar = this.resultSet.getString("tgl_keluar");
//                    this.masaTahanan = this.resultSet.getInt("masa_tahanan");
                    isoperationSuccess = true;
                }
            }catch (SQLException ex) {
                this.displayErrors (ex);
            }finally {
                this.closeConnection();
            }
            return isoperationSuccess;
    }
    
            
    public boolean updateUmur(){
        boolean isOperationSuccess = false;
        try{
            this.openConnection();
            String sql = "UPDATE narapidana SET umur = ? WHERE id_napi = ?";
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setInt(1,this.umur);
            this.preparedStatement.setInt(2,this.idNapi);
            int result = this.preparedStatement.executeUpdate();
            
            isOperationSuccess = result > 0;    
        }catch(SQLException ex){
            this.displayErrors(ex);
        }finally{
            this.closeConnection();
        }return isOperationSuccess; 
    }
    public boolean updateMasaTahanan(){
        boolean isOperationSuccess = false;
        try{
            this.openConnection();
            String sql1 = "UPDATE narapidana SET masa_tahanan= ? WHERE id_napi = ?";
            this.preparedStatement = this.connection.prepareStatement(sql1);
            this.preparedStatement.setString(1,this.masaTahanan);
            this.preparedStatement.setInt(2,this.idNapi);
            int result1 = this.preparedStatement.executeUpdate();
            isOperationSuccess = result1 > 0; 
            
            String sql2 = "UPDATE narapidana SET tgl_keluar= ? WHERE id_napi = ?";
            this.preparedStatement = this.connection.prepareStatement(sql2);
            this.preparedStatement.setString(1,this.tglKeluar);
            this.preparedStatement.setInt(2,this.idNapi);
            int result2 = this.preparedStatement.executeUpdate();
            isOperationSuccess = result2 > 0; 
        }catch(SQLException ex){
            this.displayErrors(ex);
        }finally{
            this.closeConnection();
        }return isOperationSuccess; 
    }
    public boolean updateProgram(String select){
        boolean isOperationSuccess = false;
        try{
            this.openConnection();
            if(select.equals("Napi Lapas")){
                String sql1 = "UPDATE napi_lapas SET program_rehab = ? WHERE id_napi = ?";
                this.preparedStatement = this.connection.prepareStatement(sql1);
                this.preparedStatement.setString(1,this.programRehab);
                this.preparedStatement.setInt(2,this.idNapi);
                int result1 = this.preparedStatement.executeUpdate();
                isOperationSuccess = result1 > 0;
                
                String sql2 = "UPDATE napi_program SET program = ? WHERE id_napi = ?";
                this.preparedStatement = this.connection.prepareStatement(sql2);
                this.preparedStatement.setString(1,"");
                this.preparedStatement.setInt(2,this.idNapi);
                int result2 = this.preparedStatement.executeUpdate();
                isOperationSuccess = result2 > 0;
            }else{
                String sql2 = "UPDATE napi_program SET program = ? WHERE id_napi = ?";
                this.preparedStatement = this.connection.prepareStatement(sql2);
                this.preparedStatement.setString(1,this.program);
                this.preparedStatement.setInt(2,this.idNapi);
                int result2 = this.preparedStatement.executeUpdate();
                isOperationSuccess = result2 > 0;
                
                String sql1 = "UPDATE napi_lapas SET program_rehab = ?WHERE id_napi = ?";
                this.preparedStatement = this.connection.prepareStatement(sql1);
                this.preparedStatement.setString(1,"");
                this.preparedStatement.setInt(2,this.idNapi);
                int result1 = this.preparedStatement.executeUpdate();
                isOperationSuccess = result1 > 0;
            }
        }catch(SQLException ex){
            this.displayErrors(ex);
        }finally{
            this.closeConnection();
        }return isOperationSuccess; 
    }
    public boolean delete(){
        boolean isOperationSuccess = false;
        try{
            this.openConnection();
            
            String sql2 = "DELETE FROM napi_lapas WHERE id_napi = ?";
            this.preparedStatement = this.connection.prepareStatement(sql2);
            this.preparedStatement.setInt(1,this.idNapi);
            int result2 = this.preparedStatement.executeUpdate();
            
            isOperationSuccess = result2 > 0; 
            
            String sql3 = "DELETE FROM napi_program WHERE id_napi = ?";
            this.preparedStatement = this.connection.prepareStatement(sql3);
            this.preparedStatement.setInt(1,this.idNapi);
            int result3 = this.preparedStatement.executeUpdate();
            
            isOperationSuccess = result3 > 0; 
            
            String sql1 = "DELETE FROM narapidana WHERE id_napi = ?";
            this.preparedStatement = this.connection.prepareStatement(sql1);
            this.preparedStatement.setInt(1,this.idNapi);
            int result1 = this.preparedStatement.executeUpdate();
            
            isOperationSuccess = result1 > 0;
        }catch(SQLException ex){
            this.displayErrors(ex);
        }finally{
            this.closeConnection();
        }return isOperationSuccess; 
    }

    public ArrayList<String[]> readNapiDaftar() throws SQLException {
        this.openConnection();
        String query = "SELECT * FROM narapidana";
        this.statement = this.connection.createStatement();
        ResultSet rs = this.statement.executeQuery(query);
        while (rs.next()) {            
            String idNapi = rs.getString("Id_napi");
            String nama = rs.getString("nama_napi");
            String umur = rs.getString("umur");
            String jenisKelamin = rs.getString("jenis_kelamin");
            String tglMasuk = rs.getString("tgl_masuk");
            String tglKeluar = rs.getString("tgl_keluar");
            String masaTahanan = rs.getString("masa_tahanan");
            String[] data = {idNapi, nama, umur, jenisKelamin, tglMasuk, tglKeluar, masaTahanan};   
            arrayNapi.add(data);
        }return arrayNapi;
    }
    public ArrayList<String[]> readNapi() throws SQLException {
        this.openConnection();
        String query = "SELECT * FROM narapidana";
        this.statement = this.connection.createStatement();
        ResultSet rs = this.statement.executeQuery(query);
        while (rs.next()) {            
            String idNapi = rs.getString("Id_napi");
            String nama = rs.getString("nama_napi");
            String umur = rs.getString("umur");
            String jenisKelamin = rs.getString("jenis_kelamin");
            String[] data = {idNapi, nama, umur, jenisKelamin};   
            arrayNapi.add(data);
        }return arrayNapi;
    }
    public ArrayList<String[]> readHukuman() throws SQLException {
        this.openConnection();
        String query = "SELECT * FROM narapidana";
        this.statement = this.connection.createStatement();
        ResultSet rs = this.statement.executeQuery(query);
        while (rs.next()) {            
            String idNapi = rs.getString("Id_napi");
            String tglMasuk = rs.getString("tgl_masuk");
            String tglKeluar = rs.getString("tgl_keluar");
            String masaTahanan = rs.getString("masa_tahanan");
            String[] data = {idNapi, tglMasuk, tglKeluar, masaTahanan};   
            arrayNapi.add(data);
        }return arrayNapi;
    }
    public ArrayList<String[]> readProgramRehab() throws SQLException {
        this.openConnection();
        String query1 = "SELECT * FROM napi_lapas";
        this.statement = this.connection.createStatement();
        ResultSet rs1 = this.statement.executeQuery(query1);
        while (rs1.next()) {            
            String idNapi1 = rs1.getString("Id_napi");
            String programRehab = rs1.getString("program_rehab");
            String[] data = {idNapi1, programRehab};   
            arrayNapi.add(data);
        }return arrayNapi;     
    }   
    public ArrayList<String[]> readProgram() throws SQLException {
        this.openConnection();
        String query1 = "SELECT * FROM napi_program";
        this.statement = this.connection.createStatement();
        ResultSet rs1 = this.statement.executeQuery(query1);
        while (rs1.next()) {            
            String idNapi1 = rs1.getString("Id_napi");
            String program = rs1.getString("program");
            String[] data = {idNapi1, program};   
            arrayNapi.add(data);
        }return arrayNapi;     
    }
    
}

