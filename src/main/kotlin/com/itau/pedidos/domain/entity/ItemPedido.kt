package com.itau.pedidos.domain.entity

import java.math.BigDecimal
import java.util.*

data class ItemPedido(
    val produtoId: UUID,
    val descricao: String,
    val quantidade: Int,
    val precoUnitario: BigDecimal
)
