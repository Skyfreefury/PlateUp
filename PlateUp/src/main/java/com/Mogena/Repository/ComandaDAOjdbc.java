/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Comanda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ComandaDAOjdbc implements ComandaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Comanda> rowMapper = new RowMapper<Comanda>() {
        @Override
        public Comanda mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Comanda(
                rs.getLong("id"),
                rs.getObject("pedido_id", Long.class), // Clave foránea
                rs.getTimestamp("fecha_hora") != null ? rs.getTimestamp("fecha_hora").toLocalDateTime() : null,
                rs.getString("estado")
            );
        }
    };

    @Override
    public List<Comanda> findAll() {
        return jdbcTemplate.query("SELECT * FROM comandas", rowMapper);
    }

    @Override
    public Comanda findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM comandas WHERE id = ?", rowMapper, id);
    }

    @Override
    public int save(Comanda comanda) {
        return jdbcTemplate.update("INSERT INTO comandas (pedido_id, fecha_hora, estado) VALUES (?, ?, ?)",
                comanda.getPedidoId(), comanda.getFechaHora(), comanda.getEstado());
    }

    @Override
    public int update(Comanda comanda) {
        return jdbcTemplate.update("UPDATE comandas SET pedido_id = ?, fecha_hora = ?, estado = ? WHERE id = ?",
                comanda.getPedidoId(), comanda.getFechaHora(), comanda.getEstado(), comanda.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM comandas WHERE id = ?", id);
    }
}
