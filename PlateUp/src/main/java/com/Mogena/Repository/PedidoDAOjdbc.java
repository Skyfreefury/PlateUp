/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package com.Mogena.Repository;
//
//import com.Mogena.Model.Pedido;
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
//public class PedidoDAOjdbc implements PedidoDAO {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private RowMapper<Pedido> rowMapper = new RowMapper<Pedido>() {
//        @Override
//        public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return new Pedido(
//                rs.getLong("id"),
//                rs.getObject("cliente_id", Long.class),
//                rs.getObject("mesa_id", Long.class),
//                rs.getTimestamp("fecha_hora") != null ? rs.getTimestamp("fecha_hora").toLocalDateTime() : null, // Truco para la fecha
//                rs.getDouble("total"),
//                rs.getString("estado")
//            );
//        }
//    };
//
//    @Override
//    public List<Pedido> findAll() {
//        return jdbcTemplate.query("SELECT * FROM pedidos", rowMapper);
//    }
//
//    @Override
//    public Pedido findById(Long id) {
//        return jdbcTemplate.queryForObject("SELECT * FROM pedidos WHERE id = ?", rowMapper, id);
//    }
//
//    @Override
//    public int save(Pedido pedido) {
//        return jdbcTemplate.update("INSERT INTO pedidos (cliente_id, mesa_id, fecha_hora, total, estado) VALUES (?, ?, ?, ?, ?)",
//                pedido.getClienteId(), pedido.getMesaId(), pedido.getFechaHora(), pedido.getTotal(), pedido.getEstado());
//    }
//
//    @Override
//    public int update(Pedido pedido) {
//        return jdbcTemplate.update("UPDATE pedidos SET cliente_id = ?, mesa_id = ?, fecha_hora = ?, total = ?, estado = ? WHERE id = ?",
//                pedido.getClienteId(), pedido.getMesaId(), pedido.getFechaHora(), pedido.getTotal(), pedido.getEstado(), pedido.getId());
//    }
//
//    @Override
//    public int delete(Long id) {
//        return jdbcTemplate.update("DELETE FROM pedidos WHERE id = ?", id);
//    }
//}
