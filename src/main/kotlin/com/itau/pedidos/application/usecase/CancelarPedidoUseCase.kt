package com.itau.pedidos.application.usecase

import com.itau.pedidos.domain.entity.StatusPedido
import com.itau.pedidos.domain.repository.PedidoRepository
import java.util.*

class CancelarPedidoUseCase(
    private val pedidoRepository: PedidoRepository
) {
    fun executar(id: UUID) {
        val pedido = pedidoRepository.buscarPorId(id)
            ?: throw IllegalArgumentException("Pedido não encontrado")

        if (pedido.status == StatusPedido.CANCELADO) {
            throw IllegalStateException("Pedido já cancelado")
        }

        pedido.status = StatusPedido.CANCELADO
        pedidoRepository.atualizar(pedido)
    }
}