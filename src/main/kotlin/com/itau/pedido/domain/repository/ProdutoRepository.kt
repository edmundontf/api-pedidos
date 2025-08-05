package com.itau.pedido.domain.repository

import domain.model.Produto

interface ProdutoRepository {
    fun buscarPorId(id: String): Produto?
    fun atualizarEstoque(id: String, novaQuantidade: Int)
}