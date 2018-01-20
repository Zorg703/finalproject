package by.mordas.project.dao;

import by.mordas.project.entity.Faculty;
import by.mordas.project.entity.Specialty;
import by.mordas.project.pool.ConnectionPool;
import by.mordas.project.pool.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO extends AbstractDAO<Integer,Faculty> {
    private static final String FIND_ALL_FACULTY="SELECT ID,FACULTY_NAME FROM FACULTY";
    private static final String FIND_FACULTY_BY_ID="SELECT ID,FACULTY_NAME WHERE ID=?";
    private static final String CREATE_FACULTY="INSERT INTO FACULTY(ID,FACULTY_NAME) VALUES(?,?)";
    private static final String UPDATE_FACULTY="UPDATE FACULTY SET ID=? FACULTY_NAME=?";
    private static final String DELETE_FACULTY="DELETE FROM FACULTY WHERE ID=?";


    @Override
    public List<Faculty> findAll() {
        DBConnection connection=ConnectionPool.getConnection();
        List<Faculty> faculties=new ArrayList<>();
        try(Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(FIND_ALL_FACULTY)) {
            if (rs != null) {
                while (rs.next()) {
                    Faculty faculty=new Faculty();
                    faculty.setFacultyId(rs.getInt("ID"));
                    faculty.setFacultyName(rs.getString("FACULTY_NAME"));
                    faculties.add(faculty);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(connection);

        }
        return faculties;
    }

    @Override
    public Faculty findEntityById(int id) {
        DBConnection conn= ConnectionPool.getConnection();
        Faculty faculty=new Faculty();
        try(PreparedStatement pStatement=conn.prepareStatement(FIND_FACULTY_BY_ID);
            ResultSet rs=pStatement.executeQuery()) {
            pStatement.setInt(1,id);
            if(rs!=null){
                faculty.setFacultyId(rs.getInt("ID"));
                faculty.setFacultyName(rs.getString("FACULTY_NAME"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(conn);
        }
        return faculty;
    }

    @Override
    public boolean delete(int id) {
        DBConnection connection = ConnectionPool.getConnection();
        try (PreparedStatement pStatement = connection.prepareStatement(DELETE_FACULTY)) {
            pStatement.setInt(1,id);
            return pStatement.executeUpdate()==2;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean create(Faculty faculty) {
        DBConnection connection=ConnectionPool.getConnection();
        try(PreparedStatement pStatement=connection.prepareStatement(CREATE_FACULTY)){
            pStatement.setInt(1,faculty.getFacultyId());
            pStatement.setString(2,faculty.getFacultyName());
            int result=pStatement.executeUpdate();
            return result==2;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            ConnectionPool.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Faculty update(Faculty faculty) {
        DBConnection connection=ConnectionPool.getConnection();
        try(PreparedStatement pStatement=connection.prepareStatement(UPDATE_FACULTY)){
            pStatement.setInt(1,faculty.getFacultyId());
            pStatement.setString(2,faculty.getFacultyName());
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(connection);
        }
        return faculty;
    }


}