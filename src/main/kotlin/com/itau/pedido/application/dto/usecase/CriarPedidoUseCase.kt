package com.itau.pedido.application.dto.usecase

import com.itau.pedido.application.dto.request.PedidoRequestDTO
import com.itau.pedido.domain.repository.PedidoRepository
import com.itau.pedido.domain.repository.ProdutoRepository
import domain.model.Pedido
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Service
class CriarPedidoUseCase(
    private val pedidoRepository: PedidoRepository,
    private val produtoRepository: ProdutoRepository
) {

    fun executar(dto: PedidoRequestDTO): Pedido {
        val produto = produtoRepository.buscarPorId(dto.produtoId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Produto com ID ${dto.produtoId} não encontrado")


        if (dto.quantidade > produto.estoque) {
            throw IllegalArgumentException("Quantidade solicitada maior que estoque disponível")
        }

        val pedido = Pedido(
            id = UUID.randomUUID().toString(),
            descricao = dto.descricao,
            quantidade = dto.quantidade,
            precoUnitario = produto.precoUnitario,
            ativo = true,
        )

        pedidoRepository.salvar(pedido)

        // Atualiza estoque do produto após pedido
        val novoEstoque = produto.estoque - dto.quantidade
        produtoRepository.atualizarEstoque(produto.id, novoEstoque)

        return pedido
    }
}