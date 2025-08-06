
package com.itau.pedido.application.dto.request

data class PedidoRequestDTO(
    val produtoId: String,
    val descricao: String,
    val quantidade: Int
)

// Facilita validação de entrada do cliente.