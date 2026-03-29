/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package com.Mogena.Repository;
//
//import com.Mogena.Model.LineaProducto;
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
//public class LineaProductoDAOjdbc implements LineaProductoDAO {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private RowMapper<LineaProducto> rowMapper = new RowMapper<LineaProducto>() {
//        @Override
//        public LineaProducto mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return new LineaProducto(
//                rs.getLong("id"),
//                rs.getObject("comanda_id", Long.class),
//                rs.getObject("producto_id", Long.class),
//                rs.getInt("cantidad"),
//                rs.getDouble("subtotal")
//            );
//        }
//    };
//
//    @Override
//    public List<LineaProducto> findAll() {
//        return jdbcTemplate.query("SELECT * FROM lineas_producto", rowMapper);
//    }
//
//    @Override
//    public LineaProducto findById(Long id) {
//        return jdbcTemplate.queryForObject("SELECT * FROM lineas_producto WHERE id = ?", rowMapper, id);
//    }
//
//    @Override
//    public int save(LineaProducto linea) {
//        return jdbcTemplate.update("INSERT INTO lineas_producto (comanda_id, producto_id, cantidad, subtotal) VALUES (?, ?, ?, ?)",
//                linea.getComandaId(), linea.getProductoId(), linea.getCantidad(), linea.getSubtotal());
//    }
//
//    @Override
//    public int update(LineaProducto linea) {
//        return jdbcTemplate.update("UPDATE lineas_producto SET comanda_id = ?, producto_id = ?, cantidad = ?, subtotal = ? WHERE id = ?",
//                linea.getComandaId(), linea.getProductoId(), linea.getCantidad(), linea.getSubtotal(), linea.getId());
//    }
//
//    @Override
//    public int delete(Long id) {
//        return jdbcTemplate.update("DELETE FROM lineas_producto WHERE id = ?", id);
//    }
//}
