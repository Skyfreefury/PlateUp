/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Mesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MesaDAOjdbc implements MesaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Convertimos la fila de MySQL a nuestro objeto Mesa
    private RowMapper<Mesa> rowMapper = new RowMapper<Mesa>() {
        @Override
        public Mesa mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Mesa(
                rs.getLong("id"),
                rs.getInt("numero"),
                rs.getInt("capacidad"),
                rs.getString("estado")
            );
        }
    };

    @Override
    public List<Mesa> findAll() {
        return jdbcTemplate.query("SELECT * FROM mesas", rowMapper);
    }

    @Override
    public Mesa findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM mesas WHERE id = ?", rowMapper, id);
    }

    @Override
    public int save(Mesa mesa) {
        return jdbcTemplate.update("INSERT INTO mesas (numero, capacidad, estado) VALUES (?, ?, ?)",
                mesa.getNumero(), mesa.getCapacidad(), mesa.getEstado());
    }

    @Override
    public int update(Mesa mesa) {
        return jdbcTemplate.update("UPDATE mesas SET numero = ?, capacidad = ?, estado = ? WHERE id = ?",
                mesa.getNumero(), mesa.getCapacidad(), mesa.getEstado(), mesa.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM mesas WHERE id = ?", id);
    }
}
