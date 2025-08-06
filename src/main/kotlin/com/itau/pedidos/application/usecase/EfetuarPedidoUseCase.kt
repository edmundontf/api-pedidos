package com.itau.pedidos.application.usecase

import com.itau.pedidos.domain.entity.Pedido
import com.itau.pedidos.domain.repository.PedidoRepository
import com.itau.pedidos.domain.repository.ProdutoGateway
import org.springframework.beans.factory.annotation.Qualifier

class EfetuarPedidoUseCase(
    @Qualifier("PedidoRepository")
    private val pedidoRepository: PedidoRepository,
    private val produtoGateway: ProdutoGateway
) {
    fun executar(pedido: Pedido) {
        validarPedido(pedido)
        pedidoRepository.salvar(pedido)
    }

    private fun validarPedido(pedido: Pedido) {
        require(pedido.itens.isNotEmpty()) { "Pedido sem itens." }

        pedido.itens.forEach {
            require(produtoGateway.existeProduto(it.produtoId)) {
                "Produto ${it.descricao} não existe"
            }
            require(it.quantidade > 0) { "Quantidade inválida para ${it.descricao}" }
            require(it.quantidade <= produtoGateway.quantidadeDisponivel(it.produtoId)) {
                "Estoque insuficiente para ${it.descricao}"
            }
        }
    }
}