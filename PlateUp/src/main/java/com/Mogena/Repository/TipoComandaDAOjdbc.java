/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.TipoComanda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TipoComandaDAOjdbc implements TipoComandaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<TipoComanda> rowMapper = new RowMapper<TipoComanda>() {
        @Override
        public TipoComanda mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TipoComanda(
                rs.getLong("id"),
                rs.getString("nombre")
            );
        }
    };

    @Override
    public List<TipoComanda> findAll() {
        return jdbcTemplate.query("SELECT * FROM tipos_comanda", rowMapper);
    }

    @Override
    public TipoComanda findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tipos_comanda WHERE id = ?", rowMapper, id);
    }

    @Override
    public int save(TipoComanda tipoComanda) {
        return jdbcTemplate.update("INSERT INTO tipos_comanda (nombre) VALUES (?)",
                tipoComanda.getNombre());
    }

    @Override
    public int update(TipoComanda tipoComanda) {
        return jdbcTemplate.update("UPDATE tipos_comanda SET nombre = ? WHERE id = ?",
                tipoComanda.getNombre(), tipoComanda.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM tipos_comanda WHERE id = ?", id);
    }
}
