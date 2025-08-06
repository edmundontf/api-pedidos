package com.itau.pedidos.application.usecase

import com.itau.pedidos.domain.entity.Pedido
import com.itau.pedidos.domain.entity.StatusPedido
import com.itau.pedidos.domain.repository.PedidoRepository

class ListarPedidosUseCase(
    private val pedidoRepository: PedidoRepository
) {
    fun executar(): List<Pedido> {
        return pedidoRepository.buscarTodos()
            .filter { it.status == StatusPedido.ATIVO }
    }
}