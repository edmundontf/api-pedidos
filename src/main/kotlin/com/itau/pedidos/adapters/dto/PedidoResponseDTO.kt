package com.itau.pedidos.adapters.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class PedidoResponseDTO(
    val id: UUID,
    val dataCriacao: LocalDateTime,
    val valorTotal: BigDecimal,
    val status: String,
    val itens: List<ItemPedidoDTO>
)