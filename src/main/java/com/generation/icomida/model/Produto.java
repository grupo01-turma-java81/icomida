package com.generation.icomida.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "O nome não pode ser null ou conter espaços em branco")
    @Size(min = 3)
    private String nome;
    
    @JsonFormat(shape = Shape.STRING)
    @NotNull(message = "O Preço não pode ser nulo")
    @Positive(message = "O preço precisa ser maior que zero")
    private BigDecimal preco;
    
    @NotBlank
    @Size(min = 3)
    private String produtoSaudavel;
    
    @UpdateTimestamp
    private LocalDateTime data;
    
    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Categoria categoria;
    
    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getProdutoSaudavel() {
        return produtoSaudavel;
    }

    public void setProdutoSaudavel(String produtoSaudavel) {
        this.produtoSaudavel = produtoSaudavel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
}
