package by.mordas.project.dao;
import by.mordas.project.entity.Enrollee;
import by.mordas.project.pool.ConnectionPool;
import by.mordas.project.pool.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EnrolleeDAO extends AbstractDAO<Integer,Enrollee> {
    private static final String FIND_ENROLLEE="SELECT ID,FIRST_NAME,LAST_NAME,PATRONYMIC,BIRTHDAY,CERTIFICATE_AVG," +
            "SPECIALITY_ID FROM ENROLLEE WHERE ID=?";
    private static final String FIND_ALL_ENROLLEE="SELECT ID,FIRST_NAME,LAST_NAME,PATRONYMIC,BIRTHDAY,CERTIFICATE_AVG," +
            "SPECIALITY_ID FROM ENROLLEE ";
    private static final String CREATE_ENTRANT="INSERT INTO ENTRANT(ID,FIRST_NAME,LAST_NAME,PATRONYMIC,BIRTHDAY," +
            "CERTIFICATE_AVG,SPECIALITY_ID) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTRANT ="UPDATE ENTRANT SET ID=?,FIRST_NAME=?,LAST_NAME=?,PATRONYMIC=?," +
            "BIRTHDAY=?,CERTIFICATE_AVG=?,SPECIALITY_ID=?";
    private static final String DELETE_ENROLLEE_BY_ID="DELETE FROM ENROLLEE WHERE ID=?";


    @Override
    public List<Enrollee> findAll() {
        DBConnection conn= ConnectionPool.getConnection();
        List<Enrollee> entrantList=new ArrayList<>();
        try(Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery(FIND_ALL_ENROLLEE)) {
            if(rs!=null){
                while (rs.next()) {
                    Enrollee enrollee=new Enrollee();
                    enrollee.setEnrolleeId((rs.getInt("ID")));
                    enrollee.setFirstName(rs.getString("FIRST_NAME"));
                    enrollee.setLastName(rs.getString("LAST_NAME"));
                    enrollee.setPatronymic(rs.getString("PATRONYMIC"));
                    enrollee.setBirthday(rs.getDate("BIRTHDAY"));
                    enrollee.setCertificateMark(rs.getInt("SERTIFICATE_AVG"));
                    enrollee.setSpecialityId(rs.getInt("SPECIALITY"));
                    entrantList.add(enrollee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
           ConnectionPool.closeConnection(conn);
        }
        return entrantList;
    }

    @Override
    public Enrollee findEntityById(int id) {
        DBConnection conn= ConnectionPool.getConnection();
        Enrollee enrollee = new Enrollee();
        try(PreparedStatement pStatement=conn.prepareStatement(FIND_ENROLLEE);
            ResultSet rs=pStatement.executeQuery()) {
            pStatement.setInt(1,id);
            if(rs!=null){
                enrollee.setEnrolleeId(rs.getInt("ID"));
                enrollee.setFirstName(rs.getString("FIRST_NAME"));
                enrollee.setLastName(rs.getString("LAST_NAME"));
                enrollee.setPatronymic(rs.getString("PATRONYMIC"));
                enrollee.setBirthday(rs.getDate("BIRTHDAY"));
                enrollee.setCertificateMark(rs.getInt("CERTIFICATE_AVG"));
                enrollee.setSpecialityId(rs.getInt("SPECIALITY"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(conn);
        }
        return enrollee;
    }

    @Override
    public boolean delete(int id) {

        DBConnection connection = ConnectionPool.getConnection();
        try (PreparedStatement pStatement = connection.prepareStatement(DELETE_ENROLLEE_BY_ID)) {
            pStatement.setInt(1,id);
            return pStatement.executeUpdate()==7;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(connection);
        }
        return false;
    }

        @Override
    public boolean create(Enrollee enrollee)
    {
        DBConnection connection=ConnectionPool.getConnection();
        try(PreparedStatement pStatement=connection.prepareStatement(CREATE_ENTRANT)){
            pStatement.setInt(1,enrollee.getEnrolleeId());
            pStatement.setString(2,enrollee.getFirstName());
            pStatement.setString(3,enrollee.getLastName());
            pStatement.setString(4,enrollee.getPatronymic());
            pStatement.setDate(5,enrollee.getBirthday());
            pStatement.setInt(6,enrollee.getCertificateMark());
            pStatement.setInt(7,enrollee.getSpecialityId());
            int result=pStatement.executeUpdate();
            return result==7;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            ConnectionPool.closeConnection(connection);
        }


        return false;
    }


    @Override
    public Enrollee update(Enrollee enrollee) {
        DBConnection connection=ConnectionPool.getConnection();
        try( PreparedStatement pStatement=connection.prepareStatement(UPDATE_ENTRANT)){
            pStatement.setInt(1,enrollee.getEnrolleeId());
            pStatement.setString(2,enrollee.getFirstName());
            pStatement.setString(3,enrollee.getLastName());
            pStatement.setString(4,enrollee.getPatronymic());
            pStatement.setDate(5,enrollee.getBirthday());
            pStatement.setInt(6,enrollee.getCertificateMark());
            pStatement.setInt(7,enrollee.getSpecialityId());
            pStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
          ConnectionPool.closeConnection(connection);
        }
        return enrollee;


    }


}
