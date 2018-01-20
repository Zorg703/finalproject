package by.mordas.project.dao;

import by.mordas.project.entity.Subject;
import by.mordas.project.pool.ConnectionPool;
import by.mordas.project.pool.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO extends AbstractDAO<Integer,Subject> {
    private static final String FIND_ALL_SUBJECT="SELECT ID,SUBJECT_NAME FROM SUBJECT";
    private static final String FIND_SUBJECT_BY_ID="SELECT ID,SUBJECT_NAME WHERE ID=?";
    private static final String CREATE_SUBJECT="INSERT INTO SUBJECT(ID,SUBJECT_NAME) VALUES(?,?)";
    private static final String UPDATE_SUBJECT="UPDATE SUBJECT SET ID=? SUBJECT_NAME=?";
    private static final String DELETE_SUBJECT="DELETE FROM SUBJECT WHERE ID=?";
    @Override
    public List<Subject> findAll() {
        DBConnection connection= ConnectionPool.getConnection();
        List<Subject> subjects=new ArrayList<>();
        try(Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(FIND_ALL_SUBJECT)){
            if (rs != null) {
                while (rs.next()) {
                    Subject subject=new Subject();
                    subject.setSubjectId(rs.getInt("ID"));
                    subject.setSubjectName(rs.getString("FACULTY_NAME"));
                    subjects.add(subject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(connection);

        }
        return subjects;
    }

    @Override
    public Subject findEntityById(int id) {
        DBConnection conn= ConnectionPool.getConnection();
        Subject subject=new Subject();
        try(PreparedStatement pStatement=conn.prepareStatement(FIND_SUBJECT_BY_ID);
            ResultSet rs=pStatement.executeQuery()) {
            pStatement.setInt(1,id);
            if(rs!=null){
                subject.setSubjectId(rs.getInt("ID"));
                subject.setSubjectName(rs.getString("FACULTY_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection(conn);
        }
        return subject;
    }

    @Override
    public boolean delete(int id) {
        DBConnection connection = ConnectionPool.getConnection();
        try (PreparedStatement pStatement = connection.prepareStatement(DELETE_SUBJECT)) {
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
    public boolean create(Subject subject) {
        DBConnection connection=ConnectionPool.getConnection();
        try(PreparedStatement pStatement=connection.prepareStatement(CREATE_SUBJECT)){
            subject.setSubjectId(subject.getSubjectId());
            subject.setSubjectName(subject.getSubjectName());
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
    public Subject update(Subject subject) {
        DBConnection connection=ConnectionPool.getConnection();
        try(PreparedStatement pStatement=connection.prepareStatement(UPDATE_SUBJECT)){
            pStatement.setInt(1,subject.getSubjectId());
            pStatement.setString(2,subject.getSubjectName());
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            ConnectionPool.closeConnection(connection);
        }
        return subject;
    }
}
