package com.itau.pedidos.config

import com.itau.pedidos.adapters.dto.FakeProdutoGateway
import com.itau.pedidos.application.usecase.CancelarPedidoUseCase
import com.itau.pedidos.application.usecase.EfetuarPedidoUseCase
import com.itau.pedidos.application.usecase.ListarPedidosUseCase
import com.itau.pedidos.infrastructure.PedidoFileRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {

    @Bean
    fun produtoGateway() = FakeProdutoGateway()

    @Bean
    fun pedidoRepository() = PedidoFileRepository()

    @Bean
    fun efetuarPedidoUseCase() = EfetuarPedidoUseCase(pedidoRepository(), produtoGateway())

    @Bean
    fun cancelarPedidoUseCase() = CancelarPedidoUseCase(pedidoRepository())

    @Bean
    fun listarPedidosUseCase() = ListarPedidosUseCase(pedidoRepository())
}