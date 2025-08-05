package com.itau.pedido.infraestructure.persistence

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.itau.pedido.domain.repository.PedidoRepository
import domain.model.Pedido
import org.springframework.stereotype.Repository
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock



@Repository
class PedidoRepositoryImpl : PedidoRepository {

    private val file = File("resources/pedidos.json")
    private val mapper = jacksonObjectMapper()
    private val lock = ReentrantLock()

    override fun salvar(pedido: Pedido) = lock.withLock {
        val pedidos = listar().toMutableList()
        pedidos.add(pedido)
        salvarTodos(pedidos)
    }

    override fun cancelar(id: String) = lock.withLock {
        val pedidos = listar().map {
            if (it.id == id) it.copy( ativo = false ) else it
        }
        salvarTodos(pedidos)
    }

    override fun listar(): List<Pedido> = lock.withLock {
        if (!file.exists()) return emptyList()
        return mapper.readValue(file)
    }

    override fun buscarPorId(id: String): Pedido? = lock.withLock {
        listar().find { it.id == id }
    }

    private fun salvarTodos(pedidos: List<Pedido>) {
        file.writeText(mapper.writeValueAsString(pedidos))
    }
}