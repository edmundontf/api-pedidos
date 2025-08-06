package com.itau.pedido.interfaces.controller

import com.itau.pedido.application.dto.request.PedidoRequestDTO
import com.itau.pedido.application.dto.response.PedidoResponseDTO
import com.itau.pedido.application.dto.usecase.CancelarPedidoUseCase
import com.itau.pedido.application.dto.usecase.CriarPedidoUseCase
import com.itau.pedido.application.dto.usecase.ListarPedidosUseCase
import domain.model.Pedido
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/pedidos")
class PedidoController(
    private val criarPedidoUseCase: CriarPedidoUseCase,
    private val cancelarPedidoUseCase: CancelarPedidoUseCase,
    private val listarPedidosUseCase: ListarPedidosUseCase
) {

    @PostMapping
    fun criarPedido(@RequestBody pedidoRequestDTO: PedidoRequestDTO): ResponseEntity<PedidoResponseDTO> {
        val pedido = criarPedidoUseCase.executar(pedidoRequestDTO)
        val responseDTO = PedidoResponseDTO(
            id = pedido.id,
            descricao = pedido.descricao,
            quantidade = pedido.quantidade,
            precoUnitario = pedido.precoUnitario,
            ativo = pedido.ativo.let { if (it) "Ativo" else "Cancelado" } ?: "Desconhecido"
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO)
    }

    @DeleteMapping("/{id}")
    fun cancelarPedido(@PathVariable id: String): ResponseEntity<String> {
        cancelarPedidoUseCase.executar(id)
        return ResponseEntity.ok("Pedido cancelado com sucesso.")
    }

    @GetMapping
    fun listarPedidos(): ResponseEntity<List<PedidoResponseDTO>> {
        val pedidos: List<Pedido> = listarPedidosUseCase.executar()
        val responseDTOs = pedidos.map { pedido ->
            PedidoResponseDTO(
                id = pedido.id,
                descricao = pedido.descricao,
                quantidade = pedido.quantidade,
                precoUnitario = pedido.precoUnitario,
                ativo = pedido.ativo.let { if (it) "Ativo" else "Cancelado" } ?: "Desconhecido"
            )
        }
        return ResponseEntity.ok(responseDTOs)
    }
}