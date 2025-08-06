package com.itau.pedidos.domain.repository

import com.itau.pedidos.domain.entity.Pedido
import java.util.*

interface PedidoRepository {
    fun salvar(pedido: Pedido)
    fun buscarTodos(): List<Pedido>
    fun buscarPorId(id: UUID): Pedido?
    fun atualizar(pedido: Pedido)
}
