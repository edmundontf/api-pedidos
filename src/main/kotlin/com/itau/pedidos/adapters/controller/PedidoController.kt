package com.itau.pedidos.adapters.controller

import com.itau.pedidos.adapters.dto.ItemPedidoDTO
import com.itau.pedidos.adapters.dto.PedidoRequestDTO
import com.itau.pedidos.adapters.dto.PedidoResponseDTO
import com.itau.pedidos.application.usecase.CancelarPedidoUseCase
import com.itau.pedidos.application.usecase.EfetuarPedidoUseCase
import com.itau.pedidos.application.usecase.ListarPedidosUseCase
import com.itau.pedidos.domain.entity.ItemPedido
import com.itau.pedidos.domain.entity.Pedido
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/pedidos")
class PedidoController(
    private val efetuarPedidoUseCase: EfetuarPedidoUseCase,
    private val cancelarPedidoUseCase: CancelarPedidoUseCase,
    private val listarPedidosUseCase: ListarPedidosUseCase
) {
    @PostMapping
    fun efetuarPedido(@RequestBody dto: PedidoRequestDTO) {
        val itens = dto.itens.map {
            ItemPedido(
                produtoId = it.produtoId,
                descricao = it.descricao,
                quantidade = it.quantidade,
                precoUnitario = it.precoUnitario
            )
        }
        val pedido = Pedido(itens = itens)
        efetuarPedidoUseCase.executar(pedido)
    }

    @DeleteMapping("/{id}")
    fun cancelarPedido(@PathVariable id: UUID) {
        cancelarPedidoUseCase.executar(id)
    }

    @GetMapping
    fun listarPedidos(): List<PedidoResponseDTO> {
        return listarPedidosUseCase.executar().map { pedido ->
            PedidoResponseDTO(
                id = pedido.id,
                dataCriacao = pedido.dataCriacao,
                valorTotal = pedido.valorTotal,
                status = pedido.status.name,
                itens = pedido.itens.map {
                    ItemPedidoDTO(
                        produtoId = it.produtoId,
                        descricao = it.descricao,
                        quantidade = it.quantidade,
                        precoUnitario = it.precoUnitario
                    )
                }
            )
        }
    }
}