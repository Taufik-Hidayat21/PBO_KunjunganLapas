/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessObject;

/**
 *
 * @author LENOVO
 */
import java.sql.*;
import Databases.database;
import java.util.ArrayList;
import java.util.List;
import model.Staf;
public class StafDAO extends Staf {
    ArrayList<String[]> arrayStaf = new ArrayList();
    public boolean create() {
        boolean isOperationSuccess = false;
        
        try{
            this.openConnection();         
            String queryZero = "SELECT COUNT(*) FROM staf";
            this.preparedStatement = this.connection.prepareStatement(queryZero);
            this.resultSet = this.preparedStatement.executeQuery();

            this.resultSet.next();
            int rowCount = this.resultSet.getInt(1);

            

            // Set jabatan and fkNip based on the row count
            if (rowCount == 0) {
                if (this.jabatan.equals("Staf Biasa")){
                    return isOperationSuccess = false;
                }else{
                    String sql1 = "INSERT INTO staf (nip, nama_staf, jabatan, staf_nip) VALUES (?, ?, ?, ?)";
                    this.preparedStatement = this.connection.prepareStatement(sql1);
                    this.preparedStatement.setString(1, this.nip);
                    this.preparedStatement.setString(2, this.namaStaf);
                    this.preparedStatement.setString(3, this.jabatan);
                    // Set fkNip to the kepala staf's NIP (You should replace "123" with the actual kepala staf's NIP)
                    this.preparedStatement.setString(4, this.fkNip);
                    int result1 = this.preparedStatement.executeUpdate();

                    isOperationSuccess = result1 > 0;
                }
            } else {
                if (this.jabatan.equals("Kepala Staf")){
                    return isOperationSuccess = false;
                }else {
                    String cariKepala = "SELECT nip FROM staf WHERE jabatan = 'kepala staf'";
                    this.preparedStatement = this.connection.prepareStatement(cariKepala);
                    this.resultSet = this.preparedStatement.executeQuery();
                    this.resultSet.next();
                    String nipKepala = this.resultSet.getString("nip");

                    String sql2 = "INSERT INTO staf (nip, nama_staf, jabatan, staf_nip) VALUES (?, ?, ?, ?)";
                    this.preparedStatement = this.connection.prepareStatement(sql2);
                    this.preparedStatement.setString(1, this.nip);
                    this.preparedStatement.setString(2, this.namaStaf);
                    this.preparedStatement.setString(3, this.jabatan);
    //                String sql3 = "UPDATE staf SET staf_nip = "
                    // Set fkNip to the kepala staf's NIP (You should replace "123" with the actual kepala staf's NIP)
                    this.preparedStatement.setString(4, nipKepala);
                    int result2 = this.preparedStatement.executeUpdate();

                    isOperationSuccess = result2 > 0;
                }
            }    
//                java.sql.Connection conn = (Connection) database.openConnection();            
        }catch (SQLException ex){
//            System.err.println("gagal koneksi"+ex.getMessage());
            this.displayErrors(ex);
        }finally{
            this.closeConnection();
        }
        return isOperationSuccess;
    }
    public boolean find (String user) {
        boolean isoperationSuccess = false;
            try {
                this.openConnection();
                this.preparedStatement = this.connection.prepareStatement("SELECT * FROM staf WHERE nip= ?");
                this.preparedStatement.setString(1, user);
                this.resultSet = this.preparedStatement.executeQuery();
                if (this.resultSet.next()){
                    this.nip = this.resultSet.getString("nip");
                    isoperationSuccess = true;
                }
            }catch (SQLException ex) {
                this.displayErrors (ex);
            }finally {
                this.closeConnection();
            }
            return isoperationSuccess;
    }
    public boolean delete(){
        boolean isOperationSuccess = false;
        try{
            this.openConnection();
            String sql = "DELETE FROM staf WHERE nip= ?";
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setString(1,this.nip);
            int result = this.preparedStatement.executeUpdate();
            
            isOperationSuccess = result > 0;    
        }catch(SQLException ex){
            this.displayErrors(ex);
        }finally{
            this.closeConnection();
        }return isOperationSuccess; 
    }
    public ArrayList<String[]> readStaf() throws SQLException {
        this.openConnection();
        String query = "SELECT * FROM staf";
        this.statement = this.connection.createStatement();
        ResultSet rs = this.statement.executeQuery(query);
        while (rs.next()) {            
            String nip = rs.getString("nip");
            String nama = rs.getString("nama_staf");
            String jabatan = rs.getString("jabatan");
            String stafNip = rs.getString("staf_nip");
            String[] data = {nip, nama, jabatan, stafNip};   
            arrayStaf.add(data);
        }return arrayStaf;
    }
}
