package com.itau.pedidos.adapters.dto

import java.math.BigDecimal
import java.util.*

data class PedidoRequestDTO(
    val itens: List<ItemPedidoDTO>
)

data class ItemPedidoDTO(
    val produtoId: UUID,
    val descricao: String,
    val quantidade: Int,
    val precoUnitario: BigDecimal
)
