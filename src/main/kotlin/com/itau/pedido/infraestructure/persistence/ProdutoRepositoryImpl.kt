package com.itau.pedido.infraestructure.persistence

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.itau.pedido.domain.repository.ProdutoRepository
import domain.model.Produto
import org.springframework.stereotype.Repository
import java.io.File




@Repository
class ProdutoRepositoryImpl : ProdutoRepository {

    private val file = File("produtos.json")
    private val mapper = jacksonObjectMapper()

    override fun buscarPorId(id: String): Produto? {
        return listarTodos().find { it.id == id }
    }

    override fun atualizarEstoque(id: String, novaQuantidade: Int) {
        val produtosAtualizados = listarTodos().map {
            if (it.id == id) it.copy(estoque = novaQuantidade) else it
        }
        file.writeText(mapper.writeValueAsString(produtosAtualizados))
    }

    private fun listarTodos(): List<Produto> {
        if (!file.exists()) return emptyList()
        return mapper.readValue(file)
    }
}