package by.mordas.project.dao;

import by.mordas.project.entity.Specialty;
import by.mordas.project.pool.ConnectionPool;
import by.mordas.project.pool.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpecialityDAO extends AbstractDAO<Integer,Specialty> {
    private static final String FIND_ALL_SPECIALITY="SELECT ID,SPECIALITY_NAME,RECRUITMENT_PLAN,FACULTY_ID FROM SPECIALITY";
    private static final String FIND_SPECIALITY_BY_ID="SELECT ID,SPECIALITY_NAME,RECRUITMENT_PLAN,FACULTY_ID FROM SPECIALITY WHERE ID=?";
    private static final String CREATE_SPECIALITY="INSERT INTO SPECIALITY(ID,SPECIALITY_NAME,RECRUITMENT_PLAN,FACULTY_ID) VALUES(?,?,?,?)";
    private static final String UPDATE_SPECIALITY="UPDATE SPECIALITY SET ID=? SPECIALITY_NAME=?,RECRUITMENT_PLAN=?,FACULTY_ID=?";
    private static final String DELETE_SPECIALITY="DELETE FROM SPECIALITY WHERE ID=?";
    private static final String FIND_ALL_SPECIALITY_BY_FACULTY_NAME="SELECT SPECIALITY.ID,SPECIALITY.SPECIALITY_NAME," +
            "SPECIALITY.RECRUITMENT_PALN, SPECIALITY.FACULTY_ID FROM FACULTY,SPECIALITY" +
            " WHERE SPECIALITY.ID=SPECIALITY.FACULTY_ID AND FACULTY=?";
    private static final String FIND_ENROLLEES_ON_SPECIALITY_BY_ID="SELECT ID,FIRST_NAME,LAST_NAME,PATRONYMIC,BIRTHDAY,CERTIFICATE_AVG," +
            "SPECIALITY_ID, SUBJECT_ID, ";

    @Override
    public List<Specialty> findAll() {
        DBConnection connection= ConnectionPool.getConnection();
        List<Specialty> specialties=new ArrayList<>();
        try(Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(FIND_ALL_SPECIALITY)) {
            if (rs != null) {
                while (rs.next()) {
                   setSpeciality(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(connection);

        }
        return specialties;

    }

    @Override
    public Specialty findEntityById(int id) {
        DBConnection conn= ConnectionPool.getConnection();
        Specialty specialty=new Specialty();
        try(PreparedStatement pStatement=conn.prepareStatement(FIND_SPECIALITY_BY_ID);
            ResultSet rs=pStatement.executeQuery()) {
            pStatement.setInt(1,id);
            if(rs!=null){
                    specialty.setSpecialityId(rs.getInt("ID"));
                    specialty.setSpecialityName(rs.getString("SPECIALITY_NAME"));
                    specialty.setRecruitmentPlan(rs.getInt("RECRUITMENT_PLAN"));
                    specialty.setFacultyId(rs.getInt("FACULTY_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(conn);
        }
        return specialty;
    }

    @Override
    public boolean delete(int id) {
        DBConnection connection = ConnectionPool.getConnection();
        try (PreparedStatement pStatement = connection.prepareStatement(DELETE_SPECIALITY)) {
            pStatement.setInt(1,id);
            return pStatement.executeUpdate()==4;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean create(Specialty specialty) {
        DBConnection connection=ConnectionPool.getConnection();
        try(PreparedStatement pStatement=connection.prepareStatement(CREATE_SPECIALITY)){
            pStatement.setInt(1,specialty.getSpecialityId());
            pStatement.setString(2,specialty.getSpecialityName());
            pStatement.setInt(3,specialty.getRecruitmentPlan());
            pStatement.setInt(4,specialty.getFacultyId());
            int result=pStatement.executeUpdate();
            return result==4;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            ConnectionPool.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Specialty update(Specialty specialty) {
        DBConnection connection=ConnectionPool.getConnection();
        try(PreparedStatement pStatement=connection.prepareStatement(UPDATE_SPECIALITY)){
            pStatement.setInt(1,specialty.getSpecialityId());
            pStatement.setString(2,specialty.getSpecialityName());
            pStatement.setInt(3,specialty.getRecruitmentPlan());
            pStatement.setInt(4,specialty.getFacultyId());
            pStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            ConnectionPool.closeConnection(connection);
        }
        return specialty;
    }

    public List<Specialty> showSpecialitiesOnFaculty(String name) {
        DBConnection connection = ConnectionPool.getConnection();
        List<Specialty> specialties=null;
        try (PreparedStatement pStatement = connection.prepareStatement(FIND_ALL_SPECIALITY_BY_FACULTY_NAME);
             ResultSet rs=pStatement.executeQuery()) {
            pStatement.setString(1, name);
            if(rs!=null){
                while (rs.next()){
                specialties.add(setSpeciality(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialties;
    }

    private Specialty setSpeciality(ResultSet rs) throws SQLException {
        Specialty specialty=new Specialty();
        specialty.setSpecialityId(rs.getInt("ID"));
        specialty.setSpecialityName(rs.getString("SPECIALITY_NAME"));
        specialty.setRecruitmentPlan(rs.getInt("RECRUITMENT_PLAN"));
        specialty.setFacultyId(rs.getInt("FACULTY_ID"));
        return specialty;
    }
}
