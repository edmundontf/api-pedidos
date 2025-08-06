package com.itau.pedido.application.dto.usecase

import com.itau.pedido.domain.repository.PedidoRepository
import domain.model.Pedido
import org.springframework.stereotype.Service


@Service
class ListarPedidosUseCase(
    private val pedidoRepository: PedidoRepository
) {
    fun executar(): List<Pedido> {
        return pedidoRepository.listar().filter { it.ativo }
    }
}