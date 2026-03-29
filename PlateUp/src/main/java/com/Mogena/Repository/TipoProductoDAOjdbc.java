/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.TipoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TipoProductoDAOjdbc implements TipoProductoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<TipoProducto> rowMapper = new RowMapper<TipoProducto>() {
        @Override
        public TipoProducto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TipoProducto(
                rs.getLong("id"),
                rs.getString("nombre")
            );
        }
    };

    @Override
    public List<TipoProducto> findAll() {
        return jdbcTemplate.query("SELECT * FROM tipos_producto", rowMapper);
    }

    @Override
    public TipoProducto findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tipos_producto WHERE id = ?", rowMapper, id);
    }

    @Override
    public int save(TipoProducto tipoProducto) {
        return jdbcTemplate.update("INSERT INTO tipos_producto (nombre) VALUES (?)",
                tipoProducto.getNombre());
    }

    @Override
    public int update(TipoProducto tipoProducto) {
        return jdbcTemplate.update("UPDATE tipos_producto SET nombre = ? WHERE id = ?",
                tipoProducto.getNombre(), tipoProducto.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM tipos_producto WHERE id = ?", id);
    }
}
