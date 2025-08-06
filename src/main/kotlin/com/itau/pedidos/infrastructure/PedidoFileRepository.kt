package com.itau.pedidos.infrastructure

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.itau.pedidos.domain.entity.Pedido
import com.itau.pedidos.domain.repository.PedidoRepository
import org.springframework.stereotype.Repository
import java.io.File
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Repository("pedidoFileRepository")
class PedidoFileRepository : PedidoRepository {

    val objectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    private val lock = ReentrantLock()

    private val caminhoArquivo = "data/pedidos.json"
    private val arquivo = File(caminhoArquivo)

    init {
        // Cria o arquivo e diretório se não existirem
        if (!arquivo.exists()) {
            arquivo.parentFile.mkdirs()
            arquivo.writeText("[]")
        }
    }

    override fun salvar(pedido: Pedido) {
        println("Salvando pedido: $pedido")
        val pedidos = listarTodos().toMutableList() // Carrega os existentes
        pedidos.add(pedido)                         // Adiciona o novo
        arquivo.writeText(objectMapper.writeValueAsString(pedidos)) // Salva todos de volta

        println("Pedido salvo. Total de pedidos: ${pedidos.size}")
        println("Arquivo: ${arquivo.absolutePath}")
    }

    override fun buscarPorId(id: UUID): Pedido? {
        lock.withLock {
            return listarTodos().find { it.id == id }
        }
    }

    override fun buscarTodos(): List<Pedido> {
        lock.withLock {
            return listarTodos()
        }
    }

    override fun atualizar(pedido: Pedido) {
        lock.withLock {
            val pedidos = listarTodos().toMutableList()
            val index = pedidos.indexOfFirst { it.id == pedido.id }
            if (index != -1) {
                pedidos[index] = pedido
                salvarTodos(pedidos)
            } else {
                throw NoSuchElementException("Pedido com ID ${pedido.id} não encontrado")
            }
        }
    }

    private fun listarTodos(): List<Pedido> {
        return try {
            objectMapper.readValue(arquivo, object : TypeReference<List<Pedido>>() {})
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun salvarTodos(pedidos: List<Pedido>) {
        try {
            objectMapper.writeValue(arquivo, pedidos)
        } catch (e: Exception) {
            throw RuntimeException("Erro ao salvar pedidos no arquivo", e)
        }
    }
}