package com.itau.pedidos.adapters.dto

import com.itau.pedidos.domain.repository.ProdutoGateway
import java.util.*


class FakeProdutoGateway : ProdutoGateway {

    private val estoque = mapOf(
        UUID.fromString("00000000-0000-0000-0000-000000000001") to 10,
        UUID.fromString("00000000-0000-0000-0000-000000000002") to 5
    )

    override fun existeProduto(id: UUID): Boolean = estoque.containsKey(id)

    override fun quantidadeDisponivel(id: UUID): Int = estoque[id] ?: 0
}
