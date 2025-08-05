package com.itau.pedido.domain.repository

import domain.model.Pedido

interface PedidoRepository {
    fun salvar(pedido: Pedido)
    fun cancelar(id: String)
    fun listar(): List<Pedido>
    fun buscarPorId(id: String): Pedido?
}