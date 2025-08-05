package com.itau.pedido.application.dto.response


data class PedidoResponseDTO(
    val id: String,
    val descricao: String,
    val quantidade: Int,
    val precoUnitario: Double,
    val ativo: String
)

