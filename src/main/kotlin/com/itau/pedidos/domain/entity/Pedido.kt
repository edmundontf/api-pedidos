package com.itau.pedidos.domain.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Pedido(
    val id: UUID = UUID.randomUUID(),
    val itens: List<ItemPedido>,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    var status: StatusPedido = StatusPedido.ATIVO
)
{
    val valorTotal: BigDecimal
        get() = itens.fold(BigDecimal.ZERO) { acc, item ->
            acc + item.precoUnitario.multiply(BigDecimal(item.quantidade))
        }
}