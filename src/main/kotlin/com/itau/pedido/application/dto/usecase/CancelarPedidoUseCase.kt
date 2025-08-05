package com.itau.pedido.application.dto.usecase

import com.itau.pedido.domain.repository.PedidoRepository
import org.springframework.stereotype.Service

@Service
class CancelarPedidoUseCase(
    private val pedidoRepository: PedidoRepository
) {
    fun executar(id: String) {
        val pedido = pedidoRepository.buscarPorId(id)
            ?: throw IllegalArgumentException("Pedido não encontrado")
        
        if (pedido.ativo == false) {
            throw IllegalStateException("Pedido já está cancelado")
        }

        pedidoRepository.cancelar(id)
    }
}