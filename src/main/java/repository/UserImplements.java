package repository;

import models.Users;
import util.DBConector;
import util.UserQueries;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserImplements {

    //Insert
    public boolean insert(Users user){
        try(Connection conn = DBConector.getConnection();
            PreparedStatement ps = conn.prepareStatement(UserQueries.insert)){

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            if(!validEmail(user.getEmail())){
                JOptionPane.showMessageDialog(null, "Invalid email. Please try again.");
                return false;
            }

            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Select all
    public List<Users> list(){
        //Creamos la lista de usuarios
        List<Users> users = new ArrayList<Users>();

        //Hacemos la coneccion a la base de datos
        try(Connection con = DBConector.getConnection();
            PreparedStatement ps = con.prepareStatement(UserQueries.select);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                Users u = new Users(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                users.add(u);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    //Buscar Usuario por ID
    public Users SerachByID(int id){
        Users user = null;

        try(Connection conn = DBConector.getConnection();
            PreparedStatement ps = conn.prepareStatement(UserQueries.selectId)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user = new Users(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    //Buscar Usuario por email
    public Users byEmail(String email){
        Users user = null;

        try(Connection con = DBConector.getConnection();
        PreparedStatement ps = con.prepareStatement(UserQueries.selectByEmail)){

            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user = new Users(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    //Buscar usuario por nombre
    public List<Users> seacrhName(String name){
        List<Users> usersList = new ArrayList<>();

        String sql = "SELECT * FROM usuarios WHERE nombre LIKE ?";

        try(Connection con = DBConector.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,"%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Users user = new Users(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                usersList.add(user);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return usersList;
    }

    //Actualizar
    public boolean update(Users user){
        try(Connection conn = DBConector.getConnection();
            PreparedStatement ps = conn.prepareStatement(UserQueries.update)){
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    ps.setInt(4, user.getId());

                if(!validEmail(user.getEmail())){
                    JOptionPane.showMessageDialog(null, "Invalid email. Please try again.");
                    return false;
                }
                    return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Eliminar
    public boolean delete (int id){
        try(Connection conn = DBConector.getConnection();
            PreparedStatement ps = conn.prepareStatement(UserQueries.delete)){
            ps.setInt(1,id);
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Agregamos un metodo para comporbar si un email es valido
    public boolean validEmail(String email){
        String body = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
        return email.matches(body);
    }
}