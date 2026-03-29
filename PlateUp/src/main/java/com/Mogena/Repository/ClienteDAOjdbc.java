/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package com.Mogena.Repository;
//
//import com.Mogena.Model.Cliente;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//@Repository
//public class ClienteDAOjdbc implements ClienteDAO {
//
//    // Spring nos inyecta esta herramienta mágica para hacer consultas SQL
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    // Esta herramienta convierte las filas de MySQL en objetos Cliente de Java
//    private RowMapper<Cliente> rowMapper = new RowMapper<Cliente>() {
//        @Override
//        public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return new Cliente(
//                rs.getLong("id"),
//                rs.getString("nombre"),
//                rs.getString("telefono"),
//                rs.getString("email")
//            );
//        }
//    };
//
//    @Override
//    public List<Cliente> findAll() {
//        return jdbcTemplate.query("SELECT * FROM clientes", rowMapper);
//    }
//
//    @Override
//    public Cliente findById(Long id) {
//        return jdbcTemplate.queryForObject("SELECT * FROM clientes WHERE id = ?", rowMapper, id);
//    }
//
//    @Override
//    public int save(Cliente cliente) {
//        return jdbcTemplate.update("INSERT INTO clientes (nombre, telefono, email) VALUES (?, ?, ?)",
//                cliente.getNombre(), cliente.getTelefono(), cliente.getEmail());
//    }
//
//    @Override
//    public int update(Cliente cliente) {
//        return jdbcTemplate.update("UPDATE clientes SET nombre = ?, telefono = ?, email = ? WHERE id = ?",
//                cliente.getNombre(), cliente.getTelefono(), cliente.getEmail(), cliente.getId());
//    }
//
//    @Override
//    public int delete(Long id) {
//        return jdbcTemplate.update("DELETE FROM clientes WHERE id = ?", id);
//    }
//}
