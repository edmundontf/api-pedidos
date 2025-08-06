package com.itau.pedidos.domain.repository

import java.util.*

interface ProdutoGateway {
    fun existeProduto(id: UUID): Boolean
    fun quantidadeDisponivel(id: UUID): Int
}