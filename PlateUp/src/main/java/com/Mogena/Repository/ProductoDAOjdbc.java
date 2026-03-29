/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductoDAOjdbc implements ProductoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Producto> rowMapper = new RowMapper<Producto>() {
        @Override
        public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Producto(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getObject("tipo_producto_id", Long.class), // Clave Foránea
                rs.getObject("tipo_comanda_id", Long.class)   // Clave Foránea
            );
        }
    };

    @Override
    public List<Producto> findAll() {
        return jdbcTemplate.query("SELECT * FROM productos", rowMapper);
    }

    @Override
    public Producto findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM productos WHERE id = ?", rowMapper, id);
    }

    @Override
    public int save(Producto producto) {
        return jdbcTemplate.update("INSERT INTO productos (nombre, precio, tipo_producto_id, tipo_comanda_id) VALUES (?, ?, ?, ?)",
                producto.getNombre(), producto.getPrecio(), producto.getTipoProductoId(), producto.getTipoComandaId());
    }

    @Override
    public int update(Producto producto) {
        return jdbcTemplate.update("UPDATE productos SET nombre = ?, precio = ?, tipo_producto_id = ?, tipo_comanda_id = ? WHERE id = ?",
                producto.getNombre(), producto.getPrecio(), producto.getTipoProductoId(), producto.getTipoComandaId(), producto.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM productos WHERE id = ?", id);
    }
}
